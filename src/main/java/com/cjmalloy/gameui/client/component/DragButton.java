package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.component.skin.DefaultDragButtonSkin;
import com.cjmalloy.gameui.client.component.skin.DragButtonSkin;
import com.cjmalloy.gameui.client.core.CacheMap;
import com.google.gwt.canvas.dom.client.Context2d;

public class DragButton extends DragElement
{
    public boolean cache = false;

    protected DragButtonSkin skin;
    protected DragButtonState state = DragButtonState.UP;

    private CacheMap<DragButtonState> cacheMap = null;

    public DragButton()
    {
        super();

        anim = new DragButtonAnimation();
        setButtonSkin(new DefaultDragButtonSkin());
    }

    @Override
    public void render(Context2d g, double timestamp)
    {
        if (!isVisible()) { return; }
        redrawNeeded = false;
        g.save();
        g.translate(x, y);
        if (cache)
        {
            ensureCache().render(g, state);
        }
        else
        {
            skin.getRenderer(state).render(g, timestamp);
        }
        g.restore();
    }

    @Override
    public void resize(int w, int h)
    {
        super.resize(w, h);
        skin.width = width;
        skin.height = height;
        if (cacheMap != null)
        {
            cacheMap.resize(w, h);
        }
    }

    public void setButtonSkin(DragButtonSkin skin)
    {
        this.skin = skin;
        this.width = skin.width;
        this.height = skin.height;
        redrawNeeded = true;
        if (anim != null)
        {
            anim.width = skin.width;
            anim.height = skin.height;
            anim.redrawNeeded();
        }
    }

    public void setText(String text)
    {
        skin.setText(text);
        if (cacheMap != null)
        {
            cacheMap.clear();
        }
    }

    protected CacheMap<DragButtonState> ensureCache()
    {
        if (cacheMap == null)
        {
            cacheMap = new CacheMap<DragButtonState>(skin, width, height);
        }
        return cacheMap;
    }

    protected void updateState()
    {
        DragButtonState s;
        if (isDragging())
        {
            s = DragButtonState.DRAGGING;
        }
        else if (isDisabled())
        {
            s = DragButtonState.UP_DISABLED;
        }
        else if (isPressed())
        {
            s = DragButtonState.UP_PRESSED;
        }
        else if (isHovering())
        {
            s = DragButtonState.UP_HOVERING;
        }
        else
        {
            s = DragButtonState.UP;
        }

        if (skin.getRenderer(state) != skin.getRenderer(s))
        {
            redrawNeeded = true;
        }
        state = s;

        anim.setVisible(isDragging());
    }

    public class DragButtonAnimation extends DragAnimation
    {
        public DragButtonAnimation()
        {
            setVisible(false);
        }

        @Override
        public void render(Context2d g, double timestamp)
        {
            if (!isVisible()) { return; }
            redrawNeeded = false;
            g.save();
            g.translate(x, y);
            skin.width = width = DragButton.this.width;
            skin.height = height = DragButton.this.height;
            skin.getRenderer(DragButtonState.UP).render(g, timestamp);
            g.restore();
        }
    }
    public enum DragButtonState
    {
        UP,
        UP_PRESSED,
        UP_HOVERING,
        UP_DISABLED,
        DOWN,
        DOWN_PRESSED,
        DOWN_HOVERING,
        DOWN_DISABLED,
        DRAGGING,
    }

}
