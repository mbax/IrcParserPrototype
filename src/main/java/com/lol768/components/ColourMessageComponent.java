package com.lol768.components;

public class ColourMessageComponent extends MessageComponent {

    private int foreground;
    private int background;

    @Override
    public String getCssClass() {
        return "color";
    }

    public ColourMessageComponent(int foreground, int background) {
        this.foreground = foreground;
        this.background = background;
    }
}
