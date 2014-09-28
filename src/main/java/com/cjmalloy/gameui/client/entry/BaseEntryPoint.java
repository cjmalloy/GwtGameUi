package com.cjmalloy.gameui.client.entry;

import com.cjmalloy.gameui.client.skin.Stylesheet;
import com.google.gwt.core.client.EntryPoint;

public class BaseEntryPoint implements EntryPoint
{

    @Override
    public void onModuleLoad()
    {
        Stylesheet.INSTANCE.style().ensureInjected();
    }

}
