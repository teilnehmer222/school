package de.bbq.java.tasks.school;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;

/**
 * @author Thorsten2201
 *
 */
public class DaoSchoolFile extends DaoSchoolAbstract implements ClipboardOwner, Runnable {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private File safeFile;
	private boolean occupied = false, loading = false;
	String out = "";
	int cc, ct, cs, ca;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ArrayList<SchoolItemAbstract> allItems = new ArrayList<>();
	private final static DataFlavor arrayListDataFlavor = new DataFlavor(ArrayList.class, "ArrayList");

	private final static String errorKey = "clipboard";
	private final static String fileKey = "filepath";
	private static boolean cancel = false;
	private static WatchService watcher = null;
	private static ExecutorService executor = null;
	private static boolean writeClipboard = false;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public DaoSchoolFile() {
		initializeWatchService();
	}
	//
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private File chooseFile(boolean export) {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = -1;
		String path = getFileSystemPath();
		if (path.length() > 0) {
			this.safeFile = new File(path);
			if (!this.safeFile.exists()) {
				this.safeFile = null;
			}
		}
		if (Kursverwaltung.getShell()) {
			if (this.safeFile == null) {
				if (export) {
					this.safeFile = new File(Kursverwaltung.showInput("Bitte die Datei zum exportieren auswählen"));
				} else {
					this.safeFile = new File(Kursverwaltung.showInput("Bitte die Datei zum importieren auswählen"));
				}
				setFileSystemPath(this.safeFile.getAbsolutePath());
			}
		} else {
			if (this.safeFile == null) {
				if (export) {
					fileChooser.setDialogTitle("Bitte die Datei zum exportieren auswählen");
					returnValue = fileChooser.showSaveDialog(null);
				} else {
					fileChooser.setDialogTitle("Bitte die Datei zum importieren auswählen");
					returnValue = fileChooser.showOpenDialog(null);
				}
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					this.safeFile = fileChooser.getSelectedFile();
					setFileSystemPath(this.safeFile.getAbsolutePath());
				} else {
					cancel = true;
					this.safeFile = null;
				}
			}
		}
		if (this.safeFile != null) {
			System.out.println("Ergebnis chooseFile: " + this.safeFile.getName());
		} else {
			System.out.println("Ergebnis chooseFile: null");
		}
		return this.safeFile;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// DaoSchoolAbstract properties
	@Override
	public TriState connected() {
		if (this.safeFile == null) {
			return TriState.UNCERTAIN;
		} else {
			return TriState.TRUE;
		}
	}
	
