package javaapplication7;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Frame extends JFrame {
	public Frame() throws InterruptedException, InterruptedException {
		super("MouseTest");
                MouseTestPanel pan=new MouseTestPanel(); 
		add(pan);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
                
                 new Thread("a"){
        public void run(){
          czworki czw=new czworki(null);
                czw.pan=pan;
            try {
                czw.main(null);
            } catch (InterruptedException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }.start();
                
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
                            try {
                                new Frame();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
	}
}
