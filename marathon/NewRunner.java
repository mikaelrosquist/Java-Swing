import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import static javax.swing.JOptionPane.*;

public class NewRunner extends JFrame{

	JTextField nameField = new JTextField(8);
	JTextField countryField = new JTextField(8);
	JTextField ageField = new JTextField(8);
	
	ArrayList<Runner> allRunners;
	JFrame win;
	JButton timeButton, showButton;
	
	NewRunner(ArrayList<Runner> allRunners, JFrame win, JButton timeButton, JButton showButton){
		this.allRunners = allRunners;
		this.timeButton = timeButton;
		this.showButton = showButton;
		
		JPanel form = new JPanel();
		form.setLayout(new GridLayout(4,2,0,5));
		form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Registrera löpare"),
            BorderFactory.createEmptyBorder(5, 10, 5, 10))
		);
		
		form.add(new JLabel("<html><b>Startnummer: </b></html>"));
		form.add(new JLabel(""+(allRunners.size()+1)));
 
		form.add(new JLabel("<html><b>Namn:</b></html>"));
		form.add(nameField);
		
		form.add(new JLabel("<html><b>Land:</b></html>"));
		form.add(countryField);

		form.add(new JLabel("<html><b>Ålder:</b></html>"));
		form.add(ageField);
		
		for(;;){
			try{
				int input = JOptionPane.showConfirmDialog(win, form, "Ny löpare", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);
				if (input != OK_OPTION){
					return;
				}
				String name = nameField.getText();
				int age = Integer.parseInt(ageField.getText());
				String country = countryField.getText();
				int startNo = allRunners.size()+1;
				double time = 0.0;
				Runner r = new Runner(name, country, age, startNo, time);
				allRunners.add(r);
				timeButton.setEnabled(true);
				showButton.setEnabled(true);
				return;
			}catch(NumberFormatException k){
				showMessageDialog(win, "Något blev fel!\nVänligen prova igen.");
			}
		}
	}

}