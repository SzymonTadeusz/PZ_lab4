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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.UnresolvableObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;

public class Main {
	private static JFrame oknoProgramu;
	private static JList<A> listaObiektowA;
	private static JList<B> listaObiektowB;
	private static JList<C> listaObiektowC;
    static DefaultListModel<A> modelA = new DefaultListModel<>();
	static DefaultListModel<B> modelB = new DefaultListModel<>();
    static DefaultListModel<C> modelC = new DefaultListModel<>();
    private static List<B> listaB = new ArrayList<>();
    private static List<A> listaA = new ArrayList<>();
    private static List<C> listaC = new ArrayList<>();
    private static Session session;
    
    static int wyswietlany;

	
	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {
		
        JPanel windowPanel = new JPanel(new GridLayout());
        
        
        //************DODAWANIE PANELI (zawieraj¹cych JPanele wyœwietlaj¹ce dane + przyciski akcji)****************
        JPanel modelAPanel = new JPanel(new BorderLayout());
        modelAPanel.setSize(333,300);
        JLabel labelA = new JLabel ("Zaznacz element z klasy B");
        modelAPanel.add(labelA,BorderLayout.NORTH);

        JPanel modelCPanel = new JPanel(new BorderLayout());
        modelCPanel.setSize(333,300);
        JLabel labelC = new JLabel ("Zaznacz element z klasy B");
        modelCPanel.add(labelC,BorderLayout.NORTH);

        JPanel modelBPanel = new JPanel(new BorderLayout());
        modelBPanel.setSize(334,300);
        JLabel labelB = new JLabel ("Trwa uruchamianie Hibernate'a i ³adowanie danych...");
        modelBPanel.add(labelB,BorderLayout.NORTH);
        
        JPanel buttonPanelB = new JPanel(new GridLayout());
        JPanel buttonPanelA = new JPanel(new GridLayout());
        JPanel buttonPanelC = new JPanel(new GridLayout());
        
        
        listaObiektowA = new JList<>(modelA);
        listaObiektowA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerA = new JScrollPane();
        listScrollerA.setViewportView(listaObiektowA);
        listaObiektowA.setLayoutOrientation(JList.VERTICAL);
        modelAPanel.add(listScrollerA);
        windowPanel.add(modelAPanel);
        
        listaObiektowC = new JList<>(modelC);
        listaObiektowC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerC = new JScrollPane();
        listScrollerC.setViewportView(listaObiektowC);
        listaObiektowC.setLayoutOrientation(JList.VERTICAL);
        modelCPanel.add(listScrollerC,BorderLayout.CENTER);
        windowPanel.add(modelCPanel);

        //*****************************************************************
        //***************TWORZENIE PRZYCISKÓW PANELU B*********************
        //*****************************************************************
        
        //------------------przycisk dodawania nowego obiektu
        JButton buttonAddB = new JButton();
        ImageIcon iconAdd = new ImageIcon("resources/add.jpg");
        Image imgAdd = iconAdd.getImage();
        Image newimgAdd = imgAdd.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonAddB.setIcon(new ImageIcon(newimgAdd));
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
        		JButton bt = new JButton("ZatwierdŸ");
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
						System.out.println("Dodano element B: "+b.toString());
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
        
        //----------------------przycisk usuwania obiektu
        JButton buttonRemoveB = new JButton();
        ImageIcon iconRemove = new ImageIcon("resources/remove.jpg");
        Image imgRemove = iconRemove.getImage();
        Image newimgRemove = imgRemove.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonRemoveB.setIcon(new ImageIcon(newimgRemove));
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
				try{
					q.executeUpdate();
				}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
				catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje A o podanym ID.");}
				modelB.removeElementAt(Main.wyswietlany);
				listaB.remove(wyswietlany);
	    		A.getEntityMgr().getTransaction().commit();
				}
			});
        buttonRemoveB.setSize(50, 50);
        buttonPanelB.add(buttonRemoveB);
        
       	//-----------------------przycisk edytowania istniej¹cego obiektu
        JButton buttonEditB = new JButton();
        iconAdd = new ImageIcon("resources/edit.jpg");
        Image imgEdit = iconAdd.getImage();
        Image newimgEdit = imgEdit.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonEditB.setIcon(new ImageIcon(newimgEdit));
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
        		
        		JLabel l_id = new JLabel("ID: ", JLabel.TRAILING);
        		panel.add(l_id);
        		JTextField textFieldID = new JTextField();
        		textFieldID.setText(modelB.getElementAt(wyswietlany).getB_ID()+"");
        		textFieldID.setEditable(false);
        		l_id.setLabelFor(textFieldID);
        		panel.add(textFieldID);
        		
         		JLabel l_aid = new JLabel("A_ID: ", JLabel.TRAILING);
        		panel.add(l_aid);
        		JTextField textFieldAID = new JTextField();
        		if(modelB.getElementAt(wyswietlany).getA()!=null)
        		textFieldAID.setText(modelB.getElementAt(wyswietlany).getA().getA_ID()+"");
        		l_aid.setLabelFor(textFieldAID);
        		panel.add(textFieldAID);
        		
        		JLabel l_char = new JLabel("Char: ", JLabel.TRAILING);
        		panel.add(l_char);
        		JTextField textFieldChar = new JTextField(10);
        		textFieldChar.setText(modelB.getElementAt(wyswietlany).getB_char()+"");
        		l_char.setLabelFor(textFieldChar);
        		panel.add(textFieldChar);
        		
    		    JLabel l_double = new JLabel("Double: ", JLabel.TRAILING);
    		    panel.add(l_double);
    		    JTextField textFieldDouble = new JTextField(10);
    		    textFieldDouble.setText(modelB.getElementAt(wyswietlany).getB_double()+"");
    		    l_char.setLabelFor(textFieldDouble);
    		    panel.add(textFieldDouble);

    		    JLabel l_string = new JLabel("String: ", JLabel.TRAILING);
    		    panel.add(l_string);
    		    JTextField textFieldString = new JTextField(10);
    		    textFieldString.setText(modelB.getElementAt(wyswietlany).getB_String()+"");
    		    l_char.setLabelFor(textFieldString);
    		    panel.add(textFieldString);   
            	
    		    JFrame addWindow = new JFrame("Zmodyfikuj element typu B");
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						char b_char = textFieldChar.getText().charAt(0);
						double b_double;
						int a_id;
						try{
						a_id= Integer.parseInt(textFieldAID.getText());
						b_double = Double.parseDouble(textFieldDouble.getText());
						}catch(NumberFormatException ex){b_double=0;a_id=0;}
						String b_string = textFieldString.getText();
						
						A.getEntityMgr().getTransaction().begin();
						int id = modelB.getElementAt(Main.wyswietlany).getB_ID();
						System.out.println("Zmieniam obiekt o ID="+id +"(char="+b_char+" double="+b_double+" string="+b_string+")");
						Query q = session.createQuery("update B	set a_id="+a_id+ ", b_char= '"+b_char+ "', b_double= "+b_double+ ", b_string= '"+b_string	+"' where b_id =" + id);
						try{
							q.executeUpdate();
						}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
						catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje A o podanym ID.");}
						session.refresh(modelB.get(wyswietlany));
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
        
        JButton buttonListB = new JButton();
        ImageIcon iconList = new ImageIcon("resources/list.jpg");
        Image imgList = iconList.getImage();
        Image newimgList = imgList.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonListB.setIcon(new ImageIcon(newimgList));
        buttonListB.addMouseListener(new MouseListener() {
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
				buttonListB.setEnabled(false);
    		    JFrame listWindow = new JFrame("Elementy typu B");
    		    JTextArea elementsB = new JTextArea();
        		String txt ="";	
    		    	for(B _b : listaB) 
        				{	
        				txt+=(_b.toString()+"\n");
        				}
    		    elementsB.setText(txt);	
    		    listWindow.getContentPane().add(elementsB);
    		    listWindow.pack();
        		listWindow.setVisible(true);	
				buttonListB.setEnabled(true);
        		}
			});
        buttonListB.setSize(50, 50);
        buttonPanelB.add(buttonListB);
        	
        //------------------przycisk odœwie¿ania zawartoœci jeœli zmieni³a siê w bazie danych
        JButton buttonRefresh = new JButton();
        iconAdd = new ImageIcon("resources/refresh.jpg");
        Image imgRefresh = iconAdd.getImage();
        Image newimgRefresh = imgRefresh.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonRefresh.setIcon(new ImageIcon(newimgRefresh));
        buttonRefresh.addMouseListener(new MouseListener() {
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
				buttonRefresh.setEnabled(false);
				try{
        			modelB.removeAllElements();
        			List<B> dane;
        			dane = session.createQuery("from B").list();
        			
        			for(B _b : dane) 
    				{	
    				if(!listaB.contains(_b))
    					{
    					listaB.add(_b);
    					session.persist(_b);
    					}
    				}
    			
        			for(B _b : listaB) 
        				{	
        				session.refresh(_b);
        				modelB.addElement(_b);
        				}
        			System.out.println("Odœwie¿ono zawartoœæ elementów.");
					}catch (HibernateException e1){
        			System.out.println("Blad Hibernate'a!: " + e1.getMessage());
        			}
        		catch (NullPointerException e1){
        			System.out.println("Brak rekordu o podanym ID!");
					}
				buttonRefresh.setEnabled(true);
        		}
			});
        buttonRefresh.setSize(50, 50);
        buttonPanelB.add(buttonRefresh);
        modelBPanel.add(buttonPanelB,BorderLayout.SOUTH);
        
        //************************************************************
        //************DODAWANIE PRZYCISKÓW PANELU A*******************
        //************************************************************
        JButton buttonAddA = new JButton();
        buttonAddA.setIcon(new ImageIcon(newimgAdd));
        buttonAddA.addMouseListener(new MouseListener() {
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
            	
    		    JFrame addWindow = new JFrame("Dodaj nowy element typu A");
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						char a_char = textFieldChar.getText().charAt(0);
						double a_double;
						try{
						a_double = Double.parseDouble(textFieldDouble.getText());
						}catch(NumberFormatException ex){a_double=0;}
						String a_string = textFieldString.getText();
						A.getEntityMgr().getTransaction().begin();
						A a = new A(a_char,a_double,a_string);		
						listaA.add(a);
						A.getEntityMgr().persist(a);
						A.getEntityMgr().getTransaction().commit();
						System.out.println("Dodano element A: "+a.toString());
						addWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		addWindow.getContentPane().add(panel);
        		addWindow.pack();
        		addWindow.setVisible(true);
        		}
			});
        buttonAddA.setSize(50,50);
        buttonPanelA.add(buttonAddA);

        JButton buttonRemoveA = new JButton();
        buttonRemoveA.setIcon(new ImageIcon(newimgRemove));
        buttonRemoveA.addMouseListener(new MouseListener() {
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
				JFrame removeWindow = new JFrame("Usuñ element typu A");
        		JPanel panel = new JPanel(new GridLayout());
				
				JLabel l_aid = new JLabel("A_ID: ", JLabel.TRAILING);
        		panel.add(l_aid);
        		JTextField textFieldAID = new JTextField();
        		l_aid.setLabelFor(textFieldAID);
        		panel.add(textFieldAID);
				
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int id;
						A.getEntityMgr().getTransaction().begin();
						try{
							id = Integer.parseInt(textFieldAID.getText());
							System.out.println("Usuwam element A o ID="+id+".");
							Query q = session.createQuery("delete from A where A_ID = " + id);
								try{
									q.executeUpdate();
								}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
								 catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje A o podanym ID lub jest u¿ywane.");}
							}catch(NumberFormatException ex){
								System.out.println("B³¹d! Niewlasciwy format liczby.");
								}
						A tmp = null;
						try{	
						for(A _a : listaA)
							{
							tmp = _a;
							session.refresh(_a);
							}
						}catch(UnresolvableObjectException e1){listaA.remove(tmp);}
						A.getEntityMgr().getTransaction().commit();
						removeWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		removeWindow.getContentPane().add(panel);
        		removeWindow.pack();
        		removeWindow.setVisible(true);
				}
			});
        buttonRemoveA.setSize(50, 50);
        buttonPanelA.add(buttonRemoveA);
        
        JButton buttonEditA = new JButton();
        buttonEditA.setIcon(new ImageIcon(newimgEdit));
        buttonEditA.addMouseListener(new MouseListener() {
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
        		
        		JLabel l_id = new JLabel("ID: ", JLabel.TRAILING);
        		panel.add(l_id);
        		JTextField textFieldID = new JTextField();
        		l_id.setLabelFor(textFieldID);
        		panel.add(textFieldID);
        		
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
            	
    		    JFrame addWindow = new JFrame("Zmodyfikuj element typu A");
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						char a_char = textFieldChar.getText().charAt(0);
						double a_double;
						int a_id;
						String a_string=textFieldString.getText();
						try{
						a_double = Double.parseDouble(textFieldDouble.getText());
						a_id = Integer.parseInt(textFieldID.getText());
						}catch(NumberFormatException ex){a_double=0;a_id=0;}
						
						A.getEntityMgr().getTransaction().begin();
						System.out.println("Zmieniam obiekt o ID="+a_id +"(char="+a_char+" double="+a_double+" string="+a_string+")");
						Query q = session.createQuery("update A	set a_char= '"+a_char+ "', a_double= "+a_double+ ", a_string= '"+a_string	+"' where a_id =" + a_id);
						try{
							q.executeUpdate();
						}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
						catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje A o podanym ID.");}
						if(modelA.getSize()>0) session.refresh(modelA.get(0));
						for (A _a : listaA) session.refresh(_a);
						modelAPanel.repaint();
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
        buttonEditA.setSize(50, 50);
        buttonPanelA.add(buttonEditA);

        JButton buttonListA = new JButton();
        buttonListA.setIcon(new ImageIcon(newimgList));
        buttonListA.addMouseListener(new MouseListener() {
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
				buttonListA.setEnabled(false);
    		    JFrame listWindow = new JFrame("Elementy typu A");
    		    JTextArea elementsA = new JTextArea();
        		String txt ="";	
    		    	for(A _a : listaA) 
        				{	
        				txt+=(_a.toString()+"\n");
        				}
    		    elementsA.setText(txt);	
    		    listWindow.getContentPane().add(elementsA);
    		    listWindow.pack();
        		listWindow.setVisible(true);	
				buttonListA.setEnabled(true);
        		}
			});
        buttonListA.setSize(50, 50);
        buttonPanelA.add(buttonListA);
        
        JButton buttonLinkA = new JButton();
        ImageIcon iconLink = new ImageIcon("resources/link.jpg");
        Image imgLink = iconLink.getImage();
        Image newimgLink = imgLink.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        buttonLinkA.setIcon(new ImageIcon(newimgLink));
        buttonLinkA.addMouseListener(new MouseListener() {
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
				JFrame removeWindow = new JFrame("Stwórz powi¹zanie A - B");
        		JPanel panel = new JPanel(new GridLayout());
				
				JLabel l_aid = new JLabel("A_ID: ", JLabel.TRAILING);
        		panel.add(l_aid);
        		JTextField textFieldAID = new JTextField();
        		l_aid.setLabelFor(textFieldAID);
        		panel.add(textFieldAID);
				
        		JLabel l_bid = new JLabel("B_ID: ", JLabel.TRAILING);
        		panel.add(l_bid);
        		JTextField textFieldBID = new JTextField();
        		l_aid.setLabelFor(textFieldBID);
        		panel.add(textFieldBID);
        		
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int idA, idB;
						A.getEntityMgr().getTransaction().begin();
						try{
							idA = Integer.parseInt(textFieldAID.getText());
							idB = Integer.parseInt(textFieldBID.getText());
							
							System.out.println("Dodajê powi¹zanie miêdzy elementami: A_ID="+idA+", B_ID="+idB+".");
							Query q = session.createQuery("update B set A_ID = "+idA+ " where B_ID = " + idB);
								try{
									q.executeUpdate();
								}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
								 catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje A/B o podanym ID lub jest u¿ywane.");}
							}catch(NumberFormatException ex){
								System.out.println("B³¹d! Niewlasciwy format liczby.");
								}
						A tmp = null;
						try{	
							for (A _a : listaA) session.refresh(_a);
							for (B _b : listaB) session.refresh(_b);
						}catch(UnresolvableObjectException e1){listaA.remove(tmp);}
						A.getEntityMgr().getTransaction().commit();
						removeWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		removeWindow.getContentPane().add(panel);
        		removeWindow.pack();
        		removeWindow.setVisible(true);
				}
			});
        buttonLinkA.setSize(50, 50);
        buttonPanelA.add(buttonLinkA);

        modelAPanel.add(buttonPanelA,BorderLayout.SOUTH);
        
        //*************************************************************
        //*************DODANIE PRZYCISKÓW PANELU C*********************
        //*************************************************************
        JButton buttonAddC = new JButton();
        buttonAddC.setIcon(new ImageIcon(newimgAdd));
        buttonAddC.addMouseListener(new MouseListener() {
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
            	
    		    JFrame addWindow = new JFrame("Dodaj nowy element typu C");
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						char c_char = textFieldChar.getText().charAt(0);
						double c_double;
						try{
						c_double = Double.parseDouble(textFieldDouble.getText());
						}catch(NumberFormatException ex){c_double=0;}
						String c_string = textFieldString.getText();
						A.getEntityMgr().getTransaction().begin();
						C c = new C(c_char,c_double,c_string, null);
						listaC.add(c);
						A.getEntityMgr().persist(c);
						A.getEntityMgr().getTransaction().commit();
						System.out.println("Dodano element C: "+c.toString());
						addWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		addWindow.getContentPane().add(panel);
        		addWindow.pack();
        		addWindow.setVisible(true);
        		}
			});
        buttonAddC.setSize(50,50);
        buttonPanelC.add(buttonAddC);
        
        JButton buttonRemoveC = new JButton();
        buttonRemoveC.setIcon(new ImageIcon(newimgRemove));
        buttonRemoveC.addMouseListener(new MouseListener() {
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
				JFrame removeWindow = new JFrame("Usuñ element typu C");
        		JPanel panel = new JPanel(new GridLayout());
				
				JLabel l_cid = new JLabel("C_ID: ", JLabel.TRAILING);
        		panel.add(l_cid);
        		JTextField textFieldCID = new JTextField();
        		l_cid.setLabelFor(textFieldCID);
        		panel.add(textFieldCID);
				
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int id;
						A.getEntityMgr().getTransaction().begin();
						try{
							id = Integer.parseInt(textFieldCID.getText());
							System.out.println("Usuwam element C o ID="+id+".");
							Query q = session.createQuery("delete from C where C_ID = " + id);
								try{
									q.executeUpdate();
								}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
								 catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje C o podanym ID lub jest u¿ywane.");}
							}catch(NumberFormatException ex){
								System.out.println("B³¹d! Niewlasciwy format liczby.");
								}
						C tmp = null;
						try{	
						for(C _c : listaC)
							{
							tmp = _c;
							session.refresh(_c);
							}
						}catch(UnresolvableObjectException e1){listaC.remove(tmp);}
						A.getEntityMgr().getTransaction().commit();
						removeWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		removeWindow.getContentPane().add(panel);
        		removeWindow.pack();
        		removeWindow.setVisible(true);
				}
			});
        buttonRemoveC.setSize(50, 50);
        buttonPanelC.add(buttonRemoveC);
        
        JButton buttonEditC = new JButton();
        buttonEditC.setIcon(new ImageIcon(newimgEdit));
        buttonEditC.addMouseListener(new MouseListener() {
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
        		
        		JLabel l_id = new JLabel("ID: ", JLabel.TRAILING);
        		panel.add(l_id);
        		JTextField textFieldID = new JTextField();
        		l_id.setLabelFor(textFieldID);
        		panel.add(textFieldID);
        		
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
            	
    		    JFrame addWindow = new JFrame("Zmodyfikuj element typu C");
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						char c_char = textFieldChar.getText().charAt(0);
						double c_double;
						int c_id;
						String c_string=textFieldString.getText();
						try{
						c_double = Double.parseDouble(textFieldDouble.getText());
						c_id = Integer.parseInt(textFieldID.getText());
						}catch(NumberFormatException ex){c_double=0;c_id=0;}
						
						A.getEntityMgr().getTransaction().begin();
						System.out.println("Zmieniam obiekt o ID="+c_id +"(char="+c_char+" double="+c_double+" string="+c_string+")");
						Query q = session.createQuery("update C	set c_char= '"+c_char+ "', c_double= "+c_double+ ", c_string= '"+c_string	+"' where c_id =" + c_id);
						try{
							q.executeUpdate();
						}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
						catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje C o podanym ID.");}
						if(modelC.getSize()>0) session.refresh(modelC.get(0));
						for (C _c : listaC) session.refresh(_c);
						modelCPanel.repaint();
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
        buttonEditC.setSize(50, 50);
        buttonPanelC.add(buttonEditC);
        
        JButton buttonListC = new JButton();
        buttonListC.setIcon(new ImageIcon(newimgList));
        buttonListC.addMouseListener(new MouseListener() {
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
				buttonListC.setEnabled(false);
    		    JFrame listWindow = new JFrame("Elementy typu C");
    		    JTextArea elementsC = new JTextArea();
        		String txt ="";	
    		    	for(C _c : listaC) 
        				{	
        				txt+=(_c.toString()+"\n");
        				}
    		    elementsC.setText(txt);	
    		    listWindow.getContentPane().add(elementsC);
    		    listWindow.pack();
        		listWindow.setVisible(true);	
				buttonListC.setEnabled(true);
        		}
			});
        buttonListC.setSize(50, 50);
        buttonPanelC.add(buttonListC);

        JButton buttonLinkC = new JButton();
        buttonLinkC.setIcon(new ImageIcon(newimgLink));
        buttonLinkC.addMouseListener(new MouseListener() {
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
				JFrame removeWindow = new JFrame("Stwórz powi¹zanie B - C");
        		JPanel panel = new JPanel(new GridLayout());
				
				JLabel l_bid = new JLabel("B_ID: ", JLabel.TRAILING);
        		panel.add(l_bid);
        		JTextField textFieldBID = new JTextField();
        		l_bid.setLabelFor(textFieldBID);
        		panel.add(textFieldBID);
				
        		JLabel l_cid = new JLabel("C_ID: ", JLabel.TRAILING);
        		panel.add(l_cid);
        		JTextField textFieldCID = new JTextField();
        		l_cid.setLabelFor(textFieldCID);
        		panel.add(textFieldCID);
        		
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int idC, idB;
						A.getEntityMgr().getTransaction().begin();
						try{
							idB = Integer.parseInt(textFieldBID.getText());
							idC = Integer.parseInt(textFieldCID.getText());
							
							System.out.println("Dodajê powi¹zanie miêdzy elementami: B_ID="+idB+", C_ID="+idC+".");
							Query q = session.createSQLQuery("insert into BC (B_ID, C_ID) values ("+idB+ ", " + idC +")");
						
								try{
									q.executeUpdate();
								}catch(SQLGrammarException e1){System.out.println("B³¹d! Nie wykonano aktualizacji.");}
								 catch(ConstraintViolationException e2){System.out.println("B³¹d! Nie istnieje B/C o podanym ID lub jest u¿ywane.");}
							}catch(NumberFormatException ex){
								System.out.println("B³¹d! Niewlasciwy format liczby.");
								}
						B tmp = null;
						try{	
							for (B _b : listaB) session.refresh(_b);
							for (C _c : listaC) session.refresh(_c);
						}catch(UnresolvableObjectException e1){System.out.println("Nast¹pi³ b³¹d podczas dodawania po³¹czenia");}
						A.getEntityMgr().getTransaction().commit();
						removeWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		removeWindow.getContentPane().add(panel);
        		removeWindow.pack();
        		removeWindow.setVisible(true);
				}
			});
        buttonLinkC.setSize(50, 50);
        buttonPanelC.add(buttonLinkC);
        
        
        modelCPanel.add(buttonPanelC,BorderLayout.SOUTH);
        
        windowPanel.add(modelBPanel);
        windowPanel.add(modelAPanel);
        windowPanel.add(modelCPanel);
        
        
        
        
        //******************POWO£YWANIE NOWEGO OKNA APLIKACJI***********************
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

        
        //***************TWORZENIE W¥TKU GENERUJ¥CEGO PRZYK£ADOWE DANE******************* 
        Thread populatingThread = new Thread(){
        	@Override
        	public void run() {   		
        		A a1 = new A('a',0.01,"a1");
        		B b1 = new B('b',1.01,"b1",a1);
        		C c1 = new C('c',2.01,"c1",b1);
        		C c2 = new C('c',2.02,"c2",b1);
        		B b2 = new B('b',1.02,"b2",a1);
        		c2.dodajDoZbioru(b2);
        		A a2 = new A('a',0.02,"a2");
        		C c3 = new C('c',2.03,"c3",b2);
        		C c4 = new C('c',2.04,"c4",b2);
        		B b3 = new B('b',1.03,"b3",null);
        		B b4 = new B('b',1.04,"b4",a2);
        		C c5 = new C('c',2.05,"c5",b1);
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
        		
        		try{
        			List<A> dane = session.createCriteria(A.class).list();
        			for(A _a : dane)
        			{
        				listaA.add(_a);
        				A.getEntityMgr().persist(_a);
        			}
        		}catch (HibernateException e1){
        			System.out.println("Blad Hibernate'a!");
        			}
        		catch (NullPointerException e1){
        			System.out.println("Brak rekordu o podanym ID!");
					}
        		
        		try{
        			List<C> dane = session.createCriteria(C.class).list();
        			for(C _c : dane)
        			{
        				listaC.add(_c);
        				A.getEntityMgr().persist(_c);
        			}
        		}catch (HibernateException e1){
        			System.out.println("Blad Hibernate'a!");
        			}
        		catch (NullPointerException e1){
        			System.out.println("Brak rekordu o podanym ID!");
					}
        		
        		A.getEntityMgr().getTransaction().commit();
        	}
        };
        populatingThread.run();
        
        //********************POWO£ANIE JPANELI WYŒWIETLAJ¥CYCH DANE***************************
        listaObiektowB = new JList<>(modelB);
        listaObiektowB.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollerB = new JScrollPane();
        listScrollerB.setViewportView(listaObiektowB);
        listaObiektowB.setLayoutOrientation(JList.VERTICAL);
        modelBPanel.add(listScrollerB,BorderLayout.CENTER);
        
        listaObiektowB.addListSelectionListener(new ListSelectionListener() {
        	
        	@Override
			public void valueChanged(ListSelectionEvent event) {
        		if (!modelB.isEmpty())
        		{
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
        			
        	}
		});
        

        
        Main.getWindow().revalidate();
        Main.getWindow().repaint();
	}

	public static JFrame getWindow()
	{
		if(oknoProgramu==null)	oknoProgramu = new JFrame("PZ JPA Sz. Muszyñski");
		return oknoProgramu;
	}
	
	
	public static void addToList(B _b)
	{
		modelB.addElement(_b);
	}
	
	public static void addB(B _b)
	{
		listaB.add(_b);
	}
	
	public static void removeFromList(B _b)
	{
		modelB.removeElement(_b);
	}
	
}
