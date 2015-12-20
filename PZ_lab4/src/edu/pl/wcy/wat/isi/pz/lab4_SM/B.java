package edu.pl.wcy.wat.isi.pz.lab4_SM;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
public class B implements Serializable{
	private static final long serialVersionUID = 1L;

	private int B_ID;
	private char B_char;
	private A a;
	private double B_double;
	private String B_String;
	

	protected Set<C> zbiorC = new HashSet<C>();
	
	public void dodajDoZbioru(C nowy)
	{
		zbiorC.add(nowy);
		if(!nowy.getZbiorB().contains(this))nowy.dodajDoZbioru(this); //unikamy rekurencyjnego wywo³ania
	}
	public B(char c, double d, String s, A a) {
		this.B_char=c;
		this.B_double=d;
		this.B_String=s;
		this.setA(a);
		if(a!=null)	a.dodajDoZbioru(this);
		Main.addB(this);
	}
	
	public B(){
	//	Main.addB(this);
	}

	@Override
	public String toString()
	{
		return ("Klasa=B, ID="+this.B_ID+", String="+this.B_String+", char="+this.B_char+", double="+this.B_double);
	}
	
	
	public A getA() {
		return this.a;
	}
	
	public void setA(A _a) {
		this.a = _a;
	}
	
	public int getB_ID() {
		return B_ID;
	}

	public void setB_ID(int b_ID) {
		B_ID = b_ID;
	}

	public char getB_char() {
		return B_char;
	}

	public void setB_char(char b_char) {
		B_char = b_char;
	}

	public double getB_double() {
		return B_double;
	}

	public void setB_double(double b_double) {
		B_double = b_double;
	}

	public String getB_String() {
		return B_String;
	}

	public void setB_String(String b_String) {
		B_String = b_String;
	}

	public Set<C> getZbiorC() {
		return this.zbiorC;
	}


}
	
