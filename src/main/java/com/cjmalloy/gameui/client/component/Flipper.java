package com.cjmalloy.gameui.client.component;

import com.cjmalloy.gameui.client.core.UiElement;
import com.cjmalloy.gameui.client.core.UiProxy;

/**
 * Panel that flips between two children, only one visible at a time.
 */
public class Flipper implements UiProxy {

  private DeckLayoutPanel deck;
  private UiElement el;

  /**
   * Constructor.
   */
  public Flipper(int x, int y, UiProxy first, UiProxy second) {
    deck = new DeckLayoutPanel(x, y, 0, 0);
    el = deck.getElement();
    deck.add(first);
    deck.add(second);
  }

  /**
   * Set the visible widget.
   */
  public void setState(boolean b) {
    if (b) {
      deck.setIndex(0);
    } else {
      deck.setIndex(1);
    }
  }

  @Override
  public UiElement getElement() {
    return el;
  }

}
