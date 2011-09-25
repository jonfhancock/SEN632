package com.apps4you.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.apps4you.shared.Origins;
import com.apps4you.shared.Warrior;

/**
 * Allows for the input from the user on new warriors
 * @author Craig Mersereau
 *
 */
public class CreateWarrior extends JDialog {

	
	private static final long serialVersionUID = -3237504880400716966L;
	
	private final JPanel contentPanel = new JPanel();//Panel that everything goes on
	private JTextField nameTextField; //The name of the new warrior
	private JTextField descTextField;//The description of the new warrior
	private JButton okButton = new JButton("OK"); //The OK button
	private JButton cancelButton = new JButton("Cancel"); //The Cancel Button
	private JComboBox originComboBox = new JComboBox(); //The origin of the new warrior
	
	
	/**
	 * Create the dialog.
	 */
	public CreateWarrior() {
		setTitle("Create a New Warrior");
		setBounds(100, 100, 306, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel newLabel = new JLabel("Name:");
			newLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			newLabel.setBounds(21, 11, 46, 14);
			contentPanel.add(newLabel);
		}
		
		nameTextField = new JTextField();
		nameTextField.setBounds(73, 8, 189, 20);
		contentPanel.add(nameTextField);
		nameTextField.setColumns(30);
		
		JLabel originLabel = new JLabel("Origin:");
		originLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		originLabel.setBounds(21, 47, 46, 14);

		
		contentPanel.add(originLabel);
		originComboBox.setModel(new DefaultComboBoxModel(Origins.values()));
		

		originComboBox.setBounds(73, 39, 189, 25);
		contentPanel.add(originComboBox);
		
		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		descriptionLabel.setBounds(21, 84, 75, 14);
		contentPanel.add(descriptionLabel);
		
		descTextField = new JTextField();
		descTextField.setBounds(21, 109, 251, 82);
		contentPanel.add(descTextField);
		descTextField.setColumns(10);
		
		//Setup a Action Listener for all the buttons
		ButtonHandler handler = new ButtonHandler();
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				okButton.addActionListener(handler);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{

				cancelButton.addActionListener(handler);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		} 
	}
	/**
	 * Gets the name of the Newly created Warrior
	 * @return String that is the new warriors name
	 */
	private String getNewName()
	{
		return this.nameTextField.getText();
		
	}
	/**
	 * Get the origin of the newly created warrior
	 * @return
	 */
	private Origins getOrigin()
	{
		return (Origins) this.originComboBox.getSelectedItem();

	}
	/**
	 * Get the description of the newly created warrior
	 * @return
	 */
	private String getDescription()
	{
		return this.descTextField.getText();
	}
	
	/**
	 * Button Handler that handles the actions for this class
	 * @author Craig Mersereau
	 *
	 */
    private class ButtonHandler implements ActionListener
    {
    	/**
    	 * The action performed overrirde
    	 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//OK Button was chosen
			if (e.getSource() == okButton) 
		    {
				//Gather the new information
				Warrior nw = new Warrior(getNewName(), 
						getOrigin(), 
						getDescription());

				//Setup a new file for the warrior
				File file = new File(getJarFolder(),getNewName()+".wdat");
				if(Consts.LOGGING){
					System.out.println("Wrote warrior to " + file.getAbsolutePath());
				}
				//Call the Utils to save the new warrior and specify the file to do it
				Utils.saveWarriorToFile(nw, file);
				//Set  the corresponding Client UI to have this new warrior
				ClientCombatantUI.getInstance().setWarrior(nw);
				//Set the UI visible		
				CreateWarrior.this.setVisible(false);
		    }
			//Cancel out of this UI
			else if (e.getSource() == cancelButton)
		    {	    	
				CreateWarrior.this.dispose();
		    }
		}
    }

	public File getJarFolder() {
		String decodedPath = ".";
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath(); 
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File jarFile = new File(decodedPath);
		File basePath = jarFile.getParentFile();
		return basePath;
	}
}
