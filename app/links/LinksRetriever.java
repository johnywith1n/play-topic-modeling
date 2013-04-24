package links;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpClientUtil;

import com.google.common.collect.ImmutableList;

public class LinksRetriever
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger (LinksRetriever.class);
	private static final ObjectMapper mapper = new ObjectMapper ();

	public ImmutableList<Links> getLinks (HttpClient client, String url,
			int numLinksToGet)
	{
		ImmutableList.Builder<Links> builder = ImmutableList.builder ();
		int numLinksRetrieved = 0;
		while (numLinksRetrieved < numLinksToGet)
		{
			String linksJson = HttpClientUtil.getResponseAsString (client, url);
			try
			{
				Links currentLinks = mapper.readValue (linksJson, Links.class);
				builder.add (currentLinks);
				numLinksRetrieved += currentLinks.getData ().size ();
				String pagingUrl = currentLinks.getNextPageUrl ();
				LOGGER.info ("num links got: " + numLinksRetrieved);
				if (pagingUrl != null)
					url = pagingUrl;
				else
					break;
			}
			catch (IOException e)
			{
				LOGGER.error ("Unable to read json. Ending loop", e);
				break;
			}
		}
		return builder.build ();
	}
}
