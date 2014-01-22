package com.cjmalloy.gameui.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cjmalloy.gameui.client.core.UiElement;
import com.cjmalloy.gameui.client.core.UiProxy;
import com.google.gwt.event.shared.HandlerRegistration;

public class EventBus
{

    private static final EventBus INSTANCE = new EventBus();

    UiElement capture = null;

    private Map<EventType<? extends Event>, List<Listener>> map = new HashMap<EventType<? extends Event>, List<Listener>>();
    private Event lastMouseMoveEvent = null;

    public HandlerRegistration addHandler(UiElement source, EventHandler handler, EventType<? extends Event> type, boolean ignoresCapture)
    {
        List<Listener> listeners = ensureListeners(type);
        Listener l = new Listener(source, handler, ignoresCapture);
        listeners.add(l);
        return new HandlerRegistrationImpl(type, l);
    }

    public HandlerRegistration addHandler(UiProxy source, EventHandler handler, EventType<? extends Event> type, boolean ignoresCapture)
    {
        return addHandler(source.getElement(), handler, type, ignoresCapture);
    }

    public void fireEvent(Event e)
    {
        fireEvent(e, false);
    }

    public void flushMouseMoveEvent()
    {
        if (lastMouseMoveEvent != null)
        {
            Event e = lastMouseMoveEvent;
            lastMouseMoveEvent = null;
            fireEvent(e, true);
        }
    }

    public UiElement getCapture()
    {
        return capture;
    }

    public void releaseCapture(UiElement element)
    {
        if (capture == element)
        {
            capture = null;
        }
    }

    public void validateCapture()
    {
        if (null == capture) return;

        if (capture.isDetached() || !capture.isMouseEnabled())
        {
            capture = null;
        }
    }

    private List<Listener> ensureListeners(EventType<? extends Event> type)
    {
        List<Listener> listeners = map.get(type);
        if (null == listeners)
        {
            listeners = new ArrayList<Listener>();
            map.put(type, listeners);
        }
        return listeners;
    }

    private void fireEvent(Event e, boolean flush)
    {
        if (e instanceof MouseMoveEvent)
        {
            if (!flush)
            {
                lastMouseMoveEvent = e;
                return;
            }
        }
        else
        {
            flushMouseMoveEvent();
        }

        List<Listener> listeners = ensureListeners(e.getType());
        for (Listener l : listeners)
        {
            if (capture == null || l.ignoresCapture || l.source == capture)
            {
                e.stopPropogation = false;
                e.source = l.source;
                e.callHandler(l.handler);
            }
            if (e.stopPropogation) return;
        }
    }

    public static EventBus get()
    {
        return INSTANCE;
    }

    private class HandlerRegistrationImpl implements HandlerRegistration
    {

        private Listener listener;
        private EventType<? extends Event> type;

        private HandlerRegistrationImpl(EventType<? extends Event> t, Listener l)
        {
            this.listener = l;
            this.type = t;
        }

        public void removeHandler()
        {
            if (null == listener) return;

            List<Listener> listeners = ensureListeners(type);
            listeners.remove(listener);
            listener = null;
        }
    }

    private class Listener
    {

        public boolean ignoresCapture;
        public UiElement source;
        public EventHandler handler;

        public Listener(UiElement source, EventHandler handler, boolean ignoresCapture)
        {
            this.ignoresCapture = ignoresCapture;
            this.source = source;
            this.handler = handler;
        }
    }
}
