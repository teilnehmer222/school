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
	private boolean occupied = false;
	String out = "";
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
		if (!this.occupied) {
			this.occupied = true;
			out = "";
			ret = true;
			if (this.safeFile == null) {
				chooseFile(true);
			}
			if (this.safeFile != null) {
				try {
					oos = new ObjectOutputStream(new FileOutputStream(this.safeFile));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (schoolItemAbstract instanceof ICourse) {
			out += this.getClass().getName() + ".saveElement(CourseDF " + schoolItemAbstract.toString() + ")\r\n";
		} else if (schoolItemAbstract instanceof ITeacher) {
			out += this.getClass().getName() + ".saveElement(TeacherDF " + schoolItemAbstract.toString() + ")\r\n";
		} else if (schoolItemAbstract instanceof IStudent) {
			out += this.getClass().getName() + ".saveElement(StudentDF " + schoolItemAbstract.toString() + ")\r\n";
		} else if (schoolItemAbstract instanceof Address) {
			out += this.getClass().getName() + ".saveElement(Address " + schoolItemAbstract.toString() + ")\r\n";
		}
		try {
			oos.writeObject(schoolItemAbstract);
			schoolItemAbstract.setSaved(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (schoolItemAbstract.isLast()) {
			this.occupied = false;
			ret = true;
			JOptionPane.showMessageDialog(null, "Ready!\r\n\r\n" + out);
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.safeFile = null;
		}
		return ret;
	}

	@Override
	public boolean loadElement(SchoolItemAbstract schoolItemAbstract) {
		return true;
	}

	@Override
	public boolean loadAll() {
		boolean ret = false;
		chooseFile(false);
		if (this.safeFile != null) {
			try {
				ois = new ObjectInputStream(new FileInputStream(this.safeFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (this.ois != null) {
				Object read = null;
				try {
					read = ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (read != null) {
					if (read instanceof Student) {
						Student.load((Student) read);
					}
					if (read instanceof Teacher) {
						Teacher.load((Teacher) read);
					}
					if (read instanceof Course) {
						Course.load((Course) read);
					}
				}
			}
		}
		this.safeFile = null;
		return ret;
	}
	@Override
	public boolean deleteElement(SchoolItemAbstract schoolItemAbstract) {
		return true;
	}
	/////////////////////////////////////////////////////////////////////////////////////


}
