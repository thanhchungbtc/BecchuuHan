package com.btc.supports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
   static public SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
   static public SimpleDateFormat fullDateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

   public static Date dateFromString(String string) {
      if (string == null) return null;
      try {
         return dateFormatter.parse(string);
      } catch (ParseException e) {
         e.printStackTrace();
         // TODO Auto-generated catch block
         return null;
      }
   }

   public static String stringFromFullDate(Date date) {
      if (date == null) return null;
      return fullDateFormatter.format(date).toString();
   }

   public static Date fullDateFromString(String string) {
      if (string == null) return null;
      try {
         return fullDateFormatter.parse(string);
      } catch (ParseException e) {
         e.printStackTrace();
         // TODO Auto-generated catch block
         return null;
      }
   }

   public static String stringFromDate(Date date) {
      if (date == null) return null;
      return dateFormatter.format(date).toString();
   }


   public static String convertGlobToRegEx(String line) {
      line = line.trim();
      int strLen = line.length();
      StringBuilder sb = new StringBuilder(strLen);
      // Remove beginning and ending * globs because they're useless
      if (line.startsWith("*")) {
         line = line.substring(1);
         strLen--;
      }
      if (line.endsWith("*")) {
         line = line.substring(0, strLen - 1);
         strLen--;
      }
      boolean escaping = false;
      int inCurlies = 0;
      for (char currentChar : line.toCharArray()) {
         switch (currentChar) {
            case '*':
               if (escaping)
                  sb.append("\\*");
               else
                  sb.append(".*");
               escaping = false;
               break;
            case '?':
               if (escaping)
                  sb.append("\\?");
               else
                  sb.append('.');
               escaping = false;
               break;
            case '.':
            case '(':
            case ')':
            case '+':
            case '|':
            case '^':
            case '$':
            case '@':
            case '%':
               sb.append('\\');
               sb.append(currentChar);
               escaping = false;
               break;
            case '\\':
               if (escaping) {
                  sb.append("\\\\");
                  escaping = false;
               } else
                  escaping = true;
               break;
            case '{':
               if (escaping) {
                  sb.append("\\{");
               } else {
                  sb.append('(');
                  inCurlies++;
               }
               escaping = false;
               break;
            case '}':
               if (inCurlies > 0 && !escaping) {
                  sb.append(')');
                  inCurlies--;
               } else if (escaping)
                  sb.append("\\}");
               else
                  sb.append("}");
               escaping = false;
               break;
            case ',':
               if (inCurlies > 0 && !escaping) {
                  sb.append('|');
               } else if (escaping)
                  sb.append("\\,");
               else
                  sb.append(",");
               break;
            default:
               escaping = false;
               sb.append(currentChar);
         }
      }
      return sb.toString();
   }

   public static String convertGlobToRegExCaseInsensitive(String line) {
      return "(?i)" + convertGlobToRegEx(line);
   }
}
