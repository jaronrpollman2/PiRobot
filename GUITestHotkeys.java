import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


/**
 * Just a test class to get key bindings to work
 */

/**
 * @author jaron
 *
 */
public class GUITestHotkeys extends JFrame {
	private JTextField textField;
	public GUITestHotkeys() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		KeyStroke keyForward = KeyStroke.getKeyStroke((char) KeyEvent.VK_W,Event.ALT_MASK);
		
		JButton btnHi = new JButton("HI");
		//btnHi.getActionMap().put(keyForward, );
		Action hi =  new AbstractAction("HI") {
			public void actionPerformed(ActionEvent e) {
				textField.setText("HI");
			}
		};
		btnHi.getActionMap().put("keyForward",hi);
		btnHi.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyForward,"keyForward");
		btnHi.setBounds(162, 53, 89, 23);
		panel.add(btnHi);
		
		textField = new JTextField();
		textField.setBounds(165, 130, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		this.setSize(new Dimension(300,225));
		this.setVisible(true);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUITestHotkeys k = new GUITestHotkeys();

	}
}
