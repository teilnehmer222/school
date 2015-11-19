package de.bbq.java.tasks.school;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CoursePanel extends JPanel implements ActionListener, ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7720278844639602571L;
	private JButton addCourse, delCourse, saveAll, loadAll;
	private JList<CourseDF> coursesJList;
	private JList<StudentDF> studentsJList;
	private JTextField teacher;
	private DefaultListModel<CourseDF> courseModel;
	private DefaultListModel<StudentDF> studentModel;
	private boolean refresh = true;

	private DaoSchoolAbstract store;

	public CoursePanel(DaoSchoolAbstract DaoStore) {
		this.store = DaoStore;
		this.setLayout(null); // new GridLayout(1, 1));

		this.courseModel = new DefaultListModel<CourseDF>();
		this.coursesJList = new JList<CourseDF>(this.courseModel);// array); //
																	// data has
		this.add(coursesJList);
		coursesJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		coursesJList.setLayoutOrientation(JList.VERTICAL);
		coursesJList.setVisibleRowCount(-1);
		coursesJList.addListSelectionListener(this);
		coursesJList.addMouseListener(new MouseAdapter() {
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
					CourseDF editItem = courseModel.get(index);
					new EditFrame(editItem);
				}
			}
		});
		coursesJList.setCellRenderer(new SchoolListCellRenderer());

		JScrollPane coursScroller = new JScrollPane(coursesJList);
		coursScroller.setPreferredSize(new Dimension(206, 300));

		coursScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		coursScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		coursScroller.setViewportBorder(new LineBorder(Color.BLACK));

		addCourse = SchoolLauncher.getButton("newCourse", 5, 5, 100, 20, this, "Neuer Kurs", "Neuer Kurs");
		this.add(addCourse);

		// listScroller.getViewport().add(addCourse, null);
		delCourse = SchoolLauncher.getButton("delCourse", 110, 5, 100, 20, this, "Löschen", "Kurs löschen");
		this.add(delCourse);

		saveAll = SchoolLauncher.getButton("saveAll", 450, 5, 130, 20, this, "Alles speichern",
				"Kurse, Leerer und Schüler speichern");
		this.add(saveAll);

		loadAll = SchoolLauncher.getButton("loadAll", 590, 5, 120, 20, this, "Alles laden",
				"Kurse, Leerer und Schüler laden");
		this.add(loadAll);

		coursScroller.setBounds(5, 30, 205, 300);
		this.add(coursScroller);

		teacher = new JTextField();
		teacher.setBounds(235, 5, 205, 20);
		teacher.setText("");
		teacher.setEditable(false);
		this.add(teacher);

		studentModel = new DefaultListModel();
		studentsJList = new JList(studentModel); // data has type Object[]
		this.add(studentsJList);
		studentsJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		studentsJList.setLayoutOrientation(JList.VERTICAL);
		studentsJList.setVisibleRowCount(-1);
		studentsJList.addListSelectionListener(this);

		JScrollPane pupScroller = new JScrollPane(studentsJList);
		pupScroller.setPreferredSize(new Dimension(206, 300));

		pupScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setViewportBorder(new LineBorder(Color.BLACK));

		pupScroller.setBounds(235, 30, 205, 300);
		this.add(pupScroller);
		this.refresh = false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.refresh = true;
		if (arg0.getSource() == addCourse) {
			String newName = CourseDF.generateNewName(); // JOptionPane.showInputDialog("Bitte
															// einen Kursnamen
															// eingeben:");
			try {
				CourseDF c = CourseDF.createCourse(CourseDF.generateNewName(), this.store);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// TODO: DELETE SHOW
			if (CourseDF.getCourses().size() > 1)
				this.store = new DaoSchoolJdbc();
		} else if (arg0.getSource() == delCourse) {
			CourseDF selected = (CourseDF) coursesJList.getSelectedValue();
			if (selected != null) {
				selected.deleteElement();
			}
		} else if (arg0.getSource() == saveAll) {
			if (!CourseDF.getCourses().isEmpty()) {
				CourseDF.getCourses().get(0).saveAll();
			} else if (!TeacherDF.getTeachers().isEmpty()) {
				TeacherDF.getTeachers().get(0).saveAll();
			} else 	if (!StudentDF.getStudents().isEmpty()) {
				StudentDF.getStudents().get(0).saveAll();
			}
				
		} else if (arg0.getSource() == loadAll) {
			if (!CourseDF.getCourses().isEmpty()) {
				CourseDF.getCourses().get(0).loadAll();
			} else if (!TeacherDF.getTeachers().isEmpty()) {
				TeacherDF.getTeachers().get(0).loadAll();
			} else 	if (!StudentDF.getStudents().isEmpty()) {
				StudentDF.getStudents().get(0).loadAll();
			}
		}
		this.refresh = false;
		refresh();
	}

	public void refresh() {
		this.refresh = true;
		CourseDF cindex = null;
		// this.courseModel.clear();
		for (int index = this.courseModel.getSize(); index > 0; index--) {
			try {
				cindex = (CourseDF) this.courseModel.getElementAt(index - 1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

			if (!CourseDF.getCourses().contains(cindex)) {
				try {
					this.courseModel.remove(index - 1);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}
		}
		for (CourseDF c : CourseDF.getCourses()) {
			if (!this.courseModel.contains(c)) {
				this.courseModel.addElement(c);
			}
		}
		// for (CourseDF c : this.courseModel) {
		// if(!this.courseModel.contains(c)) {
		// this.courseModel.addElement(c);
		// }
		// }
		this.refresh = false;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		CourseDF selectedCourse = (CourseDF) coursesJList.getSelectedValue();
		if (!this.refresh && arg0.getSource() == coursesJList) {
			if (selectedCourse != null) {
				if (selectedCourse.hasTeacher()) {
					teacher.setText(selectedCourse.getCourseName());
				} else
					teacher.setText("");
			}
			// List<Long> studentIds = selectedCourse.getStudentIds();
			// this.studentModel.clear();
			// for (Long id : studentIds) {
			// this.studentModel.addElement(StudentDF.findStudentById(id));
			// }
			this.studentModel.clear();
			for (StudentDF student : selectedCourse.getStudents()) {
				this.studentModel.addElement(student);
			}
		}
	}
}
