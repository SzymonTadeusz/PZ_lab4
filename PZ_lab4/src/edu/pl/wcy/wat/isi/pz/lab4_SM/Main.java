package edu.pl.wcy.wat.isi.pz.lab4_SM;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class Main {
	private static JFrame oknoProgramu;
	private static JList<A> listaObiektowA;
	private static JList<B> listaObiektowB;// = new JList<>(dane.toArray(new B[dane.size()]));
	private static JList<C> listaObiektowC;
    static DefaultListModel<A> modelA = new DefaultListModel<>();
	static DefaultListModel<B> modelB = new DefaultListModel<>();
    static DefaultListModel<C> modelC = new DefaultListModel<>();

	
	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {
		
        JPanel windowPanel = new JPanel(new BorderLayout());

        JPanel modelBPanel = new JPanel(new BorderLayout());
        JLabel labelB = new JLabel ("Trwa ³adowanie danych...");
        JPanel modelAPanel = new JPanel(new BorderLayout());
        JLabel labelA = new JLabel ("Zaznacz element z klasy B");
        JPanel modelCPanel = new JPanel(new BorderLayout());
        JLabel labelC = new JLabel ("Zaznacz element z klasy B");

        Thread watekHibernate = new Thread(){
        	@Override
        	public void run() {   		
        		A a1 = new A('a',1.25,"a1");
        		B b1 = new B('b',0.25,"b1",a1);
        		C c1 = new C('c',1.15,"c1",b1);
        		C c2 = new C('c',1.15,"c2",b1);
        		A a2 = new A('a',1.25,"a2");
        		B b2 = new B('b',0.25,"b2",a2);
        		C c3 = new C('c',1.15,"c3",b2);
        		C c4 = new C('c',1.15,"c4",b2);
        		
        		A.getEntityMgr().getTransaction().begin();
        		Session session = A.getEntityMgr().unwrap(Session.class);
        		
        		//Ponizsze zapytanie powinno zwrocic powiazane ze soba elementy
        		//select * from A join b on A.A_ID=b.A_ID join C on b.B_Id=c.B_ID
        		A.getEntityMgr().persist(b1);
        		A.getEntityMgr().persist(b2);
        		try{
        			List<B> dane = session.createCriteria(B.class).list();
        			modelB.removeAllElements();
        			for(B _b : dane)
        			{
        				modelB.addElement(_b);
        			}
        			labelB.setText("Obiekty typu B:");
        		}catch (HibernateException e1){
        			System.out.println("Blad Hibernate'a!");
        			}
        		catch (NullPointerException e1){
        			System.out.println("Brak rekordu o podanym ID!");
        		}     		     		
        		A.getEntityMgr().getTransaction().commit();
        	}
        };
        watekHibernate.run();
        
        
        modelBPanel.add(labelB,BorderLayout.NORTH);
        listaObiektowB = new JList<>(modelB);
        listaObiektowB.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerB = new JScrollPane();
        listScrollerB.setViewportView(listaObiektowB);
        listaObiektowB.setLayoutOrientation(JList.VERTICAL);
        modelBPanel.add(listScrollerB,BorderLayout.CENTER);
        listaObiektowB.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
					listaObiektowB.clearSelection();
					labelA.setText("Obiekt A dla B o ID="+modelB.get(event.getFirstIndex()).getB_ID()+":");
					modelA.removeAllElements();
					modelA.addElement(modelB.get(event.getFirstIndex()).getA());
					
					labelC.setText("Obiekty C dla B o ID="+modelB.get(event.getFirstIndex()).getB_ID()+":");
					modelC.removeAllElements();
					for(C _c : modelB.get(event.getFirstIndex()).getZbiorC())
					{
						modelC.addElement(_c);	
					}	
			}
		});
        windowPanel.add(modelBPanel,BorderLayout.WEST);
        
        modelAPanel.add(labelA,BorderLayout.NORTH);
        listaObiektowA = new JList<>(modelA);
        listaObiektowA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerA = new JScrollPane();
        listScrollerA.setViewportView(listaObiektowA);
        listaObiektowA.setLayoutOrientation(JList.VERTICAL);
        modelAPanel.add(listScrollerA,BorderLayout.CENTER);
        windowPanel.add(modelAPanel);
        
        modelCPanel.add(labelC,BorderLayout.NORTH);
        listaObiektowC = new JList<>(modelC);
        listaObiektowC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerC = new JScrollPane();
        listScrollerC.setViewportView(listaObiektowC);
        listaObiektowC.setLayoutOrientation(JList.VERTICAL);
        modelCPanel.add(listScrollerC,BorderLayout.CENTER);
        windowPanel.add(modelCPanel,BorderLayout.EAST);
		
 		Main.getWindow().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 		Main.getWindow().add(windowPanel);
 		Main.getWindow().setSize(1000, 300);
 		Main.getWindow().setLocationRelativeTo(null);
        Main.getWindow().setVisible(true);
        Main.getWindow().setEnabled(true);
        Main.getWindow().addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
            	System.out.println("Zamykam polaczenie oraz aplikacje.");
        		A.getEntityMgr().close();
        		A.emfactory.close();
        		System.exit(0);
            }
        });
		
		
      
	}

	public static JFrame getWindow()
	{
		if(oknoProgramu==null)	oknoProgramu = new JFrame("PZ JPA Sz. Muszyñski");
		return oknoProgramu;
	}
	
	
	public static void addToList(B _b)
	{
		//dane.add(_b);
		modelB.addElement(_b);
	}
	
	public static void removeFromList(B _b)
	{
		//dane.remove(_b);
		modelB.removeElement(_b);
	}
	
}
