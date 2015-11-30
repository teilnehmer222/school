package de.bbq.java.tasks.school;

import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Thorsten2201
 *
 */
public class SchoolLauncher extends JFrame implements WindowListener {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private static final long serialVersionUID = -2039718453107554584L;
	private static final int winLength = 800;
	private static final int winHight = 430;
	private static final int workStart = 7;
	private static final int workEnd = 17;
	private static EDaoSchool selectedDao = EDaoSchool.FILE;
	private static SchoolLauncher launcher;
	private static ArrayList<FrameEdit> editFrames = new ArrayList<>();
	private static boolean console = false;
	private static Shell shell;
	private static DateFormat dateFormatGermany;

	private PanelCourse panel1;
	private PanelTeacher panel2;
	private PanelStudent panel3;
	/////////////////////////////////////////////////////////////////////////////////////

	public static EDaoSchool getSelectedDao() {
		return selectedDao;
	}

	public static void setSelectedDao(EDaoSchool selectedDao) {
		SchoolLauncher.selectedDao = selectedDao;
		Course.dataAccessObject = DaoSchoolAbstract.getDaoSchool(selectedDao);
		Teacher.dataAccessObject = DaoSchoolAbstract.getDaoSchool(selectedDao);
		Student.dataAccessObject = DaoSchoolAbstract.getDaoSchool(selectedDao);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public static void main(String[] args) {
		console = false;
		if (getGermanDate() == null) {
			Locale locDE = new Locale(Locale.GERMANY.getCountry());
			setDateFormatGermany(DateFormat.getDateInstance(DateFormat.FULL, locDE));
		}
		launcher = new SchoolLauncher();
		args = new String[] { "asasas", "54908" };

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("DEBUG")) {
				console = true;
				launcher.setVisible(true);
			} else {
				console = true;
				String out = "";
				String add = "hat er";
				String it = "er steht";
				String param = "deinen";
				for (int index = 0; index < args.length; index++) {
					if (index != args.length - 1 && index > 0) {
						out += ", ";
						add = "haben sie";
						it = "sie stehen";
						param = "deine";
					} else if (index == args.length - 1) {
						out += " und ";
						add = "haben sie";
						it = "sie stehen";
						param = "deine";
					}
					out += args[index];
				}
				System.out.println("Vielen Dank für " + param + " Parameter " + out + ",\ngebracht " + add
						+ " dir nichts, aber dafür " + it + " jetzt da\nwo früher mal deine GUI gestanden hat.");
				System.out.println();
				launcher.setVisible(false);
			}
		} else {
			launcher.setVisible(!console);
		}
		if (console) {
			shell = new Shell();
			shell.Start(console);
			getInstance().closeConnections();
			System.exit(0);
		}
	}

	public static SchoolLauncher getInstance() {
		return launcher;
	}
	private void refresh() {
		panel1.refresh();
		panel2.refresh();
		panel3.refresh();
	}
	private SchoolLauncher() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				SchoolLauncher.getInstance().closeConnections();
				System.exit(0);
			}
		});
		// Image icon = Toolkit.getDefaultToolkit().getImage("form.gif");
		setTitle("Schulverwaltung");
		setSize(winLength, winHight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		addControls();
	}

	@SuppressWarnings("static-access")
	public static void showErrorMessage(String s) {
		if (console) {
			getInstance().shell.showMessage("Error", s);
		} else {
			JOptionPane.showInternalMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("static-access")
	public static String showInput(String s) {
		if (console) {
			return getInstance().shell.showInput(s);
		} else {
			return JOptionPane.showInternalInputDialog(null, s, "Information", JOptionPane.QUESTION_MESSAGE);
		}
	}

	@SuppressWarnings("static-access")
	public static void showMessage(String s) {
		if (console) {
			getInstance().shell.showMessage(s);
		} else {
			// JOptionPane.showMessageDialog(null, s, "Information",
			// JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showInternalMessageDialog(null, s, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@SuppressWarnings("static-access")
	public static void showException(Exception e) {
		if (console) {
			getInstance().shell.showMessage("Exception", e.getMessage(), e.getStackTrace().toString());
		} else {
			JOptionPane.showInternalMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getStackTrace());
		}
	}

	protected void closeConnections() {
		DaoSchoolAbstract.closeConnections();
	}

	private void addControls() {
		this.setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		ImageIcon icon = createImageIcon("course.png");

		panel1 = new PanelCourse();
		tabbedPane.addTab("Kurse", icon, panel1, "Kurse verwalten");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		icon = createImageIcon("teacher.png");
		panel2 = new PanelTeacher();
		tabbedPane.addTab("Leerer", icon, panel2, "Leerer den Kursen zuweisen");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		icon = createImageIcon("student.png");
		panel3 = new PanelStudent();
		tabbedPane.addTab("Schüler", icon, panel3, "Schüler den Kursen zuweisen");
		panel3.setPreferredSize(new Dimension(410, 50));
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane source = (JTabbedPane) e.getSource();
				switch (source.getSelectedIndex()) {
				case 0:
					panel1.refresh();
					break;
				case 1:
					panel2.refresh();
					break;
				case 2:
					panel3.refresh();
					break;

				}
			}
		});
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		panel1.refresh();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static for Panels, etc.
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = SchoolLauncher.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Datei nicht gefunden: " + path);
			return null;
		}
	}

	public static JButton getButton(String buName, int x, int y, int width, int height, ActionListener linr,
			String display, String tooltip) {
		JButton button = new JButton(buName);
		button.setName(buName);
		if (display.length() > 0) {
			button.setText(display);
		}
		if (tooltip.length() > 0) {
			button.setToolTipText(tooltip);
		}
		button.setBounds(x, y, width, height);
		button.addActionListener(linr);
		return button;
	}

	public static Integer getWorkStart() {
		return workStart;
	}

	public static Integer getWorkEnd() {
		return workEnd;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class factories returning interfaces
	public static ArrayList<ICourse> getCourseList() {
		return Course.getCourses();
	}

	public static ArrayList<ITeacher> getTeacherList() {
		return Teacher.getTeachers();
	}

	public static ArrayList<IStudent> getStudentList() {
		return Student.getStudents();
	}

	public static ArrayList<ICourse> getCourses(ITeacher teacher) {
		ArrayList<ICourse> ret = new ArrayList<>();
		for (ICourse c : getCourseList()) {
			if (c.hasTeacher()) {
				if (c.getTeacher().equals(teacher)) {
					ret.add(c);
				}
			}
		}
		return ret;
	}

	public static ICourse getNewCourse(boolean random) {
		return Course.createCourse(true, selectedDao);
	}

	public static ICourse getNewCourse(String name) {
		return Course.createCourse(name, selectedDao);
	}

	public static ITeacher getNewTeacher(boolean random) {
		return Teacher.createTeacher(random, selectedDao);
	}

	public static ITeacher getNewTeacher(String name) {
		return Teacher.createTeacher(name, selectedDao);
	}

	public static IStudent getNewStudent(boolean random) {
		return Student.createStudent(random, selectedDao);
	}

	public static IStudent getNewStudent(String name) {
		return Student.createStudent(name, selectedDao);
	}

	/***
	 * Updates the data on the object from the data source, if possible
	 * 
	 * @param schoolItemAbstract
	 */
	public static void verifyData(SchoolItemAbstract schoolItemAbstract) {
		DaoSchoolAbstract.getDaoSchool(getSelectedDao()).loadElement(schoolItemAbstract);
	}

	public static void saveItem(SchoolItemAbstract schoolItemAbstract) {
		DaoSchoolAbstract.getDaoSchool(getSelectedDao()).saveElement(schoolItemAbstract);
	}

	public void editItem(SchoolItemAbstract editItem) {
		FrameEdit edit = null;
		boolean found = false;
		if (editItem.isInEdit()) {
			for (FrameEdit edited : editFrames) {
				if (edited.hastItem(editItem)) {
					edited.setVisible(true);
					edited.toFront();
					edited.repaint();
					found = true;
					System.out.println("reactivate");
				}
			}
		}
		if (!found) {
			if (editItem.getClass() == Teacher.class) {
				edit = new FrameEdit((ITeacher) editItem);
			} else if (editItem.getClass() == Student.class) {
				edit = new FrameEdit((IStudent) editItem);
			} else if (editItem.getClass() == Course.class) {
				edit = new FrameEdit((ICourse) editItem);
			}
		}
		if (edit != null) {
			editItem.setInEdit(true);

			edit.addWindowListener(SchoolLauncher.getInstance());
			System.out.println("opened");
			editFrames.add(edit);
		}
	}

	public static boolean getShell() {
		return console;
	}

	public static boolean deleteElement(SchoolItemAbstract editItem) {
		boolean ret = DaoSchoolAbstract.getDaoSchool(selectedDao).deleteElement(editItem);
		if (ret) {
			if (editItem instanceof ICourse) {
				Course.courseDeleted((ICourse) editItem);
			} else if (editItem instanceof ITeacher) {
				Teacher.teacherDeleted(editItem);
				Course.teacherDeleted((ITeacher) editItem);
			} else if (editItem instanceof IStudent) {
				Student.studentDeleted((IStudent) editItem);
				Course.studentDeleted((IStudent) editItem);
			} else if (editItem instanceof Address) {
				//
			}
		}
		return ret;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		FrameEdit edit = (FrameEdit) arg0.getSource();
		if (editFrames.contains(edit)) {
			System.out.println("destory:" + edit.toString());
			editFrames.remove(edit);
			edit = null;
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
	/////////////////////////////////////////////////////////////////////////////////////

	public static DateFormat getGermanDate() {
		return dateFormatGermany;
	}

	public static void setDateFormatGermany(DateFormat dateFormatGermany) {
		SchoolLauncher.dateFormatGermany = dateFormatGermany;
	}

	public static void toggleFrame() {
		launcher.setVisible(!launcher.isVisible());
		launcher.refresh();
	}

}