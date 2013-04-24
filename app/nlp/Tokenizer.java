package nlp;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import gov.sandia.cognition.text.term.DefaultTerm;
import gov.sandia.cognition.text.term.DefaultTermOccurrence;
import gov.sandia.cognition.text.term.TermOccurrence;

import java.io.StringReader;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class Tokenizer
{
	/**
	 * Tokenizes the article content
	 * 
	 * @param articleMap
	 *            A map from urls to the the extracted article content from that
	 *            url
	 * @return an map from urls to an iterable of tokens from the url's article
	 *         content
	 */
	public ImmutableMap<String, Iterable<TermOccurrence>> tokenize (
			Map<String, String> articleMap)
	{
		ImmutableMap.Builder<String, Iterable<TermOccurrence>> result = ImmutableMap
				.builder ();
		for (String url : articleMap.keySet ())
			result.put (url, tokenize (articleMap.get (url)));
		return result.build ();
	}

	public ImmutableList<TermOccurrence> tokenize (String input)
	{
		ImmutableList.Builder<TermOccurrence> tokens = ImmutableList.builder ();
		PTBTokenizer<Word> ptbt = new PTBTokenizer<Word> (new StringReader (
				input), new WordTokenFactory (), "untokenizable=noneDelete");
		while (ptbt.hasNext ())
			tokens.add (new DefaultTermOccurrence (new DefaultTerm (ptbt
					.next ().word ()), 0, 0));
		return tokens.build ();
	}
}
