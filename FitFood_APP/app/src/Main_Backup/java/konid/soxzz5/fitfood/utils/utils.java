package konid.soxzz5.fitfood.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Soxzer on 09/12/2016.
 */

public class utils {

    public static Boolean findMatch(String myString, String pattern) {

        String match = "";

        // Pattern to find code
        Pattern regEx = Pattern.compile(pattern);

        // Find instance of pattern matches
        Matcher m = regEx.matcher(myString);
        if (m.find()) {
            return true;
        }
        else
        {
            return false;
        }
    }
}
