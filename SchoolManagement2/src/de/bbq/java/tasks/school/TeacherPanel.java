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

public class TeacherPanel extends JPanel implements ActionListener, ListSelectionListener {
	private JButton addTeacher, delTeacher, addCourse, remCourse;
	private JList teachersJList, coursePoolJList, courseSelectedJList;
	private DefaultListModel<TeacherDF> teacherModel;
	private DefaultListModel<CourseDF> coursePoolModel, courseSelectedModel;
	private boolean refresh = true;
	private TeacherDF selectedTeacher;

	public TeacherPanel() {
		this.setLayout(null); // new GridLayout(1, 1));
		teacherModel = new DefaultListModel<>();
		teachersJList = new JList(teacherModel); // data has type Object[]
		this.add(teachersJList);
		teachersJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		teachersJList.setLayoutOrientation(JList.VERTICAL);
		teachersJList.setVisibleRowCount(-1);
		teachersJList.addListSelectionListener(this);
		teachersJList.addMouseListener(new MouseAdapter() {
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
					TeacherDF editItem = teacherModel.get(index);
					new EditFrame(editItem);
				}
			}
		});
		teachersJList.setCellRenderer(new SchoolListCellRenderer());
		JScrollPane teacherScroller = new JScrollPane(teachersJList);
		teacherScroller.setPreferredSize(new Dimension(206, 300));

		teacherScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		teacherScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		teacherScroller.setViewportBorder(new LineBorder(Color.BLACK));

		this.addTeacher = SchoolLauncher.getButton("newTeacher", 5, 5, 100, 20, this, "Erstellen", "Neuer Leerer");
		this.add(this.addTeacher);

		// listScroller.getViewport().add(addCourse, null);
		this.delTeacher = SchoolLauncher.getButton("delTeacher", 110, 5, 100, 20, this, "Löschen", "Leerer löschen");
		this.add(this.delTeacher);

		teacherScroller.setBounds(5, 30, 205, 300);
		this.add(teacherScroller);

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
		if (arg0.getSource() == addTeacher) {
			String newName = TeacherDF.generateNewName();
			// JOptionPane.showInputDialog("Bitte einen Namen eingeben:");
			try {
				TeacherDF t = new TeacherDF(); // Course.generateNewName());
				t.setFirstName(newName);
				TeacherDF.addTeacherToList(t);
			} catch (Exception e) {
				// TODO: handle exception
			}
			this.selectedTeacher = (TeacherDF) teachersJList.getSelectedValue();
		} else if (arg0.getSource() == delTeacher) {
			if (this.selectedTeacher == null || !TeacherDF.getTeachers().contains(this.selectedTeacher)) {
				teachersJList.setSelectedIndex(teachersJList.getSelectedIndex());
				this.selectedTeacher = (TeacherDF) teachersJList.getSelectedValue();
			}
			TeacherDF.removeTeacher(selectedTeacher);
		}
		if (arg0.getSource() == addCourse) {
			CourseDF selectedCourse = (CourseDF) coursePoolJList.getSelectedValue();
			if (selectedCourse != null) {
				if (selectedTeacher != null) {
					try {
						School.addCourseToTeacher(selectedCourse.getId(), selectedTeacher.getId());
						/*
						 * selectedTeacher.addCourseId(selectedCourse.getId());
						 * selectedTeacher.
						 */
						// Course.writeBack(selectedTeacher);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		} else if (arg0.getSource() == remCourse) {
			CourseDF selectedCourse = (CourseDF) courseSelectedJList.getSelectedValue();
			if (selectedCourse != null) {
				if (this.selectedTeacher != null) {
					selectedTeacher.removeCourseId(selectedCourse.getId());
				}
			}
		}
		this.refresh = false;
		refresh();
	}

	public void refresh() {
		int selIndex = teachersJList.getSelectedIndex();
		this.refresh = true;
		this.teacherModel.clear();
		for (TeacherDF t : TeacherDF.getTeachers()) {
			this.teacherModel.addElement(t);
		}
		this.coursePoolModel.clear();
		this.courseSelectedModel.clear();
		if (this.selectedTeacher != null) {
			for (CourseDF c : CourseDF.getCourses()) {
				if (c.getMyTeacherId() == this.selectedTeacher.getId()) {
					courseSelectedModel.addElement(c);
				} else if (c.getMyTeacherId() == -1) {
					coursePoolModel.addElement(c);
				}
			}
		}
		if (selIndex < 0) {
			// do Nothing
		} else if (selIndex < teacherModel.getSize()) {
			teachersJList.setSelectedIndex(selIndex);
		} else {
			teachersJList.setSelectedIndex(teacherModel.getSize() - 1);
		}
		this.refresh = false;
		this.selectedTeacher = (TeacherDF) teachersJList.getSelectedValue();
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!this.refresh) {
			if (arg0.getSource() == teachersJList) {
				TeacherDF selTeacher = (TeacherDF) teachersJList.getSelectedValue();
				if (selTeacher != null) {
					this.selectedTeacher = selTeacher;
					refresh();
				}
			}
		}
	}
}
