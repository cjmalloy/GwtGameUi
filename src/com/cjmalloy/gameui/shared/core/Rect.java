package com.cjmalloy.gameui.shared.core;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Rect implements IsSerializable
{

    public int x;
    public int y;
    public int width;
    public int height;

    public Rect()
    {}

    public Rect(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect(Rect r)
    {
        this(r.x, r.y, r.width, r.height);
    }

    public Rect boundingRect(Rect r)
    {
        int ux = Math.min(r.x, x);
        int uy = Math.min(r.y, y);
        width = Math.max(r.x + r.width, x + width) - ux;
        height = Math.max(r.y + r.height, y + height) - uy;
        x = ux;
        y = uy;
        return this;
    }

    public boolean contains(int x, int y)
    {
        return x > this.x &&x < this.x + this.width &&
                y > this.y && y < this.y + this.height;
    }

    public boolean contains(Point p)
    {
        return contains(p.x, p.y);
    }

    public Rect copy()
    {
        return new Rect(this);
    }

    public Rect set(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }
}
