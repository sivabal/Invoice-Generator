package com.bill.utility;

import java.util.regex.Pattern;

public class Regex {

	public static Pattern regexProdId = Pattern.compile("^[1-9][0-9]{2,}$");
	public static Pattern regexProdName = Pattern.compile("^[A-Za-z]+(\\s[A-Za-z]+)*$");
	public static Pattern regexProdRate = Pattern.compile("^[0-9]+([.]?[0-9]+)?$");
	public static Pattern regexProdSgst = Pattern.compile("^[0-9]+([.]?[0-9]+)?$");
	public static Pattern regexProdCgst = Pattern.compile("^[0-9]+([.]?[0-9]+)?$");
	public static Pattern regexInvoiceNumber = Pattern.compile("^INV-[6-9][0-9]{3,}$");
}
