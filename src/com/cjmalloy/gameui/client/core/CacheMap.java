package com.cjmalloy.gameui.client.core;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.canvas.dom.client.Context2d;


public class CacheMap<T>
{
    private int width;
    private int height;
    private HasRenderer<T> factory;
    private Map<T, Buffer> cache = new HashMap<T, Buffer>();

    public CacheMap(HasRenderer<T> factory)
    {
        this.factory = factory;
    }

    public CacheMap(HasRenderer<T> factory, int width, int height)
    {
        this(factory);
        resize(width, height);
    }

    public void clear()
    {
        cache.clear();
    }

    public void render(Context2d g, T t)
    {
        if (!cache.containsKey(t))
        {
            cache.put(t, new Buffer(factory.getRenderer(t), width, height));
        }
        cache.get(t).flip(g);
    }

    public void resize(int width, int height)
    {
        if (this.width == width && this.height == height) return;

        this.width = width;
        this.height = height;
        cache.clear();
    }
}
