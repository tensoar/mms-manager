package ink.labrador.mmsmanager.integration.transfer.transformer;

import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.util.crypto.CryptoRSA;

public class RSADecodeUsePrivateKeyTransformer implements IFormValueTransformer<String, String> {

    @Override
    public String transform(String value) throws FormValueTransformerException {
        if (value == null) {
            return null;
        }
        CryptoRSA cryptoRSA = CryptoRSA.getInstance();
        if (cryptoRSA == null) {
            throw new FormValueTransformerException("程序为就绪");
        }
        String decoded = cryptoRSA.decodeUsePrivateKey(value);
        if (decoded == null) {
            throw new FormValueTransformerException("非法的加密字符串");
        }
        return decoded;
    }
    
}
