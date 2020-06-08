package com.evolution.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.evolution.config.token.CustomTokenEnhancer;


@Profile("oauth-security")
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	
	@Autowired 
	public AuthenticationManager authenticationManager;


	@Autowired
	private UserDetailsService userDetailsService;
	 
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		 clients.inMemory()
		        .withClient("servidor")
		        .secret("$2a$10$RaI5.M7R2YSJMRkZnZF6/uQ5C8X2BPkOMRwqpOSNGCSY10BjVNjzS")
		        .scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600 * 24)
				.refreshTokenValiditySeconds(3600 * 168)
				.and()
				.withClient("mobile")
				.secret("$2a$10$mZECxCVnrwX4SM54zQqb1.4wsJ60vAfK8lXeNDFHvQ2K8YWZMgfGq")
				.scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(3600 * 24)
				.refreshTokenValiditySeconds(3600 * 168);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

		endpoints
		        .tokenStore(tokenStore())
		        .tokenEnhancer(tokenEnhancerChain)
		        .reuseRefreshTokens(false)
		        .userDetailsService(userDetailsService)
				.authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("MediaIndoorApp");
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}
