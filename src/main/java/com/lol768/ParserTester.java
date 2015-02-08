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

    private TreeNode<MessageComponent> root = new TreeNode<>(new ColourResetMessageComponent());
    private TreeNode<MessageComponent> lastAdded = root;

    private int shouldSkip = 1;

    public ParserTester(String msg) {
        this.msg = msg;
        processMessage(msg);
        int spansToClose = 0;
        for (TreeNode<MessageComponent> tn : this.root) {
            if (!tn.data.getCssClass().equals("")) {
                System.out.print("<span class=\"" + tn.data.getCssClass() + "\">");
                spansToClose++;

            } else {
                //System.out.print("<span>");
            }

            if (tn.data instanceof TextMessageComponent) {
                System.out.print(((TextMessageComponent)tn.data).getText());
            }
        }

        for (int i = spansToClose; i > 0; i--)  {
            System.out.print("</span>");
        }
    }

    public static void main(String[] args) {
        String msg = FormatChars.BOLD + "Bold test! " + FormatChars.COLOR + "4,5Colourful text " + FormatChars.REVERSE + "with reverse";
        new ParserTester(msg);
    }

    private void processMessage(String fragment) {
        System.out.println("Got fragment: " + fragment);
        boolean done = false;
        out:
        for (int i = 0; i < fragment.length(); i++) {
            char currentChar = fragment.charAt(i);
            for (FormatChars item : FormatChars.values()) {
                if (currentChar == item.getChar()) {
                    System.out.println("Got special character " + (int) currentChar);
                    addToTree(currentChar, fragment, i);
                    processMessage(fragment.substring(i+shouldSkip));
                    done = true;
                    break out;
                }
            }
        }
        if (!done) {
            lastAdded.addChild(new TextMessageComponent(fragment));
        }
    }

    private void addToTree(char c, String fragment, int index) {

        shouldSkip = 1;

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
            TreeNode<MessageComponent> parent = new TreeNode<MessageComponent>(null);
            parent.parent = lastAdded;
            mc = new ColourMessageComponent(null, null);

            while ((parent = parent.parent) != null) {
                if (parent.data instanceof ColourMessageComponent) {
                    mc = ((ColourMessageComponent)parent.data).getInverse();
                }
            }
        }

        if (c == FormatChars.COLOR.getChar()) {
            mc = parseColour(fragment, index);
        }

        if (mc == null) {

            throw new NotImplementedException();
        }
        this.lastAdded = this.lastAdded.addChild(mc);

    }

    private MessageComponent parseColour(String fragment, int index) {
        String regex = "([0-9][0-9]?)(?:,([0-9][0-9]?))?"; // mIRC accepts 0 => 99 and ignores invalid colours

        // Get 5 characters following the colour character (xx,yy)
        String toMatch = fragment.substring(index+1, index+6);
        System.out.println("Inspecting " + toMatch);
        Pattern pattern = Pattern.compile(regex);


        Matcher match = pattern.matcher(toMatch);

        if (match.find()) {
            if (match.groupCount() == 1) {
                System.out.println("It's a foreground!");
                shouldSkip = 2;
                return new ColourMessageComponent(Integer.parseInt(match.group(1)), null);
            } else {
                System.out.println("It's a foreground and background!");
                shouldSkip = 4;
                return new ColourMessageComponent(Integer.parseInt(match.group(1)), Integer.parseInt(match.group(2)));
            }
        } else {
            // No matches? We treat it as a request to back to the normal/default colour
            System.out.println("It's a reset!");
            return new ColourMessageComponent(null, null);
        }

    }
}
