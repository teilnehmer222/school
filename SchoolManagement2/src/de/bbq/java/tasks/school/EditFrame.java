package de.bbq.java.tasks.school;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EditFrame extends JFrame implements ActionListener {
	ArrayList<Component> fields = new ArrayList<>();
	JButton exit;

	private final int winLength = 400;
	private final int winHight = 400;

	void Construct() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		pack();
		setSize(winLength, winHight);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	EditFrame(StudentDF editItem) {
		setTitle("Schüler editieren");
		JLabel label = new JLabel("Nachname:");
		label.setBounds(5, 5, 100, 20);
		add(label);	
		label = new JLabel("Vorname:");
		label.setBounds(5, 30, 100, 20);
		add(label);	
		label = new JLabel("DOB:");
		label.setBounds(5, 55, 100, 20);
		add(label);	
		label = new JLabel("Strasse:");
		label.setBounds(5, 80, 100, 20);
		add(label);	
		label = new JLabel("Adresse2:");
		label.setBounds(5, 105, 100, 20);
		add(label);	
		label = new JLabel("PLZ:");
		label.setBounds(5, 130, 100, 20);
		add(label);	
		label = new JLabel("Ort:");
		label.setBounds(5, 155, 100, 20);
		add(label);		
		
		JTextField field = new JTextField();
		field.setBounds(110, 5, 200, 20);
		field.setName("Nachname:");
		add(field);
		fields.add(field);
		
		field = new JTextField();
		field.setBounds(110, 30, 200, 20);
		field.setName("Vorname:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 55, 200, 20);
		field.setName("DOB:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 80, 200, 20);
		field.setName("Strasse:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 105, 200, 20);
		field.setName("Adresse2:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 130, 200, 20);
		field.setName("PLZ:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 155, 200, 20);
		field.setName("Ort:");
		add(field);
		fields.add(field);		
	
		exit = SchoolLauncher.getButton("Abbrechen", 110, 180, 100, 20, this, "Abbrechen", "Nichts wie raus hier...");
		add(exit);
		
		Construct();
	}

	EditFrame(CourseDF c) {
		setTitle("Kurs editieren");
		JLabel label = new JLabel("Kursname:");
		label.setBounds(5, 5, 100, 20);
		add(label);	
		label = new JLabel("Fach:");
		label.setBounds(5, 30, 100, 20);
		add(label);	
		label = new JLabel("Dauer:");
		label.setBounds(5, 55, 100, 20);
		add(label);	
		label = new JLabel("Sprache:");
		label.setBounds(5, 80, 100, 20);
		add(label);	
		label = new JLabel("Raum:");
		label.setBounds(5, 105, 100, 20);
		add(label);	
		label = new JLabel("Beamer:");
		label.setBounds(5, 130, 100, 20);
		add(label);	
		
		JTextField field = new JTextField();
		field.setBounds(110, 5, 200, 20);
		field.setName("Kursname:");
		add(field);
		fields.add(field);
		
		field = new JTextField();
		field.setBounds(110, 30, 200, 20);
		field.setName("Fach:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 55, 200, 20);
		field.setName("Dauer:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 80, 200, 20);
		field.setName("Sprache:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 105, 200, 20);
		field.setName("Raum:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 130, 200, 20);
		field.setName("Beamer:");
		add(field);
		fields.add(field);		

		exit = SchoolLauncher.getButton("Abbrechen", 110, 180, 100, 20, this, "Abbrechen", "Nichts wie raus hier...");
		add(exit);
		
		Construct();
	}

	EditFrame(TeacherDF t) {
		setTitle("Leerer editieren");
		JLabel label = new JLabel("Nachname:");
		label.setBounds(5, 5, 100, 20);
		add(label);	
		label = new JLabel("Vorname:");
		label.setBounds(5, 30, 100, 20);
		add(label);	
		label = new JLabel("DOB:");
		label.setBounds(5, 55, 100, 20);
		add(label);	
		label = new JLabel("Strasse:");
		label.setBounds(5, 80, 100, 20);
		add(label);	
		label = new JLabel("Adresse2:");
		label.setBounds(5, 105, 100, 20);
		add(label);	
		label = new JLabel("PLZ:");
		label.setBounds(5, 130, 100, 20);
		add(label);	
		label = new JLabel("Ort:");
		label.setBounds(5, 155, 100, 20);
		add(label);		
		
		JTextField field = new JTextField();
		field.setBounds(110, 5, 200, 20);
		field.setName("Nachname:");
		add(field);
		fields.add(field);
		
		field = new JTextField();
		field.setBounds(110, 30, 200, 20);
		field.setName("Vorname:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 55, 200, 20);
		field.setName("DOB:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 80, 200, 20);
		field.setName("Strasse:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 105, 200, 20);
		field.setName("Adresse2:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 130, 200, 20);
		field.setName("PLZ:");
		add(field);
		fields.add(field);		
		
		field = new JTextField();
		field.setBounds(110, 155, 200, 20);
		field.setName("Ort:");
		add(field);
		fields.add(field);		
	
		exit = SchoolLauncher.getButton("Abbrechen", 110, 180, 100, 20, this, "Abbrechen", "Nichts wie raus hier...");
		add(exit);

		Construct();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			this.dispose();
		}

	}
}
