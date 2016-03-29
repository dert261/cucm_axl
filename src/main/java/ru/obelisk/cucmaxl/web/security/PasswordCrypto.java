package ru.obelisk.cucmaxl.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordCrypto {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private static PasswordCrypto instance;
	//private static PasswordEncoder encoder;
	
	public static PasswordCrypto getInstance() {
		if(instance == null) {
			instance = new PasswordCrypto();
	    }
		return instance;
	}

	public String encrypt(String str) {
		return passwordEncoder.encode(str);
	}
}
