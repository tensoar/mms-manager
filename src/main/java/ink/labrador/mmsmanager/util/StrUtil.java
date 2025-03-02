package ink.labrador.mmsmanager.util;

import ink.labrador.mmsmanager.constant.SaltMixType;
import org.springframework.util.StringUtils;

public class StrUtil extends StringUtils {
    public static  String removeSuffix(String str, CharSequence suffix) {
        if (!StrUtil.hasLength(str) || !StrUtil.hasLength(suffix)) {
            return str;
        }

        if (str.endsWith(suffix.toString())) {
            return str.substring(0, str.length() - suffix.length() - 1);// 截取前半段
        }
        return str;
    }

    public static String mixSalt(final String s1, final String salt, SaltMixType mixType) {
        if (s1 == null) {
            return salt;
        }
        if (salt == null) {
            return s1;
        }
        if (mixType == SaltMixType.PRE) {
            return salt + s1;
        }
        if (mixType == SaltMixType.AFTER) {
            return s1 + salt;
        }
        int maxLength = Math.max(s1.length(), salt.length());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxLength; i ++) {
            if (s1.length() > i) {
                builder.append(s1.charAt(i));
            }
            if (salt.length() > i) {
                builder.append(salt.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String mixSaltCharByChar(String s, String salt) {
        return mixSalt(s, salt, SaltMixType.CHAR_BY_CHAR);
    }
}

