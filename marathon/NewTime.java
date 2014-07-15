import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import static javax.swing.JOptionPane.*;

public class NewTime extends JFrame{

	JTextField startNoField = new JTextField(8);
	JTextField timeField = new JTextField(8);
	ArrayList<Runner> allRunners;
	JFrame win;
	
	//Metod för att söka igenom alla löpare och retunerar den löpare med rätt startnummer
	Runner getRunner(int startNo){
		for (Runner r : allRunners)
			if (r.getStartNo() == Integer.parseInt(startNoField.getText()))
				return r;
		return null;
	}
	
	NewTime(ArrayList<Runner> allRunners, JFrame win){		
		this.allRunners = allRunners;

		JPanel form = new JPanel();
		form.setLayout(new GridLayout(2,2,0,5));
		form.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Registrera tid"),
			BorderFactory.createEmptyBorder(5, 10, 5, 10))
		);
		
		form.add(new JLabel("<html><b>Startnummer:</b></html>"));
		form.add(startNoField);

		form.add(new JLabel("<html><b>Tid (MM.SS)</b></html>"));
		form.add(timeField);
		
		for(;;){
			try{
				int input = JOptionPane.showConfirmDialog(win, form, "Registrera tid", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);
				if (input != OK_OPTION){
					return;
				}
				int startNo = Integer.parseInt(startNoField.getText()); //Hämtar startnumret man skrivit in
				double time = Double.parseDouble(timeField.getText()); //Hämtar tiden man skrivit in och parsar den till en double
				Runner r = getRunner(startNo); //Anropar metoden för att söka efter rätt löpare och skickar startnumret man skrivit in
				if (r!=null){ //Om den hittar rätt löpare ska följande göras
					if (r.getTime() != 0.0){
						int alreadyRegistred = JOptionPane.showConfirmDialog(win, "Löparen har redan en registrerad tid!\nVill du uppdatera den?", "Varning!", JOptionPane.YES_NO_OPTION);
						if (alreadyRegistred == OK_OPTION){
							r.setTime(time);
							return;
						}
					}else{
						r.setTime(time);
						return;
					}
				}else{ //Om ingen löpare hittas dyker detta fönster upp
					showMessageDialog(win, "Hittade ingen löpare med det angivna startnumret!");
				}
			}catch(NumberFormatException e){
				showMessageDialog(win, "Något blev fel!\nVänligen prova igen.");
			}
		}
	}
}