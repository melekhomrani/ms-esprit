package com.store.product.feignClient;

// import feign.RequestInterceptor;
// import feign.RequestTemplate;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.core.AbstractOAuth2Token;


// @Configuration
// public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
//     private static final String AUTHORIZATION_HEADER = "Authorization";
//     private static final String TOKEN_TYPE = "Bearer";

//     @Override
//     public void apply(RequestTemplate template) {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         AbstractOAuth2Token accessToken = (AbstractOAuth2Token) authentication.getCredentials();
//         template.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, accessToken.getTokenValue()));
//     }
// }

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            String token = jwtAuth.getToken().getTokenValue();
            template.header("Authorization", "Bearer " + token);
        }
    }
}