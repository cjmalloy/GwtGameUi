package com.cjmalloy.gameui.client.core;

import java.util.ArrayList;
import java.util.List;

import com.cjmalloy.gameui.client.component.UiElement;
import com.cjmalloy.gameui.client.event.EventBus;
import com.cjmalloy.gameui.client.event.MouseClickEvent;
import com.cjmalloy.gameui.client.event.MouseDownEvent;
import com.cjmalloy.gameui.client.event.MouseMoveEvent;
import com.cjmalloy.gameui.client.event.MouseUpEvent;
import com.cjmalloy.gameui.client.event.MouseWheelEvent;
import com.cjmalloy.gameui.client.skin.Stylesheet;
import com.cjmalloy.gameui.client.util.GraphicsUtil;
import com.cjmalloy.gameui.client.util.UserAgent;
import com.cjmalloy.gameui.shared.core.Point;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is the main class for this UI library, and the only object that contains
 * references to HTML elements. This is an absolute panel that wraps a variable
 * number of canvas objects called layers.
 *
 * <ol>
 * <li>Add this object to your HTML DOM</li>
 * <li>Create as many layers as necessary</li>
 * <li>Begin to add widgets provided by this library to the various layers</li>
 * </ol>
 *
 * @author chris
 *
 */
public class RenderEngine extends Composite implements HasMouseDownHandlers, HasMouseUpHandlers, HasMouseMoveHandlers,
        HasMouseWheelHandlers, MouseDownHandler, MouseUpHandler, MouseMoveHandler, MouseWheelHandler
{

    /* ************************************************
     *                 DEBUG                          *
     **************************************************/
    private JavaScriptObject stats;

    public void attachProfiler()
    {
        if (null == stats)
        {
            stats = createStats();
        }
    }
    private static final native void begin(JavaScriptObject stats) /*-{
        stats.begin();
    }-*/;

    private static final native JavaScriptObject createStats() /*-{
        var stats = new $wnd.Stats();
        stats.setMode(0); // 0: fps, 1: ms

        // Align top-left
        stats.domElement.style.cssFloat = 'right';

        $doc.body.appendChild(stats.domElement);

        return stats;
    }-*/;

    private static final native void end(JavaScriptObject stats) /*-{
        stats.end();
    }-*/;
    /* ************************************************/

    private static final int MAX_CLICK_DIST_SQUARED = 16;
    private static final int RESIZE_DELAY = 30;

    private final AbsolutePanel panel = new AbsolutePanel();

    private final List<Layer> layers = new ArrayList<Layer>();
    private final AnimationScheduler scheduler = AnimationScheduler.get();
    private final AnimationCallback callback = new AnimationCallback()
    {
        @Override
        public void execute(double timestamp)
        {
            scheduler.requestAnimationFrame(callback);
            render(timestamp);
        }
    };
    private final Timer updateSize = new Timer()
    {
        @Override
        public void run()
        {
            panel.setPixelSize(width, height);
            for (Layer l : layers)
            {
                l.resize(width, height);
            }
        }
    };

    private int width;
    private int height;

    private Point clickStart = new Point();

    public RenderEngine(int width, int height)
    {
        this.width = width;
        this.height = height;
        initWidget(panel);
        setStylePrimaryName(Stylesheet.INSTANCE.style().rendererStyles());

        panel.setPixelSize(width, height);

        if (!UserAgent.isTablet())
        {
            addMouseDownHandler(this);
            addMouseUpHandler(this);
            addMouseMoveHandler(this);
            addMouseWheelHandler(this);
        }
        else
        {
            // TODO: add touch handlers
        }
        Event.setCapture(this.getElement());

        scheduler.requestAnimationFrame(callback);
    }

    public int addAnimationLayer()
    {
        Layer l = new AnimationLayer(width, height);
        layers.add(l);
        panel.add(l, 0, 0);
        return layers.indexOf(l);
    }

    public void addChild(int layerId, UiElement child)
    {
        layers.get(layerId).add(child);
    }

    public int addLayer()
    {
        Layer l = new Layer(width, height);
        layers.add(l);
        panel.add(l, 0, 0);
        return layers.indexOf(l);
    }

    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler)
    {
        return addDomHandler(handler, com.google.gwt.event.dom.client.MouseDownEvent.getType());
    }

    @Override
    public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler)
    {
        return addDomHandler(handler, com.google.gwt.event.dom.client.MouseMoveEvent.getType());
    }

    @Override
    public HandlerRegistration addMouseUpHandler(MouseUpHandler handler)
    {
        return addDomHandler(handler, com.google.gwt.event.dom.client.MouseUpEvent.getType());
    }

    @Override
    public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler)
    {
        return addDomHandler(handler, com.google.gwt.event.dom.client.MouseWheelEvent.getType());
    }

    public HandlerRegistration addResizeHandler(ResizeHandler handler)
    {
        return Window.addResizeHandler(handler);
    }

    @Override
    public void onMouseDown(com.google.gwt.event.dom.client.MouseDownEvent event)
    {
        MouseDownEvent e = new MouseDownEvent();
        e.x = event.getRelativeX(this.getElement());
        e.y = event.getRelativeY(this.getElement());
        clickStart = new Point(e.x, e.y);
        EventBus.get().fireEvent(e);
    }

    @Override
    public void onMouseMove(com.google.gwt.event.dom.client.MouseMoveEvent event)
    {
        MouseMoveEvent e = new MouseMoveEvent();
        e.x = event.getRelativeX(this.getElement());
        e.y = event.getRelativeY(this.getElement());
        EventBus.get().fireEvent(e);
    }

    @Override
    public void onMouseUp(com.google.gwt.event.dom.client.MouseUpEvent event)
    {
        MouseUpEvent e = new MouseUpEvent();
        e.x = event.getRelativeX(this.getElement());
        e.y = event.getRelativeY(this.getElement());
        EventBus.get().fireEvent(e);

        clickStart.subtract(e.x, e.y);
        if (clickStart.x * clickStart.x + clickStart.y * clickStart.y <= MAX_CLICK_DIST_SQUARED)
        {
            MouseClickEvent click = new MouseClickEvent();
            click.x = e.x;
            click.y = e.y;
            EventBus.get().fireEvent(click);
        }
    }

    @Override
    public void onMouseWheel(com.google.gwt.event.dom.client.MouseWheelEvent event)
    {
        MouseWheelEvent e = new MouseWheelEvent();
        e.x = event.getRelativeX(this.getElement());
        e.y = event.getRelativeY(this.getElement());
        e.deltaY = event.getDeltaY();
        EventBus.get().fireEvent(e);
    }

    public void removeChild(int layerId, UiElement child)
    {
        layers.get(layerId).remove(child);
    }

    public void removeLayer(int id)
    {
        if (id >= 0 && layers.size() > id)
        {
            Layer l = layers.get(id);
            if (null != l)
            {
                panel.remove(l);
                layers.set(id, null);
            }
        }
    }

    public void resize(int w, int h)
    {
        if (width  == w &&
            height == h)
        {
            return;
        }

        this.width = w;
        this.height = h;
        updateSize.schedule(RESIZE_DELAY);
    }

    protected void render(double timestamp)
    {
        if (null != stats)
        {
            begin(stats);
        }
        for (Layer l : layers)
        {
            l.render(timestamp);
        }
        if (null != stats)
        {
            end(stats);
        }
    }

    private class Layer extends Composite
    {

        protected final Canvas canvas;
        protected final Context2d g;

        protected List<UiElement> children = new ArrayList<UiElement>();

        public Layer(int width, int height)
        {
            canvas = GraphicsUtil.createCanvas(width, height);
            initWidget(canvas);
            g = canvas.getContext2d();
        }

        public void add(UiElement child)
        {
            if (children.indexOf(child) == -1)
            {
                children.add(child);
            }
        }

        public void remove(UiElement child)
        {
            if (children.indexOf(child) != -1)
            {
                children.remove(child);
            }
        }

        public void render(double timestamp)
        {
            g.save();
            for (UiElement r : children)
            {
                r.redrawIfNecessary(g, timestamp);
            }
            g.restore();
        }

        public void resize(int w, int h)
        {
            GraphicsUtil.resizeCanvas(canvas, w, h);
            for (UiElement r : children)
            {
                r.redrawNeeded();
            }
        }
    }

    private class AnimationLayer extends Layer
    {

        public AnimationLayer(int width, int height)
        {
            super(width, height);
        }

        @Override
        public void render(double timestamp)
        {
            g.save();
            for (UiElement r : children)
            {
                r.render(g, timestamp);
            }
            g.restore();
        }
    }
}
