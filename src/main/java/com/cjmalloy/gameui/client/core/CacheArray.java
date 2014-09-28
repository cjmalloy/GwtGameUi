package com.cjmalloy.gameui.client.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cjmalloy.gameui.client.util.GraphicsUtil;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;


public class CacheArray<T>
{
    private int width;
    private int height;
    private List<T> items;
    private List<Renderer> renderers;
    private Canvas canvas = Canvas.createIfSupported();
    private Context2d g = canvas.getContext2d();

    public CacheArray(HasRenderer<T> factory, List<T> l)
    {
        items = new ArrayList<T>(l);
        renderers = new ArrayList<Renderer>(l.size());
        for (T t : items)
        {
            renderers.add(factory.getRenderer(t));
        }
    }

    public CacheArray(HasRenderer<T> factory, List<T> l, int width, int height)
    {
        this(factory, l);
        resize(width, height);
    }

    public CacheArray(HasRenderer<T> factory, T[] l)
    {
        this(factory, Arrays.asList(l));
    }

    public CacheArray(HasRenderer<T> factory, T[] l, int width, int height)
    {
        this(factory, Arrays.asList(l), width, height);
    }

    public void flip(Context2d x, int i)
    {
        x.drawImage(canvas.getCanvasElement(), width*i, 0, width, height, 0, 0, width, height);
    }

    public void flip(Context2d x, T t)
    {
        flip(x, items.indexOf(t));
    }

    public void resize(int width, int height)
    {
        if (this.width == width && this.height == height) return;

        this.width = width;
        this.height = height;
        GraphicsUtil.resizeCanvas(canvas, items.size()*width, height);

        g.save();
        g.clearRect(0, 0, items.size()*width, height);
        for (int i=0; i<items.size(); i++)
        {
            renderers.get(i).render(g, 0);
            g.translate(width, 0);
        }
        g.restore();
    }
}
