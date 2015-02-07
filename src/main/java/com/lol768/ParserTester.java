package com.lol768;

import com.lol768.components.MessageComponent;
import com.lol768.components.TextMessageComponent;

/**
 * Hello world!
 */
public class ParserTester {


    private String msg;

    private TreeNode<MessageComponent> root = new TreeNode<>(new TextMessageComponent(""));

    public ParserTester(String msg) {
        this.msg = msg;
    }

    public static void main(String[] args) {
        String msg = FormatChars.BOLD + "Bold test! " + FormatChars.COLOR + "4,5Colourful text " + FormatChars.REVERSE + "with reverse";
        new ParserTester(msg).processMessage();
    }

    private void processMessage() {
        for (int i = 0; i < this.msg.length(); i++) {
            char currentChar = this.msg.charAt(i);
            System.out.println(currentChar);
            for (FormatChars item : FormatChars.values()) {
                if (currentChar == item.getChar()) {
                    System.out.println("Got special character " + (int) currentChar);

                }
            }

        }
    }
}
