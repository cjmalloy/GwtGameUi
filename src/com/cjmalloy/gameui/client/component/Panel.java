package com.cjmalloy.gameui.client.component;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;

public class Panel extends UiElement
{

    protected final List<UiElement> children = new ArrayList<UiElement>();

    public Panel(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void add(UiElement child)
    {
        if (children.indexOf(child) == -1)
        {
            if (null != child.parent)
            {
                child.parent.remove(child);
            }
            children.add(child);
            child.parent = this;
            redrawNeeded = true;
        }
    }

    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
        redrawNeeded = true;
    }

    public void resize(int w, int h)
    {
        this.width = w;
        this.height = h;
        redrawNeeded = true;
    }

    @Override
    public void redrawIfNecessary(Context2d g, double timestamp)
    {
        if (!visible) { return; }
        if (redrawNeeded)
        {
            render(g, timestamp);
        }
        else
        {
            g.save();
            g.translate(x, y);
            for (UiElement c : children)
            {
                c.redrawIfNecessary(g, timestamp);
            }
            g.restore();
        }
    }

    public void remove(UiElement child)
    {
        if (children.indexOf(child) != -1)
        {
            children.remove(child);
            child.parent = null;
            redrawNeeded = true;
        }
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!visible) { return; }
        g.save();
        g.translate(x, y);
        g.clearRect(0, 0, width, height);
        for (UiElement c : children)
        {
            c.render(g, timestamp);
        }
        g.restore();
        redrawNeeded = false;
    }
}
