package de.bbq.java.tasks.school;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;

import java.awt.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * @author Thorsten2201
 *
 */
public class FrameEdit extends JFrame implements ActionListener, ComponentListener {
	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -7952586514775627163L;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	private int winWidth = 343;
	private int winHight = 400;
	private final int labelWidth = 110;
	private final int buttonWidth = 100;
	private final int dateHeight = 26;

	private Course courseDF;
	private Teacher teacherDF;
	private Student studentDF;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Controls
	private JButton saveButton, exitButton;

	private JTextField topicTextField = new JTextField(), languageTextField = new JTextField("");
	private JCheckBox beamerCheckBox = new JCheckBox();;
	private JTextField lastNameTextField = new JTextField(""), firstNameTextField = new JTextField("");
	private JDatePickerImpl startTextField, birthDayTextField, endTextField;
	private JTextField streetTextField = new JTextField(""), cityTextField = new JTextField(""),
			zipTextField = new JTextField(""), countryTextField = new JTextField(""),
			roomNumberTextField = new JTextField("");
	private JTextField streetNumberTextField = new JTextField("");
//	private JComboBox<Integer> hourStartComboBox = new JComboBox<>(), minuteStartComboBox = new JComboBox<>(),
//			hourEndComboBox = new JComboBox<>(), minuteEndComboBox = new JComboBox<>();
	private JPanel contentPane;
	private JSpinner timeSpinnerStart, timeSpinnerEnd;
	private JComponent dateEditorStart,dateEditorEnd;
	private SpinnerModel spinnerModelStart, spinnerModelEnd;
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
				Kursverwaltung.saveItem(this.courseDF);
			} else if (this.teacherDF != null) {
				ReadDataPerson(this.teacherDF);
				ReadDataAdress(this.teacherDF.getAdress());
				Kursverwaltung.saveItem(this.teacherDF);
			} else if (this.studentDF != null) {
				ReadDataPerson(this.studentDF);
				ReadDataAdress(this.studentDF.getAdress());
				Kursverwaltung.saveItem(this.studentDF);
			}
			if (this.courseDF != null) {
				this.courseDF.setInEdit(false);
			} else if (this.teacherDF != null) {
				this.teacherDF.setInEdit(false);
			} else if (this.studentDF != null) {
				this.studentDF.setInEdit(false);
			}
			this.dispose();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Write control[].value to Object
	public void ReadDataPerson(SchoolPersonAbstract per) {
		Calendar cal = Calendar.getInstance();
		per.setFirstName(this.firstNameTextField.getText().toString());
		per.setLastName(this.lastNameTextField.getText().toString());
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
			if (this.zipTextField.getText().length() > 0) {
				try {
					add.setZipCode(Integer.parseInt(this.zipTextField.getText()));
				} catch (Exception e) {
					Kursverwaltung.showException(e);
				}
				add.setCountry(this.countryTextField.getText());
				add.setHouseNumber(this.streetNumberTextField.getText());
			}
		}
	}

	public void ReadDataCourse() {
		this.courseDF.setTopic(this.topicTextField.getText());
		Calendar cal = Calendar.getInstance(); // creates calendar
		Calendar date = Calendar.getInstance(); // creates calendar
		try {
			UtilDateModel model = (UtilDateModel) this.startTextField.getModel();
			date.setTime(model.getValue());
			cal.setTime((Date) timeSpinnerStart.getModel().getValue());
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.YEAR, date.get(Calendar.YEAR));
			cal.set(Calendar.MONTH, date.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
			this.courseDF.setStartTime(cal.getTime());
		} catch (Exception e2) {
			this.courseDF.setStartTime(null);
		}

		try {
			UtilDateModel model = (UtilDateModel) this.endTextField.getModel();
			date.setTime(model.getValue());
			cal.setTime((Date) timeSpinnerEnd.getValue());
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.YEAR, date.get(Calendar.YEAR));
			cal.set(Calendar.MONTH, date.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
			this.courseDF.setEndTime(cal.getTime());
		} catch (Exception e2) {
			this.courseDF.setEndTime(null);
		}
		this.courseDF.setLanguage(this.languageTextField.getText());
		this.courseDF.setRoomNumber(this.roomNumberTextField.getText());
		courseDF.setNeedsBeamer(this.beamerCheckBox.isSelected());
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	void Construct() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setMinimumSize(new Dimension(this.winWidth, this.winHight));
		addComponentListener(this);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	void Construct(Address address, JPanel labels, JPanel texts) {
		addTextField(labels, texts, "streetTextField", "Strasse:", address.getStreetName(), this.streetTextField, true);
		addTextField(labels, texts, "streetNumberTextField", "Hausnummer:", address.getHouseNumber(),
				this.streetNumberTextField, true);
		addTextField(labels, texts, "cityTextField", "Stadt:", address.getCity(), this.cityTextField, true);
		String zipCode = "";
		if (address.getZipCode() != 0) {
			zipCode = Integer.toString(address.getZipCode());
		}
		addTextField(labels, texts, "zipTextField", "PLZ:", zipCode, this.zipTextField, true);
		addTextField(labels, texts, "countryTextField", "Land:", address.getCountry(), this.countryTextField, true);
	}

	void Construct(SchoolPersonAbstract pers, JPanel labels, JPanel texts) {
		addTextField(labels, texts, "firstNameTextField", "Nachname:", pers.getLastName(), this.lastNameTextField,
				true);
		addTextField(labels, texts, "lastNameTextField", "Vorname:", pers.getFirstName(), this.firstNameTextField,
				true);
		UtilDateModel model = getDate(pers.getBirthDate(), false);
		// new UtilDateModel();
		// Calendar cal = getDate(pers.getBirthDate());
		// Calendar.getInstance();
		// if (pers.getBirthDate() != null) {
		// model.setValue(pers.getBirthDate());
		// cal.setTime(pers.getBirthDate());
		// } else {
		// cal.setTime(new Date());
		// cal.set(Calendar.HOUR_OF_DAY, (int) 0);
		// cal.set(Calendar.MINUTE, (int) 0);
		// cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.MILLISECOND, 0);
		// }
		// model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Heute");
		p.put("text.month", "Monat");
		p.put("text.year", "Jahr");

		// JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.birthDayTextField = new JDatePickerImpl(new JDatePanelImpl(model, p), new DateLabelFormatter());
		addDateTime(labels, texts, "birthDayTextField", "Geburtsdatum:", pers.getBirthDate(),this.birthDayTextField,
				this.timeSpinnerStart, true);
		Construct(pers.getAdress(), labels, texts);
	}

	FrameEdit(ITeacher editItem) { // ,WindowListener windowListener) {
		// this.addWindowListener(windowListener);
		this.teacherDF = (Teacher) editItem;
		this.teacherDF.setInEdit(true);
		Kursverwaltung.verifyData(this.teacherDF);
		setTitle("Leerer editieren");
		SpringLayout layout = new SpringLayout();
		setupSpringLayout(this.teacherDF.getId() + " " + this.teacherDF.toString(), 20, layout);

		JPanel labels = new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
		labels.setPreferredSize(new Dimension(labelWidth, 10));
		labels.setMaximumSize(new Dimension(labelWidth, Integer.MAX_VALUE));
		JPanel texts = new JPanel();
		texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));

		Construct(this.teacherDF, labels, texts);

		contentPane.add(labels);
		contentPane.add(texts);

		// Adjust constraints for the label so it's at (5,5).
		SpringLayout.Constraints labelCons = layout.getConstraints(labels);
		labelCons.setX(Spring.constant(5));
		labelCons.setY(Spring.constant(5));

		// Adjust constraints for the text field so it's at
		// (<label's right edge> + 5, 5).
		SpringLayout.Constraints textFieldCons = layout.getConstraints(texts);
		textFieldCons.setX(Spring.sum(Spring.constant(5), labelCons.getConstraint(SpringLayout.EAST)));
		textFieldCons.setY(Spring.constant(5));

		JPanel bottomPanel = new JPanel();
		// bottomPanel.setLayout(null);
		bottomPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);

		this.exitButton = Kursverwaltung.getButton("Abbrechen", 110, 235, 100, 20, this, "Abbrechen",
				"Nichts wie raus hier, Leerer stinken...");
		// add(this.exitButton);
		this.saveButton = Kursverwaltung.getButton("Speichern", 230, 235, 100, 20, this, "Speichern",
				"Den Leerer fein abspeichern damit auch nichts verloren geht");
		// add(this.saveButton);
		bottomPanel.add(this.exitButton);
		bottomPanel.add(this.saveButton);
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		bottomPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));

		// Adjust constraints for the content pane.
		setContainerSize(contentPane, 5);
		this.getContentPane().add(contentPane, BorderLayout.CENTER);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		this.winWidth = 343;
		this.winHight = 318;
		Construct();
	}

	FrameEdit(IStudent editItem) { // ,WindowListener windowListener) {
		// this.addWindowListener(windowListener);
		this.studentDF = (Student) editItem;
		this.studentDF.setInEdit(true);
		Kursverwaltung.verifyData(this.studentDF);
		setTitle("Schüler editieren");
		SpringLayout layout = new SpringLayout();
		setupSpringLayout(this.studentDF.getId() + " " + this.studentDF.toString(), 20, layout);

		JPanel labels = new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
		labels.setPreferredSize(new Dimension(labelWidth, 10));
		labels.setMaximumSize(new Dimension(labelWidth, Integer.MAX_VALUE));
		JPanel texts = new JPanel();
		texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));

		Construct(this.studentDF, labels, texts);

		contentPane.add(labels);
		contentPane.add(texts);

		// Adjust constraints for the label so it's at (5,5).
		SpringLayout.Constraints labelCons = layout.getConstraints(labels);
		labelCons.setX(Spring.constant(5));
		labelCons.setY(Spring.constant(5));

		// Adjust constraints for the text field so it's at
		// (<label's right edge> + 5, 5).
		SpringLayout.Constraints textFieldCons = layout.getConstraints(texts);
		textFieldCons.setX(Spring.sum(Spring.constant(0), labelCons.getConstraint(SpringLayout.EAST)));
		textFieldCons.setY(Spring.constant(5));

		JPanel bottomPanel = new JPanel();
		// bottomPanel.setLayout(null);
		bottomPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);

		this.exitButton = Kursverwaltung.getButton("Abbrechen", 110, 235, 100, 20, this, "Abbrechen",
				"Nichts wie raus hier, Schüler nerven...");
		// add(this.exitButton);
		this.saveButton = Kursverwaltung.getButton("Speichern", 230, 235, 100, 20, this, "Speichern",
				"Schüler fein abspeichern damit er auch nichts über sich vergissst");
		// add(this.saveButton);
		bottomPanel.add(this.exitButton);
		bottomPanel.add(this.saveButton);
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		bottomPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));

		// Adjust constraints for the content pane.
		setContainerSize(contentPane, 5);
		this.getContentPane().add(contentPane, BorderLayout.CENTER);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		this.winWidth = 343;
		this.winHight = 318;
		Construct();
	}

	FrameEdit(ICourse editItem) { // ),WindowListener windowListener) {
		// this.addWindowListener(windowListener);
		this.courseDF = (Course) editItem;
		this.courseDF.setInEdit(true);
		Kursverwaltung.verifyData(this.courseDF);
		setTitle("Kurs editieren");

		SpringLayout layout = new SpringLayout();
		setupSpringLayout(this.courseDF.getId() + " " + this.courseDF.toString(), 20, layout);
		JPanel labels = new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
		labels.setPreferredSize(new Dimension(labelWidth, 10));
		labels.setMaximumSize(new Dimension(labelWidth, Integer.MAX_VALUE));
		JPanel texts = new JPanel();
		texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
		addTextField(labels, texts, "topicTextField", "Fach:", this.courseDF.getTopic(), this.topicTextField, true);

		Date value = this.courseDF.getSartTime();
		UtilDateModel model = getDate(value, true);// new UtilDateModel();
		// Calendar cal = Calendar.getInstance();
		// if (value != null) {
		// model.setValue(value);
		// cal.setTime(value);
		// cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.MILLISECOND, 0);
		// } else {
		// cal.setTime(new Date());
		// cal.set(Calendar.HOUR_OF_DAY, (int) 0);
		// cal.set(Calendar.MINUTE, (int) 0);
		// cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.MILLISECOND, 0);
		// }
		// model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Heute");
		p.put("text.month", "Monat");
		p.put("text.year", "Jahr");

		// Integer[] hourArray = new Integer[Kursverwaltung.getWorkEnd() -
		// Kursverwaltung.getWorkStart()];
		// for (Integer index = Kursverwaltung.getWorkStart(); index <
		// Kursverwaltung.getWorkEnd(); index++) {
		// hourArray[index - Kursverwaltung.getWorkStart()] = index;
		// }
		// Integer[] minuteAray = new Integer[60 / 5];
		// for (Integer index = 0; index < 60; index = index + 5) {
		// minuteAray[index / 5] = index;
		// }
		// JDatePanelImpl datePanel =;
		this.startTextField = new JDatePickerImpl(new JDatePanelImpl(model, p), new DateLabelFormatter());
		// hourStartComboBox = new JComboBox<>(hourArray);
		// minuteStartComboBox = new JComboBox<>(minuteAray);

		spinnerModelStart  = new SpinnerDateModel();
		timeSpinnerStart = new JSpinner(spinnerModelStart);
		dateEditorStart = new JSpinner.DateEditor(timeSpinnerStart, "HH:mm:ss");
		timeSpinnerStart.setEditor(dateEditorStart);
		timeSpinnerStart.setValue(startTextField.getModel().getValue());
//		texts.add(timeSpinnerStart);
		addDateTime(labels, texts, "startDatePicker", "Start:", this.courseDF.getSartTime(), this.startTextField,timeSpinnerStart, true);
		//
		// hourEndComboBox = new JComboBox<>(hourArray);
		// minuteEndComboBox = new JComboBox<>(minuteAray);
		value = this.courseDF.getEndTime();
		model = getDate(value, true);
		// cal = Calendar.getInstance();
		// if (value != null) {
		// model.setValue(value);
		// cal.setTime(value);
		// cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.MILLISECOND, 0);
		// } else {
		// cal.setTime(new Date());
		// cal.set(Calendar.HOUR_OF_DAY, (int) 0);
		// cal.set(Calendar.MINUTE, (int) 0);
		// cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.MILLISECOND, 0);
		// }
		// model.setSelected(true);
		// datePanel = new JDatePanelImpl(model, p);
		this.endTextField = new JDatePickerImpl(new JDatePanelImpl(model, p), new DateLabelFormatter());

		spinnerModelEnd = new SpinnerDateModel();
		timeSpinnerEnd = new JSpinner(spinnerModelEnd);
		dateEditorEnd = new JSpinner.DateEditor(timeSpinnerEnd, "HH:mm:ss");
		timeSpinnerEnd.setEditor(dateEditorEnd);
		timeSpinnerEnd.setValue(this.endTextField.getModel().getValue());
		texts.add(timeSpinnerEnd);

		addDateTime(labels, texts, "endDatePicker", "Ende:", this.courseDF.getEndTime(), endTextField,timeSpinnerEnd, true);
		addTextField(labels, texts, "languageTextField", "Sprache:", this.courseDF.getLanguage(),
				this.languageTextField, true);
		String roomNr = "";
		if (this.courseDF.getRoomNumber() != null) {
			roomNr = this.courseDF.getRoomNumber().toString();
		}
		addTextField(labels, texts, "roomNumberTextField", "Raum:", roomNr, this.roomNumberTextField, true);

		addCheckBox(labels, texts, "beamerCheckBox", "Beamer:", this.courseDF.getNeedsBeamer(), 20, this.beamerCheckBox,
				false);

		contentPane.add(labels);
		contentPane.add(texts);

		// Adjust constraints for the label so it's at (5,5).
		SpringLayout.Constraints labelCons = layout.getConstraints(labels);
		labelCons.setX(Spring.constant(5));
		labelCons.setY(Spring.constant(5));

		// Adjust constraints for the text field so it's at
		// (<label's right edge> + 5, 5).
		SpringLayout.Constraints textFieldCons = layout.getConstraints(texts);
		textFieldCons.setX(Spring.sum(Spring.constant(5), labelCons.getConstraint(SpringLayout.EAST)));
		textFieldCons.setY(Spring.constant(5));

		JPanel bottomPanel = new JPanel();
		// bottomPanel.setLayout(null);
		bottomPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		this.exitButton = Kursverwaltung.getButton("exitButton", 110, 195, buttonWidth, 20, this, "Abbrechen",
				"Nichts wie raus hier, Kurse sind anstrengend...");
		// add(this.exitButton);
		this.saveButton = Kursverwaltung.getButton("saveButton", 230, 195, buttonWidth, 20, this, "Speichern",
				"Kurs fein abspeichern damit er auch nicht verloren geht");
		// add(this.saveButton);

		bottomPanel.add(this.exitButton);
		bottomPanel.add(this.saveButton);
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		bottomPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));

		// Adjust constraints for the content pane.
		setContainerSize(contentPane, 5);
		this.getContentPane().add(contentPane, BorderLayout.CENTER);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		this.winWidth = 343;
		this.winHight = 269;

		Construct();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	public boolean hastItem(SchoolItemAbstract otherItem) {
		boolean ret = false;
		if (otherItem != null) {
			ret |= otherItem.equals(this.courseDF);
			ret |= otherItem.equals(this.teacherDF);
			ret |= otherItem.equals(this.studentDF);
		}
		return ret;
	}

	public SchoolItemAbstract getItem() {
		if (this.courseDF != null) {
			return courseDF;
		} else if (this.teacherDF != null) {
			return teacherDF;
		} else if (this.studentDF != null) {
			return studentDF;
		}
		return null;
	}

	private void setContainerSize(Container parent, int pad) {
		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component[] components = parent.getComponents();
		Spring maxHeightSpring = Spring.constant(0);
		SpringLayout.Constraints pCons = layout.getConstraints(parent);

		// Set the container's right edge to the right edge
		// of its rightmost component + padding.
		Component rightmost = components[components.length - 1];
		SpringLayout.Constraints rCons = layout.getConstraints(rightmost);
		pCons.setConstraint(SpringLayout.EAST,
				Spring.sum(Spring.constant(pad), rCons.getConstraint(SpringLayout.EAST)));

		// Set the container's bottom edge to the bottom edge
		// of its tallest component + padding.
		for (int i = 0; i < components.length; i++) {
			SpringLayout.Constraints cons = layout.getConstraints(components[i]);
			maxHeightSpring = Spring.max(maxHeightSpring, cons.getConstraint(SpringLayout.SOUTH));
		}
		pCons.setConstraint(SpringLayout.SOUTH, Spring.sum(Spring.constant(pad), maxHeightSpring));
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		int width = getWidth();
		int height = getHeight();
		boolean resize = false;
		// if (width > this.winLength) {
		// resize = true;
		// width = this.winLength;
		// }
		if (height > this.winHight) {
			resize = true;
			height = this.winHight;
		}
		if (resize) {
			setSize(width, height);
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}

	void setupSpringLayout(String headline, int height, SpringLayout layout) {
		JPanel topPanel = new JPanel();
		JLabel label = new JLabel(headline);
		// label.setBounds(110, 5, 100, 20);
		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		topPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, height));
		topPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		topPanel.add(label);

		// Lay out the panel.
		Container borderPane = this.getContentPane();
		borderPane.setLayout(new BorderLayout());
		borderPane.add(topPanel, BorderLayout.NORTH);

		contentPane = new JPanel();
		contentPane.setLayout(layout);
		contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	}

	private UtilDateModel getDate(Date value, boolean withTime) {
		UtilDateModel model = new UtilDateModel();
		Calendar cal = Calendar.getInstance();
		if (value != null) {
			cal.setTime(value);
		} else {
			cal.setTime(new Date());
		}
		if (!withTime) {
			cal.set(Calendar.HOUR_OF_DAY, (int) 0);
			cal.set(Calendar.MINUTE, (int) 0);
			cal.set(Calendar.SECOND,(int) 0);
			cal.set(Calendar.MILLISECOND,(int) 0);
		} else {
			cal.set(Calendar.SECOND,(int) 0);
			cal.set(Calendar.MILLISECOND,(int) 0);
		}
		model.setValue(cal.getTime());
		model.setSelected(true);
		return model;
	}

	public void addDateTime(JPanel labels, JPanel texts, String name, String text, Date value, JDatePickerImpl date,
			JSpinner timeSpinnerStart, boolean spacer) { // JComboBox<Integer> hours, JComboBox<Integer>
								// minutes,
		addLabel(labels, text, dateHeight);

		JPanel panel = new JPanel();
		BorderLayout layout = new BorderLayout(); // SpringLayout();
		panel.setLayout(layout);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateHeight));
		panel.setMinimumSize(new Dimension(Integer.MAX_VALUE, dateHeight));

		UtilDateModel model = new UtilDateModel();
		Calendar cal = Calendar.getInstance();
		if (value != null) {
			cal.setTime(value);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			model.setValue(value);
		} else {
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, (int) 0);
			cal.set(Calendar.MINUTE, (int) 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}
		date.setName(name);
		date.setMinimumSize(new Dimension(130, dateHeight));
		date.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateHeight));
		panel.add(date, BorderLayout.CENTER);
		if (timeSpinnerStart != null) {
			panel.add(timeSpinnerStart,BorderLayout.EAST);
		}
		// // addComponent(texts, name, dateHeight, panel);
		// int selectIndex = 0;
		// if (hours != null) {
		// SpinnerModel spinnerModel = new SpinnerDateModel();
		// JSpinner timeSpinner = new JSpinner(spinnerModel);
		// JComponent dateEditor = new JSpinner.DateEditor(timeSpinner,
		// "HH:mm:ss");
		// timeSpinner.setEditor(dateEditor);
		// if (value != null) {
		// timeSpinner.setValue(cal.getTime());
		// }
		// panel.add(timeSpinner);
		//
		// if (false) {
		// Integer[] hourArray = new Integer[Kursverwaltung.getWorkEnd() -
		// Kursverwaltung.getWorkStart()];
		// for (Integer index = Kursverwaltung.getWorkStart(); index <
		// Kursverwaltung.getWorkEnd(); index++) {
		// hourArray[index - Kursverwaltung.getWorkStart()] = index;
		// if (cal.get(Calendar.HOUR_OF_DAY) == index) {
		// selectIndex = index - Kursverwaltung.getWorkStart();
		// }
		// }
		// hours.setMaximumSize(new Dimension(50, 26));
		// hours.setMinimumSize(new Dimension(50, 26));
		// hours.setSelectedIndex(selectIndex);
		// // if (minutes != null) {
		// // hours.setBorder(new EmptyBorder(0,0,0,10));
		// // }
		// panel.add(hours);
		// }
		// }
		// if (minutes != null && false) {
		// selectIndex = 0;
		// Integer[] minuteAray = new Integer[60 / 5];
		// for (Integer index = 0; index < 60; index = index + 5) {
		// minuteAray[index / 5] = index;
		// if (cal.get(Calendar.MINUTE) == index) {
		// selectIndex = index / 5;
		// }
		// }
		// // minutes.setBounds(180, 0, 50, 26);
		// minutes.setMaximumSize(new Dimension(50, 26));
		// minutes.setMinimumSize(new Dimension(50, 26));
		// minutes.setSelectedIndex(selectIndex);
		// panel.add(minutes);
		// }
