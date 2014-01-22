package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.core.Renderer;
import com.cjmalloy.gameui.client.core.UiElement;
import com.google.gwt.canvas.dom.client.Context2d;


public class RendererElement extends UiElement
{

    private Renderer renderer;

    public RendererElement(Renderer r)
    {
        this.renderer = r;
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!isVisible()) { return; }
        redrawNeeded = false;
        g.save();
        g.translate(x, y);
        renderer.render(g, timestamp);
        g.restore();
    }

}
