package com.cjmalloy.gameui.client.core.debug;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;


public class StatsImplJs extends Stats
{
    private static final String STATS_URL = GWT.getModuleBaseForStaticFiles() + "stats.min.js";

    private JavaScriptObject stats = null;

    protected StatsImplJs()
    {
        ScriptInjector.fromUrl(STATS_URL).setCallback(new Callback<Void, Exception>()
        {
            @Override
            public void onFailure(Exception reason)
            {
                System.err.println("Failed to inject " + STATS_URL);
            }

            @Override
            public void onSuccess(Void result)
            {
                stats = createStats();
            }
        }).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }

    @Override
    public void begin()
    {
        if (stats != null) begin(stats);
    }

    @Override
    public void end()
    {
        if (stats != null) end(stats);
    }

    private static final native void begin(JavaScriptObject stats) /*-{
        stats.begin();
    }-*/;

    private static final native JavaScriptObject createStats() /*-{
        var stats = new $wnd.Stats();
        stats.setMode(0); // 0: fps, 1: ms

        // Align top-left
        stats.domElement.style.cssFloat = 'right';

        $doc.body.appendChild(stats.domElement);

        return stats;
    }-*/;

    private static final native void end(JavaScriptObject stats) /*-{
        stats.end();
    }-*/;
}
