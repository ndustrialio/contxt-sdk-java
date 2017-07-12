package com.ndustrialio.contxt;

import java.util.Map;


public class BaseConfiguredComponent
{
	protected Map conf;

	public void setConf(Map conf)
	{
        this.conf = conf;

	}

	public Object getConfigurationValue(String valueKey)
    {
		Object value = this.conf.get(valueKey);
		if (value == null) {
			throw new RuntimeException("Key not in configuration! Key: " + valueKey);
		}
		return value;
	}
}
