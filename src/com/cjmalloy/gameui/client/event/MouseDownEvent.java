package com.cjmalloy.gameui.client.event;

public class MouseDownEvent extends MouseEvent
{

    public static final EventType<MouseDownEvent> TYPE = new EventType<MouseDownEvent>()
    {

        @Override
        public MouseDownEvent createEvent()
        {
            return new MouseDownEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof MouseDownEvent;
        }
    };

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof MouseDownHandler)) { throw new InvalidEventHandlerError(); }
        if (!source.isMouseEnabled()) return;

        if (EventBus.get().capture == source || containsPoint())
        {
            MouseDownHandler h = (MouseDownHandler) handler;
            h.onMouseDown(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
