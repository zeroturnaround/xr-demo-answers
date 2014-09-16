package com.zeroturnaround.rebelanswers.mvc.taglib;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JspUtils {
  private static Pattern whitespace = Pattern.compile("\\p{javaWhitespace}+");
  private static Pattern nonword = Pattern.compile("[^\\p{Alnum}_]");

  public static String[] sortStringArray(String[] strings) {
    List<String> list = Arrays.asList(strings);
    Collections.sort(list);
    String[] result = new String[strings.length];
    return list.toArray(result);
  }

  public static String sanitizeForUrl(final String text) {
    if (null == text) return "";

    String result = text;
    Matcher mws = whitespace.matcher(result);
    result = mws.replaceAll("_");
    Matcher mnw = nonword.matcher(result);
    result = mnw.replaceAll("");

    return result.toLowerCase();
  }
}
