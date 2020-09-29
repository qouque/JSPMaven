package kr.or.ddit.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.utils.SecurityUtils.CipherVO;
import kr.or.ddit.utils.SecurityUtils.RSAKey;

public class SecurityUtilsTest {

	String plain;
	Map<String, CipherVO> resultMap;
	
	@Before
	public void setUp() throws Exception {
		plain = "한글데이터평문";
		resultMap = new HashMap<>();
	}

//	@Test
	public void testEncryptSha512() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecryptAES() {
		System.out.println("===================AES===================");
		String encoded = SecurityUtils.encryptARS(plain, resultMap);
		System.out.println(encoded);
		
		String plain = SecurityUtils.decryptAES(encoded, resultMap.get("cipherSpec"));
		System.out.println(plain);
		
		System.out.println("=========================================");
	}

	@Test
	public void testEncryptRSA() {
		System.out.println("===================RSA===================");
		String encoded = SecurityUtils.encryptRSA(plain, RSAKey.PRIVATEKEY, resultMap);
		System.out.println(encoded);
		String plain = SecurityUtils.decryptRSA(encoded, RSAKey.PUBLICKEY, resultMap.get("cipherSpec"));
		System.out.println(plain);
		System.out.println("=========================================");
	}

}















