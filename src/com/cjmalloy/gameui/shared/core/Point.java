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

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return x == p.x &&
               y == p.y;
    }

    @Override
    public int hashCode()
    {
        return (x & 0xFFFF) << 0x1000 | y & 0xFFFF;
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

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
