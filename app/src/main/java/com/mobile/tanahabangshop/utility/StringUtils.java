package com.mobile.tanahabangshop.utility;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class StringUtils {
    public static String getFirstName(String fullName) {
        String splitted[] = fullName.trim().split(" +");
        String firstName = "";
        if (splitted.length <= 2) {
            firstName = splitted[0].replaceAll("\\s+", "");
            return firstName;
        } else {
            for (int i = 0; i < splitted.length - 1; i++) {
                String sp = splitted[i];
                sp = sp.replaceAll("\\s+", "");
                if (sp.length() > 0) {
                    firstName = firstName.concat(sp + " ");
                }
            }
            firstName = firstName.substring(0, firstName.length() - 1);
            return firstName;
        }
    }
}
