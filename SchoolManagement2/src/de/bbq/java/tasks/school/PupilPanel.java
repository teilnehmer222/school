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
	private JButton addPupil, delPupil, addCourse, remCourse;
	private JList pupilsJList, coursePoolJList, courseSelectedJList;
	private DefaultListModel<Pupil> pupilModel;
	private DefaultListModel<Course> coursePoolModel, courseSelectedModel;
	private boolean refresh = true;
	private Pupil selectedPupil;

	public PupilPanel() {
		this.setLayout(null); // new GridLayout(1, 1));
		pupilModel = new DefaultListModel<>();
		pupilsJList = new JList(pupilModel); // data has type Object[]
		this.add(pupilsJList);
		pupilsJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		pupilsJList.setLayoutOrientation(JList.VERTICAL);
		pupilsJList.setVisibleRowCount(-1);
		pupilsJList.addListSelectionListener(this);
		pupilsJList.addMouseListener(new MouseAdapter() {
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
					Pupil editItem = pupilModel.get(index);
					new EditFrame(editItem);
				}
			}
		});
		pupilsJList.setCellRenderer(new SchoolListCellRenderer());

		JScrollPane pupScroller = new JScrollPane(pupilsJList);
		pupScroller.setPreferredSize(new Dimension(206, 300));

		pupScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pupScroller.setViewportBorder(new LineBorder(Color.BLACK));

		this.addPupil = SchoolLauncher.getButton("newPupil", 5, 5, 100, 20, this, "Hinzufügen", "Neuer Schüler");
		this.add(this.addPupil);

		// listScroller.getViewport().add(addCourse, null);
		this.delPupil = SchoolLauncher.getButton("delPupil", 110, 5, 100, 20, this, "Löschen", "Schüler löschen");
		this.add(this.delPupil);

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
		if (arg0.getSource() == addPupil) {
			String newName = Pupil.generateNewName(); 
			// JOptionPane.showInputDialog("Bitte einen Namen eingeben:");
			try {
				Pupil p = new Pupil(newName); // Course.generateNewName());
				Pupil.addPupil(p);
			} catch (Exception e) {
				// TODO: handle exception
			}
			//pupilsJList.setSelectedIndex(index+1);
			this.selectedPupil = (Pupil) pupilsJList.getSelectedValue();
		} else if (arg0.getSource() == delPupil) {
			if (this.selectedPupil == null || !Pupil.getPupilList().contains(this.selectedPupil)) {
				pupilsJList.setSelectedIndex(pupilsJList.getSelectedIndex());
				this.selectedPupil = (Pupil) pupilsJList.getSelectedValue();
			}
			Pupil.getPupilList().remove(this.selectedPupil);
		}
		if (arg0.getSource() == addCourse) {
			Course selectedCourse = (Course) coursePoolJList.getSelectedValue();
			if (selectedCourse != null) {
				if (selectedPupil != null) {
					try {
						selectedPupil.addCourse(selectedCourse);
						Course.writeBack(selectedPupil);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		} else if (arg0.getSource() == remCourse) {
			Course selectedCourse = (Course) courseSelectedJList.getSelectedValue();
			if (selectedCourse != null) {
				if (this.selectedPupil != null) {
					if (this.selectedPupil.hasCourse(selectedCourse)) { // getCourseList().contains(selectedCourse))
																		// {
						try {
							this.selectedPupil.clearCourse(); // remCourse(selectedCourse);
							Course.writeBack(selectedPupil);
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
		int selIndex = pupilsJList.getSelectedIndex();
		this.refresh = true;
		this.pupilModel.clear();
		for (Pupil p : Pupil.getPupilList()) {
			this.pupilModel.addElement(p);
		}
		this.coursePoolModel.clear();
		this.courseSelectedModel.clear();
		if (this.selectedPupil != null) {
			for (Course c : Course.getCourseList()) {
				if (!this.selectedPupil.hasCourse()) {
					coursePoolModel.addElement(c);
				} else if (this.selectedPupil.hasCourse(c)) { // getCourseList().contains(c))
																// {
					courseSelectedModel.addElement(c);
				} else {
					// coursePoolModel.addElement(c);
				}
			}
		}
		if (selIndex < 0) {
			//do Nothing
		} else if (selIndex < pupilModel.getSize()) {
			pupilsJList.setSelectedIndex(selIndex);
		} else {
			pupilsJList.setSelectedIndex(pupilModel.getSize() - 1);
		}
		this.refresh = false;
		this.selectedPupil = (Pupil) pupilsJList.getSelectedValue();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!this.refresh) {
			if (e.getSource() == pupilsJList) {
				Pupil selPupil = (Pupil) pupilsJList.getSelectedValue();
				if (selPupil != null) {
					this.selectedPupil = selPupil;
					refresh();
				}
			}
		}
	}
}
