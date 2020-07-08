package com.ktaksv.ekb.config;

import com.ktaksv.ekb.model.UserModel;
import com.ktaksv.ekb.model.mapper.UserModelMapper;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenConverter extends JwtAccessTokenConverter {

    public CustomTokenConverter(UserModelMapper mapper) {
        this.mapper = mapper;
    }

    private final UserModelMapper mapper;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {

        UserModel principal = (UserModel) authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("userData", mapper.mapToToken(principal));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

        accessToken = super.enhance(accessToken, authentication);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());
        return accessToken;
    }
}
