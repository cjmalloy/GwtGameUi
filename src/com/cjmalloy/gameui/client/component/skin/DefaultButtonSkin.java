package com.cjmalloy.gameui.client.component.skin;

import com.cjmalloy.gameui.client.component.Button.ButtonSkin;
import com.cjmalloy.gameui.client.component.Button.ButtonState;
import com.cjmalloy.gameui.client.core.IRender;
import com.cjmalloy.gameui.client.util.GraphicsUtil;
import com.google.gwt.canvas.dom.client.Context2d;

public class DefaultButtonSkin extends ButtonSkin
{

    private IRender up = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setFillStyle("#999");
            g.setStrokeStyle("#333");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };
    private IRender down = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setFillStyle("#333");
            g.setStrokeStyle("#999");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };
    private IRender upPressed = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setFillStyle("#99F");
            g.setStrokeStyle("#333");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };
    private IRender downPressed = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setFillStyle("#33F");
            g.setStrokeStyle("#999");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };
    private IRender upHovering = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setFillStyle("#F99");
            g.setStrokeStyle("#333");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };
    private IRender downHovering = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setFillStyle("#F33");
            g.setStrokeStyle("#999");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };
    private IRender upDisabled = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setGlobalAlpha(0.4);
            g.setFillStyle("#999");
            g.setStrokeStyle("#333");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };
    private IRender downDisabled = new IRender()
    {

        @Override
        public void render(Context2d g, double timestamp)
        {
            g.save();
            g.setGlobalAlpha(0.4);
            g.setFillStyle("#333");
            g.setStrokeStyle("#999");
            g.beginPath();
            GraphicsUtil.drawRoundedRect(g, 0.5, 0.5, width - 0.5, height - 0.5, 8);
            g.closePath();
            g.fill();
            g.stroke();
            g.restore();
        }
    };

    public DefaultButtonSkin()
    {
        states.put(ButtonState.UP, up);
        states.put(ButtonState.DOWN, down);
        states.put(ButtonState.UP_PRESSED, upPressed);
        states.put(ButtonState.DOWN_PRESSED, downPressed);
        states.put(ButtonState.UP_HOVERING, upHovering);
        states.put(ButtonState.DOWN_HOVERING, downHovering);
        states.put(ButtonState.UP_DISABLED, upDisabled);
        states.put(ButtonState.DOWN_DISABLED, downDisabled);
    }

    @Override
    public void setText(String text)
    {
        // TODO Auto-generated method stub

    }

}
