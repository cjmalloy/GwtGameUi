package com.cjmalloy.gameui.client.event;

public class MouseWheelEvent extends MouseEvent
{

    public static final EventType<MouseWheelEvent> TYPE = new EventType<MouseWheelEvent>()
    {

        @Override
        public MouseWheelEvent createEvent()
        {
            return new MouseWheelEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof MouseWheelEvent;
        }
    };

    public int deltaY;

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof MouseWheelHandler)) { throw new InvalidEventHandlerError(); }

        if (EventBus.get().capture == source || containsPoint())
        {
            MouseWheelHandler h = (MouseWheelHandler) handler;
            h.onMouseWheel(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
