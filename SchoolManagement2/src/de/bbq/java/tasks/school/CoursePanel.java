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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CoursePanel extends JPanel implements ActionListener, ListSelectionListener {
	private JButton addCourse, delCourse;
	private JList<Course> coursesJList;
	private JList<Pupil> pupilsJList;
	private JTextField teacher;
	private DefaultListModel<Course> courseModel;
	private DefaultListModel<Pupil> pupilModel;
	private boolean refresh = true;

	public CoursePanel() {
		this.setLayout(null); // new GridLayout(1, 1));

		this.courseModel = new DefaultListModel();
		this.coursesJList = new JList(this.courseModel);// array); // data has
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
					Course editItem = courseModel.get(index);
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

		coursScroller.setBounds(5, 30, 205, 300);
		this.add(coursScroller);

		teacher = new JTextField();
		teacher.setBounds(235, 5, 205, 20);
		teacher.setText("");
		teacher.setEditable(false);
		this.add(teacher);

		pupilModel = new DefaultListModel();
		pupilsJList = new JList(pupilModel); // data has type Object[]
		this.add(pupilsJList);
		pupilsJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		pupilsJList.setLayoutOrientation(JList.VERTICAL);
		pupilsJList.setVisibleRowCount(-1);
		pupilsJList.addListSelectionListener(this);

		JScrollPane pupScroller = new JScrollPane(pupilsJList);
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
			String newName = Course.generateNewName(); // JOptionPane.showInputDialog("Bitte
														// einen Kursnamen
														// eingeben:");
			try {
				Course c = new Course(newName); // Course.generateNewName());
				Course.addCource(c);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else if (arg0.getSource() == delCourse) {
			Course selected = (Course) coursesJList.getSelectedValue();
			for (Course c : Course.getCourseList()) {
				if (c.equals(selected)) {
					Course.getCourseList().remove(c);
					break;
				}
			}
		}
		this.refresh = false;
		refresh();
	}

	public void refresh() {
		this.refresh = true;
		this.courseModel.clear();
		for (Course c : Course.getCourseList()) {
			this.courseModel.addElement(c);
		}
		this.refresh = false;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!this.refresh && arg0.getSource() == coursesJList) {
			Course selectedCourse = (Course) coursesJList.getSelectedValue();
			if (selectedCourse != null) {
				if (selectedCourse.getTeacher() != null) {
					teacher.setText(selectedCourse.getTeacher().toString());
				} else {
					teacher.setText("");
				}
				ArrayList<Pupil> pupils = selectedCourse.getPupils();
				this.pupilModel.clear();
				for (Pupil p : pupils) {
					this.pupilModel.addElement(p);
				}
			}
		}
	}
}
