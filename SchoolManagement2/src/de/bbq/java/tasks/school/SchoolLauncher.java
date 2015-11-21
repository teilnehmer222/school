package de.bbq.java.tasks.school;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author Thorsten2201
 *
 */
public class SchoolLauncher extends JFrame {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private static final long serialVersionUID = -2039718453107554584L;
	private static final int winLength = 800;
	private static final int winHight = 430;

	private CoursePanel panel1;
	private TeacherPanel panel2;
	private StudentPanel panel3;

	private static DaoSchoolJdbcMysql daoJdbc = new DaoSchoolJdbcMysql();
	private static DaoSchoolFile daoFile = new DaoSchoolFile();
	private static DaoSchoolAbstract daoAbstract = daoFile;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public static void main(String[] args) {
		// frame.add(keyboardExample);
		SchoolLauncher launcher = new SchoolLauncher();
		launcher.setVisible(true);
	}

	private SchoolLauncher() {
		setTitle("School Management");
		setSize(winLength, winHight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		addControls();
	}

	private void addControls() {
		this.setLayout(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon icon = createImageIcon("middle.gif");

		panel1 = new CoursePanel(daoAbstract);
		tabbedPane.addTab("Kurse", icon, panel1, "Kurse");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		panel2 = new TeacherPanel(daoAbstract);
		tabbedPane.addTab("Leerer", icon, panel2, "Leerer");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		panel3 = new StudentPanel(daoAbstract);
		tabbedPane.addTab("Sch�ler", icon, panel3, "Sch�ler");
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
			System.err.println("Couldn't find file: " + path);
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
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class factories returning interfaces
	public static ArrayList<ICourse> getCourseList() {
		return CourseDF.getCourses();
	}

	public static ArrayList<ITeacher> getTeacherList() {
		return TeacherDF.getTeachers();
	}

	public static ArrayList<IStudent> getStudentList() {
		return StudentDF.getStudents();
	}

	public static ICourse getNewCourse(boolean random, DaoSchoolAbstract store) {
		if (SchoolLauncher.getCourseList().size() > 1)
			store = daoJdbc;
		return CourseDF.createCourse(true, store);
	}

	public static ITeacher getNewTeacher(boolean random, DaoSchoolAbstract store) {
		return TeacherDF.createTeacher(random, store);
	}

	public static IStudent getNewStudent(boolean random, DaoSchoolAbstract store) {
		return StudentDF.createStudent(random, store);
	}
	/////////////////////////////////////////////////////////////////////////////////////

}