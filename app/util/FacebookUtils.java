package util;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class FacebookUtils
{
	private static final ObjectMapper mapper = new ObjectMapper ();
	private static final ResourceBundle PROPS = ResourceBundle
			.getBundle ("facebook");
	private static final HttpClient client = new DefaultHttpClient ();
	private static final String appId = PROPS.getString ("appId");
	private static final String appSecret = System.getProperty ("appSecret");
	private static final Pattern accessTokenPattern = Pattern
			.compile ("access_token=(.+)(?:&expires=(.*))");

	public static String getAccessTokenUrl (String cookie)
			throws JsonParseException, JsonMappingException, IOException
	{
		String cookieValue = cookie.replace ("fbsr_" + appId + "=", "");
		String[] parts = cookieValue.split ("\\.");
		String json = StringUtils.newStringUtf8 (Base64.decodeBase64 (parts[1]
				.getBytes ()));
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = mapper.readValue (json, Map.class);
		/*
		 * redirect uri is blank because of
		 * http://stackoverflow.com/a/7512586/839710
		 */
		return "https://graph.facebook.com/oauth/access_token?" + "client_id="
				+ appId + "&redirect_uri=" + "" + "&client_secret=" + appSecret
				+ "&code=" + jsonMap.get ("code");
	}

	public static String getAccessToken (String cookie)
			throws JsonParseException, JsonMappingException, IOException
	{
		String fbResponse = HttpClientUtil.getResponseAsString (client,
				getAccessTokenUrl (cookie));
		Matcher matcher = accessTokenPattern.matcher (fbResponse);
		matcher.find ();
		return matcher.group (1);
	}
}
