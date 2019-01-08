package servlets;

import java.io.*;

public class Printer {
	private PrintWriter out;
	public Printer(PrintWriter out) {
		this.out = out;
	}
	public synchronized void print(String str) {
		out.print("<p>" + str + "</p>");
	}
}
