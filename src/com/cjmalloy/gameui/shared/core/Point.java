package com.cjmalloy.gameui.shared.core;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Point implements IsSerializable
{

    public int x, y;

    public Point()
    {}

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Point(Point p)
    {
        this(p.x, p.y);
    }

    public Point add(int x, int y)
    {
        this.x += x;
        this.y += y;
        return this;
    }

    public Point add(Point p)
    {
        x += p.x;
        y += p.y;
        return this;
    }

    public Point copy()
    {
        return new Point(this);
    }

    public Point divide(double s)
    {
        x /= s;
        y /= s;
        return this;
    }

    public Point multiply(double s)
    {
        x *= s;
        y *= s;
        return this;
    }

    public Point set(Point p)
    {
        x = p.x;
        y = p.y;
        return this;
    }

    public Point subtract(int x, int y)
    {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Point subtract(Point p)
    {
        x -= p.x;
        y -= p.y;
        return this;
    }
}
