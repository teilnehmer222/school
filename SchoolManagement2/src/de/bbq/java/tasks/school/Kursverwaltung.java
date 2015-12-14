package de.bbq.java.tasks.school;

import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author Thorsten2201
 *
 */
public class Kursverwaltung extends JFrame implements WindowListener {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private static final long serialVersionUID = -2039718453107554584L;
	private static final int winLength = 800;
	private static final int winHight = 430;
	private static final int workStart = 7;
	private static final int workEnd = 17;

	private static EDaoSchool selectedDao = EDaoSchool.FILE;
	private static Kursverwaltung launcher;
	private static ArrayList<FrameEdit> editFrames = new ArrayList<>();
	@SuppressWarnings("unused")
	private static boolean console = false, initalizing = true, mainDataSourceLoaded = false, backupLoaded = false;
	private static Shell shell;
	private static DateFormat dateFormatGermany;
	private static EStoreMode operationMode = EStoreMode.MYSQL_LOAD;
	private PanelCourse panelCourse;
	private PanelTeacher panelTeacher;
	private PanelStudent panelStudent;
	private static TrayIcon trayIcon = null;
	/////////////////////////////////////////////////////////////////////////////////////

	public static EDaoSchool getSelectedDao() {
		return selectedDao;
	}

	protected static void setSelectedDao(EDaoSchool selectedDao) {
		Kursverwaltung.selectedDao = selectedDao;
		Course.dataAccessObject = DaoSchoolAbstract.getDaoSchool(selectedDao);
		Teacher.dataAccessObject = DaoSchoolAbstract.getDaoSchool(selectedDao);
		Student.dataAccessObject = DaoSchoolAbstract.getDaoSchool(selectedDao);
		// setStoreMode(selectedDao, "setSelectedDao");
	}

	public static String getOperationMode() {
		return operationMode.toString();
	}

	private static void setOperationMode(EStoreMode operationMode, String source) {
		if (!(Kursverwaltung.operationMode.equals(operationMode))) {
			System.out.println("State Changed: " + operationMode.toString() + "; Source: " + source);
		}
		Kursverwaltung.operationMode = operationMode;

	}

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	@SuppressWarnings("incomplete-switch")
	public static void main(String[] args) {
		console = false;
		if (getGermanDate() == null) {
			Locale locDE = new Locale(Locale.GERMANY.getCountry());
			setDateFormatGermany(DateFormat.getDateInstance(DateFormat.FULL, locDE));
		}
		setSelectedDao(EDaoSchool.JDBC_MYSQL);
		launcher = new Kursverwaltung();
		// args = new String[] { "asasas", "54908" };
		String osname = System.getProperty("os.name");

		if (!(osname.regionMatches(true, 0, "Windows", 0, 7))) {
			System.out.println(
					"Was ist das denn für ein Dreck, wo ist meine Windows API hin?\nNa da darfst du jetzt schön tippen mein Freund, klicken is nicht.\r\n");
			console = true;
		} else if (args.length > 0) {
			if (args[0].equalsIgnoreCase("DEBUG")) {
				System.out.println("<Rebug-Mode>");
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
						+ " dir nichts, aber dafür " + it + " jetzt da wo früher mal deine GUI gestanden hat.");
				System.out.println();
				launcher.setVisible(false);
			}
		} else {
			launcher.setVisible(!console);
		}
		
