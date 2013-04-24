package nlp;

import gov.sandia.cognition.text.term.TermOccurrence;
import gov.sandia.cognition.text.term.filter.AbstractSingleTermFilter;

import org.apache.commons.lang3.StringUtils;

public class AlphabetFilter extends AbstractSingleTermFilter
{

	private static final long serialVersionUID = 2722238183595198261L;

	public AlphabetFilter()
	{
		super ();
	}

	@Override
	public TermOccurrence filterTerm (TermOccurrence occurrence)
	{
		if (StringUtils.isAlpha (occurrence.getTerm ().toString ()))
			return occurrence;
		else
			return null;
	}

}