package util;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class FacebookUtils
{
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final ResourceBundle PROPS = ResourceBundle
			.getBundle("facebook");

	public static String getAccessTokenUrl(String cookie)
			throws JsonParseException, JsonMappingException, IOException
	{
		String cookieValue = cookie.replace("fbsr_" + PROPS.getString("appId")
				+ "=", "");
		String[] parts = cookieValue.split("\\.");
		String json = StringUtils.newStringUtf8(Base64.decodeBase64(parts[1]
				.getBytes()));
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = mapper.readValue(json, Map.class);
		/*
		 * redirect uri is blank because of
		 * http://stackoverflow.com/a/7512586/839710
		 */
		return "https://graph.facebook.com/oauth/access_token?" + "client_id="
				+ PROPS.getString("appId") + "&redirect_uri=" + ""
				+ "&client_secret=" + getAppSecret() + "&code="
				+ jsonMap.get("code");
	}

	public static String getAppSecret()
	{
		return System.getProperty("appSecret");
	}
}
