package com.cjmalloy.gameui.client.component;

import java.util.ArrayList;
import java.util.List;


public class DeckLayoutPanel implements HasChildren
{

    private List<UiElement> deck = new ArrayList<UiElement>();
    private Panel panel;
    private int index = -1;
    private UiElement currentElement = null;

    public DeckLayoutPanel(int x, int y, int width, int height)
    {
        panel = new Panel(x, y, width, height);
    }

    @Override
    public void add(UiElement child)
    {
        deck.add(child);
        if (null == currentElement)
        {
            setIndex(0);
        }
    }

    @Override
    public void add(UiProxy proxy)
    {
        add(proxy.getElement());
    }

    @Override
    public UiElement getElement()
    {
        return panel;
    }

    @Override
    public void remove(UiElement child)
    {
        deck.remove(child);
        if (currentElement == child)
        {
            setIndex(Math.max(0, index - 1));
        }
    }

    @Override
    public void remove(UiProxy proxy)
    {
        add(proxy.getElement());
    }

    public void setIndex(int i)
    {
        if (this.index == i) return;

        this.index = i;
        if (null != currentElement)
        {
            currentElement.setVisible(false);
            panel.remove(currentElement);
        }
        if (i < 0 || i >= deck.size())
        {
            index = -1;
            currentElement = null;
        }
        else
        {
            currentElement = deck.get(index);
            currentElement.setVisible(true);
            panel.add(currentElement);
        }
    }


}
