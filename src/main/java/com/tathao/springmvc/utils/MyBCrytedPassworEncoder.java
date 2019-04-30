package com.tathao.springmvc.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyBCrytedPassworEncoder {
	
	public String encoder(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}
	
	
	public static void main(String...strings) {
		MyBCrytedPassworEncoder b = new MyBCrytedPassworEncoder();
		String password = "123";
		String crytedPassword = b.encoder(password);
		System.out.println(crytedPassword);
	}

}
