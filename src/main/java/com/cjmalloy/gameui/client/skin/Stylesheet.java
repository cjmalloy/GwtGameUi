package com.cjmalloy.gameui.client.skin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Stylesheet extends ClientBundle
{

    public static Stylesheet INSTANCE = GWT.create(Stylesheet.class);

    @Source("style.css")
    Style style();

    public interface Style extends CssResource
    {

        @ClassName("renderer")
        String rendererStyles();
    }
}
