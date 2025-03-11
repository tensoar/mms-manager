package ink.labrador.mmsmanager.util;

import ink.labrador.mmsmanager.properties.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;

public class WebUtil {
    public static String getToken(HttpServletRequest request, SecurityProperties securityProperties) {
        String token = request.getHeader(securityProperties.getTokenHeaderName());
        if (token == null || token.length() < securityProperties.getTokenPrefix().length()) {
            return null;
        }
        return token.substring(securityProperties.getTokenPrefix().length());
    }
}
