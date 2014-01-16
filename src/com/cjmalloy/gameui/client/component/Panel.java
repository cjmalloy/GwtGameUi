package com.cjmalloy.gameui.client.component;

import java.util.ArrayList;
import java.util.List;

import com.cjmalloy.gameui.client.event.EventBus;
import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This is the base class for all container type widgets in this
 * library.
 *
 * @author chris
 *
 */
public class Panel extends UiElement implements HasChildren
{

    public boolean clip = false;

    protected final List<UiElement> children = new ArrayList<UiElement>();

    public Panel(int x, int y, int width, int height)
    {
        super(x, y, width, height);
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

    public void add(UiProxy proxy)
    {
        add(proxy.getElement());
    }

    public void addAll(List<UiElement> l)
    {
        for (UiElement e : l)
        {
            add(e);
        }
    }

    @Override
    public void redrawIfNecessary(Context2d g, double timestamp)
    {
        if (!isVisible()) { return; }
        if (redrawNeeded)
        {
            clearRect(g);
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
            EventBus.get().validateCapture();
        }
    }

    public void remove(UiProxy child)
    {
        remove(child.getElement());
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!isVisible()) { return; }
        g.save();
        g.translate(x, y);
        if (clip)
        {
            g.beginPath();
            g.rect(0, 0, width, height);
            g.clip();
        }
        for (UiElement c : children)
        {
            c.render(g, timestamp);
        }
        g.restore();
        redrawNeeded = false;
    }
}
