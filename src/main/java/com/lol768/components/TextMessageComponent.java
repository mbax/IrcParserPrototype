package com.lol768.components;

public class TextMessageComponent extends MessageComponent {

    private String text;

    public TextMessageComponent(String text) {
        this.text = text;
    }

    @Override
    public String getCssClass() {
        return "normal-text";
    }

    @Override
    public String toString() {
        return super.toString() + " and my text is \"" + text + "\"";
    }
}
