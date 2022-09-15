package com.stockmarketapp.searchstocksservice.exception;

public class StockNotFoundException extends Exception {

	public StockNotFoundException() {}
	public StockNotFoundException(String m) {
		super(m);
	}
	public StockNotFoundException(Exception e) {
		super(e);
	}
	public StockNotFoundException(String m, Exception e) {
		super(m, e);
	}
	
}
