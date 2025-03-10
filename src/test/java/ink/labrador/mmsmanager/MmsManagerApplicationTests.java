package ink.labrador.mmsmanager;

import ink.labrador.mmsmanager.util.crypto.CryptoRSA;
import ink.labrador.mmsmanager.util.digest.DigestSHA512;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MmsManagerApplicationTests {
	@Autowired
	private CryptoRSA cryptoRSA;

	@Test
	void contextLoads() {
		String data = cryptoRSA.encodeUsePublicKeyAsHex("12345");
		System.out.println(data);
	}

}
