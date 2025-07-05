// public/app.js - Add debugging to see what's happening

console.log("Starting Keycloak initialization...");

const keycloak = new Keycloak({
    url: 'http://localhost:18080/',
    realm: 'ms-esprit',
    clientId: 'frontend',
});

// Add event listeners to see what's happening
keycloak.onReady = function (authenticated) {
    console.log("Keycloak ready. Authenticated:", authenticated);
};

keycloak.onAuthSuccess = function () {
    console.log("Authentication successful");
};

keycloak.onAuthError = function () {
    console.log("Authentication error");
};

keycloak.onAuthRefreshSuccess = function () {
    console.log("Token refresh successful");
};

keycloak.onAuthRefreshError = function () {
    console.log("Token refresh error");
};

keycloak.onAuthLogout = function () {
    console.log("User logged out");
};

// Try without login-required first
keycloak.init({
    onLoad: 'check-sso',  // Changed from 'login-required'
    checkLoginIframe: false,
    pkceMethod: 'S256'
}).then(function (authenticated) {
    console.log("Init completed. Authenticated:", authenticated);

    if (authenticated) {
        console.log("User is authenticated:", keycloak.tokenParsed.preferred_username);
        fetchProducts();
    } else {
        console.log("User is not authenticated. Showing login button.");
        showLoginButton();
    }
}).catch(function (error) {
    console.error("Keycloak init failed:", error);
});

function showLoginButton() {
    const button = document.createElement('button');
    button.textContent = 'Login';
    button.onclick = function () {
        keycloak.login();
    };
    document.body.insertBefore(button, document.getElementById('product-list'));
}

function fetchProducts() {
    fetch('http://localhost:8081/api/products', {
        // headers: {
        //     Authorization: `Bearer ${keycloak.token}`
        // }
    })
        .then(res => res.json())
        .then(products => {
            const list = document.getElementById('product-list');
            list.innerHTML = '';
            console.log("Fetched products:", list);
            products.forEach(p => {
                const li = document.createElement('li');
                li.innerText = `${p.name} (${p.category}) - ${p.price} DT [${p.manufacturer?.name || "Unknown"}]`;
                list.appendChild(li);
            });
        })
        .catch(err => {
            console.error("Fetch error:", err);
        });
}

function logout() {
    keycloak.logout({
        redirectUri: 'http://localhost:3000'
    });
}

fetchProducts();