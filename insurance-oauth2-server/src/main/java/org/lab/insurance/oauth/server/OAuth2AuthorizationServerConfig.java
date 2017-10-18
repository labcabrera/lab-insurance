package org.lab.insurance.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final int ONE_HOUR = 3600;
	private static final int TREINTA_DAYS = 2592000;

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception { // @formatter:off
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	} //@formatter:on

	// TODO mongodb
	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception { // @formatter:off
		clients.inMemory()
		
			.withClient("lab-insurance-app")
				.authorizedGrantTypes("implicit")
				.scopes("read", "write", "foo", "bar")
				.autoApprove(false)
				.accessTokenValiditySeconds(3600)

			.and()
			.withClient("lab-insurance-mobile")
				.secret("secret")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.scopes("foo", "read", "write")
				.accessTokenValiditySeconds(ONE_HOUR)
				.refreshTokenValiditySeconds(TREINTA_DAYS)

			.and()
			.withClient("lab-insurance-thirdparty")
				.secret("secret")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.scopes("bar", "read", "write")
				.accessTokenValiditySeconds(ONE_HOUR)
				.refreshTokenValiditySeconds(TREINTA_DAYS)
		;
	} // @formatter:on

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception { // @formatter:off
		endpoints.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.authenticationManager(authenticationManager);
	} // @formatter:on

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final ClassPathResource resource = new ClassPathResource(env.getProperty("security.keystore.file"));
		final char[] password = env.getProperty("security.keystore.password").toCharArray();
		final String alias = env.getProperty("security.keystore.alias");

		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, password);
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
		return converter;
	}
}
