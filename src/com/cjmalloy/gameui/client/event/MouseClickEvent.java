package com.cjmalloy.gameui.client.event;

public class MouseClickEvent extends MouseEvent
{

    public static final EventType<MouseClickEvent> TYPE = new EventType<MouseClickEvent>()
    {

        @Override
        public MouseClickEvent createEvent()
        {
            return new MouseClickEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof MouseClickEvent;
        }
    };

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof MouseClickHandler)) { throw new InvalidEventHandlerError(); }
        if (!source.isMouseEnabled()) return;

        if (EventBus.get().capture == source || containsPoint())
        {
            MouseClickHandler h = (MouseClickHandler) handler;
            h.onMouseClick(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
