package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.component.skin.DefaultAccordianHandleSkin;
import com.cjmalloy.gameui.client.event.MouseClickEvent;
import com.cjmalloy.gameui.client.event.MouseClickHandler;

public class Accordion extends ScrollPanel
{
    public boolean onlyOneOpen = false;

    private MouseClickHandler accordianShuffleHandler = new MouseClickHandler()
    {

        @Override
        public void onMouseClick(MouseClickEvent event)
        {
            AccordionFile f = (AccordionFile) event.source.parent;
            int index = children.indexOf(f);
            f.toggle();

            if (onlyOneOpen)
            {
                for (int i = 0; i < children.size(); i++)
                {
                    f = (AccordionFile) children.get(i);
                    if (i != index)
                    {
                        f.setOpen(false);
                    }
                }
            }
        }
    };

    public Accordion(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    @Override
    public void add(UiElement child)
    {
        add(child, "");
    }

    public void add(UiElement child, Button handle)
    {
        AccordionFile a = new AccordionFile(child, handle);
        handle.addMouseClickHandler(accordianShuffleHandler);
        handle.toggle = true;
        if (children.size() == 0)
        {
            a.setOpen(true);
        }
        super.add(a);
        layoutPanels();
        redrawNeeded = true;
    }

    public void add(UiElement child, String handleText)
    {
        Button handle = new  Button();
        handle.skin = new DefaultAccordianHandleSkin();
        handle.setText(handleText);
        add(child, handle);
    }

    public void add(UiProxy proxy, Button handle)
    {
        add(proxy.getElement(), handle);
    }

    public void add(UiProxy proxy, String handleText)
    {
        add(proxy.getElement(), handleText);
    }

    @Override
    public void remove(UiElement child)
    {
        for (UiElement c : children)
        {
            AccordionFile a = (AccordionFile) c;
            if (a.file == child)
            {
                super.remove(a);
                layoutPanels();
                redrawNeeded = true;
                return;
            }
        }
    }

    private void layoutPanels()
    {
        int yoff = 0;
        for (int i = 0; i < children.size(); i++)
        {
            AccordionFile f = (AccordionFile) children.get(i);
            f.y = yoff;
            yoff += f.height;
        }
    }

    private class AccordionFile extends Panel
    {

        public final UiElement file;
        public final Button handle;

        public int heightOpen;
        public int heightClosed;

        public boolean open = false;

        public AccordionFile(UiElement file, Button handle)
        {
            super(0, 0, Accordion.this.width, handle.height);
            this.file = file;
            this.handle = handle;
            this.heightClosed = handle.height;
            this.heightOpen = handle.height + file.height;

            handle.x = 0;
            handle.y = 0;
            handle.width = width;
            add(handle);

            file.x = 0;
            file.y = heightClosed;
            file.width = width;
            file.setVisible(false);
            add(file);

            height = heightClosed;
        }

        public void setOpen(boolean value)
        {
            if (open != value)
            {
                open = value;
                handle.setDown(open);
                height = open ? heightOpen : heightClosed;
                file.setVisible(open);
                redrawNeeded = true;

                layoutPanels();
                Accordion.this.redrawNeeded = true;
            }
        }

        public void toggle()
        {
            setOpen(!open);
        }
    }

    @Override
    public void scrollIntoView(UiElement child)
    {
        AccordionFile a = getFile(child);
        if (a == null) return;

        a.setOpen(true);
        super.scrollIntoView(a);
    }

    private AccordionFile getFile(UiElement child)
    {
        if (!(child.parent instanceof AccordionFile)) return null;
        AccordionFile a = (AccordionFile) child.parent;
        if (!children.contains(a)) return null;
        return a;
    }
}
