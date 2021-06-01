package javaapplication7;

import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Adrian
 */ 

public class MouseTestPanel extends JPanel implements MouseListener, ActionListener {

	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 400;
        private JButton Button;
        private JButton Button2;
	private int x, y;
        public boolean blok=false,blok2=false;
        public int wybcol=8;
        public Board b=new Board();
 
        public boolean nowa_gra=true;


	public MouseTestPanel() throws InterruptedException {
		Button = new JButton("Nowa gra");
                Button2 = new JButton("Wykonaj ruch");
                addMouseListener(this);
                Button.addActionListener(this);
                Button2.addActionListener(this);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
                add(Button);
                add(Button2);
	}
        @Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(source == Button)
                {
                   nowa_gra=true;
                        }
                else if(source == Button2){
                    blok2=true;
                }

	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!blok)
                {
                    blok=true;
                    x = e.getX();
                    y = e.getY();
                    if(between(x,50,100))wybcol=0;
                    if(between(x,100,150))wybcol=1;
                    if(between(x,150,200))wybcol=2;
                    if(between(x,200,250))wybcol=3;
                    if(between(x,250,300))wybcol=4;
                    if(between(x,300,350))wybcol=5;
                    if(between(x,350,400))wybcol=6;
                 
                }
            
        }
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(Color.BLACK);
                
                for(int i=0;i<7;i++)
                    for(int j=0;j<6;j++)
                    {
                        g2d.drawRect(i*50+50,j*50+50, 50, 50);
                    }
                
                
                
		drawOponent1(g2d);
 
             
	}

	private void drawOponent1(Graphics2D g2d) {
		int x, y;
                g2d.setColor(Color.RED);
		for(int i=0;i<7;i++)
                    for(int j=0;j<6;j++) {
			if(b.board[j][i]==1)
                        {
                            g2d.setColor(Color.RED);
                            g2d.fillOval(i*50+60, j*50+60, 40, 40);
                        }
                        if(b.board[j][i]==2)
                        {
                            g2d.setColor(Color.GREEN);
                           g2d.fillOval(i*50+60, j*50+60, 40, 40); 
                        }
			
		}
	}
        
             public static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
    return (i >= minValueInclusive && i <= maxValueInclusive);
}
      public void odmal()
      {
          repaint();
      }

}
