package com.evolution.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Generator {

	public String password(String args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode(args));
		return encoder.encode(args);
	}

}
