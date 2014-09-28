package com.cjmalloy.gameui.client.core;

import com.google.gwt.canvas.dom.client.Context2d;

public interface Renderer
{

    void render(Context2d g, double timestamp);
}
