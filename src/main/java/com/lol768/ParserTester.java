package com.lol768;

import com.lol768.components.*;
import sun.plugin2.message.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    addToTree(currentChar, fragment, i);
                    processMessage(fragment.substring(i+1));
                    break out;
                }
            }
        }
        System.out.println("Quit!");
    }

    private void addToTree(char c, String fragment, int index) {

        if (index != 0) {
            lastAdded.addChild(new TextMessageComponent(fragment.substring(0,index)));
        }

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

        if (c == FormatChars.COLOR.getChar()) {
            mc = parseColour(fragment, index);
        }

        if (mc == null) {
            for (TreeNode<MessageComponent> tn : this.root) {
                System.out.println("I'm on level " + tn.getLevel() + ". " + tn.data);
            }
            throw new NotImplementedException();
        }
        this.lastAdded = this.lastAdded.addChild(mc);

    }

    private MessageComponent parseColour(String fragment, int index) {
        String regex = "([0-9][0-9]?)(?:,[0-9][0-9]?)?"; // mIRC accepts 0 => 99 and ignores invalid colours

        // Get 5 characters following the colour character (xx,yy)
        String toMatch = fragment.substring(index+1, index+6);
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(toMatch);

        // No matches? We treat it as a request to back to the normal/default colour
        return null;
    }
}
