package com.cjmalloy.gameui.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cjmalloy.gameui.client.component.UiElement;

public class EventBus
{

    public class HandlerRegistration
    {

        private Listener listener;
        private EventType<? extends Event> type;

        private HandlerRegistration(EventType<? extends Event> t, Listener l)
        {
            this.listener = l;
            this.type = t;
        }

        public void removeHandler()
        {
            if (null != listener)
            {
                List<Listener> listeners = ensureListeners(type);
                listeners.remove(listener);
                listener = null;
            }
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

    private static final EventBus INSTANCE = new EventBus();

    public static EventBus get()
    {
        return INSTANCE;
    }

    UiElement capture = null;

    private Map<EventType<? extends Event>, List<Listener>> map = new HashMap<EventType<? extends Event>, List<Listener>>();

    public HandlerRegistration addHandler(UiElement source, EventHandler handler, EventType<? extends Event> type, boolean ignoresCapture)
    {
        List<Listener> listeners = ensureListeners(type);
        Listener l = new Listener(source, handler, ignoresCapture);
        listeners.add(l);
        return new HandlerRegistration(type, l);
    }

    public void fireEvent(Event e)
    {
        List<Listener> listeners = ensureListeners(e.getType());
        for (Listener l : listeners)
        {
            if (capture == null || l.ignoresCapture || l.source == capture)
            {
                e.stopPropogation = false;
                e.source = l.source;
                e.callHandler(l.handler);
            }
            if (e.stopPropogation) { return; }
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
}