		switch (getSelectedDao()) {
		case FILE:
			setOperationMode(EStoreMode.DISK_IDLE, "main(String[] args)");
			break;
		case JDBC_MYSQL:
//			setOperationMode(EStoreMode.MYSQL_IDLE, "main(String[] args)");
	//		DaoSchoolAbstract jdbcDao = DaoSchoolAbstract.getDaoSchool(getSelectedDao());
////			boolean success = jdbcDao.loadAll();
//			if (false) {
//				setSelectedDao(EDaoSchool.JDBC_MYSQL);
//				setOperationMode(EStoreMode.MYSQL_IDLE, "main(String[] args)");
//			} else {
//				setSelectedDao(EDaoSchool.FILE);
//				setOperationMode((DaoSchoolAbstract.getDaoSchool(getSelectedDao()).connected() == TriState.UNCERTAIN)?EStoreMode.RAM_IDLE:EStoreMode.DISK_IDLE, "main(String[] args)");	
//			}
			((DaoSchoolJdbcMysql)DaoSchoolAbstract.getDaoSchool(getSelectedDao())).startTimer();
			break;
		}
		addTrayIcon();
		initalizing = false;
		if (console) {
			shell = new Shell();
			shell.Start(console);
			System.out.println("Application exiting from console...");
			getInstance().closeConnections();
			System.exit(0);
		}
	}

	protected void refresh() {
		panelCourse.refresh();
		panelTeacher.refresh();
		panelStudent.refresh();
	}

	private Kursverwaltung() {
		// Runtime.getRuntime().addShutdownHook(new Thread() {
		// @Override
		// public void run() {
		// System.out.println("Application closing from ShutdownHook...");
		//// if (getInstance() != null) {
		// getInstance().closeConnections();
		//// }
		// launcher = null;
		// System.exit(0);
		// }
		// });
		// Image icon = Toolkit.getDefaultToolkit().getImage("form.gif");
		setTitle("Schulverwaltung");
		setSize(winLength, winHight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		addWindowListener(this);
		// dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		addControls();
	}

	public static Date parseDate(String st) throws ParseException {
		return dateFormatGermany.parse(st);
	}

	public static String formatDate(Date date) throws ParseException {
		return dateFormatGermany.format(date);
	}

	@SuppressWarnings("static-access")
	public static void showErrorMessage(String s) {
		if (console) {
			getInstance().shell.showMessage("Error", s);
		} else {
			JOptionPane.showMessageDialog(Kursverwaltung.getInstance(), s, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("static-access")
	public static void showErrorMessage(Frame parent, String s) {
		if (console) {
			getInstance().shell.showMessage("Error", s);
		} else {
			JOptionPane.showMessageDialog(parent, s, "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	@SuppressWarnings("static-access")
	public static String showInput(String s) {
		if (console) {
			return getInstance().shell.showInput(s);
		} else {
			return JOptionPane.showInputDialog(Kursverwaltung.getInstance(), s, "Information",
					JOptionPane.QUESTION_MESSAGE);
		}
	}

	@SuppressWarnings("static-access")
	public static int showYesNo(String s, boolean cancel) {
		if (console) {
			return getInstance().shell.showConfirm(s, cancel);
		} else {
			return JOptionPane.showConfirmDialog(Kursverwaltung.getInstance(), s, "Information",
					(cancel ? JOptionPane.YES_NO_OPTION : JOptionPane.YES_NO_CANCEL_OPTION));
		}
	}

	@SuppressWarnings("static-access")
	public static void showMessage(String s) {
		if (console) {
			getInstance().shell.showMessage(s);
		} else {
			// JOptionPane.showMessageDialog(null, s, "Information",
			// JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(Kursverwaltung.getInstance(), s, "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@SuppressWarnings("static-access")
	public static void showException(Exception e) {
		if (console) {
			getInstance().shell.showMessage("Exception", e.getMessage(), e.getStackTrace().toString());
		} else {
			JOptionPane.showMessageDialog(Kursverwaltung.getInstance(), e.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getStackTrace());
		}
	}

	protected void closeConnections() {
		try {
			DaoSchoolAbstract.closeConnections();
		} catch (Exception e) {
		}
		try {
			panelCourse.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} catch (Exception e) {
		}
		try {
			panelTeacher.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} catch (Exception e) {
		}
		try {
			panelStudent.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} catch (Exception e) {
		}
		try {
			editFrames.clear();
		} catch (Exception e) {
		}
		trayIcon = null;
		editFrames = null;
		shell = null;
		dateFormatGermany = null;

		panelCourse = null;
		panelTeacher = null;
		panelStudent = null;

		launcher = null;
	}

	private void addControls() {
		this.setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		ImageIcon icon = createImageIcon("course.png");

		panelCourse = new PanelCourse();
		tabbedPane.addTab("Kurse", icon, panelCourse, "Kurse verwalten");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		icon = createImageIcon("teacher.png");
		panelTeacher = new PanelTeacher();
		tabbedPane.addTab("Leerer", icon, panelTeacher, "Leerer den Kursen zuweisen");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		icon = createImageIcon("student.png");
		panelStudent = new PanelStudent();
		tabbedPane.addTab("Schüler", icon, panelStudent, "Schüler den Kursen zuweisen");
		panelStudent.setPreferredSize(new Dimension(410, 50));
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane source = (JTabbedPane) e.getSource();
				switch (source.getSelectedIndex()) {
				case 0:
					panelCourse.refresh();
					break;
				case 1:
					panelTeacher.refresh();
					break;
				case 2:
					panelStudent.refresh();
					break;

				}
			}
		});
		add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		panelCourse.refresh();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static for Panels, etc.
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Kursverwaltung.class.getResource(path);
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

	public static DateFormat getGermanDate() {
		return dateFormatGermany;
	}

	public static void setDateFormatGermany(DateFormat dateFormatGermany) {
		Kursverwaltung.dateFormatGermany = dateFormatGermany;
	}

	public static void toggleFrame() {
		launcher.setVisible(!launcher.isVisible());
		launcher.refresh();
	}

	public static Kursverwaltung getInstance() {
		return launcher;
	}

	public static void reset() {
		SchoolItemAbstract.highestMemberId = 1000;
		Course.reset();
		Teacher.reset();
		Student.reset();
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
		String type = "";
		if (editItem.isInEdit()) {
			for (FrameEdit edited : editFrames) {
				if (edited.hastItem(editItem)) {
					edited.setVisible(true);
					edited.toFront();
					edited.repaint();
					found = true;
					System.out.println("Bearbeiten-Fenster von \"" + editItem.toString() + "\" sichtbar gemacht");
				}
			}
		}
		if (!found) {
			if (editItem.getClass() == Teacher.class) {
				edit = new FrameEdit((ITeacher) editItem);
				type = "Teacher";
			} else if (editItem.getClass() == Student.class) {
				edit = new FrameEdit((IStudent) editItem);
				type = "Student";
			} else if (editItem.getClass() == Course.class) {
				edit = new FrameEdit((ICourse) editItem);
				type = "Course";
			}
		}
		if (edit != null) {
			editItem.setInEdit(true);
			edit.addWindowListener(Kursverwaltung.getInstance());
			System.out.println(type + " zum Bearbeiten geöffnet: " + editItem.toString());
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
	public void windowClosing(WindowEvent arg0) {
		FrameEdit edit = null;

		if (arg0.getSource() == this) {
			System.out.println("Application closing...");
			// if (getInstance() != null) {
			getInstance().closeConnections();
			// }
			System.exit(0);
		} else {
			try {
				edit = (FrameEdit) arg0.getSource();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (edit != null) {
			if (editFrames.contains(edit)) {
				System.out.println("Fenster zerstört: " + edit.toString());
				editFrames.remove(edit);
				edit = null;
			}
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

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void setDataBase(EDaoSchool edao, String sourceFunction) {
		setStoreMode(edao, sourceFunction);
	}

	@SuppressWarnings("static-access")
	public static void setStoreMode(EDaoSchool edao, String sourceFunction) {
		EStoreMode sStore = null;
		switch (edao) {
		case FILE:
			sStore = EStoreMode.DISK_LOAD;
			break;
		case JDBC_MYSQL:
			sStore = EStoreMode.MYSQL_LOAD;
			break;
		default:
			sStore = EStoreMode.RAM_LOAD;
			break;
		}
		DaoSchoolAbstract jdbc = DaoSchoolAbstract.getDaoSchool(EDaoSchool.JDBC_MYSQL);
		TriState connected = TriState.FALSE;
		if (jdbc != null) {
			connected = jdbc.connected();
		}
		if (!mainDataSourceLoaded && sourceFunction.equals("TimerTask MySQL()")
				&& (jdbc.isConnected()
						&& (connected == TriState.TRUE))) {
			// MySql TimerTask returned connected or not
			setOperationMode(sStore, sourceFunction + "(EDaoSchool " + edao.toString() + ")");
			setSelectedDao(edao);
			getInstance().panelCourse.setDataBase(edao);
			DaoSchoolAbstract.getDaoSchool(getSelectedDao()).loadAll();
			getInstance().refresh();
			mainDataSourceLoaded = true;
		} else if (!backupLoaded && (sourceFunction.equals("TimerTask MySQL()") ||sourceFunction.equals("DaoSchoolJdbcMysql()")    )
				&& (connected == TriState.FALSE)) {
			edao = EDaoSchool.FILE;
			setSelectedDao(edao);
			getInstance().panelCourse.setDataBase(edao);
			setOperationMode(sStore, sourceFunction + "(EDaoSchool " + edao.toString() + ")");
			if (! ((DaoSchoolFile)DaoSchoolAbstract.getDaoSchool(edao)).isCancel()) {
				DaoSchoolAbstract.getDaoSchool(edao).loadAll();
			}
			getInstance().refresh();
			backupLoaded = true;
		} 
	}

	private static void addTrayIcon() {
		if (SystemTray.isSupported()) {
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			ImageIcon image = createImageIcon("courseS.png"); // Toolkit.getDefaultToolkit().getImage(...);
			// create a action listener to listen for default action executed on
			// the tray icon
			// create a popup menu
			PopupMenu popup = new PopupMenu();
			// create menu item for the default action
			MenuItem defaultItem = new MenuItem("Fenster " +(getInstance().isVisible()?"un":"") + "sichtbar machen");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Kursverwaltung.toggleFrame();
					getInstance().setLocationRelativeTo(null);
					trayIcon.getPopupMenu().getItem(0).setLabel("Fenster " +(getInstance().isVisible()?"un":"") + "sichtbar machen");
				}
			});
			popup.add(defaultItem);
			/// ... add other items
			// construct a TrayIcon
			trayIcon = new TrayIcon(image.getImage(), "Kursverwaltung", popup);
			// set the TrayIcon properties
			trayIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Kursverwaltung.toggleFrame();
					getInstance().setLocationRelativeTo(null);
					trayIcon.getPopupMenu().getItem(0).setLabel("Fenster " +(getInstance().isVisible()?"un":"") + "sichtbar machen");
				}
			});
			// ...
			// add the tray image
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				showException(e);
			}
		} else {
			// disable tray option in your application or
			// perform other actions
		}
		// some time later
		// the application state has changed - update the image
		// if (trayIcon != null) {
		// trayIcon.setImage(updatedImage);
		// }
	}

	/////////////////////////////////////////////////////////////////////////////////////
}