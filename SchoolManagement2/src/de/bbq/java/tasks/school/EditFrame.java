package de.bbq.java.tasks.school;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EditFrame extends JFrame implements ActionListener {
	ArrayList<Component> fields = new ArrayList<>();
	JButton exit;
	JTextField Fach, Start, Ende, Sprache, Raum, Beamer;

	CourseDF c;
	TeacherDF t;
	StudentDF s;

	private final int winLength = 400;
	private final int winHight = 400;
	private JButton save;

	void Construct() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		pack();
		setSize(winLength, winHight);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	EditFrame(StudentDF editItem) {
		this.s = editItem;
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

	EditFrame(CourseDF editItem) {
		this.c = editItem;
		setTitle("Kurs editieren");
		JLabel label = new JLabel(c.getCourseName());
		label.setBounds(110, 5, 200, 20);
		add(label);
		label = new JLabel("Fach:");
		label.setBounds(5, 30, 100, 20);
		add(label);
		label = new JLabel("Start:");
		label.setBounds(5, 55, 100, 20);
		add(label);
		label = new JLabel("Ende:");
		label.setBounds(5, 80, 100, 20);
		add(label);
		label = new JLabel("Sprache:");
		label.setBounds(5, 105, 100, 20);
		add(label);
		label = new JLabel("Raum:");
		label.setBounds(5, 130, 100, 20);
		add(label);
		label = new JLabel("Beamer:");
		label.setBounds(5, 155, 100, 20);
		add(label);
		// JTextField field = new JTextField();
		// field.setBounds(110, 5, 200, 20);
		// field.setName("Kursname:");
		// add(field);
		// fields.add(field);

		this.Fach = new JTextField();
		Fach.setBounds(110, 30, 200, 20);
		Fach.setName("Fach:");
		Fach.setText(c.getTopic());
		add(Fach);
		fields.add(Fach);

		this.Start = new JTextField();
		Start.setBounds(110, 55, 200, 20);
		Start.setName("Dauer:");
		if (c.getSartTime() != null) {
			Start.setText(c.getSartTime().toString());
		}
		add(Start);
		fields.add(Start);

		this.Ende = new JTextField();
		Ende.setBounds(110, 80, 200, 20);
		Ende.setName("Ende:");
		if (c.getEndTime() != null) {
			Ende.setText(c.getEndTime().toString());
		}
		add(Ende);
		fields.add(Ende);

		this.Sprache = new JTextField();
		Sprache.setBounds(110, 105, 200, 20);
		Sprache.setName("Sprache:");
		Sprache.setText(c.getLanguage());
		add(Sprache);
		fields.add(Sprache);

		this.Raum = new JTextField();
		Raum.setBounds(110, 130, 200, 20);
		Raum.setName("Raum:");
		if (c.getRoomNumber() != null) {
			Raum.setText(c.getRoomNumber().toString());
		}
		add(Raum);
		fields.add(Raum);

		this.Beamer = new JTextField();
		Beamer.setBounds(110, 155, 200, 20);
		Beamer.setName("Beamer:");
		add(Beamer);
		fields.add(Beamer);

		exit = SchoolLauncher.getButton("Abbrechen", 110, 180, 100, 20, this, "Abbrechen", "Nichts wie raus hier...");
		add(exit);

		save = SchoolLauncher.getButton("Speichern", 230, 180, 100, 20, this, "Speichern",
				"Alles fein abspeichern damit auch nichts verloren geht");
		add(save);
		Construct();
	}

	EditFrame(TeacherDF editItem) {
		this.t = editItem;
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
		// JTextField Nachname,Vorname,DOB,Strasse,Adresse2,PLZ,Ort
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
		} else if (e.getSource() == save) {
			if (c != null) {
				c.setTopic(Fach.getText());
				try {
					Date d = DateFormat.getDateInstance().parse(Ende.getText());
					c.setEndTime(d);
				} catch (Exception e2) {
					c.setEndTime(new Date());
				}
				try {
					Date d = DateFormat.getDateInstance().parse(Start.getText());
					c.setEndTime(d);
				} catch (Exception e2) {
					c.setEndTime(new Date());
				}
				c.setLanguage(Sprache.getText());
				try {
					c.setRoomNumber(Integer.parseInt(Raum.getText()));
				} catch (Exception e2) {
					c.setRoomNumber(0);
				}
				c.update();
			}
			this.dispose();
		}

	}
}
