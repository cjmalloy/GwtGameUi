package com.cjmalloy.gameui.client.anim;

import com.cjmalloy.gameui.client.anim.ease.Easing;
import com.cjmalloy.gameui.client.anim.ease.EasingFn;
import com.cjmalloy.gameui.client.component.UiElement;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;


public abstract class Animation extends UiElement
{
    public Easing easingFn = EasingFn.easeInOutQuad;

    protected int duration;

    private boolean started = false;
    private boolean ended = false;
    private double startTime;

    public Animation(int duration)
    {
        this.duration = duration;
    }

    @Override
    public void redrawIfNecessary(Context2d g, double timestamp)
    {
        if (ended) return;
        render(g, timestamp);
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (ended) return;

        if (!started)
        {
            startTime = timestamp;
            started = true;
            onStart();
        }
        double elapsed = timestamp-startTime;
        if (elapsed > duration)
        {
            ended = true;
            onEnd();
        }
        else
        {
            animate(g, ease(elapsed/duration), timestamp);
            parent.redrawNeeded();
        }
    }

    protected abstract void animate(Context2d g, double p, double timestamp);

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

    public static double interp(double a, double b, double p)
    {
        return a + (b-a)*p;
    }

    public static double spread(double p, int i, int total)
    {
        return spread(p, i, total, 0.7);
    }

    public static double spread(double p, int i, int total, double width)
    {
        if (total == 1) return p;
        double padding = 1.0 - width;
        double rel = i / (total - 1.0);
        double delay = rel * padding;
        if (p <= delay) return 0;
        if (p >= delay + width) return 1;
        return (p - delay) / width;
    }

}
