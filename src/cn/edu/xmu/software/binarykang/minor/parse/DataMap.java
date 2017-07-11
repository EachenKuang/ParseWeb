package cn.edu.xmu.software.binarykang.minor.parse;

public class DataMap
{
	private String key = null;
	private Double rate = null;

	public DataMap()
	{
		
	}

	public DataMap(String key, Double rate)
	{
		this.key = key;
		this.rate = rate;
	}

	public String getKey()
	{
		return key;
	}

	public Double getRate()
	{
		return rate;
	}

	@Override
	public String toString()
	{
		return key + ":" + rate;
	}
}