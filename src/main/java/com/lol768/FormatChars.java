package com.lol768;

/**
 * Thanks mbaxter for providing a reference for these.
 */
public enum FormatChars {

    COLOR('\u0003'),
    BOLD('\u0002'),
    ITALIC('\u001d'),
    UNDERLINE('\u001f'),
    REVERSE('\u0016'),
    RESET('\u000f');

    private char character;

    FormatChars(char character) {
        this.character = character;
    }

    public String toString() {
        return String.valueOf(this.character);
    }

    public char getChar() {
        return this.character;
    }

}
