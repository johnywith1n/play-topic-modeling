package links;

import java.util.List;
import java.util.Map;

public class Links
{
	private List<Link> data;
	private Map<String, Object> paging;
	private static final String NEXT_URL_KEY = "next";

	public List<Link> getData ()
	{
		return data;
	}

	public Map<String, Object> getPaging ()
	{
		return paging;
	}

	public void setData (List<Link> data)
	{
		this.data = data;
	}

	public void setPaging (Map<String, Object> paging)
	{
		this.paging = paging;
	}

	public String getNextPageUrl ()
	{
		if (paging != null)
			return (String) paging.get (NEXT_URL_KEY);
		return null;
	}
}
