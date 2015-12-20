package edu.pl.wcy.wat.isi.pz.lab4_SM;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class C implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int C_ID;
	@Column
	private char C_char;
	@Column
	private double C_double;
	@Column
	private String C_String;
	
	//@ManyToMany(mappedBy="C.zbiorB")
	@Column
	protected Set<B> zbiorB = new HashSet<B>(); 
	
	public void dodajDoZbioru(B nowy)
	{
		zbiorB.add(nowy);
	}
	public Set<B> getZbiorB()
	{
		return this.zbiorB;
	}
	
	public C(){}
	
	public C(char c, double d, String s, B b) {
		this.C_char=c;
		this.C_double=d;
		this.C_String=s;
		this.dodajDoZbioru(b);
		b.dodajDoZbioru(this);
	}
	
	@Override
	public String toString()
	{
		return ("Klasa=C, ID="+this.C_ID+", String="+this.C_String+", char="+this.C_char+", double="+this.C_double);
	}
	
	public int getC_ID() {
		return C_ID;
	}
	public void setC_ID(int c_ID) {
		C_ID = c_ID;
	}
	public char getC_char() {
		return C_char;
	}
	public void setC_char(char c_char) {
		C_char = c_char;
	}
	public double getC_double() {
		return C_double;
	}
	public void setC_double(double c_double) {
		C_double = c_double;
	}
	public String getC_String() {
		return C_String;
	}
	public void setC_String(String c_String) {
		C_String = c_String;
	}
}
