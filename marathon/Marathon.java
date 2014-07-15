import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static javax.swing.JOptionPane.*;

//Metoder för att sortera objekten i arrayListen
class StartNoCmp implements Comparator<Runner>{
	public int compare(Runner r1, Runner r2){
		return r1.getStartNo() - r2.getStartNo();
	}
}
class NameCmp implements Comparator<Runner>{
	public int compare(Runner r1, Runner r2){
		String n1 = r1.getName();
		String n2 = r2.getName();
		return n1.compareTo(n2);		
	}
}
class AgeCmp implements Comparator<Runner>{
	public int compare(Runner r1, Runner r2){
		return r1.getAge() - r2.getAge();
	}
}
class TimeCmp implements Comparator<Runner>{
	public int compare(Runner r1, Runner r2){
		if (r1.getTime() < r2.getTime() && r1.getTime() > 0.0) return -1;
		if (r1.getTime() > r2.getTime() && r2.getTime() > 0.0) return 1;
        if (r1.getTime() == 0.0) return 1;
        return 0;
	}
}


class Marathon extends JFrame {

	ArrayList<Runner> allRunners = new ArrayList<Runner>();
	JButton newButton = new JButton("Registrera löpare");
	JButton timeButton = new JButton("Registrera tid");
	JButton showButton = new JButton("Uppdatera listan");
	JRadioButton startNoRadioButton = new JRadioButton("Startnr");
	JRadioButton nameRadioButton = new JRadioButton("Namn");
	JRadioButton ageRadioButton = new JRadioButton("Ålder");
	JRadioButton timeRadioButton = new JRadioButton("Tid");
	JFrame win;
	JTextArea RunnerList;
	JPanel south, east, north, center;
	BorderLayout border;
	JScrollPane scroll;
	Image icon = Toolkit.getDefaultToolkit().getImage("icon.png"); //Programmets ikon
	Font font = new Font("Consolas", Font.PLAIN, 12); //Ändrar font
	
	Marathon(){
		win = new JFrame("DSV Kista Marathon");
		border = new BorderLayout();

		//Rubrik i NORTH
		JLabel head = new JLabel("DSV Kista Marathon");
		north = new JPanel();
		north.add(head);
		win.add(north, BorderLayout.NORTH);

		//Textfält i WEST
		final JTextArea RunnerList=new JTextArea(30,48);
		RunnerList.setEditable(false);
		RunnerList.setLineWrap(true);
		RunnerList.setFont(font); //Ändrar typsnittet till det vi angivit i början
		JScrollPane scroll=new JScrollPane(RunnerList); //Lägger till scrollpane i textrutan
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //Visar alltid scrollbaren
		win.add(scroll);
		RunnerList.append("Inga registrerade löpare!"); //Försvinner så fort man lägger till en ny löpare

		//Panel med gridLayout i EAST
		east = new JPanel();
		east.setLayout(new BorderLayout());
		win.add(east, BorderLayout.EAST);
		JPanel eastSouth = new JPanel();
		east.add(eastSouth, BorderLayout.SOUTH);
		eastSouth.setLayout(new GridLayout(5,1));

		JLabel sortLabel = new JLabel("<html><b>Sortering</b></html>");
		eastSouth.add(sortLabel);

		//Radioknappar i EAST/SOUTH		
		ButtonGroup radioGroup = new ButtonGroup(); //Grupperar radioknapparna
		radioGroup.add(startNoRadioButton);
		radioGroup.add(nameRadioButton);
		radioGroup.add(ageRadioButton);
		radioGroup.add(timeRadioButton);
		
		eastSouth.add(startNoRadioButton);
		eastSouth.add(nameRadioButton);
		eastSouth.add(ageRadioButton);
		eastSouth.add(timeRadioButton);
		
		startNoRadioButton.setSelected(true); //Markerar startnummer-radioknappen som default
		
		//Panel med gridLayout i SOUTH (ej klar)
		south = new JPanel();
		south.setLayout(new GridLayout(1,3));
		win.add(south, BorderLayout.SOUTH);
		
		south.add(newButton);
		south.add(timeButton);
		south.add(showButton);
		timeButton.setEnabled(false); //Så länge det inte finns någon registrerad löpare i systemet är dessa två knappar avaktiverade
		showButton.setEnabled(false);

		//ActionListeener för knappen "Ny"
		newButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				NewRunner ny = new NewRunner(allRunners, win, timeButton, showButton);
			}
		});

		showButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				RunnerList.setText(""); //Rensar NewRunner-fönstret
				
				if (nameRadioButton.isSelected()) //Sorterar listan utifrån vilken radioknapp som är markerad
					Collections.sort(allRunners, new NameCmp());
				else if (ageRadioButton.isSelected())
					Collections.sort(allRunners, new AgeCmp());
				else if (timeRadioButton.isSelected())
					Collections.sort(allRunners, new TimeCmp());
				else
					Collections.sort(allRunners, new StartNoCmp());
	
				for(Runner runner : allRunners){
					String time = Double.toString(runner.getTime());
					if (runner.getTime() == 0.0) {
						time = "--";
					}
					
					RunnerList.append(runner.getStartNo() + ": " + runner.getName() + ", " + runner.getAge() + " år, " + runner.getCountry() + ". Tid: " + time + "\n");
				}
			}
		});


		timeButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				NewTime ny = new NewTime(allRunners, win);
			}
		});

		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setVisible(true);
		win.pack();
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setIconImage(icon);
	}

	public static void main(String [] args){		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Anpassar programmets knappar och känsla efter operativsystemet
		} catch (Exception unused) {
			;
		}
		new Marathon();; 
	}
	
}