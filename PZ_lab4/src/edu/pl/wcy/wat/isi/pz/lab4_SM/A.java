package edu.pl.wcy.wat.isi.pz.lab4_SM;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="A")
public class A implements Serializable{
	private static final long serialVersionUID = 1L;
    
    private static EntityManager entitymanager;
    private static EntityManagerFactory emfactory;
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int A_ID;
	@Column
	private char A_char;
	@Column
	private double A_double;
	@Column
	private String A_String;
	
	//@OneToMany(mappedBy="a.zbiorb")
	@Column
	protected Set<B> zbiorB = new HashSet<B>(); 
	
	public A(){}
	
	public A(char c, double d, String s) {
		this.A_char=c;
		this.A_double=d;
		this.A_String=s;
	}
	public static void main(String[] args) {
		getEntityMgr().getTransaction().begin();
		A a1 = new A('a',1.25,"a1");
		B b1 = new B('b',0.25,"b1",a1);
		C c1 = new C('c',1.15,"c1",b1);
		C c2 = new C('c',1.15,"c2",b1);
		A a2 = new A('a',1.25,"a2");
		B b2 = new B('b',0.25,"b2",a2);
		C c3 = new C('c',1.15,"c3",b2);
		C c4 = new C('c',1.15,"c4",b2);
		//Ponizsze zapytanie powinno zwrocic powiazane ze soba elementy
		//select * from A join b on A.A_ID=b.A_ID join C on b.B_Id=c.B_ID

		getEntityMgr().persist(b1);
		getEntityMgr().persist(b2);
		b1.setB_char('x');
		getEntityMgr().getTransaction().commit();
		//A a1 = getEntityMgr().find(A.class, 0);
		//System.out.println(a1.getA_ID() + a1.getA_String());
		getEntityMgr().close();
	      emfactory.close();
	}

	public static EntityManager getEntityMgr()
	{
		if (entitymanager==null)
			{
		    emfactory = Persistence.createEntityManagerFactory("PZ_lab4");
			entitymanager = emfactory.createEntityManager();
		//	emfactory.close();
			}
		 return entitymanager;
	}
	
	
	public void dodajDoZbioru(B nowy)
	{
		zbiorB.add(nowy);
		nowy.setA(this);
	}
	public Set<B> getZbiorB()
	{
		return this.zbiorB;
	}
	
	public int getA_ID() {
		return A_ID;
	}
	public void setA_ID(int a_ID) {
		A_ID = a_ID;
	}
	public char getA_char() {
		return A_char;
	}
	public void setA_char(char a_char) {
		A_char = a_char;
	}
	public double getA_double() {
		return A_double;
	}
	public void setA_double(double a_double) {
		A_double = a_double;
	}
	public String getA_String() {
		return A_String;
	}
	public void setA_String(String a_String) {
		A_String = a_String;
	}
}
