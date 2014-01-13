package com.cjmalloy.gameui.client.event;

public class DragMoveEvent extends DragEvent
{

    public static final EventType<DragMoveEvent> TYPE = new EventType<DragMoveEvent>()
    {

        @Override
        public DragMoveEvent createEvent()
        {
            return new DragMoveEvent();
        }

        @Override
        public boolean equals(Event e)
        {
            return e instanceof DragMoveEvent;
        }
    };

    @Override
    public void callHandler(EventHandler handler)
    {
        if (!(handler instanceof DragMoveHandler)) { throw new InvalidEventHandlerError(); }
        if (!source.isMouseEnabled()) return;

        if (EventBus.get().capture == source || containsPoint())
        {
            DragMoveHandler h = (DragMoveHandler) handler;
            h.onDragMove(this);
        }
    }

    public EventType<? extends Event> getType()
    {
        return TYPE;
    }
}
