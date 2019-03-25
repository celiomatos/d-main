package br.com.dmain.dmain.util;

import org.springframework.util.StringUtils;

public class Util {

    public static String arrayToCommaDelimited(String strArray) {
        String result = strArray.replace(']', ')').replace('[', '(');
        result = StringUtils.trimAllWhitespace(result);

        return result;
    }
}
