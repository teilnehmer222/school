package de.bbq.java.tasks.school;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PupilPanel extends JPanel implements ActionListener, ListSelectionListener {
	private JButton addStudent, delStudent, addCourse, remCourse;
	private JList studentsJList, coursePoolJList, courseSelectedJList;
	private DefaultListModel<StudentDF> studentModel;
	private DefaultListModel<CourseDF> coursePoolModel, courseSelectedModel;
	private boolean refresh = true;
	private StudentDF selectedStudent;

	public PupilPanel() {
		this.setLayout(null); // new GridLayout(1, 1));
		studentModel = new DefaultListModel<>();
		studentsJList = new JList(studentModel); // data has type Object[]
		this.add(studentsJList);
		studentsJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		studentsJList.setLayoutOrientation(JList.VERTICAL);
		studentsJList.setVisibleRowCount(-1);
		studentsJList.addListSelectionListener(this);
		studentsJList.addMouseListener(new MouseAdapter() {
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
					StudentDF editItem = studentModel.get(index);
					new EditFrame(editItem);
				}
			}
		});
		studentsJList.setCellRenderer(new SchoolListCellRenderer());

		JScrollPane pupScroller = new JScrollPane(studentsJList);
		pupScroller.setPreferredSize(new Dimension(206, 300));

		pupScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setViewportBorder(new LineBorder(Color.BLACK));

		this.addStudent = SchoolLauncher.getButton("newPupil", 5, 5, 100, 20, this, "Hinzufügen", "Neuer Schüler");
		this.add(this.addStudent);

		// listScroller.getViewport().add(addCourse, null);
		this.delStudent = SchoolLauncher.getButton("delPupil", 110, 5, 100, 20, this, "Löschen", "Schüler löschen");
		this.add(this.delStudent);

		pupScroller.setBounds(5, 30, 205, 300);
		this.add(pupScroller);

		this.addCourse = SchoolLauncher.getButton("addCourse", 235, 5, 205, 20, this, "Hinzufügen ->",
				"Kurs Hinzufügen");
		this.add(this.addCourse);

		this.remCourse = SchoolLauncher.getButton("remCourse", 470, 5, 205, 20, this, "<- Entfernen", "Kurs Entfernen");
		this.add(this.remCourse);

		coursePoolModel = new DefaultListModel<>();
		coursePoolJList = new JList(coursePoolModel); // data has type Object[]
		this.add(coursePoolJList);
		coursePoolJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		coursePoolJList.setLayoutOrientation(JList.VERTICAL);
		coursePoolJList.setVisibleRowCount(-1);

		JScrollPane poolScroller = new JScrollPane(coursePoolJList);
		poolScroller.setPreferredSize(new Dimension(206, 300));

		poolScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		poolScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		poolScroller.setViewportBorder(new LineBorder(Color.BLACK));

		poolScroller.setBounds(235, 30, 205, 300);
		this.add(poolScroller);

		courseSelectedModel = new DefaultListModel<>();
		courseSelectedJList = new JList(courseSelectedModel); // data has type
		this.add(courseSelectedJList);
		courseSelectedJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		courseSelectedJList.setLayoutOrientation(JList.VERTICAL);
		courseSelectedJList.setVisibleRowCount(-1);

		JScrollPane tookScroller = new JScrollPane(courseSelectedJList);
		tookScroller.setPreferredSize(new Dimension(206, 300));

		tookScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tookScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tookScroller.setViewportBorder(new LineBorder(Color.BLACK));

		tookScroller.setBounds(470, 30, 205, 300);
		this.add(tookScroller);
		this.refresh = false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.refresh = true;
		if (arg0.getSource() == addStudent) {
			String newName = StudentDF.generateNewName(); 
			// JOptionPane.showInputDialog("Bitte einen Namen eingeben:");
			try {
				StudentDF s = new StudentDF(); // Course.generateNewName());
				s.setFirstName(newName);
				StudentDF.addStudentToList(s);
			} catch (Exception e) {
				// TODO: handle exception
			}
			//pupilsJList.setSelectedIndex(index+1);
			this.selectedStudent = (StudentDF) studentsJList.getSelectedValue();
		} else if (arg0.getSource() == delStudent) {
			if (this.selectedStudent == null || !StudentDF.getList().contains(this.selectedStudent)) {
				studentsJList.setSelectedIndex(studentsJList.getSelectedIndex());
				this.selectedStudent = (StudentDF) studentsJList.getSelectedValue();
			}
			StudentDF.getList().remove(this.selectedStudent);
		}
		if (arg0.getSource() == addCourse) {
			CourseDF selectedCourse = (CourseDF) coursePoolJList.getSelectedValue();
			if (selectedCourse != null) {
				if (selectedStudent != null) {
					try {
						School.addStudentToCourse(selectedStudent.getId(), selectedCourse.getId());
						//Course.writeBack(selectedStudent);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		} else if (arg0.getSource() == remCourse) {
			CourseDF selectedCourse = (CourseDF) courseSelectedJList.getSelectedValue();
			if (selectedCourse != null) {
				if (this.selectedStudent != null) {
					if (this.selectedStudent.getMyCourseId() ==selectedCourse.getId()) { // getCourseList().contains(selectedCourse))
																		// {
						try {
							School.removeStudentFromCourse(selectedStudent.getId(), selectedCourse.getId());
							//Course.writeBack(selectedStudent);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}
		this.refresh = false;
		refresh();
	}

	public void refresh() {
		int selIndex = studentsJList.getSelectedIndex();
		this.refresh = true;
		this.studentModel.clear();
		for (StudentDF p : StudentDF.getList()) {
			this.studentModel.addElement(p);
		}
		this.coursePoolModel.clear();
		this.courseSelectedModel.clear();
		if (this.selectedStudent != null) {
			for (CourseDF c : CourseDF.getCourses()) {
				if (this.selectedStudent.getMyCourseId() !=c.getId()) {
					coursePoolModel.addElement(c);
				} else {
					courseSelectedModel.addElement(c);
				} 
			}
		}
		if (selIndex < 0) {
			//do Nothing
		} else if (selIndex < studentModel.getSize()) {
			studentsJList.setSelectedIndex(selIndex);
		} else {
			studentsJList.setSelectedIndex(studentModel.getSize() - 1);
		}
		this.refresh = false;
		this.selectedStudent = (StudentDF) studentsJList.getSelectedValue();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!this.refresh) {
			if (e.getSource() == studentsJList) {
				StudentDF selStudent = (StudentDF) studentsJList.getSelectedValue();
				if (selStudent != null) {
					this.selectedStudent = selStudent;
					refresh();
				}
			}
		}
	}
}
