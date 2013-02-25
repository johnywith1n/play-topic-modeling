package controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.http.impl.cookie.DateUtils;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.channel;
import views.html.index;

public class Application extends Controller
{
	public static Result index()
	{
		return ok(index.render("Your new application is ready."));
	}

	/**
	 * Controller for the Facebook channel page used with the javascript SDK
	 * 
	 * @return
	 */
	public static Result channel()
	{
		response().setHeader(CACHE_CONTROL, "max-age=31536000");
		response().setHeader(EXPIRES, getNextYearAsString());
		return ok(channel.render());
	}

	private static String getNextYearAsString()
	{
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.YEAR, 1);
		return DateUtils.formatDate(calendar.getTime());
	}
}
