import static javax.swing.JOptionPane.*;
//import com.apple.eawt.Application; //ENDAST FÖR APPLE OSX
import graphs.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.filechooser.FileNameExtensionFilter;

class PathFinder extends JFrame{

	private File imageFile;
	private String imageFilePath;
	private ImageIcon image;
	private JLabel imageLabel;
	private NewPlaceAdapter ml = new NewPlaceAdapter();
	private NodeClick nodeClick = new NodeClick();
	private JButton findPathButton, newPlaceButton, showConnectionButton, newConnectionButton, editConnectionButton;
	private JMenuItem newMenuItem, exitMenuItem, findPathMenuItem, showConnectionMenuItem, newPlaceMenuItem, newConnectionMenuItem, editConnectionMenuItem, aboutMenuItem, helpMenuItem, clearFieldsMenuItem, populateMenuItem;
	private JCheckBoxMenuItem showButtonsMenuItem;
	private ListGraph<City> nodes = new ListGraph<City>();
	private City c1 = null, c2 = null;
	
	PathFinder(){
		super("PathFinder - v.0.5 Beta");

		//MENYN
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		//MENYALTERNATIV
		JMenu fileMenu = new JMenu("Arkiv");
		menuBar.add(fileMenu);
		JMenu operationsMenu = new JMenu("Operationer");
		menuBar.add(operationsMenu);
		JMenu viewMenu = new JMenu("Visa");
		menuBar.add(viewMenu);
		JMenu helpMenu = new JMenu("Hjäp");
		menuBar.add(helpMenu);

		//MENY > ARKIV
		newMenuItem = new JMenuItem("Ny karta");
		newMenuItem.addActionListener(new OpenFile());
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		exitMenuItem = new JMenuItem("Avsluta PathFinder");
		exitMenuItem.addActionListener(new CloseApplication());
		fileMenu.add(exitMenuItem);
		
		//MENY > OPERATIONER
		findPathMenuItem = new JMenuItem("Hitta väg");
		findPathMenuItem.addActionListener(new FindPathListener());
		findPathMenuItem.setEnabled(false);
		operationsMenu.add(findPathMenuItem);
		showConnectionMenuItem = new JMenuItem("Visa förbindelser");
		showConnectionMenuItem.addActionListener(new ShowConnection());
		showConnectionMenuItem.setEnabled(false);
		operationsMenu.add(showConnectionMenuItem);
		operationsMenu.addSeparator();
		newPlaceMenuItem = new JMenuItem("Ny plats");
		newPlaceMenuItem.addActionListener(new NewPlaceListener());
		newPlaceMenuItem.setEnabled(false);
		operationsMenu.add(newPlaceMenuItem);
		newConnectionMenuItem = new JMenuItem("Ny förbindelse");
		newConnectionMenuItem.addActionListener(new NewConnectionListener());
		newConnectionMenuItem.setEnabled(false);
		operationsMenu.add(newConnectionMenuItem);
		operationsMenu.addSeparator();
		editConnectionMenuItem = new JMenuItem("Ändra förbindelser");
		editConnectionMenuItem.addActionListener(new EditConnectionListener());
		editConnectionMenuItem.setEnabled(false);
		operationsMenu.add(editConnectionMenuItem);
		operationsMenu.addSeparator();
		populateMenuItem = new JMenuItem("Lägg till slumpvisa noder");
		populateMenuItem.addActionListener(new PopulateListener());
		populateMenuItem.setEnabled(false);
		operationsMenu.add(populateMenuItem);
		clearFieldsMenuItem = new JMenuItem("Radera alla noder");
		clearFieldsMenuItem.addActionListener(new ClearFieldsListener());
		clearFieldsMenuItem.setEnabled(false);
		operationsMenu.add(clearFieldsMenuItem);

		//MENY > VISA
		showButtonsMenuItem = new JCheckBoxMenuItem("Visa menyraden");
		viewMenu.add(showButtonsMenuItem);
		showButtonsMenuItem.setSelected(true);

		//MENY > HJÄLP
		helpMenuItem = new JMenuItem("Hjälp");
		helpMenuItem.addActionListener(new Help());
		helpMenu.add(helpMenuItem);
		helpMenu.addSeparator();
		aboutMenuItem = new JMenuItem("Om PathFinder");
		aboutMenuItem.setEnabled(false);
		helpMenu.add(aboutMenuItem);

		//TANGENTBORDSGENVÄGAR
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		findPathMenuItem.setAccelerator(KeyStroke.getKeyStroke('F', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		newPlaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		newConnectionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		showButtonsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
		populateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		clearFieldsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		
		//KNAPPAR
		final JPanel topButtons = new JPanel();
		add(topButtons, BorderLayout.NORTH);

		findPathButton = new JButton ("Hitta väg");
		findPathButton.addActionListener(new FindPathListener());
		findPathButton.setEnabled(false);
		topButtons.add(findPathButton);
		showConnectionButton = new JButton ("Visa förbindelser");
		showConnectionButton.addActionListener(new ShowConnection());
		showConnectionButton.setEnabled(false);
		topButtons.add(showConnectionButton);
		newPlaceButton = new JButton ("Ny plats");
		newPlaceButton.addActionListener(new NewPlaceListener());
		newPlaceButton.setEnabled(false);
		topButtons.add(newPlaceButton);
		newConnectionButton = new JButton ("Ny förbindelse");
		newConnectionButton.addActionListener(new NewConnectionListener());
		newConnectionButton.setEnabled(false);
		topButtons.add(newConnectionButton);
		editConnectionButton = new JButton ("Ändra förbindelser");
		editConnectionButton.addActionListener(new EditConnectionListener());
		editConnectionButton.setEnabled(false);
		topButtons.add(editConnectionButton);

		//VISA ELLER GÖM KNAPPARNA
		showButtonsMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(showButtonsMenuItem.isSelected())
					add(topButtons, BorderLayout.NORTH);
				else
					remove(topButtons);
				repaint();
				pack();
			}
		});

