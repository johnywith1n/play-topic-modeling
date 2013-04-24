package controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import links.LinkContentExtractor;
import links.Links;
import links.LinksRetriever;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import play.mvc.Controller;
import play.mvc.Result;
import util.FacebookUtils;
import views.html.channel;
import views.html.index;

public class Application extends Controller
{
	private static final ResourceBundle PROPS = ResourceBundle
			.getBundle ("facebook");

	public static Result index ()
	{
		return ok (index.render (PROPS.getString ("appId")));
	}

	public static Result test (String cookie) throws JsonParseException,
			JsonMappingException, IOException
	{
		String accessToken = FacebookUtils.getAccessToken (cookie);
		LinksRetriever foo = new LinksRetriever ();
		List<Links> linksList = foo.getLinks (new DefaultHttpClient (),
				"https://graph.facebook.com/me/links?access_token="
						+ accessToken, 25);
		LinkContentExtractor extractor = new LinkContentExtractor ();
		Map<String, String> stuff = extractor.extractArticles (new DefaultHttpClient (), linksList);
		for(String key: stuff.keySet ())
			System.out.println (key + "\n"+stuff.get (key));
		return ok (accessToken);
	}

	/**
	 * Controller for the Facebook channel page used with the javascript SDK
	 * 
	 * @return
	 */
	public static Result channel ()
	{
		response ().setHeader (CACHE_CONTROL, "max-age=31536000");
		response ().setHeader (EXPIRES, getNextYearAsString ());
		return ok (channel.render ());
	}

	private static String getNextYearAsString ()
	{
		Calendar calendar = new GregorianCalendar ();
		calendar.add (Calendar.YEAR, 1);
		return DateUtils.formatDate (calendar.getTime ());
	}
}