//		Component[] components = panel.getComponents();
//		Spring xPad = Spring.constant(5);
//		Spring ySpring = Spring.constant(0);
//		Spring xSpring = ySpring;

		// Make every component 5 pixels away from the component to its left.
//		for (int i = 0; i < components.length; i++) {
//			SpringLayout.Constraints cons = layout.getConstraints(components[i]);
//			cons.setX(xSpring);
//			if (i == components.length - 1) {
//				xSpring = cons.getConstraint("East");
//			} else {
//				xSpring = Spring.sum(xPad, cons.getConstraint("East"));
//			}
//			cons.setY(ySpring);
//		}
//		Spring maxHeightSpring = Spring.constant(dateHeight);
		// Make the window's preferred size depend on its components.
//		SpringLayout.Constraints pCons = layout.getConstraints(panel);
//		pCons.setConstraint("East", xSpring);
//		pCons.setConstraint("South", Spring.sum(maxHeightSpring, ySpring));
		texts.add(panel);

		if (spacer) {
			labels.add(Box.createRigidArea(new Dimension(0, 5)));
			texts.add(Box.createVerticalStrut(5));
		}
	}

	@Override
	public String toString() {
		if (this.courseDF != null) {
			return this.courseDF.toString();
		} else if (this.teacherDF != null) {
			return this.teacherDF.toString();
		} else if (this.studentDF != null) { 
			return this.studentDF.toString();
		} 
		return "";
		//		return "FrameEdit [courseDF=" + courseDF.toString() + ", teacherDF=" + teacherDF + ", studentDF=" + studentDF + "]";
	}

	public void addCheckBox(JPanel labels, JPanel texts, String name, String text, Boolean value, int height,
			JCheckBox checkBox, boolean spacer) {
		addLabel(labels, text, height);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		panel.setMinimumSize(new Dimension(Integer.MAX_VALUE, height));

		checkBox.setSelected((boolean) value);
		panel.add(checkBox);
		panel.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		texts.add(panel);

		if (spacer) {
			labels.add(Box.createRigidArea(new Dimension(0, 5)));
			texts.add(Box.createVerticalStrut(5));
		}
	}

	public void addTextField(JPanel labels, JPanel texts, String name, String text, String value, JTextField textField,
			boolean spacer) {
		addLabel(labels, text, 5);
		textField.setText(value);
		addComponent(texts, name, 19, textField);

		if (spacer) {
			labels.add(Box.createRigidArea(new Dimension(0, 5)));
			texts.add(Box.createVerticalStrut(5));
		}
	}

	public void addComponent(JPanel texts, String name, int height, JComponent component) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		panel.setMinimumSize(new Dimension(Integer.MAX_VALUE, height));

		component.setName(name);
		component.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		panel.add(component);
		texts.add(panel);
	}

	public void addLabel(JPanel labels, String text, int height) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel(text);
		label.setMaximumSize(new Dimension(labelWidth, height + 10));
		label.setMinimumSize(new Dimension(labelWidth, height + 10));
		panel.add(label);

		labels.add(panel);
	}

}
