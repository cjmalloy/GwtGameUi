package com.cjmalloy.gameui.client.event;

public class MouseUpEvent extends MouseEvent
{

    public static final EventType<MouseUpEvent> TYPE = new EventType<MouseUpEvent>()
    {

        @Override
        public MouseUpEvent createEvent()
        {
            return new MouseUpEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof MouseUpEvent;
        }
    };

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof MouseUpHandler)) { throw new InvalidEventHandlerError(); }
        if (!source.isMouseEnabled()) return;

        if (EventBus.get().capture.contains(source) || containsPoint())
        {
            MouseUpHandler h = (MouseUpHandler) handler;
            h.onMouseUp(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
