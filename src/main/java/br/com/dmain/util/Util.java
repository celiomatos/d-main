package br.com.dmain.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class Util {

    public static String arrayToCommaDelimited(String strArray) {
        strArray = StringUtils.trimAllWhitespace(strArray);
        return strArray.replace(']', ')').replace('[', '(');
    }

    public static String arrayToStringCommaDelimited(String strArray) {
        strArray = StringUtils.trimAllWhitespace(strArray);
        return strArray.replaceAll("]", "')")
                .replaceAll(",", "','")
                .replaceAll(Pattern.quote("["), "('");
    }
}
