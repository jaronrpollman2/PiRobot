
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;

import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * 
 */

/**
 * @author jaron
 *
 */
public class ControlGUI extends JFrame {
	static IOManager io;
	@SuppressWarnings("deprecation")
	public ControlGUI() throws IOException {
		io = new IOManager();
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		
		final KeyStroke keyForward = KeyStroke.getKeyStroke((char) KeyEvent.VK_W,Event.ALT_MASK);
		final KeyStroke keyReverse = KeyStroke.getKeyStroke((char) KeyEvent.VK_S,Event.ALT_MASK);
		final KeyStroke keyLeft = KeyStroke.getKeyStroke((char)KeyEvent.VK_A,Event.ALT_MASK);
		final KeyStroke keyRight = KeyStroke.getKeyStroke((char)KeyEvent.VK_D,Event.ALT_MASK);
		final KeyStroke keyShoot = KeyStroke.getKeyStroke((char) KeyEvent.VK_L,Event.ALT_MASK);
		final KeyStroke keyStop = KeyStroke.getKeyStroke((char) KeyEvent.VK_X,Event.ALT_MASK);
		
		
		JButton btnW = new JButton("Forward");
		Action forward = new AbstractAction("Forward") {
			public void actionPerformed(ActionEvent e) {
				try {
					io.controlDrive();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		};
		
		btnW.getActionMap().put("Forward", forward);
		btnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyForward, "Forward");
		btnW.setBounds(170, 49, 89, 23);
		panel.add(btnW);
		
		JButton btnA = new JButton("Left");
		Action left = new AbstractAction("left") {
			public void actionPerformed(ActionEvent e) {
				try {
					io.turnLeft();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		};
		
		btnW.getActionMap().put("left", left);
		btnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyLeft, "left");
		btnA.setBounds(47, 115, 89, 23);
		panel.add(btnA);
		
		JButton btnS = new JButton("Reverse");
		Action reverse = new AbstractAction("Reverse") {
			public void actionPerformed(ActionEvent e) {
				try {
					io.controlDriveReverse();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		};
		
		btnW.getActionMap().put("Reverse", reverse);
		btnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyReverse, "Reverse");
		btnS.setBounds(170, 171, 89, 23);
		panel.add(btnS);
		
		JButton btnD = new JButton("Right");
		Action right = new AbstractAction("Right") {
			public void actionPerformed(ActionEvent e) {
				try {
					io.turnRight();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		};
		
		btnW.getActionMap().put("Right", right);
		btnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyRight, "Right");
		btnD.setBounds(264, 115, 89, 23);
		panel.add(btnD);
		
		JButton btnL = new JButton("Fire");
		Action fire = new AbstractAction("Fire") {
			public void actionPerformed(ActionEvent e) {
					try {
						io.c1.fire();
					} catch (IOException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		};
		
		btnW.getActionMap().put("fire", fire);
		btnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyShoot, "fire");
		btnL.setBounds(322, 24, 89, 23);
		panel.add(btnL);
		
		JButton btnStop = new JButton("Stop");
		Action stop = new AbstractAction("Stop") {
			public void actionPerformed(ActionEvent e) {
				try {
					io.disableOutputs();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		};
		
		btnW.getActionMap().put("Stop", stop);
		btnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStop, "Stop");
		btnStop.setBounds(430, 191, 89, 23);
		panel.add(btnStop);
		
		
		
		
		this.setSize(new Dimension(50,50));
		this.setVisible(true);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ControlGUI c = new ControlGUI();

	}
}
