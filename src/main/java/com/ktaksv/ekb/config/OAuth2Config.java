package com.ktaksv.ekb.config;

import com.ktaksv.ekb.model.mapper.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private String clientid = "ekb";
    private String clientSecret = "1234567";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpQIBAAKCAQEAwHCMz1PH4dDJfQHl3VMCSMQmAKZEdN0M2uBOYtBAf8uPUkSp\n" +
            "BAzVwT8S80hpXojutivKIYVvs8DzvNJIFhJaFNYpn1FgQhKd2hCtt5+3rmXg7wo7\n" +
            "F60XEK1abTUU8WcjuTcRe5QHPMUhHbNMYuPWpembwkS8gbWWp3rXKGQXT6AQyx3I\n" +
            "9PMeZDwAcbPOzWjf3Wx4UP3IzqKjUiJ0QRDOdv2ZofrcxB4tYyGAD+gTIadl3vTB\n" +
            "FX5T/SPb6TtEdUOJ1LxWlRP6w7pnq2+pnP9GL23cRCYOYlbDdUxUjOzfmuOZwoie\n" +
            "eEV9Tc+JM0+7yHSleRZPbeg4yjzBf6rWxnHZ1QIDAQABAoIBAFAQA2VWk/htJwbt\n" +
            "PTchi/e+M5ldk2mTdf+yBqqxvcPtaslta2wV8vCdT0MMQ1pjWf1MpqHfpCODCWTr\n" +
            "j7YvGG9G0rZH4uZaXHYXLk8REhhqpnSNKxyklM4JcW+x/+6XXJN4R7vEUGaGFUsI\n" +
            "B6W06N7KHFVsx4inhfBT+SQC0Hf9hYfnNYsNwIQsKKJmhidkvwXS8c261bobwYJq\n" +
            "zz0jKdLccJFRR1KjqREbAuenqQzAhZ2CDXKVu9Gu27Jlwp8uswiyet0gn0o5UKrR\n" +
            "Xfv54a7SpMpNoNRt3KRXCqHAd7vHUSLkGY99tLg6X53FeKbs+3rTGmXDP3Hq+iDI\n" +
            "7b9r/hkCgYEA4dPsqoVQoNLXPYslh1Ya4zRkv58MxDIxVpfvyalwKdIK+KfoleBN\n" +
            "qu3P4TCzNJv2Nc2CeLBhYtD0tr6i+eFBsaAL0GGEBsvlKd43xZrVP4aqaPNfqn35\n" +
            "iPI8CyYuSBvOyscPCg/rmzdIB4UJUXxDpU96hbF0NWoUdIwfT65rBiMCgYEA2iaj\n" +
            "B/5yoSckO5AEGw6STBq/9KDFvWo8Mv/5TYxIJwAv+1a0XL+LhamAhyFbIdGPI85M\n" +
            "ripiHcaWyV4u3Lyq+cSIiAH+SdTwFeWviUxbbx1L5RBEyva497eDcV9Hgbr97anf\n" +
            "gJT5dNHUSodM8ivXJO59czyY61j/wi82I6lG06cCgYEAufzHIt1vPTTIbnhmLSMe\n" +
            "O9ePzj2YzxEjj1TM+QJiTR8fSBFJvf4hGVCI78cjIIX+betWtD1Xx4Geuc8h/Lin\n" +
            "WTod4L4fdVWB5EyPFGDjllE7kfo25Pyhgkc2alxEq8CzPFBbM4dPHOOXoBHSTdkR\n" +
            "AVoG54rx0XgPZrlspDxbCo8CgYEAjVQept0nslPDW3svzb+g9AEYfwlxUgdzPK8s\n" +
            "+hp53Bp4+2Hus2+8mjl39ROdEMnM7CuI7gSzUAsKQSbtQUBRnELBGSwBbB+x8t28\n" +
            "3TxEuDXIp3vktYEl0AZp95b8ooVcYQ5+p7Tb79zIpggFx5azaU/AolmN0Fr2Rv2D\n" +
            "G5NdOV0CgYEA0tl1y+g4K8M8CPxRFxNrIN0dP3LCaFqul4O4H0ztkB3zawqO0eZy\n" +
            "mj30V42qIHCHN8Cb0hIXGfyOp/4akZdZUUXjNxuqc2QqPKFRfzHgQnDDxV/V7uXt\n" +
            "Di3LPVdd6VgbvdJfj9CAvrGeTfOnblCB6PXQrNndob9Hl7krzvB3s6c=\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwHCMz1PH4dDJfQHl3VMC\n" +
            "SMQmAKZEdN0M2uBOYtBAf8uPUkSpBAzVwT8S80hpXojutivKIYVvs8DzvNJIFhJa\n" +
            "FNYpn1FgQhKd2hCtt5+3rmXg7wo7F60XEK1abTUU8WcjuTcRe5QHPMUhHbNMYuPW\n" +
            "pembwkS8gbWWp3rXKGQXT6AQyx3I9PMeZDwAcbPOzWjf3Wx4UP3IzqKjUiJ0QRDO\n" +
            "dv2ZofrcxB4tYyGAD+gTIadl3vTBFX5T/SPb6TtEdUOJ1LxWlRP6w7pnq2+pnP9G\n" +
            "L23cRCYOYlbDdUxUjOzfmuOZwoieeEV9Tc+JM0+7yHSleRZPbeg4yjzBf6rWxnHZ\n" +
            "1QIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserModelMapper mapper;

    @Autowired
    private CustomUserDetailService service;

    @Bean
    public JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenConverter(mapper), tokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(service)
                .accessTokenConverter(tokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(clientid)
                .secret(encoder.encode(clientSecret))
                .scopes("read", "write", "openid")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);
    }
}