	@Override
	public void run() {
		try {
			WatchKey key = watcher.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				if (event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
					System.out.println("ENTRY_MODIFY - Path: " + event.context());
					if (this.safeFile != null) {
						if (writeClipboard) {
							if (event.context().toString().equals(this.safeFile.getName())) {
								// TODO: update items from modified file
								if (this.safeFile.canWrite()) {
									writeClipboard = false;
								}
							}
						}
					} else {
						if (writeClipboard) {
							//TODO: import items
						}
					}
			
				} else if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
					if (this.safeFile != null) {
						if (event.context().toString().equals(this.safeFile.getName())) {
							// Enter backup mode
							System.out.println("ENTRY_DELETE - FILEBACKUP DELETED: Path: " + event.context());
							writeClipboard = true;
						} else {
							// Do nothing
							System.out.println("ENTRY_DELETE - Path: " + event.context());
						}
					} else {
						// Do nothing
						System.out.println("ENTRY_DELETE - Path: savefile null" + event.context());
					}
				} else if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
					if (Paths.get(event.context().toString()).equals(Paths.get(safeFile.getAbsolutePath()))) {
						// Resume to normal mode from backup mode
						System.out.println("ENTRY_CREATE - FILEBACKUP CREATED: Path: " + event.context());
						writeClipboard = false;
						initializeWatchService();
					} else {
						System.out.println("ENTRY_CREATE - Path: " + event.context());
						int ret = Kursverwaltung.showYesNo("Do you want to import " + event.context() + "?", false);
						Kursverwaltung.showMessage(
								(ret == 0) ? "Importiere den hingerotzen Unsinn..." : "Ich fass das nicht an!");
						// TODO: Import
					}
				} else if (event.kind().equals(StandardWatchEventKinds.OVERFLOW)) {
					// Do nothing, wait for next cycle
				}
			}
			key.reset();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Kursverwaltung.showException(e);
		}
		executor.execute(this);
	}

	@Override
	public boolean dispose() {
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("FileWatch-ExecutorService beendet");
		executor = null;
		return false;
	}

	@Override
	public boolean saveElement(SchoolItemAbstract schoolItemAbstract) {
		boolean ret = false, deleted = false, writeDisk = false;
		Kursverwaltung.setStoreMode(eDao, "saveElement()");
		// real time SQL database update, doesn't fit file system save
		if (schoolItemAbstract.isInEdit()) {
			return super.saveAll();
		}
		// real time SQL database update, doesn't fit file system save
		if ((!this.occupied && schoolItemAbstract.isLast()) && schoolItemAbstract.isSingle()) {
			schoolItemAbstract.setSingle(false);
			return true;
		} else if (!this.occupied) {
			this.occupied = true;
			allItems.clear();
			out = "";
			cc = ct = cs = ca = 0;
			ret = true;
			if (writeClipboard) {
				oos = null;
			} else {
				if (this.safeFile == null) {
					chooseFile(true);
				}
				if (this.safeFile != null) {
					if (!this.safeFile.exists()) {
						writeDisk = true;
					}
					if (this.safeFile.canWrite()) {
						writeDisk = true;
						try {
							oos = new ObjectOutputStream(new FileOutputStream(this.safeFile, false));
						} catch (FileNotFoundException e) {
							Kursverwaltung.showException(e);
							allItems.add(schoolItemAbstract);
						} catch (IOException e) {
							Kursverwaltung.showException(e);
							allItems.add(schoolItemAbstract);
						}
					} else {
//						if (oos == null) { allItems.add(schoolItemAbstract);
					}
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
		if (oos == null) {
			allItems.add(schoolItemAbstract);
			schoolItemAbstract.setSaved(true);
		} else {
			try {
				oos.writeObject(schoolItemAbstract);
				schoolItemAbstract.setSaved(true);
				ret = true;
			} catch (IOException e) {
				Kursverwaltung.showException(e);
				ret = false;
				;
				this.occupied = false;
			}
		}
		if (schoolItemAbstract.isLast()) {
			out += "In " + (writeDisk ? "Datei" : "Zwischenablage") + " gekotzt: " + cc + " mal Dummgelaber, " + ct
					+ " Labertaschen und " + cs + " Hohlköpfe.";
			if (deleted) {
				out += "\r\nVorhandene Datei gelöscht.";
			}
			if (oos == null) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				ClipboardContainer clipboardList = new ClipboardContainer(allItems);
				clipboard.setContents(clipboardList, this);
				putClipBoardData(true);
				allItems.clear();
				System.out.println("Dreck fertig!\r\n\r\n" + out);
			} else {
				try {
					oos.close();
					System.out.println("Dreck fertig!\r\n\r\n" + out);
				} catch (IOException e) {
					Kursverwaltung.showException(e);
					ret = false;
				}
			}
			this.occupied = false;
			this.safeFile = null;
		}
		return ret;
	}

	@Override
	public boolean loadElement(SchoolItemAbstract schoolItemAbstract) {
		if (loading) {
			if (schoolItemAbstract instanceof Student) {
				Student.load((Student) schoolItemAbstract);
				System.out.println(eDao.toString() + ": Schüler geladen: " + ((Student) schoolItemAbstract).toString());
				cs++;
			}
			if (schoolItemAbstract instanceof Teacher) {
				Teacher.load((Teacher) schoolItemAbstract);
				System.out.println(eDao.toString() + ": Leerer geladen: " + ((Teacher) schoolItemAbstract).toString());
				ct++;
			}
			if (schoolItemAbstract instanceof Course) {
				Course course = (Course) schoolItemAbstract;
				System.out.println(eDao.toString() + ": Kurs geladen: " + course.toString());
				Course.load(course);
				cc++;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean loadAll() {
		boolean ret = false;
		cc = ct = cs = ca = 0;
		Kursverwaltung.setStoreMode(eDao, "loadAll()");
		if (chooseFile(false) != null || hasClipBoardData()) {
			if (hasClipBoardData() && this.safeFile == null) {
				this.ois = null;
				;
			} else {
				try {
					this.ois = new ObjectInputStream(new FileInputStream(this.safeFile));
				} catch (FileNotFoundException e) {
					Kursverwaltung.showException(e);
				} catch (IOException e) {
					Kursverwaltung.showException(e);
				}
			}
			if (this.ois == null) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable clipboardContent = clipboard.getContents(this);
				if (clipboardContent.isDataFlavorSupported(arrayListDataFlavor)) {
					System.out.println("Clipboard bereit zum laden");
				} else {
					clipboard.setContents(new Transferable() {
						public DataFlavor[] getTransferDataFlavors() {
							return new DataFlavor[0];
						}

						public boolean isDataFlavorSupported(DataFlavor flavor) {
							return false;
						}

						public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
							throw new UnsupportedFlavorException(flavor);
						}
					}, null);
				}
				DataFlavor[] flavors = clipboardContent.getTransferDataFlavors();
				for (int i = 0; i < flavors.length; i++) {
					// System.out.println("flavor[" + i + "] = " + flavors[i]);
					if (flavors[i].equals(arrayListDataFlavor)) {
						ArrayList<SchoolItemAbstract> clipboardList = null;
						try {
							clipboardList = (ArrayList<SchoolItemAbstract>) clipboardContent
									.getTransferData(arrayListDataFlavor);
						} catch (UnsupportedFlavorException e) {
							Kursverwaltung.showException(e);
						} catch (IOException e) {
							Kursverwaltung.showException(e);
						}
						if (clipboardList != null) {
							this.loading = true;
							for (int index = 0; index < clipboardList.size(); index++) {
								ret &= this.loadElement(clipboardList.get(index));
							}
							this.loading = false;
						}
					}
				}
			} else {
				Object read = null;
				try {
					read = this.ois.readObject();
				} catch (ClassNotFoundException e) {
					Kursverwaltung.showException(e);
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
						Kursverwaltung.showException(e);
						ret = false;
					} catch (IOException e) {
						// We're done
					}
				}
				this.loading = false;
				this.out = "Aus Datei gelutscht: " + cc + " mal Dummgelaber, " + ct + " Labertaschen und " + cs
						+ " Hohlköpfe.";
				Kursverwaltung.showMessage("Dreck fertig!\r\n\r\n" + out);
				try {
					ois.close();
				} catch (IOException e) {
					Kursverwaltung.showException(e);
					ret = false;
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

	@Override
	public boolean isConnected() {
		boolean ret = false;
		if (safeFile != null) {
			ret = safeFile.canWrite();
		}
		return ret;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		System.out.println("Clipboard: Besitzrecht verloren!");
	}
	/////////////////////////////////////////////////////////////////////////////////////

	public static boolean hasClipBoardData() {
		Preferences preferences = Preferences.userNodeForPackage(Kursverwaltung.class);
		String check = preferences.get(errorKey, "");
		return (check.length() > 0);
	}

	public static void putClipBoardData(boolean clipBoardData) {
		Preferences preferences = Preferences.userNodeForPackage(Kursverwaltung.class);
		if (clipBoardData) {
			preferences.put(errorKey, errorKey);
		} else {
			preferences.put(errorKey, "");
		}
	}

	public static String getFileSystemPath() {
		Preferences preferences = Preferences.userNodeForPackage(Kursverwaltung.class);
		return preferences.get(fileKey, "");
	}

	public static void setFileSystemPath(String fileSystemPath) {
		Preferences preferences = Preferences.userNodeForPackage(Kursverwaltung.class);
		preferences.put(fileKey, fileSystemPath);
	}

	private class ClipboardContainer implements Transferable {
		private ArrayList<SchoolItemAbstract> list;

		@SuppressWarnings("unchecked")
		public ClipboardContainer(ArrayList<SchoolItemAbstract> list) {
			this.list = (ArrayList<SchoolItemAbstract>) list.clone();
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (flavor == arrayListDataFlavor) {
				return list;
			}
			return new UnsupportedFlavorException(flavor);
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] dataFlavors = new DataFlavor[1];
			dataFlavors[0] = arrayListDataFlavor;
			return dataFlavors;
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			if (flavor == arrayListDataFlavor) {
				return true;
			}
			return false;
		};
	}

	private void initializeWatchService() {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			File choose = chooseFile(false);
			if (choose != null) {
				Paths.get(choose.getParent()).register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
				executor = Executors.newFixedThreadPool(1);
				executor.execute(this);
			}
		} catch (IOException e) {
			Kursverwaltung.showException(e);
		}
	}

	public static boolean isCancel() {
		return cancel;
	}

}
