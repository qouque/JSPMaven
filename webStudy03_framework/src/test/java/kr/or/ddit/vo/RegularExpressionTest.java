package kr.or.ddit.vo;

import org.junit.Test;

public class RegularExpressionTest {
	
//	@Test
	public void regexTest() {
		String password = "@2";
		String regex = "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%&*]).*";
		System.out.println(password.matches(regex));
	}
	
	@Test
	public void telRegexTest() {
		String tel = "070-09313-62169";
		String regex = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
		System.out.println(tel.matches(regex));
	}
}
