package com.cjmalloy.gameui.client.event;

public class DragCancelEvent extends DragEvent
{
    public static final EventType<DragCancelEvent> TYPE = new EventType<DragCancelEvent>()
    {

        @Override
        public DragCancelEvent createEvent()
        {
            return new DragCancelEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof DragCancelEvent;
        }
    };

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof DragCancelHandler)) { throw new InvalidEventHandlerError(); }
        if (!source.isMouseEnabled()) return;

        if (EventBus.get().capture.contains(source) || containsPoint())
        {
            DragCancelHandler h = (DragCancelHandler) handler;
            h.onDragCancel(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