		//KARTBILDEN
		JPanel imagePanel = new JPanel();
		imageLabel = new JLabel(image);
		imagePanel.add(imageLabel);
		add(imagePanel, BorderLayout.SOUTH);
		
		//HUVUDFÖNSTRETS UTSEENDE
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./icon.png"));
//Application.getApplication().setDockIconImage(new ImageIcon("./icon.png").getImage()); //APPLE OSX IKONFIX

		setVisible(true);
		setMinimumSize(new Dimension(550, 120));
	}

	//------MOUSELISTENERS------
	class NewPlaceAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent mev){
			int x = mev.getX();
			int y = mev.getY();

			imageLabel.removeMouseListener(ml);
			imageLabel.setCursor(Cursor.getDefaultCursor());

			newPlaceButton.setEnabled(true);
			newPlaceMenuItem.setEnabled(true);

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1,1));
			panel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Lägg till ny plats"),
					BorderFactory.createEmptyBorder(5, 10, 5, 10)));
			
			panel.add(new JLabel("Namn:"));
			JTextField placeField = new JTextField(10);
			panel.add(placeField);

			for(;;){
				int input = JOptionPane.showConfirmDialog(PathFinder.this, panel, "Ny plats", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);

				if (input == CANCEL_OPTION)
					break;

				if (placeField.getText().isEmpty())
					showMessageDialog(PathFinder.this, "Vänligen fyll i fältet.");
				else{
					addCity(placeField.getText(), x, y);
					//showMessageDialog(PathFinder.this, name+" har blivit tillagd på koordinaterna:\nX: "+x+", Y: "+y);
					break;
				}
			}
		}
	}

	class NodeClick extends MouseAdapter{
		public void mouseClicked(MouseEvent mev){
			City c = (City) mev.getSource();
			if(c1 == null && c != c2){
				c1 = c;
				c.setSelected(true);
				System.out.println("Markerade " + c.getName());
			}else if(c1 == c){
				c1 = c2;
				c2 = null;
				c.setSelected(false);
				System.out.println("Avmarkerade " + c.getName());
			}else if(c2 == null && c != c1){
				c2 = c;
				c.setSelected(true);
				System.out.println("Markerade " + c.getName());
			}else if(c2 == c){
				c2 = null;
				c.setSelected(false);
				System.out.println("Avmarkerade " + c.getName());
			}
			else{
				c2.setSelected(false);
				c2 = c;
				c.setSelected(true);
				System.out.println("Markerade " + c.getName() + ", avmarkerade " + c2.getName());
			}
			repaint();
		}
	}

	//------ACTIONLISTENERS------
	class OpenFile implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			JFileChooser choice = new JFileChooser("./images");
			choice.setFileFilter(new FileNameExtensionFilter("Pictures", "gif", "jpg", "png", "jpeg"));
			int option = choice.showOpenDialog(null);

			if (option == JFileChooser.APPROVE_OPTION){
				imageLabel.removeAll();
				imageFile = choice.getSelectedFile();
				imageFilePath = imageFile.getPath();
				imageLabel.setIcon(new ImageIcon(imageFilePath));
				pack();
				nodes.newMap();
				setLocationRelativeTo(null);

				//AKTIVERA PROGRAMMETS KNAPPAR EFTER EN KARTA ÖPPNATS
				newPlaceMenuItem.setEnabled(true);
				populateMenuItem.setEnabled(true);
				newPlaceButton.setEnabled(true);
			}	
		}
	}

	class NewPlaceListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			imageLabel.addMouseListener(ml);
			imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));  
			newPlaceButton.setEnabled(false);
			newPlaceMenuItem.setEnabled(false);
			repaint();
		}
	}

	class NewConnectionListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			if(c1 == null || c2 == null)
				showMessageDialog(PathFinder.this, "Två noder måste väljas för att skapa förbindelse", "Fel!", JOptionPane.WARNING_MESSAGE);
			else if(c1 != null && c2 != null){
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(2,1,0,5));
				panel.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder("Förbindelse mellan " + c1.getName() + " och " + c2.getName()),
						BorderFactory.createEmptyBorder(5, 20, 5, 20)));
				
				panel.add(new JLabel("Förbindelse:"));
				JTextField nameField = new JTextField(10);
				panel.add(nameField);

				panel.add(new JLabel("Tid (timmar):"));
				JTextField weightField = new JTextField(10);
				panel.add(weightField);

				for(;;){
					try{
						int input = JOptionPane.showConfirmDialog(PathFinder.this, panel, "Ny Förbindelse", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);
						if (input != OK_OPTION)
							return;

						int connectionWeight = Integer.parseInt(weightField.getText());

						if (nameField.getText().isEmpty())
							showMessageDialog(PathFinder.this, "Vänligen fyll i fältet.");
						else{
							nodes.connect(c1, c2, nameField.getText(), connectionWeight);
							return;
						}		
					}catch(NumberFormatException e){
						showMessageDialog(PathFinder.this, "Tid fylldes i fel.");
					}
				}
			}
		}
	}

	class EditConnectionListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){

			if(c1 == null || c2 == null){
				showMessageDialog(PathFinder.this, "Två noder måste väljas!", "Fel!", JOptionPane.WARNING_MESSAGE);
				return;
			}else{

				String title;
				if(c1 == null)
					title = "Alla förbindelser från " + c2;
				else if(c2 == null)
					title = "Alla förbindelser från " + c1;
				else
					title = "Alla förbindelser från " + c1 + " till " + c2;

				JPanel panel = new JPanel();
				panel.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder(title),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));

				JList<Edge<City>> list = new JList(nodes.getEdgesBetween(c1, c2).toArray());

				int row = nodes.getEdgesBetween(c1, c2).size();

				if(row < 3)
					row = 3;

				list.setFixedCellWidth(250);
				list.setVisibleRowCount(row);
				list.setFont(new Font("Arial",Font.BOLD,12));
				list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
				JScrollPane listContainer = new JScrollPane(list);
				panel.add(listContainer);

				JPanel editPanel = new JPanel();
				editPanel.setLayout(new GridLayout(2,1,0,5));
				editPanel.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder("Förbindelse mellan " + c1.getName() + " och " + c2.getName()),
						BorderFactory.createEmptyBorder(5, 20, 5, 20)));

				editPanel.add(new JLabel("Förbindelse:"));
				JTextField nameField = new JTextField(10);
				nameField.setEditable(false);
				editPanel.add(nameField);

				editPanel.add(new JLabel("Tid (h):"));
				JTextField weightField = new JTextField(10);
				editPanel.add(weightField);

				Edge<City> e = null;
				int input;

				if (nodes.getEdgesBetween(c1, c2).size() == 1)
					for (Edge<City> etmp : nodes.getEdgesBetween(c1, c2))
						e = etmp;

				else if (nodes.getEdgesBetween(c1, c2).size() > 1){
					int editInput = showConfirmDialog(PathFinder.this, panel, "Välj förbindelse", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);
					if (editInput == OK_OPTION){
						e = list.getSelectedValue();
					}
					else if(editInput == CANCEL_OPTION)
						return;
				}
				else{
					showMessageDialog(PathFinder.this, "Det finns inga förbindelser mellan\n" + c1 + " och " + c2, "Inga förbindelser", JOptionPane.WARNING_MESSAGE);
					return;
				}

				nameField.setText(e.getName());
				weightField.setText(""+e.getWeight());
				input = showConfirmDialog(PathFinder.this, editPanel, "Ändra förbindelse", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);
				if (input == OK_OPTION){
					String name = e.getName();
					int weight = Integer.parseInt(weightField.getText());
					weightField.setText(""+e.getWeight());

					System.out.println("Förbindelse med " + name + " ändrad till " + weight + " timmar.");
					nodes.setConnectionWeight(c1, c2, name, weight);
					return;
				}else if(input == CANCEL_OPTION)
					return;
			}
		}
	}

	class ShowConnection implements ActionListener{
		public void actionPerformed(ActionEvent ave){

			String title;
			if(c1 == null)
				title = "Förbindelser från " + c2;
			else if(c2 == null)
				title = "Förbindelser från " + c1;
			else
				title = "Förbindelser från " + c1 + " till " + c2;

			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(title),
					BorderFactory.createEmptyBorder(5, 10, 5, 10)));

			JList list = new JList();
			boolean connExist = false;
			int row;
			
			if(c1 != null && c2 == null){
				if (nodes.getEdgesFrom(c1).size() != 0)
					connExist = true;
				row = nodes.getEdgesFrom(c1).size();
				list = new JList(nodes.getEdgesFrom(c1).toArray());
			}else if(c1 == null && c2 != null){
				if (nodes.getEdgesFrom(c2).size() != 0)
					connExist = true;
				row = nodes.getEdgesFrom(c2).size();
				list = new JList(nodes.getEdgesFrom(c2).toArray());
			}else if(c1 != null && c2 != null){
				if (nodes.getEdgesBetween(c1, c2).size() != 0)
					connExist = true;
				row = nodes.getEdgesBetween(c1, c2).size();
				list = new JList(nodes.getEdgesBetween(c1, c2).toArray());
			}else{
				showMessageDialog(PathFinder.this, "Minst en nod måste väljas!", "Fel!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(row < 3)
				row = 3;

			list.setFixedCellWidth(250);
			list.setVisibleRowCount(row);
			list.setFont(new Font("Arial",Font.BOLD,12));

			list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
			panel.add(new JScrollPane(list));			
			if(connExist){
				JOptionPane.showMessageDialog(PathFinder.this, panel, "Förbindelser", JOptionPane.PLAIN_MESSAGE);

			}else
				showMessageDialog(PathFinder.this, "Det finns inga förbindelser mellan\n" + c1 + " och " + c2, "Inga förbindelser", JOptionPane.WARNING_MESSAGE);

		}
	}

	class FindPathListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Snabbaste förbindelse från " + c1 + " till " + c2),
					BorderFactory.createEmptyBorder(5, 10, 5, 10)));

			JList<Edge<City>> list;

			Edge<City> e = null;
			
			if(c1 != null && c2 != null){
				try{
					list = new JList(GraphMethods.shortestPath(nodes, c1, c2).toArray());
				}catch (NoSuchElementException noConn){
					showMessageDialog(PathFinder.this, "Det finns ingen förbindelse mellan de valda noderna!", "Fel!", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}else{
				showMessageDialog(PathFinder.this, "Två noder måste väljas!", "Fel!", JOptionPane.WARNING_MESSAGE);
				return;
			}

			list.setFixedCellWidth(300);
			list.setVisibleRowCount(3);
			list.setFont(new Font("Arial",Font.BOLD,12));
			list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
			panel.add(new JScrollPane(list), BorderLayout.CENTER);	
			JLabel total = new JLabel("Totalt: " + GraphMethods.getTotalTime() + " timmar");
			total.setFont(new Font("Arial",Font.BOLD,14));
			panel.add(total, BorderLayout.SOUTH);

			JOptionPane.showMessageDialog(PathFinder.this, panel, "Förbindelser", JOptionPane.PLAIN_MESSAGE);

		}
	}

	class PopulateListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			int input = JOptionPane.showConfirmDialog(PathFinder.this, "Du kommer nu att läga till noder", "Lägga till noder", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);
			
			if (input == OK_OPTION){

				//LÄGGER TILL 4 NODER PÅ SLUMPADE PLATSER
				Random rand = new Random();

				for(int i = 1; i < 5; i++)
					addCity("Plats " + i, rand.nextInt(i * 50) + (i * 50), rand.nextInt(i * 50) + (i * 50));

				populateMenuItem.setEnabled(false);
				repaint();
			}
			else
				return;
		}
	}

	class ClearFieldsListener implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			int input = JOptionPane.showConfirmDialog(PathFinder.this, "Du kommer nu att ta bort alla noder!", "Radera noder", JOptionPane.OK_CANCEL_OPTION, PLAIN_MESSAGE);

			if (input == OK_OPTION){
				imageLabel.removeAll();
				nodes.newMap();
				imageFilePath = imageFile.getPath();
				imageLabel.setIcon(new ImageIcon(imageFilePath));
				c1 = null;
				c2 = null;
				populateMenuItem.setEnabled(true);
				clearFieldsMenuItem.setEnabled(false);
				newConnectionMenuItem.setEnabled(false);
				editConnectionMenuItem.setEnabled(false);
				findPathMenuItem.setEnabled(false);
				showConnectionMenuItem.setEnabled(false);
				findPathButton.setEnabled(false);
				showConnectionButton.setEnabled(false);
				newConnectionButton.setEnabled(false);
				editConnectionButton.setEnabled(false);

				repaint();
			}
			else
				return;
		}
	}

	class Help implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			try {
				JFrame helpPopup = new JFrame("Hjälp");

				JEditorPane helpPane = new JEditorPane();
				helpPane.setEditorKit(new HTMLEditorKit());
				helpPane.setEditable(false);

				String filename = "help.html";

				FileReader reader = new FileReader(filename);
				helpPane.read(reader, filename);

				JScrollPane scrollPane = new JScrollPane(helpPane);
				helpPopup.add(scrollPane);
				helpPopup.setSize(300, 400);
				helpPopup.setVisible(true);
				helpPopup.setLocationRelativeTo(PathFinder.this);

			}catch(IOException e){
				showMessageDialog(PathFinder.this, e.getMessage() + "\nKunde inte hitta hjälpfilen!");
			}
		}
	}

	class CloseApplication implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			System.exit(0);
		}
	}

	//------HJÄLPMETODER------
	public void addCity(String name, int x, int y){
		City c = new City(name, x, y);
		c.addMouseListener(nodeClick);
		c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  
		JLabel cityName = new JLabel(name);
		cityName.setHorizontalAlignment(SwingConstants.CENTER);
		cityName.setBounds(x-65, y+7, 130, 13);
		cityName.setFont(new Font(cityName.getFont().getName(),Font.BOLD,cityName.getFont().getSize()));  
		nodes.add(c);
		imageLabel.add(c);
		imageLabel.add(cityName);
		if(nodes.getNodes().size() > 1){
			newConnectionMenuItem.setEnabled(true);
			editConnectionMenuItem.setEnabled(true);
			findPathMenuItem.setEnabled(true);
			showConnectionMenuItem.setEnabled(true);
			findPathButton.setEnabled(true);
			showConnectionButton.setEnabled(true);
			newConnectionButton.setEnabled(true);
			editConnectionButton.setEnabled(true);
		}
		clearFieldsMenuItem.setEnabled(true);
		repaint();
	}

	public static void main(String[] args){
		
		//APPLE OSX MENYFIX
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		//LOOK AND FEEL
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception unused) {
			;
		}

		new PathFinder();
	}
}