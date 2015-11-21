package de.bbq.java.tasks.school;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Thorsten2201
 *
 */
public class CoursePanel extends JPanel implements ActionListener, ListSelectionListener {
	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -7720278844639602571L;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private boolean refresh = true;
	private DaoSchoolAbstract daoSchoolAbstract;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Controls
	private JButton addButton, deleteButton, saveAllButton, loadAllButton;
	private JList<ICourse> coursesJList;
	private JList<IStudent> studentsJList;
	private JTextField teacherTextField;

	private DefaultListModel<ICourse> courseListModel;
	private DefaultListModel<IStudent> studentListModel;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class methods
	public void refresh() {
		this.refresh = true;
		ICourse cindex = null;
		for (int index = this.courseListModel.getSize(); index > 0; index--) {
			try {
				cindex = this.courseListModel.getElementAt(index - 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

			if (!SchoolLauncher.getCourseList().contains(cindex)) {
				try {
					this.courseListModel.remove(index - 1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}
		}
		for (ICourse c : SchoolLauncher.getCourseList()) {
			if (!this.courseListModel.contains(c)) {
				this.courseListModel.addElement(c);
			}
		}
		this.refresh = false;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// ListSelectionListener
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		ICourse selectedCourse = this.coursesJList.getSelectedValue();
		if (!this.refresh && arg0.getSource() == this.coursesJList) {
			if (selectedCourse != null) {
				if (selectedCourse.hasTeacher()) {
					this.teacherTextField.setText(selectedCourse.getTeacher().toString());
				} else
					this.teacherTextField.setText("");
			}
			this.studentListModel.clear();
			for (IStudent student : selectedCourse.getStudents()) {
				this.studentListModel.addElement(student);
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// ActionListener
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.refresh = true;
		if (arg0.getSource() == addButton) {
			SchoolLauncher.getNewCourse(true, this.daoSchoolAbstract);
		} else if (arg0.getSource() == deleteButton) {
			ICourse selected = coursesJList.getSelectedValue();
			if (selected != null) {
				selected.deleteElement();
			}
		} else if (arg0.getSource() == saveAllButton) {
			if (!SchoolLauncher.getCourseList().isEmpty()) {
				SchoolLauncher.getCourseList().get(0).saveAll();
			} else if (!SchoolLauncher.getTeacherList().isEmpty()) {
				SchoolLauncher.getTeacherList().get(0).saveAll();
			} else if (!SchoolLauncher.getStudentList().isEmpty()) {
				SchoolLauncher.getStudentList().get(0).saveAll();
			}

		} else if (arg0.getSource() == loadAllButton) {
			if (!SchoolLauncher.getCourseList().isEmpty()) {
				SchoolLauncher.getCourseList().get(0).loadAll();
			} else if (!SchoolLauncher.getTeacherList().isEmpty()) {
				SchoolLauncher.getTeacherList().get(0).loadAll();
			} else if (!SchoolLauncher.getStudentList().isEmpty()) {
				SchoolLauncher.getStudentList().get(0).loadAll();
			}
		}
		this.refresh = false;
		refresh();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public CoursePanel(DaoSchoolAbstract daoSchoolAbstract) {
		this.daoSchoolAbstract = daoSchoolAbstract;
		this.setLayout(null); // new GridLayout(1, 1));

		this.courseListModel = new DefaultListModel<ICourse>();
		this.coursesJList = new JList<ICourse>(this.courseListModel);
		this.coursesJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.coursesJList.setLayoutOrientation(JList.VERTICAL);
		this.coursesJList.setVisibleRowCount(-1);
		this.coursesJList.addListSelectionListener(this);
		this.coursesJList.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("rawtypes")
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				int index = -1;
				if (evt.getClickCount() == 2) {
					// Double-click detected
					index = list.locationToIndex(evt.getPoint());
				} else if (evt.getClickCount() == 3) {
					// Triple-click detected
					index = list.locationToIndex(evt.getPoint());
				}
				if (index >= 0) {
					new EditFrame(courseListModel.get(index));
				}
			}
		});

		this.coursesJList.setCellRenderer(new SchoolListCellRenderer());
		JScrollPane coursScroller = new JScrollPane(this.coursesJList);
		coursScroller.setPreferredSize(new Dimension(206, 300));
		coursScroller.setBounds(5, 30, 205, 300);
		coursScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		coursScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		coursScroller.setViewportBorder(new LineBorder(Color.BLACK));
		this.add(this.coursesJList);

		this.addButton = SchoolLauncher.getButton("newCourse", 5, 5, 100, 20, this, "Neuer Kurs", "Neuer Kurs");
		this.add(this.addButton);

		this.deleteButton = SchoolLauncher.getButton("delCourse", 110, 5, 100, 20, this, "Löschen", "Kurs löschen");
		this.add(this.deleteButton);

		this.saveAllButton = SchoolLauncher.getButton("saveAll", 450, 5, 130, 20, this, "Alles speichern",
				"Kurse, Leerer und Schüler speichern");
		this.add(this.saveAllButton);

		this.loadAllButton = SchoolLauncher.getButton("loadAll", 590, 5, 120, 20, this, "Alles laden",
				"Kurse, Leerer und Schüler laden");
		this.add(this.loadAllButton);

		this.teacherTextField = new JTextField();
		this.teacherTextField.setBounds(235, 5, 205, 20);
		this.teacherTextField.setText("");
		this.teacherTextField.setEditable(false);
		this.add(this.teacherTextField);

		this.studentListModel = new DefaultListModel<IStudent>();
		this.studentsJList = new JList<IStudent>(this.studentListModel);
		this.studentsJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.studentsJList.setLayoutOrientation(JList.VERTICAL);
		this.studentsJList.setVisibleRowCount(-1);
		this.studentsJList.addListSelectionListener(this);

		JScrollPane pupScroller = new JScrollPane(this.studentsJList);
		pupScroller.setPreferredSize(new Dimension(206, 300));
		pupScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setViewportBorder(new LineBorder(Color.BLACK));
		pupScroller.setBounds(235, 30, 205, 300);
		this.add(this.studentsJList);

		this.refresh = false;
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
