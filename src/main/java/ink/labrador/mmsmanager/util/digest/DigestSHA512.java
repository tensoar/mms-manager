package ink.labrador.mmsmanager.util.digest;

import ink.labrador.mmsmanager.constant.SaltMixType;
import ink.labrador.mmsmanager.util.StrUtil;
import org.apache.commons.codec.digest.DigestUtils;

public class DigestSHA512 {
    public static String digestAsHex(String data) {
        return DigestUtils.sha512Hex(data);
    }

    public static String digestAsHex(String data, String salt, SaltMixType mixType) {
        return digestAsHex(StrUtil.mixSalt(data, salt, mixType));
    }

    public static boolean verify(String data, String hex) {
        if (!StrUtil.hasLength(data) || !StrUtil.hasLength(hex)) {
            return false;
        }
        return digestAsHex(data).equals(hex);
    }

    public static boolean verify(String data, String salt, SaltMixType mixType, String hex) {
        return verify(StrUtil.mixSalt(data, salt, mixType), hex);
    }
}
