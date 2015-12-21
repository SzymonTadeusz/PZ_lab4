package edu.pl.wcy.wat.isi.pz.lab4_SM;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class Main {
	private static JFrame oknoProgramu;
	private static JList<A> listaObiektowA;
	private static JList<B> listaObiektowB;// = new JList<>(dane.toArray(new B[dane.size()]));
	private static JList<C> listaObiektowC;
    static DefaultListModel<A> modelA = new DefaultListModel<>();
	static DefaultListModel<B> modelB = new DefaultListModel<>();
    static DefaultListModel<C> modelC = new DefaultListModel<>();
    private static List<B> listaB = new ArrayList<>();
    private static Session session;
    
    static int wyswietlany;

	
	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {
		
        JPanel windowPanel = new JPanel(new BorderLayout());

        JPanel modelBPanel = new JPanel(new BorderLayout());
        modelBPanel.setSize(334,300);
        JLabel labelB = new JLabel ("Trwa uruchamianie Hibernate'a i ≥adowanie danych...");
        modelBPanel.add(labelB,BorderLayout.NORTH);
        
        JPanel buttonPanelB = new JPanel(new GridLayout());
        JButton buttonAddB = new JButton();
        ImageIcon iconAdd = new ImageIcon("resources/add.jpg");
        Image img = iconAdd.getImage();
        Image newimg = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonAddB.setIcon(new ImageIcon(newimg));
        buttonAddB.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
        		JPanel panel = new JPanel(new GridLayout());
        		JLabel l_char = new JLabel("Char: ", JLabel.TRAILING);
        		panel.add(l_char);
        		JTextField textFieldChar = new JTextField(10);
        		l_char.setLabelFor(textFieldChar);
        		panel.add(textFieldChar);
        		
    		    JLabel l_double = new JLabel("Double: ", JLabel.TRAILING);
    		    panel.add(l_double);
    		    JTextField textFieldDouble = new JTextField(10);
    		    l_char.setLabelFor(textFieldDouble);
    		    panel.add(textFieldDouble);

    		    JLabel l_string = new JLabel("String: ", JLabel.TRAILING);
    		    panel.add(l_string);
    		    JTextField textFieldString = new JTextField(10);
    		    l_char.setLabelFor(textFieldString);
    		    panel.add(textFieldString);   
            	
    		    JFrame addWindow = new JFrame("Dodaj nowy element typu B");
        		JButton bt = new JButton("Zatwierdü");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						char b_char = textFieldChar.getText().charAt(0);
						double b_double;
						try{
						b_double = Double.parseDouble(textFieldDouble.getText());
						}catch(NumberFormatException ex){b_double=0;}
						String b_string = textFieldString.getText();
						A.getEntityMgr().getTransaction().begin();
						B b = new B(b_char,b_double,b_string,null);		
						modelB.addElement(b);
						listaB.add(b);
						A.getEntityMgr().persist(b);
						A.getEntityMgr().getTransaction().commit();
						addWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		addWindow.getContentPane().add(panel);
        		addWindow.pack();
        		addWindow.setVisible(true);
        		}
			});
        buttonAddB.setSize(50,50);
        buttonPanelB.add(buttonAddB);
        
        JButton buttonRemoveB = new JButton();
        iconAdd = new ImageIcon("resources/remove.jpg");
        img = iconAdd.getImage();
        newimg = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonRemoveB.setIcon(new ImageIcon(newimg));
        buttonRemoveB.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				A.getEntityMgr().getTransaction().begin();
				int id = modelB.getElementAt(Main.wyswietlany).getB_ID();
				System.out.println("Usuwam "+modelB.getElementAt(Main.wyswietlany)+" "+id);
				Query q = session.createQuery("delete from B where B_ID = " + id);
				q.executeUpdate();
				modelB.removeElementAt(Main.wyswietlany);
				listaB.remove(wyswietlany);
	    		A.getEntityMgr().getTransaction().commit();
				}
			});
        buttonRemoveB.setSize(50, 50);
        buttonPanelB.add(buttonRemoveB);
        
        JButton buttonEditB = new JButton();
        iconAdd = new ImageIcon("resources/edit.jpg");
        img = iconAdd.getImage();
        newimg = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonEditB.setIcon(new ImageIcon(newimg));
        buttonEditB.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
        		JPanel panel = new JPanel(new GridLayout());
        		JLabel l_char = new JLabel("Char: ", JLabel.TRAILING);
        		panel.add(l_char);
        		JTextField textFieldChar = new JTextField(10);
        		l_char.setLabelFor(textFieldChar);
        		panel.add(textFieldChar);
        		
    		    JLabel l_double = new JLabel("Double: ", JLabel.TRAILING);
    		    panel.add(l_double);
    		    JTextField textFieldDouble = new JTextField(10);
    		    l_char.setLabelFor(textFieldDouble);
    		    panel.add(textFieldDouble);

    		    JLabel l_string = new JLabel("String: ", JLabel.TRAILING);
    		    panel.add(l_string);
    		    JTextField textFieldString = new JTextField(10);
    		    l_char.setLabelFor(textFieldString);
    		    panel.add(textFieldString);   
            	
    		    JFrame addWindow = new JFrame("Zmodyfikuj element typu B");
        		JButton bt = new JButton("Zatwierdü");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						char b_char = textFieldChar.getText().charAt(0);
						double b_double;
						try{
						b_double = Double.parseDouble(textFieldDouble.getText());
						}catch(NumberFormatException ex){b_double=0;}
						String b_string = textFieldString.getText();
						
						A.getEntityMgr().getTransaction().begin();
						int id = modelB.getElementAt(Main.wyswietlany).getB_ID();
						Query q = session.createQuery("update B	set b_char="+b_char+ ", b_double="+b_double+ ", b_string="+b_string	+" where b_id =" + id);
						q.executeUpdate();
			    		A.getEntityMgr().getTransaction().commit();
						addWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		addWindow.getContentPane().add(panel);
        		addWindow.pack();
        		addWindow.setVisible(true);
        		}
			});
        buttonEditB.setSize(50, 50);
        buttonPanelB.add(buttonEditB);
        
        
        modelBPanel.add(buttonPanelB,BorderLayout.SOUTH);
        JPanel modelAPanel = new JPanel(new BorderLayout());
        modelAPanel.setSize(333,300);
        JLabel labelA = new JLabel ("Zaznacz element z klasy B");
        modelAPanel.add(labelA,BorderLayout.NORTH);

        JPanel modelCPanel = new JPanel(new BorderLayout());
        modelCPanel.setSize(333,300);
        JLabel labelC = new JLabel ("Zaznacz element z klasy B");
        modelCPanel.add(labelC,BorderLayout.NORTH);
        
        windowPanel.add(modelBPanel,BorderLayout.WEST);

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
        		System.out.println("******************\nZamykam polaczenie oraz aplikacje.\n******************");
        		try {
					A.getEntityMgr().close();
					A.emfactory.close();
				} catch (HibernateException e1) {
					System.out.println("Blad Hibernate'a!");
				}
        		catch(IllegalStateException e2)
        		{
        			System.out.println("Blad - menedzer zostal wczesniej zamkniety!");
        		}
        		System.exit(0);
        	}
        });
        
        Thread watekHibernate = new Thread(){
        	@Override
        	public void run() {   		
        		A a1 = new A('a',1.25,"a1");
        		B b1 = new B('b',0.25,"b1",a1);
        		C c1 = new C('c',1.15,"c1",b1);
        		C c2 = new C('c',1.15,"c2",b1);
        		B b2 = new B('b',0.25,"b2",a1);
        		c2.dodajDoZbioru(b2);
        		A a2 = new A('a',1.25,"a2");
        		C c3 = new C('c',1.15,"c3",b2);
        		C c4 = new C('c',1.15,"c4",b2);
        		B b3 = new B('b',0.25,"b3",null);
        		B b4 = new B('b',0.25,"b3",a2);
        		C c5 = new C('c',1.15,"c5",b1);
        		c5.dodajDoZbioru(b2);
        		
        		A.getEntityMgr().getTransaction().begin();
        		session = A.getEntityMgr().unwrap(Session.class);
        		
        		//Ponizsze zapytanie powinno zwrocic powiazane ze soba elementy
        		//select * from A join b on A.A_ID=b.A_ID join C on b.B_Id=c.B_ID
        		for(B _b : Main.listaB) A.getEntityMgr().persist(_b);
        		
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
        
        Thread watekUpdate = new Thread()
        	{
    		@Override
    		public void run() 
        	{
	    		while(true)
	    		{
	    			try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {}
	    			
	    	
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
	    	
	    		}
        	}
        };
        //watekUpdate.run();
        
        
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
				wyswietlany=event.getFirstIndex();
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
        
        listaObiektowA = new JList<>(modelA);
        listaObiektowA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerA = new JScrollPane();
        listScrollerA.setViewportView(listaObiektowA);
        listaObiektowA.setLayoutOrientation(JList.VERTICAL);
        modelAPanel.add(listScrollerA,BorderLayout.CENTER);
        windowPanel.add(modelAPanel);
        
        listaObiektowC = new JList<>(modelC);
        listaObiektowC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerC = new JScrollPane();
        listScrollerC.setViewportView(listaObiektowC);
        listaObiektowC.setLayoutOrientation(JList.VERTICAL);
        modelCPanel.add(listScrollerC,BorderLayout.CENTER);
        windowPanel.add(modelCPanel,BorderLayout.EAST);
        
        Main.getWindow().revalidate();
        Main.getWindow().repaint();
	}

	public static JFrame getWindow()
	{
		if(oknoProgramu==null)	oknoProgramu = new JFrame("PZ JPA Sz. MuszyÒski");
		return oknoProgramu;
	}
	
	
	public static void addToList(B _b)
	{
		//dane.add(_b);
		modelB.addElement(_b);
	}
	
	public static void addB(B _b)
	{
		listaB.add(_b);
	}
	
	public static void removeFromList(B _b)
	{
		//dane.remove(_b);
		modelB.removeElement(_b);
	}
	
}
