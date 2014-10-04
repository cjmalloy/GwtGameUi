package com.cjmalloy.gameui.client.core.debug;

import com.google.gwt.core.client.GWT;


public class Stats
{
    private static Stats instance = null;

    public void begin() {}
    public void end() {}

    public static Stats get()
    {
        if (instance == null)
        {
            instance = GWT.create(Stats.class);
        }
        return instance;
    }
}
