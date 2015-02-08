package com.lol768.components;

public abstract class MessageComponent {

    public abstract String getCssClass();

   public String toString() {
       return "My CSS class is \"" + this.getCssClass() + "\"";
   }
}
