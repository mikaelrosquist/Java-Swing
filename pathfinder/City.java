import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class City extends JComponent {
	String name;
	int x, y;
	boolean selected;

	City(String name, int x, int y){
		this.name = name;
		this.x = x-7;
		this.y = y-7;
		selected = false;
		setBounds(this.x, this.y, 14, 14);
		setPreferredSize(new Dimension(14,14));
		setMaximumSize(new Dimension(14,14));
		setMinimumSize(new Dimension(14,14));

	}

	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		   g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                        RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponents(g2);
		if(selected){
			g2.setColor(Color.BLACK);
			g2.fillOval(0, 0, 14, 14);
			g2.setColor(Color.RED);
			g2.fillOval(1, 1, 12, 12);
		} else {
			g2.setColor(Color.BLACK);
			g2.fillOval(0, 0, 14, 14);
			g2.setColor(Color.CYAN);
			g2.fillOval(1, 1, 12, 12);
		}
	}

	public void setSelected(boolean b){
		selected = b;
		repaint();
	}

	public boolean isSelected(){
		return selected;
	}

	public String getName(){
		return name;
	}
	
	public boolean equals(City c){
		if (c == null || this != c)
			return false;
		else
			return true;
	}
	public String toString(){
		return name;
	}
}




