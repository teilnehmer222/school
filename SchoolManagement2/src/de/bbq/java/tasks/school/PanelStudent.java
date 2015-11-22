package de.bbq.java.tasks.school;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelStudent extends JPanel implements ActionListener, ListSelectionListener {
	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = 7573321200815259292L;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private boolean refresh = true;
	private IStudent selectedStudent;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Controls
	private JButton addStudentButton, deleteStudentButton, addCourseButton, removeCourseButton;
	private JList<IStudent> studentsJList;
	private JList<ICourse> coursePoolJList, courseSelectedJList;
	private DefaultListModel<IStudent> studentListModel;
	private DefaultListModel<ICourse> coursePoolListModel, courseSelectedListModel;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class methods
	public void refresh() {
		int selIndex = studentsJList.getSelectedIndex();
		this.refresh = true;
		IStudent student = null;
		for (int index = this.studentListModel.getSize(); index > 0; index--) {
			try {
				student = (IStudent) this.studentListModel.getElementAt(index - 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

			if (!SchoolLauncher.getCourseList().contains(student)) {
				try {
					this.studentListModel.remove(index - 1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}
		}
		for (IStudent p : SchoolLauncher.getStudentList()) {
			this.studentListModel.addElement(p);
		}
		int poolIndex = coursePoolJList.getSelectedIndex();
		this.coursePoolListModel.clear();
		this.courseSelectedListModel.clear();
		if (this.selectedStudent != null) {
			for (ICourse c : SchoolLauncher.getCourseList()) {
				if (this.selectedStudent.hasCourse(c)) {
					this.courseSelectedListModel.addElement(c);
				} else {
					this.coursePoolListModel.addElement(c);
				}
			}
		}
		if (selIndex < 0) {
			// do Nothing
		} else if (selIndex < this.studentListModel.getSize()) {
			this.studentsJList.setSelectedIndex(selIndex);
		} else {
			this.studentsJList.setSelectedIndex(this.studentListModel.getSize() - 1);
		}
		if (poolIndex < 0) {
			// do Nothing
		} else if (poolIndex < this.coursePoolListModel.getSize()) {
			this.coursePoolJList.setSelectedIndex(poolIndex);
		} else {
			this.coursePoolJList.setSelectedIndex(this.coursePoolListModel.getSize() - 1);
		}
		checkButtons();
		this.refresh = false;
	}

	public void checkButtons() {
		this.deleteStudentButton.setEnabled(this.studentsJList.getModel().getSize() > 0);
		if (this.courseSelectedJList.getSelectedIndex() < 0) {
			if (this.courseSelectedJList.getModel().getSize() > 0) {
				this.courseSelectedJList.setSelectedIndex(0);
			}
		}
		if (this.coursePoolJList.getSelectedIndex() < 0) {
			if (this.coursePoolJList.getModel().getSize() > 0) {
				this.coursePoolJList.setSelectedIndex(0);
			}
		}
		this.removeCourseButton.setEnabled(this.courseSelectedJList.getModel().getSize() > 0);
		this.addCourseButton.setEnabled(this.coursePoolJList.getModel().getSize() > 0);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// ListSelectionListener
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!this.refresh) {
			if (e.getSource() == this.studentsJList) {
				refreshStudents();
				checkButtons();
			}
		}
	}

	private void refreshStudents() {
		IStudent selStudent = (IStudent) this.studentsJList.getSelectedValue();
		if (selStudent != null) {
			this.selectedStudent = selStudent;
			refresh();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// ActionListener
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.refresh = true;
		int index = this.studentsJList.getSelectedIndex();
		int indexPool = this.coursePoolJList.getSelectedIndex();
		int indexSel = this.courseSelectedJList.getSelectedIndex();
		if (arg0.getSource() == this.addStudentButton) {
			SchoolLauncher.getNewStudent(true, SchoolLauncher.getSelectedDao());
			index = this.studentsJList.getModel().getSize();
		} else if (arg0.getSource() == deleteStudentButton) {
			if (this.selectedStudent == null || !SchoolLauncher.getStudentList().contains(this.selectedStudent)) {
				studentsJList.setSelectedIndex(index);
				this.selectedStudent = this.studentsJList.getSelectedValue();
			}
			if (this.selectedStudent != null) {
				this.selectedStudent.deleteElement();
				this.selectedStudent = null;
			}
			if (index >= this.studentsJList.getModel().getSize() - 1) {
				index--;
			}
		}
		if (arg0.getSource() == this.addCourseButton) {
			ICourse selectedCourse = (ICourse) this.coursePoolJList.getSelectedValue();
			if (selectedCourse != null) {
				if (this.selectedStudent != null) {
					try {
						selectedCourse.addStudent(this.selectedStudent);
						indexSel = this.courseSelectedJList.getModel().getSize();
						if (index < this.studentsJList.getModel().getSize() - 1) {
							index++;
						} else {
							index = 0;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				}
			}
		} else if (arg0.getSource() == this.removeCourseButton) {
			ICourse selectedCourse = (ICourse) this.courseSelectedJList.getSelectedValue();
			if (selectedCourse != null) {
				if (this.selectedStudent != null) {
					if (this.selectedStudent.hasCourse(selectedCourse)) {
						try {
							selectedCourse.removeStudent(this.selectedStudent);
							if (indexSel >= this.courseSelectedJList.getModel().getSize()) {
								indexSel--;
							}
							if (indexPool < 0) {
								indexPool = 0;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}
		this.refresh = false;
		refresh();
		if (index <= this.studentsJList.getModel().getSize()) {
			this.studentsJList.setSelectedIndex(index);
			this.selectedStudent = this.studentsJList.getSelectedValue();
			refreshStudents();
		}
		if (indexPool <= this.coursePoolJList.getModel().getSize()) {
			this.coursePoolJList.setSelectedIndex(indexPool);
		}
		if (indexSel <= this.courseSelectedJList.getModel().getSize()) {
			this.courseSelectedJList.setSelectedIndex(indexSel);
		}
		checkButtons();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public PanelStudent() {
		// this.setLayout(null); // new GridLayout(1, 1));
		this.setLayout(new BorderLayout(5, 5));
		JPanel panelCreate = new JPanel(new GridLayout(1, 2, 5, 5));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel panelTop = new JPanel(new GridLayout(1, 3, 10, 5));
		JPanel panelBottom = new JPanel(new GridLayout(1, 3, 10, 10));
		panelTop.add(panelCreate);
		this.add(panelTop, BorderLayout.NORTH);

		this.studentListModel = new DefaultListModel<>();
		this.studentsJList = new JList<IStudent>(this.studentListModel);
		this.studentsJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.studentsJList.setLayoutOrientation(JList.VERTICAL);
		this.studentsJList.setVisibleRowCount(-1);
		this.studentsJList.addListSelectionListener(this);
		this.studentsJList.addMouseListener(new MouseAdapter() {
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
					new FrameEdit(studentListModel.get(index));
				}
			}
		});

		this.studentsJList.setCellRenderer(new SchoolListCellRenderer());
		JScrollPane pupScroller = new JScrollPane(this.studentsJList);
		pupScroller.setPreferredSize(new Dimension(206, 300));
		pupScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setViewportBorder(new LineBorder(Color.BLACK));
		pupScroller.setBounds(5, 30, 205, 300);
		// this.add(pupScroller);
		panelBottom.add(pupScroller);

		this.addStudentButton = SchoolLauncher.getButton("newPupil", 5, 5, 100, 20, this, "Hinzufügen",
				"Neuer Schüler");
		this.deleteStudentButton = SchoolLauncher.getButton("delPupil", 110, 5, 100, 20, this, "Löschen",
				"Schüler löschen");
		// this.add(this.deleteStudentButton);
		// this.add(this.addStudentButton);
		panelCreate.add(this.addStudentButton);
		panelCreate.add(this.deleteStudentButton);

		this.addCourseButton = SchoolLauncher.getButton("addCourse", 235, 5, 205, 20, this, "Hinzufügen ->",
				"Kurs Hinzufügen");
		// this.add(this.addCourseButton);
		panelTop.add(this.addCourseButton);

		this.removeCourseButton = SchoolLauncher.getButton("remCourse", 470, 5, 205, 20, this, "<- Entfernen",
				"Kurs Entfernen");
		// this.add(this.removeCourseButton);
		panelTop.add(this.removeCourseButton);

		this.coursePoolListModel = new DefaultListModel<>();
		this.coursePoolJList = new JList<ICourse>(coursePoolListModel);
		this.coursePoolJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.coursePoolJList.setLayoutOrientation(JList.VERTICAL);
		this.coursePoolJList.setVisibleRowCount(-1);

		JScrollPane poolScroller = new JScrollPane(this.coursePoolJList);
		poolScroller.setPreferredSize(new Dimension(206, 300));
		poolScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		poolScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		poolScroller.setViewportBorder(new LineBorder(Color.BLACK));
		poolScroller.setBounds(235, 30, 205, 300);
		// this.add(poolScroller);

		this.courseSelectedListModel = new DefaultListModel<>();
		this.courseSelectedJList = new JList<ICourse>(this.courseSelectedListModel);
		this.courseSelectedJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.courseSelectedJList.setLayoutOrientation(JList.VERTICAL);
		this.courseSelectedJList.setVisibleRowCount(-1);

		JScrollPane tookScroller = new JScrollPane(this.courseSelectedJList);
		tookScroller.setPreferredSize(new Dimension(206, 300));
		tookScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tookScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tookScroller.setViewportBorder(new LineBorder(Color.BLACK));
		tookScroller.setBounds(470, 30, 205, 300);
		// this.add(tookScroller);
		panelBottom.add(poolScroller);
		panelBottom.add(tookScroller);
		this.add(panelBottom, BorderLayout.CENTER);
		this.refresh = false;
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
