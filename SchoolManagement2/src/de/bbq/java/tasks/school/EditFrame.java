package de.bbq.java.tasks.school;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class EditFrame extends JFrame implements ActionListener {
	ArrayList<Component> fields = new ArrayList<>();
	JButton exit;
	JTextField Nachname, Vorname;
	JTextField Fach, Sprache;
	JTextField Strasse, Stadt, PLZ, Land, Raum, Hausnummer;
	JCheckBox Beamer;
	CourseDF c;
	TeacherDF t;
	StudentDF s;
	JDatePickerImpl Start, Geburtsdatum, Ende;

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

	void Construct(Person pers) {
		JLabel label = new JLabel("Nachname:");
		label.setBounds(5, 30, 100, 20);
		add(label);
		label = new JLabel("Vorname:");
		label.setBounds(5, 55, 100, 20);
		add(label);
		label = new JLabel("Geburtsdatum:");
		label.setBounds(5, 82, 100, 20);
		add(label);
		label = new JLabel("Strasse:");
		label.setBounds(5, 110, 100, 20);
		add(label);
		label = new JLabel("Hausnummer:");
		label.setBounds(5, 135, 100, 20);
		add(label);
		label = new JLabel("Stadt:");
		label.setBounds(5, 160, 100, 20);
		add(label);
		label = new JLabel("PLZ:");
		label.setBounds(5, 185, 100, 20);
		add(label);
		label = new JLabel("Land:");
		label.setBounds(5, 210, 100, 20);
		add(label);
		// JTextField field = new JTextField();
		// field.setBounds(110, 5, 200, 20);
		// field.setName("Nachname:");
		// add(field);
		// fields.add(field);
		Nachname = new JTextField();
		Nachname.setBounds(110, 30, 200, 20);
		Nachname.setName("Nachname:");
		Nachname.setText(pers.getLastName());
		add(Nachname);
		fields.add(Nachname);

		Vorname = new JTextField();
		Vorname.setBounds(110, 55, 200, 20);
		Vorname.setName("Vorname:");
		Vorname.setText(pers.getFirstName());
		add(Vorname);
		fields.add(Vorname);

		UtilDateModel model = new UtilDateModel();
		if (pers.getBirthDate() != null) {
			model.setValue(pers.getBirthDate());
		}
		model.setSelected(true);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Heute");
		p.put("text.month", "Monat");
		p.put("text.year", "Jahr");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.Geburtsdatum = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		Geburtsdatum.setBounds(110, 80, 200, 26);
		add(Geburtsdatum);
		fields.add(Geburtsdatum);

		Strasse = new JTextField();
		Strasse.setBounds(110, 110, 200, 20);
		Strasse.setName("Strasse:");
		if (pers.getAdress() != null) {
			Strasse.setText(pers.getAdress().getStreetName());
		}
		add(Strasse);
		fields.add(Strasse);

		Hausnummer = new JTextField();
		Hausnummer.setBounds(110, 135, 200, 20);
		Hausnummer.setName("Hausnummer:");
		if (pers.getAdress() != null) {
			Hausnummer.setText(pers.getAdress().getHouseNumber());
		}
		add(Hausnummer);
		fields.add(Hausnummer);

		Stadt = new JTextField();
		Stadt.setBounds(110, 160, 200, 20);
		Stadt.setName("Stadt:");
		if (pers.getAdress() != null) {
			Stadt.setText(pers.getAdress().getCity());
		}
		add(Stadt);
		fields.add(Stadt);

		PLZ = new JTextField();
		PLZ.setBounds(110, 185, 200, 20);
		PLZ.setName("PLZ:");
		if (pers.getAdress() != null) {
			PLZ.setText(Integer.toString(pers.getAdress().getZipCode()));
		}
		add(PLZ);
		fields.add(PLZ);

		Land = new JTextField();
		Land.setBounds(110, 210, 200, 20);
		Land.setName("Land:");
		if (pers.getAdress() != null) {
			Land.setText(pers.getAdress().getCountry());
		}
		add(Land);
		fields.add(Land);
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
		label.setBounds(5, 57, 100, 20);
		add(label);
		label = new JLabel("Ende:");
		label.setBounds(5, 90, 100, 20);
		add(label);
		label = new JLabel("Sprache:");
		label.setBounds(5, 120, 100, 20);
		add(label);
		label = new JLabel("Raum:");
		label.setBounds(5, 145, 100, 20);
		add(label);
		label = new JLabel("Beamer:");
		label.setBounds(5, 170, 100, 20);
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

		// this.Start = new JTextField();
		// Start.setBounds(110, 55, 200, 20);
		// Start.setName("Dauer:");
		// if (c.getSartTime() != null) {
		// Start.setText(c.getSartTime().toString());
		// }
		// add(Start);
		// fields.add(Start);

		UtilDateModel model = new UtilDateModel();
		if (c.getSartTime() != null) {
			model.setValue(c.getSartTime());
		}
		model.setSelected(true);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Heute");
		p.put("text.month", "Monat");
		p.put("text.year", "Jahr");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.Start = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		this.Start.setName("Start:");
		Start.setBounds(110, 55, 200, 26);
		add(Start);
		fields.add(Start);

		model = new UtilDateModel();
		if (c.getEndTime() != null) {
			model.setValue(c.getEndTime());
		}
		model.setSelected(true);
		JDatePanelImpl datePanelEnd = new JDatePanelImpl(model, p);
		this.Ende = new JDatePickerImpl(datePanelEnd, new DateLabelFormatter());
		Ende.setName("Ende:");
		Ende.setBounds(110, 85, 200, 26);
		add(Ende);
		fields.add(Ende);

		this.Sprache = new JTextField();
		Sprache.setBounds(110, 120, 200, 20);
		Sprache.setName("Sprache:");
		Sprache.setText(c.getLanguage());
		add(Sprache);
		fields.add(Sprache);

		this.Raum = new JTextField();
		Raum.setBounds(110, 145, 200, 20);
		Raum.setName("Raum:");
		if (c.getRoomNumber() != null) {
			Raum.setText(c.getRoomNumber().toString());
		}
		add(Raum);
		fields.add(Raum);

		this.Beamer = new JCheckBox();
		Beamer.setBounds(110, 170, 200, 20);
		Beamer.setName("Beamer:");
		Beamer.setSelected((boolean) c.getNeedsBeamer());
		add(Beamer);
		fields.add(Beamer);

		exit = SchoolLauncher.getButton("Abbrechen", 110, 195, 100, 20, this, "Abbrechen", "Nichts wie raus hier...");
		add(exit);

		save = SchoolLauncher.getButton("Speichern", 230, 195, 100, 20, this, "Speichern",
				"Alles fein abspeichern damit auch nichts verloren geht");
		add(save);
		Construct();
	}

	EditFrame(TeacherDF editItem) {
		this.t = editItem;
		setTitle("Leerer editieren");
		Construct(editItem);

		JLabel label = new JLabel(t.toString());
		label.setBounds(110, 5, 100, 20);
		add(label);

		exit = SchoolLauncher.getButton("Abbrechen", 110, 235, 100, 20, this, "Abbrechen", "Nichts wie raus hier...");
		add(exit);
		save = SchoolLauncher.getButton("Speichern", 230, 235, 100, 20, this, "Speichern",
				"Alles fein abspeichern damit auch nichts verloren geht");
		add(save);
		Construct();
	}

	EditFrame(StudentDF editItem) {
		this.s = editItem;
		Construct(editItem);
		setTitle("Schüler editieren");

		JLabel label = new JLabel(s.toString());
		label.setBounds(110, 5, 100, 20);
		add(label);

		exit = SchoolLauncher.getButton("Abbrechen", 110, 235, 100, 20, this, "Abbrechen", "Nichts wie raus hier...");
		add(exit);
		save = SchoolLauncher.getButton("Speichern", 230, 235, 100, 20, this, "Speichern",
				"Alles fein abspeichern damit auch nichts verloren geht");
		add(save);

		Construct();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			this.dispose();
		} else if (e.getSource() == save) {
			if (c != null) {
				ReadDataCourse();
			} else if (t != null) {
				ReadDataPerson(this.t);
				ReadDataAdress(this.t.getAdress());

			} else if (s != null) {
				ReadDataPerson(this.s);
				ReadDataAdress(this.s.getAdress());
			}
			this.dispose();
		}

	}

	public void ReadDataPerson(Person per) {
		per.setFirstName(Vorname.getText());
		per.setLastName(Nachname.getText());
		try {
			UtilDateModel model = (UtilDateModel) Geburtsdatum.getModel();
			per.setBirthDate(model.getValue());
		} catch (Exception e2) {
			per.setBirthDate(null);
		}

	}

	public void ReadDataAdress(Adress add) {
		if (add != null) {
			add.setStreetName(Strasse.getText());
			add.setCity(Stadt.getText());
			add.setZipCode(Integer.parseInt(PLZ.getText()));
			add.setCountry(Land.getText());
			add.setHouseNumber(Hausnummer.getText());
		}
	}

	public void ReadDataCourse() {
		c.setTopic(Fach.getText());
		try {
			UtilDateModel model = (UtilDateModel) Ende.getModel();
			c.setEndTime(model.getValue());
		} catch (Exception e2) {
			c.setEndTime(new Date());
		}
		try {
			UtilDateModel model = (UtilDateModel) Start.getModel();
			c.setStartTime(model.getValue());
		} catch (Exception e2) {
			c.setStartTime(new Date());
		}
		c.setLanguage(Sprache.getText());
		try {
			c.setRoomNumber(Integer.parseInt(Raum.getText()));
		} catch (Exception e2) {
			c.setRoomNumber(0);
		}
		c.setNeedsBeamer(this.Beamer.isSelected());
	}
}
