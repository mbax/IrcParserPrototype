package com.lol768;

import com.lol768.components.*;
import sun.plugin2.message.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Hello world!
 */
public class ParserTester {


    private String msg;

    private TreeNode<MessageComponent> root = new TreeNode<>(new TextMessageComponent(""));
    private TreeNode<MessageComponent> lastAdded = root;

    public ParserTester(String msg) {
        this.msg = msg;
        processMessage(msg);
    }

    public static void main(String[] args) {
        String msg = FormatChars.BOLD + "Bold test! " + FormatChars.COLOR + "4,5Colourful text " + FormatChars.REVERSE + "with reverse";
        new ParserTester(msg);
    }

    private void processMessage(String fragment) {
        out:
        for (int i = 0; i < fragment.length(); i++) {
            char currentChar = fragment.charAt(i);
            System.out.println(currentChar);
            for (FormatChars item : FormatChars.values()) {
                if (currentChar == item.getChar()) {
                    System.out.println("Got special character " + (int) currentChar);
                    addToTree(currentChar);
                    processMessage(fragment.substring(i+1));
                    break out;
                }
            }
        }
        System.out.println("Quit!");
    }

    private void addToTree(char c) {
        MessageComponent mc = null;
        if (c == FormatChars.BOLD.getChar()) {
            mc = new BoldMessageComponent();
        }

        if (c == FormatChars.UNDERLINE.getChar()) {
            mc = new UnderlineMessageComponent();
        }

        if (c == FormatChars.REVERSE.getChar()) {
            mc = new ReverseMessageComponent();
        }

        if (mc == null) {
            throw new NotImplementedException();
        }
        this.lastAdded = this.lastAdded.addChild(mc);

    }
}
