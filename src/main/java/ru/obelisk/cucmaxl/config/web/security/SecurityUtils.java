package ru.obelisk.cucmaxl.config.web.security;

public class SecurityUtils {
	public static String addParamToURL(String url, String param, String value,
            boolean replace) {
        if (replace == true)
            url = removeParamFromURL(url, param);
        return url + ((url.indexOf("?") == -1) ? "?" : "&") + param + "="
                + value;
    }
 
    public static String removeParamFromURL(String url, String param) {
        String sep = "&";
        int startIndex = url.indexOf(sep + param + "=");
        boolean firstParam = false;
        if (startIndex == -1) {
            startIndex = url.indexOf("?" + param + "=");
            if (startIndex != -1) {
                startIndex++;
                firstParam = true;
            }
        }
 
        if (startIndex != -1) {
            String startUrl = url.substring(0, startIndex);
            String endUrl = "";
            int endIndex = url.indexOf(sep, startIndex + 1);
            if(firstParam && endIndex != 1) {
                //remove separator from remaining url
                endUrl = url.substring(endIndex + 1);
            }
            return startUrl + endUrl;
        }
 
        return url;
    }
}
