package com.lol768.components;

public class ColourMessageComponent extends MessageComponent {

    private Integer foreground;
    private Integer background;

    @Override
    public String getCssClass() {
        return "color-" + foreground + "-" + background;
    }

    public ColourMessageComponent(Integer foreground, Integer background) {
        this.foreground = foreground;
        this.background = background;
    }

    @Override
    public String toString() {
        return super.toString() + " with colours " + foreground + " and BG: " + background;
    }

    public ColourMessageComponent getInverse() {
        return new ColourMessageComponent(background, foreground);
    }
}
