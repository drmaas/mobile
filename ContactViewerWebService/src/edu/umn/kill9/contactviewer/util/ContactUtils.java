package edu.umn.kill9.contactviewer.util;

import android.content.Context;

/**
 * User: drmaas
 * Date: 2/19/13
 */
public class ContactUtils {

    /**
     * check if a string is empty or null
     *
     * @param s
     * @return
     */
    public static boolean empty(String s) {
        if (s == null || s == "" || s.length() < 1) {
            return true;
        }
        else {
            return false;
        }
    }

}
