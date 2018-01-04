package com.jaime.auth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	@Qualifier("userDetailsServiceImp")
	private UserDetailsService userDetailsService;
	
	// clientId: Define el id de la aplicación que está autorizada para autenticar
	// clientSecret: Es la contraseña de la aplicación que está autorizada
	// Ambos datos deben migrase a una base de datos
	// grantType: Define el tipo de clave que se busca en este caso password
	// resourceId: Define el Id del servidor de autorización
	// AuthenticationManager: Se utiliza para verificar las credenciales
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
				.inMemory()
					.withClient(clientId)
					.secret(clientSecret)
					.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
					.scopes(scopeRead, scopeWrite)
					.resourceIds(resourceIds)
					.accessTokenValiditySeconds(9000)
					.refreshTokenValiditySeconds(18000)
				.and()
					.withClient("infinite")
					.secret("test")
					.authorizedGrantTypes("password")
					.scopes("read", "write")
					.accessTokenValiditySeconds(-1);
	}
	
	@Override
	public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore)
				.accessTokenConverter(accessTokenConverter)
				.tokenEnhancer(enhancerChain)
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
	}
}
