package edu.pl.wcy.wat.isi.pz.lab4_SM;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class B implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int B_ID;
	@Column
	private char B_char;
	@ManyToOne
	@Column
	private A a;
	@Column
	private double B_double;
	@Column
	private String B_String;
	
	//@ManyToMany(mappedBy="B.zbiorC")
	@Column
	protected Set<C> zbiorC = new HashSet<C>();
	
	public void dodajDoZbioru(C nowy)
	{
		zbiorC.add(nowy);
	}
	public B(char c, double d, String s, A a) {
		this.B_char=c;
		this.B_double=d;
		this.B_String=s;
		this.setA(a);
		a.dodajDoZbioru(this);
	}
	
	public B(){}

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
	
