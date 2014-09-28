package com.cjmalloy.gameui.client.core;

import com.cjmalloy.gameui.client.util.GraphicsUtil;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;


public class Buffer
{
    private int width;
    private int height;
    private Renderer renderer;
    private Canvas canvas = Canvas.createIfSupported();
    private Context2d g = canvas.getContext2d();

    public Buffer(Renderer renderer)
    {
        this.renderer = renderer;
    }

    public Buffer(Renderer renderer, int width, int height)
    {
        this(renderer);
        resize(width, height);
    }

    public void flip(Context2d x)
    {
        x.drawImage(canvas.getCanvasElement(), 0, 0);
    }

    public void render()
    {
        g.clearRect(0, 0, width, height);
        renderer.render(g, 0);
    }

    public void resize(int width, int height)
    {
        if (this.width == width && this.height == height) return;

        this.width = width;
        this.height = height;
        GraphicsUtil.resizeCanvas(canvas, width, height);
        render();
    }

}
