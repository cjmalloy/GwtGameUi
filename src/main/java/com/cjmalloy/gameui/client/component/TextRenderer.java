package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.core.Renderer;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;


public class TextRenderer implements Renderer
{
    public int width;
    public int height;

    public String text = "";
    public String font = "16px sans";
    public String color = "#000";
    public TextClip textClip = TextClip.OVERFLOW;
    public TextAlign textAlign = TextAlign.LEFT;
    public TextBaseline textBaseline = TextBaseline.BOTTOM;

    @Override
    public void render(Context2d g, double timestamp)
    {
        g.save();
        {
            if (textClip == TextClip.HIDDEN)
            {
                g.beginPath();
                g.rect(0, 0, width, height);
                g.clip();
            }
            setTextAlign(g, textAlign, width);
            setTextBaseline(g, textBaseline, height);
            g.setFont(font);
            g.setTextAlign(textAlign);
            g.setTextBaseline(textBaseline);
            g.setFillStyle(color);

            String text = this.text;
            if (textClip == TextClip.ELLIPSE)
            {
                text = fitEllipse(g, text, width);
            }
            if (textClip == TextClip.FIT)
            {
                g.fillText(text, 0, 0, width);
            }
            else
            {
                g.fillText(text, 0, 0);
            }
        }
        g.restore();
    }

    public static String fitEllipse(Context2d g, String text, int width)
    {
        double w = g.measureText(text).getWidth();
        if (w > width) return text;

        String e;
        do
        {
            text = text.substring(0, text.length()-1);
            e = text + "...";
            w = g.measureText(e).getWidth();
        }
        while (w > width);
        return e;
    }

    public static void setTextAlign(Context2d g, TextAlign textAlign, int width)
    {
        switch (textAlign)
        {
        case RIGHT:
            g.translate(width, 0);
            break;
        case CENTER:
            g.translate(width/2, 0);
            break;
        }
    }

    public static void setTextBaseline(Context2d g, TextBaseline textBaseline, int height)
    {
        switch (textBaseline)
        {
        case BOTTOM:
            g.translate(0, height);
            break;
        case MIDDLE:
            g.translate(0, height/2);
            break;
        }
    }

    public static enum TextClip
    {
        OVERFLOW,
        HIDDEN,
        ELLIPSE,
        FIT,
    }
}
