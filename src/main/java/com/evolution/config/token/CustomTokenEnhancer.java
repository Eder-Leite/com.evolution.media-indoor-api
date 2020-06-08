package com.evolution.config.token;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.evolution.security.SystemUser;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		SystemUser user = (SystemUser) authentication.getPrincipal();

		Map<String, Object> addInfo = new HashMap<>();

		addInfo.put("id", user.getUser().getId());
		addInfo.put("name", user.getUser().getName());
		addInfo.put("email", user.getUser().getEmail());
		addInfo.put("type", user.getUser().getTypeUser());
		addInfo.put("status", user.getUser().getStatus());
		addInfo.put("updateDate", user.getUser().getUpdateDate());
		addInfo.put("creationDate", user.getUser().getCreationDate());

		Object obj = accessToken.getRefreshToken();

		Class<?> classe = obj.getClass();
		Field[] fields = classe.getDeclaredFields();

		for (Field field : fields) {
			try {
				if (field.getName().equals("expiration")) {
					field.setAccessible(true);

					Date dataRefreshToken = (Date) field.get(obj);

					addInfo.put("refreshTokenExpires_in", (dataRefreshToken.getTime() - new Date().getTime()) / 1000);

					addInfo.put("expRefresh", dataRefreshToken.getTime() / 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}

	public void printAttributesAndValuesPojo(Object object) {
		Class<?> classe = object.getClass();
		Field[] fields = classe.getDeclaredFields();

		String name = "";
		Object value = null;
		for (Field field : fields) {
			try {
				name = field.getName();
				field.setAccessible(true);
				value = field.get(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(name + ": " + value);
		}
	}

}