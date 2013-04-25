package links;

import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpClientUtil;

import com.google.common.collect.ImmutableMap;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class LinkContentExtractor
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger (LinkContentExtractor.class);

	public Map<String, String> extractArticles (HttpClient client,
			List<Links> linksList)
	{
		ImmutableMap.Builder<String, String> articleMap = ImmutableMap
				.builder ();

		int i = 0;
		for (Links links : linksList)
		{
			for (Link link : links.getData ())
			{
				LOGGER.info ("Getting link #" + i++);
				String url = link.getLink ();
				articleMap.put (url, extractArticle (client, url));
			}
		}

		return articleMap.build ();
	}

	public String extractArticle (HttpClient client, String url)
	{
		ArticleExtractor extractor = new ArticleExtractor ();
		try
		{
			return extractor.getText (HttpClientUtil.getResponseAsInputSource (
					client, url));
		}
		catch (BoilerpipeProcessingException e)
		{
			LOGGER.error ("", e);
		}
		return "Unable to extract article";
	}
}
