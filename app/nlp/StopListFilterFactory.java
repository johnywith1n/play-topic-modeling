package nlp;

import gov.sandia.cognition.text.term.filter.DefaultStopList;
import gov.sandia.cognition.text.term.filter.StopListFilter;
import play.Logger;
import play.api.Play;

public class StopListFilterFactory
{

	private static final DefaultStopList stopList;

	static
	{
		DefaultStopList temp;
		try
		{
			temp = DefaultStopList.loadFromText (Play.classloader (
					Play.current ()).getResource ("stoplist.txt"));
		}
		catch (Exception e)
		{
			Logger.error ("Unable to load stop words.", e);
			temp = new DefaultStopList ();
		}
		stopList = temp;
	}

	public static StopListFilter getFilter ()
	{
		return new StopListFilter (stopList);
	}
}
