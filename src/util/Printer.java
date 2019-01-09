package util;

import java.io.*;

import javax.servlet.jsp.JspWriter;

public class Printer {
	private Writer out;
	public Printer(Writer out) {
		this.out = out;
	}
	public synchronized void print(String str) {
		try {
			out.write("<p>" + str + "</p>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
