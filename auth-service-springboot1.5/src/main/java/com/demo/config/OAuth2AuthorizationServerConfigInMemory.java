package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigInMemory extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {// @formatter:off
        clients
                .inMemory()
                .withClient("qa-ui")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code", "implicit")
                .authorities(Role.ROLE_TRUSTED_CLIENT.toString())
                .scopes("read", "write")
                .accessTokenValiditySeconds(86400)
                .refreshTokenValiditySeconds(30000)
                .secret("secret")
                .autoApprove(true)
                .redirectUris("http://mt-qa.techolution.com:4201/callback")

                .and()

                .withClient("prod-ui")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code", "implicit")
                .authorities(Role.ROLE_TRUSTED_CLIENT.toString())
                .scopes("read", "write")
                .accessTokenValiditySeconds(86400)
                .refreshTokenValiditySeconds(30000)
                .secret("secret")
                .autoApprove(true)
                .redirectUris("http://sm04.telecom.mu/callback")

                .and()

                .withClient("local-ui")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code", "implicit")
                .authorities(Role.ROLE_TRUSTED_CLIENT.toString())
                .scopes("read", "write")
                .accessTokenValiditySeconds(86400)
                .refreshTokenValiditySeconds(30000)
                .secret("secret")
                .autoApprove(true)
                .redirectUris("http://localhost:4200/callback")

                .and()
                .withClient("pr-ui")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code", "implicit")
                .authorities(Role.ROLE_TRUSTED_CLIENT.toString())
                .scopes("read", "write")
                .accessTokenValiditySeconds(86400)
                .refreshTokenValiditySeconds(30000)
                .secret("secret")
                .autoApprove(true);
    } // @formatter:on

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        /*TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));*/

        endpoints.tokenStore(tokenStore())
                //.tokenEnhancer(tokenEnhancerChain)
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_REGISTER,
        ROLE_TRUSTED_CLIENT,
        ROLE_CLIENT
    }

}
