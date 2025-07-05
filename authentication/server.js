const express = require('express');
const axios = require('axios');
const qs = require('querystring');

require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Keycloak configuration
const KEYCLOAK_CONFIG = {
    serverUrl: process.env.KEYCLOAK_SERVER_URL,
    realm: process.env.KEYCLOAK_REALM,
    clientId: process.env.KEYCLOAK_CLIENT_ID,
    clientSecret: process.env.KEYCLOAK_CLIENT_SECRET,
    username: process.env.KEYCLOAK_USERNAME,
    password: process.env.KEYCLOAK_PASSWORD
};

// Helper function to get token URL
const getTokenUrl = () => {
    return `${KEYCLOAK_CONFIG.serverUrl}/realms/${KEYCLOAK_CONFIG.realm}/protocol/openid-connect/token`;
};

// Route: Get token using client credentials flow
app.post('/token/client-credentials', async (req, res) => {
    try {
        const tokenUrl = getTokenUrl();

        const data = qs.stringify({
            grant_type: 'client_credentials',
            client_id: KEYCLOAK_CONFIG.clientId,
            client_secret: KEYCLOAK_CONFIG.clientSecret
        });

        const response = await axios.post(tokenUrl, data, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        res.json({
            success: true,
            token: response.data.access_token,
            tokenType: response.data.token_type,
            expiresIn: response.data.expires_in,
            refreshToken: response.data.refresh_token || null
        });

    } catch (error) {
        console.error('Error getting client credentials token:', error.response?.data || error.message);
        res.status(500).json({
            success: false,
            error: 'Failed to get token',
            details: error.response?.data || error.message
        });
    }
});

// Route: Get token using password flow (Resource Owner Password Credentials)
app.post('/token/password', async (req, res) => {
    try {
        const { username, password } = req.body;
        const tokenUrl = getTokenUrl();

        const data = qs.stringify({
            grant_type: 'password',
            client_id: KEYCLOAK_CONFIG.clientId,
            client_secret: KEYCLOAK_CONFIG.clientSecret,
            username: username || KEYCLOAK_CONFIG.username,
            password: password || KEYCLOAK_CONFIG.password
        });

        const response = await axios.post(tokenUrl, data, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        res.json({
            success: true,
            token: response.data.access_token,
            tokenType: response.data.token_type,
            expiresIn: response.data.expires_in,
            refreshToken: response.data.refresh_token,
            scope: response.data.scope
        });

    } catch (error) {
        console.error('Error getting password token:', error.response?.data || error.message);
        res.status(500).json({
            success: false,
            error: 'Failed to get token',
            details: error.response?.data || error.message
        });
    }
});

// Route: Refresh token
app.post('/token/refresh', async (req, res) => {
    try {
        const { refreshToken } = req.body;

        if (!refreshToken) {
            return res.status(400).json({
                success: false,
                error: 'Refresh token is required'
            });
        }

        const tokenUrl = getTokenUrl();

        const data = qs.stringify({
            grant_type: 'refresh_token',
            client_id: KEYCLOAK_CONFIG.clientId,
            client_secret: KEYCLOAK_CONFIG.clientSecret,
            refresh_token: refreshToken
        });

        const response = await axios.post(tokenUrl, data, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        res.json({
            success: true,
            token: response.data.access_token,
            tokenType: response.data.token_type,
            expiresIn: response.data.expires_in,
            refreshToken: response.data.refresh_token
        });

    } catch (error) {
        console.error('Error refreshing token:', error.response?.data || error.message);
        res.status(500).json({
            success: false,
            error: 'Failed to refresh token',
            details: error.response?.data || error.message
        });
    }
});

// Route: Validate token
app.post('/token/validate', async (req, res) => {
    try {
        const { token } = req.body;

        if (!token) {
            return res.status(400).json({
                success: false,
                error: 'Token is required'
            });
        }

        const introspectUrl = `${KEYCLOAK_CONFIG.serverUrl}/realms/${KEYCLOAK_CONFIG.realm}/protocol/openid-connect/token/introspect`;

        const data = qs.stringify({
            token: token,
            client_id: KEYCLOAK_CONFIG.clientId,
            client_secret: KEYCLOAK_CONFIG.clientSecret
        });

        const response = await axios.post(introspectUrl, data, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        res.json({
            success: true,
            valid: response.data.active,
            tokenInfo: response.data
        });

    } catch (error) {
        console.error('Error validating token:', error.response?.data || error.message);
        res.status(500).json({
            success: false,
            error: 'Failed to validate token',
            details: error.response?.data || error.message
        });
    }
});

// Route: Get user info using token
app.get('/userinfo', async (req, res) => {
    try {
        const authHeader = req.headers.authorization;

        if (!authHeader || !authHeader.startsWith('Bearer ')) {
            return res.status(401).json({
                success: false,
                error: 'Bearer token is required'
            });
        }

        const token = authHeader.substring(7);
        const userInfoUrl = `${KEYCLOAK_CONFIG.serverUrl}/realms/${KEYCLOAK_CONFIG.realm}/protocol/openid-connect/userinfo`;

        const response = await axios.get(userInfoUrl, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        res.json({
            success: true,
            userInfo: response.data
        });

    } catch (error) {
        console.error('Error getting user info:', error.response?.data || error.message);
        res.status(500).json({
            success: false,
            error: 'Failed to get user info',
            details: error.response?.data || error.message
        });
    }
});

// Health check route
app.get('/health', (req, res) => {
    res.json({
        status: 'OK',
        timestamp: new Date().toISOString(),
        keycloakConfig: {
            serverUrl: KEYCLOAK_CONFIG.serverUrl,
            realm: KEYCLOAK_CONFIG.realm,
            clientId: KEYCLOAK_CONFIG.clientId
        }
    });
});

// Start server
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
    console.log(`Keycloak Server: ${KEYCLOAK_CONFIG.serverUrl}`);
    console.log(`Realm: ${KEYCLOAK_CONFIG.realm}`);
    console.log(`Client ID: ${KEYCLOAK_CONFIG.clientId}`);
    console.log('\nAvailable endpoints:');
    console.log('POST /token/client-credentials - Get token using client credentials');
    console.log('POST /token/password - Get token using username/password');
    console.log('POST /token/refresh - Refresh an existing token');
    console.log('POST /token/validate - Validate a token');
    console.log('GET /userinfo - Get user info (requires Bearer token)');
    console.log('GET /health - Health check');
});

module.exports = app;