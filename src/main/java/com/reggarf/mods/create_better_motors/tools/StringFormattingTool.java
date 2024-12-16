package com.reggarf.mods.create_better_motors.tools;

public class StringFormattingTool {

    public static final String[] postfixes = new String[] {
            "",
            "K",
            "M",
            "B",
            "T",
            "Q"
    };


    public static String formatLong(long l) {
        int d = 0;
        double number = l;
        while (number >= 1000 && d < postfixes.length) {
            number /= 1000;
            d++;
        }

        if (l >= 1000 && Math.floor(number) != number)
            return String.format("%.1f%s", number, postfixes[d]);

        return (int)number + postfixes[d];
    }


    public static String formatFloat(float f) {
        int d = 0;
        while (f >= 1000 && d < postfixes.length) {
            f/=1000;
            d++;
        }
        return String.format("%.1f%s", f, postfixes[d]);
    }

    public static String formatPercentFloat(float f) {
        return String.format("%.1f", f * 100);
    }

}
