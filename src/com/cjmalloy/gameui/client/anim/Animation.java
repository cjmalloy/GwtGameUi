package com.cjmalloy.gameui.client.anim;

import com.cjmalloy.gameui.client.anim.ease.Easing;
import com.cjmalloy.gameui.client.component.UiElement;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;


public abstract class Animation extends UiElement
{
    public Easing easingFn = null;

    protected int duration;

    private boolean started = false;
    private double startTime;

    public Animation(int duration)
    {
        this.duration = duration;
    }

    @Override
    public void redrawIfNecessary(Context2d g, double timestamp)
    {
        render(g, timestamp);
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!started)
        {
            startTime = timestamp;
            started = true;
            onStart();
        }
        if (startTime >= timestamp)
        {
            onEnd();
        }
        else
        {
            animate(g, ease((timestamp-startTime)/duration));
        }
    }

    protected abstract void animate(Context2d g, double p);

    protected void onEnd() {}

    protected void onStart() {}

    protected void removeSelf()
    {
        Scheduler.get().scheduleDeferred(new ScheduledCommand()
        {
            @Override
            public void execute()
            {
                parent.remove(Animation.this);
            }
        });
    };

    private double ease(double d)
    {
        if (easingFn != null)
        {
            return easingFn.ease(d);
        }
        return d;
    };

    public static double spread(double p, int i, int total)
    {
        return spread(p, i, total, 0.5);
    }

    public static double spread(double p, int i, int total, double width)
    {
        double padding = 1-width;
        double rel = i/(double)total;
        if (p <= rel*padding) return 0;
        if (p >= width + rel*padding) return 1;
        return p * width;
    }

    public static int interp(int a, int b, double p)
    {
        return (int) (a + (b-a)*p);
    }

}
