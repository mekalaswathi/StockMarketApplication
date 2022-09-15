package com.stockmarketapp.searchstocksservice.exception;

public class CompanyNotFoundException extends Exception {

	public CompanyNotFoundException() {}
	public CompanyNotFoundException(String m) {
		super(m);
	}
	public CompanyNotFoundException(Exception e) {
		super(e);
	}
	public CompanyNotFoundException(String m, Exception e) {
		super(m, e);
	}
	
}
