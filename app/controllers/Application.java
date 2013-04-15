package controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

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
		return ok (FacebookUtils.getAccessToken (cookie));
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
