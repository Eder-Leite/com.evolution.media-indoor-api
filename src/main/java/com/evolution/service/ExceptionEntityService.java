package com.evolution.service;

public class ExceptionEntityService extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExceptionEntityService(String msg) {
		super(msg);
	}
}
