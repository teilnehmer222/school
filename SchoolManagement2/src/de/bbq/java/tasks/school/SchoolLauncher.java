package de.bbq.java.tasks.school;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
 
public class SchoolLauncher extends JFrame {
	private static final int winLength = 800;
	private static final int winHight = 430;
	private CoursePanel panel1;
	private TeacherPanel panel2;
	private StudentPanel panel3;
	public static void main(String[] args) {
		// frame.add(keyboardExample);
		SchoolLauncher automat = new SchoolLauncher();
		automat.setVisible(true);
	}
	private void addControls() {
        this.setLayout(new GridLayout(1, 1));
         
        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("middle.gif");
         
        panel1 = new CoursePanel();// makeTextPanel("Hier sollen die Kurse aufgelistet werden");
        tabbedPane.addTab("Kurse", icon, panel1,"Kurse");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        panel2 = new TeacherPanel();//  makeTextPanel("Hier sollen die Leerer aufgelistet werden");
        tabbedPane.addTab("Leerer", icon, panel2,"Leerer");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
         
        panel3 = new StudentPanel();// makeTextPanel("Hier sollen die Schüler aufgelistet werden");
        tabbedPane.addTab("Schüler", icon, panel3,"Schüler");
        panel3.setPreferredSize(new Dimension(410, 50));
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
         
        tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane source = (JTabbedPane) e.getSource();
				switch(source.getSelectedIndex()) {
				case 0:
					panel1.refresh();
					break;
				case 1:
					panel2.refresh();
					break;
				case 2:
					panel3.refresh();
					break;
					
				}
				// TODO Auto-generated method stub
				
			}
		});
        //Add the tabbed pane to this panel.
        add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
     
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
     
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SchoolLauncher.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.

        //Add content to the window.
        JFrame frame = new SchoolLauncher();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("School Example");
        

        frame.setSize(winLength, winHight);
        frame.pack();
        frame.setLocationRelativeTo(null);
     
        //Display the window.
 
        frame.setVisible(true);
    }

	public static JButton getButton(String buName, int x, int y, int width, int height, ActionListener linr,
			String display, String tooltip) {
		JButton button = new JButton(buName);
		button.setName(buName);
		if (display.length() > 0) {
			button.setText(display);
		}
		if (tooltip.length() > 0) {
			button.setToolTipText(tooltip);
		}
		button.setBounds(x, y, width, height);
		button.addActionListener(linr);
		return button;
	}
	
	public SchoolLauncher() {
		setTitle("School Management");
		setSize(winLength, winHight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		addControls();
	}
}