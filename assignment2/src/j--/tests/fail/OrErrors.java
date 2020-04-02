package fail;

import java.lang.System;

public class LexicalErrors {

    public static void main(String[] args) {
        System.out.println(false || "not a boolean");
    }

}
