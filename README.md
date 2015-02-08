Input:

`FormatChars.BOLD + "Bold test! " + FormatChars.COLOR + "4,5Colourful text " + FormatChars.REVERSE + "with reverse"`

Output:

````html
<span class="default-color"><span class="bold">Bold test! <span class="color-4-5">Colourful text <span class="color-5-4">with reverse</span></span></span></span>
```

Tree representation:

![](https://i.imgur.com/Z2Q1yLq.png)