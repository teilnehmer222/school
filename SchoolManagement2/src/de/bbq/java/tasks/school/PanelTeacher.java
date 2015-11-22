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

/**
 * @author Thorsten2201
 *
 */
public class PanelTeacher extends JPanel implements ActionListener, ListSelectionListener {
	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -6951335589393103017L;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private boolean refresh = true;
	private ITeacher selectedTeacher;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Controls
	private JButton addTeacherButton, deleteTeacherButton, addCourseButton, removeCourseButton;
	private JList<ITeacher> teachersJList;
	private JList<ICourse> coursePoolJList, courseSelectedJList;
	private DefaultListModel<ITeacher> teacherListModel;
	private DefaultListModel<ICourse> coursePoolListModel, courseSelectedListModel;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class methods
	public void refresh() {
		int selIndex = teachersJList.getSelectedIndex();
		this.refresh = true;
		ITeacher cindex = null;
		for (int index = this.teacherListModel.getSize(); index > 0; index--) {
			try {
				cindex = this.teacherListModel.getElementAt(index - 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

			if (!SchoolLauncher.getCourseList().contains(cindex)) {
				try {
					this.teacherListModel.remove(index - 1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}
		}
		for (ITeacher t : SchoolLauncher.getTeacherList()) {
			this.teacherListModel.addElement(t);
		}
		int poolIndex = coursePoolJList.getSelectedIndex();
		this.coursePoolListModel.clear();
		this.courseSelectedListModel.clear();
		if (this.selectedTeacher != null) {
			for (ICourse c : SchoolLauncher.getCourseList()) {
				if (this.selectedTeacher.equals(c.getTeacher())) {
					this.courseSelectedListModel.addElement(c);
				} else if (!c.hasTeacher()) {
					this.coursePoolListModel.addElement(c);
				}
			}
		}
		if (selIndex < 0) {
			// do Nothing
		} else if (selIndex < this.teacherListModel.getSize()) {
			this.teachersJList.setSelectedIndex(selIndex);
		} else {
			this.teachersJList.setSelectedIndex(this.teacherListModel.getSize() - 1);
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

	void checkButtons() {
		this.deleteTeacherButton.setEnabled(this.teachersJList.getModel().getSize() > 0);
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
	public void valueChanged(ListSelectionEvent arg0) {
		if (!this.refresh) {
			if (arg0.getSource() == this.teachersJList) {
				refreshTeachers();
				checkButtons();
			}
		}
	}

	private void refreshTeachers() {
		ITeacher selTeacher = this.teachersJList.getSelectedValue();
		if (selTeacher != null) {
			this.selectedTeacher = selTeacher;
			refresh();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// ActionListener
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.refresh = true;
		int index = this.teachersJList.getSelectedIndex();
		int indexPool = this.coursePoolJList.getSelectedIndex();
		int indexSel = this.courseSelectedJList.getSelectedIndex();
		if (arg0.getSource() == this.addTeacherButton) {
			SchoolLauncher.getNewTeacher(true, SchoolLauncher.getSelectedDao());
			index = this.teachersJList.getModel().getSize();
		} else if (arg0.getSource() == this.deleteTeacherButton) {
			if (!SchoolLauncher.getTeacherList().contains(this.selectedTeacher)) {
				this.teachersJList.setSelectedIndex(index);
				this.selectedTeacher = this.teachersJList.getSelectedValue();
			}
			if (this.selectedTeacher != null) {
				this.selectedTeacher.deleteElement();
				this.selectedTeacher = null;
			}
			if (index >= this.teachersJList.getModel().getSize() - 1) {
				index--;
			}
		}
		if (arg0.getSource() == this.addCourseButton) {
			ICourse selectedCourse = this.coursePoolJList.getSelectedValue();
			if (selectedCourse != null) {
				if (selectedTeacher != null) {
					try {
						if (!selectedCourse.hasTeacher()) {
							try {
								this.selectedTeacher.addCourse(selectedCourse);
								indexSel = this.courseSelectedJList.getModel().getSize();
								if (indexPool >= this.coursePoolJList.getModel().getSize() - 1) {
									indexPool--;
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				}
			}
		} else if (arg0.getSource() == this.removeCourseButton) {
			ICourse selectedCourse = this.courseSelectedJList.getSelectedValue();
			if (selectedCourse != null) {
				if (this.selectedTeacher != null) {
					try {
						this.selectedTeacher.removeCourse(selectedCourse);
						if (indexSel >= this.courseSelectedJList.getModel().getSize() - 1) {
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
		this.refresh = false;
		refresh();
		if (index <= this.teachersJList.getModel().getSize()) {
			this.teachersJList.setSelectedIndex(index);
			this.selectedTeacher = this.teachersJList.getSelectedValue();
			refreshTeachers();
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
	public PanelTeacher() {
		// this.setLayout(null); // new GridLayout(1, 1));
		this.setLayout(new BorderLayout(5, 5));
		JPanel panelCreate = new JPanel(new GridLayout(1, 2, 5, 5));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel panelTop = new JPanel(new GridLayout(1, 3, 10, 5));
		JPanel panelBottom = new JPanel(new GridLayout(1, 3, 10, 10));
		panelTop.add(panelCreate);
		this.add(panelTop, BorderLayout.NORTH);

		this.teacherListModel = new DefaultListModel<>();
		this.teachersJList = new JList<ITeacher>(this.teacherListModel);
		this.teachersJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.teachersJList.setLayoutOrientation(JList.VERTICAL);
		this.teachersJList.setVisibleRowCount(-1);
		this.teachersJList.addListSelectionListener(this);
		this.teachersJList.addMouseListener(new MouseAdapter() {
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
					new FrameEdit(teacherListModel.get(index));
				}
			}
		});

		this.teachersJList.setCellRenderer(new SchoolListCellRenderer());
		JScrollPane teacherScroller = new JScrollPane(this.teachersJList);
		teacherScroller.setPreferredSize(new Dimension(206, 300));
		teacherScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		teacherScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		teacherScroller.setViewportBorder(new LineBorder(Color.BLACK));
		teacherScroller.setBounds(5, 30, 205, 300);
		// this.add(teacherScroller);
		panelBottom.add(teacherScroller);

		this.addTeacherButton = SchoolLauncher.getButton("newTeacher", 5, 5, 100, 20, this, "Erstellen",
				"Neuer Leerer");
		this.deleteTeacherButton = SchoolLauncher.getButton("delTeacher", 110, 5, 100, 20, this, "Löschen",
				"Leerer löschen");
		// this.add(this.addTeacherButton);
		// this.add(this.deleteTeacherButton);
		panelCreate.add(this.addTeacherButton);
		panelCreate.add(this.deleteTeacherButton);

		this.addCourseButton = SchoolLauncher.getButton("addCourse", 235, 5, 205, 20, this, "Hinzufügen ->",
				"Kurs Hinzufügen");
		// this.add(this.addCourseButton);
		panelTop.add(this.addCourseButton);

		this.removeCourseButton = SchoolLauncher.getButton("remCourse", 470, 5, 205, 20, this, "<- Entfernen",
				"Kurs Entfernen");
		// this.add(this.removeCourseButton);
		panelTop.add(this.removeCourseButton);

		this.coursePoolListModel = new DefaultListModel<>();
		this.coursePoolJList = new JList<ICourse>(this.coursePoolListModel);
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
