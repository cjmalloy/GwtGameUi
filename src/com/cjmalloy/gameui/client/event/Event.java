package com.cjmalloy.gameui.client.event;

import com.cjmalloy.gameui.client.core.UiElement;

public abstract class Event
{

    public UiElement source;

    boolean stopPropogation = false;

    private EventBus eventBus;

    public abstract void callHandler(EventHandler handler);
    public abstract EventType<? extends Event> getType();

    public Event()
    {
        eventBus = EventBus.get();
    }

    public Event(EventBus b)
    {
        eventBus = b;
    }

    public void releaseCapture(UiElement element)
    {
        eventBus.releaseCapture(element);
    }

    public void setCapture(UiElement element)
    {
        if (!eventBus.capture.contains(element))
        {
            eventBus.capture.add(element);
        }
    }

    public void stopPropogation()
    {
        stopPropogation = true;
    }

    public class InvalidEventHandlerError extends Error
    {
        private static final long serialVersionUID = -9159041323478124659L;
    };
}
