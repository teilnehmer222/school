package de.bbq.java.tasks.school;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author Thorsten2201
 *
 */
public class DaoSchoolFile extends DaoSchoolAbstract {
	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private File safeFile;
	private boolean occupied = false, loading = false;
	String out = "";
	int cc, ct, cs, ca;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public DaoSchoolFile() {
		super(EDaoSchool.FILE);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	void chooseFile(boolean export) {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = -1;
		if (export) {
			fileChooser.setDialogTitle("Bitte die Datei zum exportieren auswählen");
			returnValue = fileChooser.showSaveDialog(null);
		} else {
			fileChooser.setDialogTitle("Bitte die Datei zum importieren auswählen");
			returnValue = fileChooser.showOpenDialog(null);
		}
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			this.safeFile = fileChooser.getSelectedFile();
			System.out.println(this.safeFile.getName());
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// DaoSchoolAbstract properties
	@Override
	public boolean saveElement(SchoolItemAbstract schoolItemAbstract) {
		boolean ret = false;
		boolean deleted = false;
		// 4sql
		if (schoolItemAbstract.isInEdit()) {
			return true;
		}
		if (!this.occupied) {
			this.occupied = true;
			out = "";
			cc = ct = cs = ca = 0;
			ret = true;
			if (this.safeFile == null) {
				chooseFile(true);
			}
			if (this.safeFile != null) {
				try {
					oos = new ObjectOutputStream(new FileOutputStream(this.safeFile, false));
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			}
		}
		if (schoolItemAbstract instanceof ICourse) {
			cc++;
		} else if (schoolItemAbstract instanceof ITeacher) {
			ct++;
		} else if (schoolItemAbstract instanceof IStudent) {
			cs++;
		} else if (schoolItemAbstract instanceof Address) {
			ca++;
		}
		try {
			oos.writeObject(schoolItemAbstract);
			schoolItemAbstract.setSaved(true);
			ret = true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			ret = false;
		}
		if (schoolItemAbstract.isLast()) {
			this.occupied = false;
			out += "In Datei gekotzt: " + cc + " mal Dummgelaber, " + ct + " Labertaschen und " + cs + " Hohlköpfe.";
			if (deleted) {
				out += "\r\nVorhandene Datei gelöscht.";
			}
			JOptionPane.showMessageDialog(null, "Dreck fertig!\r\n\r\n" + out);
			try {
				oos.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
				ret = false;
			}
			this.safeFile = null;
		}
		return ret;
	}

	@Override
	public boolean loadElement(SchoolItemAbstract schoolItemAbstract) {
		if (loading) {
			if (schoolItemAbstract instanceof Student) {
				Student.load((Student) schoolItemAbstract);
				System.out.println(((Student) schoolItemAbstract).toString());
				cs++;
			}
			if (schoolItemAbstract instanceof Teacher) {
				Teacher.load((Teacher) schoolItemAbstract);
				System.out.println(((Teacher) schoolItemAbstract).toString());
				ct++;
			}
			if (schoolItemAbstract instanceof Course) {
				Course course = (Course) schoolItemAbstract;
				System.out.println(course.toString());
				Course.load(course);
				cc++;
			}
			// if (course.hasTeacher()) {
			// Teacher.load((Teacher) course.getTeacher());
			// ct++;
			// }
			// if (course.hasStudents()) {
			// for (int index = 0; index < course.getStudents().size(); index++)
			// {
			//// course.getStudents().get(index).setCourse(course);
			// // Student.load((Student) course.getStudents().get(index));
			// // cs++;
			// }
			// }
		}
		return true;
	}

	@Override
	public boolean loadAll() {
		boolean ret = false;
		cc = ct = cs = ca = 0;
		chooseFile(false);
		if (this.safeFile != null) {
			try {
				this.ois = new ObjectInputStream(new FileInputStream(this.safeFile));
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
			if (this.ois != null) {
				Object read = null;
				try {
					read = this.ois.readObject();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					// We're done
					read = null;
				}
				ret = true;
				this.loading = true;
				while (read != null) {
					ret &= this.loadElement((SchoolItemAbstract) read);
					read = null;
					try {
						read = ois.readObject();
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace();
						ret = false;
					} catch (IOException e) {
						// We're done
					}
				}
				this.loading = false;
				this.out = "Aus Datei gelutscht: " + cc + " mal Dummgelaber, " + ct + " Labertaschen und " + cs
						+ " Hohlköpfe.";
				JOptionPane.showMessageDialog(null, "Dreck fertig!\r\n\r\n" + out);
				try {
					ois.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
					ret = false;
				}
			}
		}
		SchoolLauncher.setSelectedDao(EDaoSchool.FILE);
		this.safeFile = null;
		return ret;
	}

	@Override
	public boolean deleteElement(SchoolItemAbstract schoolItemAbstract) {
		return true;
	}
	/////////////////////////////////////////////////////////////////////////////////////
}
