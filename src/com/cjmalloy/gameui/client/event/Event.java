package com.cjmalloy.gameui.client.event;

import com.cjmalloy.gameui.client.component.UiElement;

public abstract class Event
{

    public class InvalidEventHandlerError extends Error
    {

        private static final long serialVersionUID = -9159041323478124659L;
    };

    public UiElement source;

    boolean stopPropogation = false;

    public abstract void callHandler(EventHandler handler);

    public abstract EventType<? extends Event> getType();

    public void releaseCapture(UiElement element)
    {
        if (EventBus.get().capture == element)
        {
            EventBus.get().capture = null;
        }
    }

    public void setCapture(UiElement element)
    {
        EventBus.get().capture = element;
    }

    public void stopPropogation()
    {
        stopPropogation = true;
    }

}
