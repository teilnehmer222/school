package de.bbq.java.tasks.school;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * @author Thorsten2201
 *
 */
public class FrameEdit extends JFrame implements ActionListener {
	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -7952586514775627163L;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	private int winLength = 400;
	private int winHight = 400;

	private Course courseDF;
	private Teacher teacherDF;
	private Student studentDF;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Controls
	private JButton saveButton, exitButton;

	private JTextField topicTextField, languageTextField;
	private JCheckBox beamerCheckBox;
	private JTextField lastNameTextField, firstNameTextField;
	private JDatePickerImpl startTextField, birthDayTextField, endTextField;
	private JTextField streetTextField, cityTextField, zipTextField, countryTextField, roomNumberTextField;
	private JTextField streetNumberTextField;
	private JComboBox<Integer> hourStartComboBox, minuteStartComboBox, hourEndComboBox, minuteEndComboBox;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.exitButton) {
			this.dispose();
		} else if (e.getSource() == this.saveButton) {
			if (this.courseDF != null) {
				ReadDataCourse();
			} else if (this.teacherDF != null) {
				ReadDataPerson(this.teacherDF);
				ReadDataAdress(this.teacherDF.getAdress());

			} else if (this.studentDF != null) {
				ReadDataPerson(this.studentDF);
				ReadDataAdress(this.studentDF.getAdress());
			}
			this.dispose();
		}

	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Write control[].value to Object
	public void ReadDataPerson(SchoolPersonAbstract per) {
		Calendar cal = Calendar.getInstance();
		per.setFirstName(this.firstNameTextField.getText());
		per.setLastName(this.lastNameTextField.getText());
		try {
			UtilDateModel model = (UtilDateModel) this.birthDayTextField.getModel();
			cal.setTime(model.getValue());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		} catch (Exception e2) {
			per.setBirthDate(null);
		}
		per.setBirthDate(cal.getTime());
	}

	public void ReadDataAdress(Address add) {
		if (add != null) {
			add.setStreetName(this.streetTextField.getText());
			add.setCity(this.cityTextField.getText());
			try {
				add.setZipCode(Integer.parseInt(this.zipTextField.getText()));
			} catch (Exception e) {
				// TODO
				System.out.println(e.getStackTrace());
			}
			add.setCountry(this.countryTextField.getText());
			add.setHouseNumber(this.streetNumberTextField.getText());
		}
	}

	public void ReadDataCourse() {
		this.courseDF.setTopic(this.topicTextField.getText());
		Calendar cal = Calendar.getInstance(); // creates calendar
		try {
			UtilDateModel model = (UtilDateModel) this.startTextField.getModel();
			cal.setTime(model.getValue());
			cal.set(Calendar.HOUR_OF_DAY, (int) this.hourStartComboBox.getSelectedItem());
			cal.set(Calendar.MINUTE, (int) this.minuteStartComboBox.getSelectedItem());
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		} catch (Exception e2) {
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}
		this.courseDF.setStartTime(cal.getTime());
		try {
			UtilDateModel model = (UtilDateModel) this.endTextField.getModel();
			cal.setTime(model.getValue());
			cal.set(Calendar.HOUR_OF_DAY, (int) this.hourEndComboBox.getSelectedItem());
			cal.set(Calendar.MINUTE, (int) this.minuteEndComboBox.getSelectedItem());
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		} catch (Exception e2) {
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}
		this.courseDF.setEndTime(cal.getTime());
		this.courseDF.setLanguage(this.languageTextField.getText());
		this.courseDF.setRoomNumber(this.roomNumberTextField.getText());
		courseDF.setNeedsBeamer(this.beamerCheckBox.isSelected());
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	void Construct() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		pack();
		setSize(this.winLength, this.winHight);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	void Construct(SchoolPersonAbstract pers) {
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

		this.lastNameTextField = new JTextField();
		this.lastNameTextField.setBounds(110, 30, 200, 20);
		this.lastNameTextField.setName("Nachname:");
		this.lastNameTextField.setText(pers.getLastName());
		add(this.lastNameTextField);

		this.firstNameTextField = new JTextField();
		this.firstNameTextField.setBounds(110, 55, 200, 20);
		this.firstNameTextField.setName("Vorname:");
		this.firstNameTextField.setText(pers.getFirstName());
		add(this.firstNameTextField);

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
		this.birthDayTextField = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		this.birthDayTextField.setBounds(110, 80, 200, 26);
		add(this.birthDayTextField);

		this.streetTextField = new JTextField();
		this.streetTextField.setBounds(110, 110, 200, 20);
		this.streetTextField.setName("Strasse:");
		if (pers.getAdress() != null) {
			this.streetTextField.setText(pers.getAdress().getStreetName());
		}
		add(this.streetTextField);

		this.streetNumberTextField = new JTextField();
		this.streetNumberTextField.setBounds(110, 135, 200, 20);
		this.streetNumberTextField.setName("Hausnummer:");
		if (pers.getAdress() != null) {
			this.streetNumberTextField.setText(pers.getAdress().getHouseNumber());
		}
		add(this.streetNumberTextField);

		this.cityTextField = new JTextField();
		this.cityTextField.setBounds(110, 160, 200, 20);
		this.cityTextField.setName("Stadt:");
		if (pers.getAdress() != null) {
			this.cityTextField.setText(pers.getAdress().getCity());
		}
		add(this.cityTextField);

		this.zipTextField = new JTextField();
		this.zipTextField.setBounds(110, 185, 200, 20);
		this.zipTextField.setName("PLZ:");
		if (pers.getAdress() != null) {
			this.zipTextField.setText(Integer.toString(pers.getAdress().getZipCode()));
		}
		add(this.zipTextField);

		this.countryTextField = new JTextField();
		this.countryTextField.setBounds(110, 210, 200, 20);
		this.countryTextField.setName("Land:");
		if (pers.getAdress() != null) {
			this.countryTextField.setText(pers.getAdress().getCountry());
		}
		add(this.countryTextField);
	}

	FrameEdit(ITeacher editItem) {
		this.teacherDF = (Teacher) editItem;
		setTitle("Leerer editieren");
		Construct(this.teacherDF);

		JLabel label = new JLabel(this.teacherDF.toString());
		label.setBounds(110, 5, 100, 20);
		add(label);

		this.exitButton = SchoolLauncher.getButton("Abbrechen", 110, 235, 100, 20, this, "Abbrechen",
				"Nichts wie raus hier, Leerer stinken...");
		add(this.exitButton);
		this.saveButton = SchoolLauncher.getButton("Speichern", 230, 235, 100, 20, this, "Speichern",
				"Den Leerer fein abspeichern damit auch nichts verloren geht");
		add(this.saveButton);
		Construct();
	}

	FrameEdit(IStudent editItem) {
		this.studentDF = (Student) editItem;
		Construct(this.studentDF);
		setTitle("Schüler editieren");

		JLabel label = new JLabel(studentDF.toString());
		label.setBounds(110, 5, 100, 20);
		add(label);

		this.exitButton = SchoolLauncher.getButton("Abbrechen", 110, 235, 100, 20, this, "Abbrechen",
				"Nichts wie raus hier, Schüler nerven...");
		add(this.exitButton);
		this.saveButton = SchoolLauncher.getButton("Speichern", 230, 235, 100, 20, this, "Speichern",
				"Schüler fein abspeichern damit er auch nichts über sich vergissst");
		add(this.saveButton);

		Construct();
	}

	FrameEdit(ICourse editItem) {
		this.courseDF = (Course) editItem;
		setTitle("Kurs editieren");
		JLabel label = new JLabel(this.courseDF.getCourseName());
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

		this.topicTextField = new JTextField();
		this.topicTextField.setBounds(110, 30, 200, 20);
		this.topicTextField.setName("topicTextField");
		this.topicTextField.setText(this.courseDF.getTopic());
		add(this.topicTextField);

		UtilDateModel model = new UtilDateModel();
		if (this.courseDF.getSartTime() != null) {
			model.setValue(this.courseDF.getSartTime());
		}
		model.setSelected(true);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Heute");
		p.put("text.month", "Monat");
		p.put("text.year", "Jahr");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.startTextField = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		this.startTextField.setName("startTextField");
		this.startTextField.setBounds(110, 55, 120, 26);
		add(this.startTextField);
		Calendar cal = Calendar.getInstance();
		if (this.courseDF.getSartTime() != null) {
			cal.setTime(this.courseDF.getSartTime());
		} else {
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, (int) 0);
			cal.set(Calendar.MINUTE, (int) 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}
		int selectIndex = 0;
		Integer[] hours = new Integer[SchoolLauncher.getWorkEnd() - SchoolLauncher.getWorkStart()];
		for (Integer index = SchoolLauncher.getWorkStart(); index < SchoolLauncher.getWorkEnd(); index++) {
			hours[index - SchoolLauncher.getWorkStart()] = index;
			if (cal.get(Calendar.HOUR_OF_DAY) == index) {
				selectIndex = index - SchoolLauncher.getWorkStart();
			}

		}
		this.hourStartComboBox = new JComboBox<>(hours);
		this.hourStartComboBox.setBounds(235, 57, 50, 20);
		this.hourStartComboBox.setSelectedIndex(selectIndex);
		this.add(hourStartComboBox);

		selectIndex = 0;
		Integer[] minutes = new Integer[60 / 5];
		for (Integer index = 0; index < 60; index = index + 5) {
			minutes[index / 5] = index;
			if (cal.get(Calendar.MINUTE) == index) {
				selectIndex = index / 5;
			}

		}
		this.minuteStartComboBox = new JComboBox<>(minutes);
		this.minuteStartComboBox.setBounds(290, 57, 50, 20);
		this.minuteStartComboBox.setSelectedIndex(selectIndex);
		this.add(minuteStartComboBox);

		model = new UtilDateModel();
		if (this.courseDF.getEndTime() != null) {
			model.setValue(this.courseDF.getEndTime());
		}
		model.setSelected(true);
		JDatePanelImpl datePanelEnd = new JDatePanelImpl(model, p);
		this.endTextField = new JDatePickerImpl(datePanelEnd, new DateLabelFormatter());
		this.endTextField.setName("endTextField");
		this.endTextField.setBounds(110, 85, 120, 26);
		this.add(this.endTextField);

		if (this.courseDF.getEndTime() != null) {
			cal.setTime(this.courseDF.getEndTime());
		} else {
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, (int) 0);
			cal.set(Calendar.MINUTE, (int) 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}

		selectIndex = 0;
		for (Integer index = SchoolLauncher.getWorkStart(); index < SchoolLauncher.getWorkEnd(); index++) {
			if (cal.get(Calendar.HOUR_OF_DAY) == index) {
				selectIndex = index - SchoolLauncher.getWorkStart();
				break;
			}

		}
		this.hourEndComboBox = new JComboBox<>(hours);
		this.hourEndComboBox.setBounds(235, 87, 50, 20);
		this.hourEndComboBox.setSelectedIndex(selectIndex);
		this.add(hourEndComboBox);

		selectIndex = 0;
		for (Integer index = 0; index < 60; index = index + 5) {
			if (cal.get(Calendar.MINUTE) == index) {
				selectIndex = index / 5;
				break;
			}
		}
		this.minuteEndComboBox = new JComboBox<>(minutes);
		this.minuteEndComboBox.setBounds(290, 87, 50, 20);
		this.minuteEndComboBox.setSelectedIndex(selectIndex);
		this.add(minuteEndComboBox);

		this.languageTextField = new JTextField();
		this.languageTextField.setBounds(110, 120, 200, 20);
		this.languageTextField.setName("languageTextField");
		this.languageTextField.setText(this.courseDF.getLanguage());
		add(this.languageTextField);

		this.roomNumberTextField = new JTextField();
		this.roomNumberTextField.setBounds(110, 145, 200, 20);
		this.roomNumberTextField.setName("roomNumberTextField");
		if (this.courseDF.getRoomNumber() != null) {
			this.roomNumberTextField.setText(this.courseDF.getRoomNumber().toString());
		}
		add(this.roomNumberTextField);

		this.beamerCheckBox = new JCheckBox();
		this.beamerCheckBox.setBounds(110, 170, 200, 20);
		this.beamerCheckBox.setName("beamerCheckBox");
		this.beamerCheckBox.setSelected((boolean) this.courseDF.getNeedsBeamer());
		add(this.beamerCheckBox);

		this.exitButton = SchoolLauncher.getButton("exitButton", 110, 195, 100, 20, this, "Abbrechen",
				"Nichts wie raus hier, Kurse sind anstrengend...");
		add(this.exitButton);

		this.saveButton = SchoolLauncher.getButton("saveButton", 230, 195, 100, 20, this, "Speichern",
				"Kurs fein abspeichern damit er auch nicht verloren geht");
		add(this.saveButton);
		Construct();
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
