package ink.labrador.mmsmanager.util.crypto;

import ink.labrador.mmsmanager.properties.CryptoProperties;
import ink.labrador.mmsmanager.util.ByteUtil;
import ink.labrador.mmsmanager.util.FileUtil;
import ink.labrador.mmsmanager.util.SpringUtil;
import ink.labrador.mmsmanager.util.StrUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Order(-1)
public class CryptoRSA {
    private final Logger logger = LoggerFactory.getLogger(CryptoRSA.class);
    private final String TRANSFORM = "RSA/ECB/PKCS1Padding";
    private final String ALGORITHM = "RSA";
    private PrivateKey privateKey = null;
    private PublicKey publicKey = null;
    private final CryptoProperties cryptoProperties;
    @Getter
    private static CryptoRSA instance;

    public CryptoRSA(CryptoProperties cryptoProperties) {
        this.cryptoProperties = cryptoProperties;
        loadPrivateKey();
        loadPublicKey();
        instance = this;
    }

    private void loadPrivateKey() {
        if (!StrUtil.hasLength(cryptoProperties.getRsa().getPrivateKeyFile())) {
            logger.warn("Private key file was not specified ...");
            return;
        }
        try {
            logger.info("load private key: " + cryptoProperties.getRsa().getPrivateKeyFile());
            InputStream inputStream = FileUtil.loadFileAsInputStream(cryptoProperties.getRsa().getPrivateKeyFile());
            byte[] bytes = FileUtil.readAllBytesAndClose(inputStream);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            privateKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Load private key failed ...");
        }
    }

    private void loadPublicKey() {
        CryptoProperties cryptoProperties = SpringUtil.getBean(CryptoProperties.class);
        if (!StrUtil.hasLength(cryptoProperties.getRsa().getPublicKeyFile())) {
            logger.warn("Public key file was not specified ...");
            return;
        }
        try {
            logger.info("load public key: " + cryptoProperties.getRsa().getPublicKeyFile());
            InputStream inputStream = FileUtil.loadFileAsInputStream(cryptoProperties.getRsa().getPublicKeyFile());
            byte[] bytes = FileUtil.readAllBytesAndClose(inputStream);
            String content = new String(bytes, StandardCharsets.UTF_8)
                    .replace("-", "")
                    .replace("BEGIN PUBLIC KEY", "")
                    .replace("END PUBLIC KEY", "")
                    .replace("\n", "")
                    .replace("\r", "")
                    .trim();
            byte[] decodedContent = Base64.getDecoder().decode(content);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedContent);
            publicKey = KeyFactory.getInstance(ALGORITHM).generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Load public key failed ...");
        }
    }

    public String decodeUsePrivateKey(String dataInHex) {
        return decode(dataInHex, privateKey);
    }

    public String decodeUsePublicKey(String dataInHex) {
        return decode(dataInHex, publicKey);
    }

    public String encodeUsePrivateKey(String data) {
        return encode(data, privateKey);
    }

    public String encodeUsePublicKeyAsHex(String data) {
        return encode(data, publicKey);
    }

    private String encode(String data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return ByteUtil.toHexString(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    private String decode(String dataInHex, Key key) {
        try {
            byte[] data = ByteUtil.parseHexString(dataInHex);
            if (data == null) {
                logger.warn("非法的HEX字符串: " + dataInHex);
                return null;
            }
            Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(data);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
