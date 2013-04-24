package nlp;

import gov.sandia.cognition.text.term.TermOccurrence;
import gov.sandia.cognition.text.term.filter.LowerCaseTermFilter;
import gov.sandia.cognition.text.term.filter.TermFilter;
import gov.sandia.cognition.text.term.filter.TermLengthFilter;

import com.google.common.collect.ImmutableMap;

public class Filter
{
	public ImmutableMap<String, Iterable<TermOccurrence>> filter (
			ImmutableMap<String, Iterable<TermOccurrence>> map)
	{
		return filter (
				filter (filter (
						filter (map, StopListFilterFactory.getFilter ()),
						new AlphabetFilter ()), new TermLengthFilter ()),
				new LowerCaseTermFilter ());
	}

	public ImmutableMap<String, Iterable<TermOccurrence>> filter (
			ImmutableMap<String, Iterable<TermOccurrence>> map,
			TermFilter filter)
	{
		ImmutableMap.Builder<String, Iterable<TermOccurrence>> result = ImmutableMap
				.builder ();
		for (String key : map.keySet ())
		{
			result.put (key, filter.filterTerms (map.get (key)));
		}
		return result.build ();
	}
}
