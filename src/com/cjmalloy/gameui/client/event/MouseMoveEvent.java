package com.cjmalloy.gameui.client.event;

public class MouseMoveEvent extends MouseEvent
{

    public static final EventType<MouseMoveEvent> TYPE = new EventType<MouseMoveEvent>()
    {

        @Override
        public MouseMoveEvent createEvent()
        {
            return new MouseMoveEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof MouseMoveEvent;
        }
    };

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof MouseMoveHandler)) { throw new InvalidEventHandlerError(); }

        if (EventBus.get().capture == source || containsPoint())
        {
            MouseMoveHandler h = (MouseMoveHandler) handler;
            h.onMouseMove(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
