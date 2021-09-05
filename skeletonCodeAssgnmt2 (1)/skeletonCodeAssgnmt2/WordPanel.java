package skeletonCodeAssgnmt2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		public Timer timer;
		private WordRecord[] words;
		private int noWords;
		private int maxY;
		Graphics g;
		Score score;
		
		public void paintComponent(Graphics g) {
		    int width = getWidth();
		    int height = getHeight();
			this.g=g;
		    g.clearRect(0,0,width,height);
		    g.setColor(Color.red);
		    g.fillRect(0,maxY-10,width,height);

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation must be added 
		    for (int i=0;i<noWords;i++){	    	
		    	//g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
		    	g.drawString(words[i].getWord() + " " + words[i].getSpeed(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words

		    }
		   
		  }
		
		WordPanel(WordRecord[] words, int maxY,Score score) {
			this.words=words; //will this work?
			noWords = words.length;
			done=false;
			this.maxY=maxY;
			this.score = score;
		}
		
		public void run() {
			//add in code to animate this
			done =false;
			for (int i=0;i<noWords;i++){
				words[i].resetWord();

			}

				 timer = new Timer(0, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						for (int i = 0; i < noWords; i++) {
							words[i].drop(words[i].getSpeed()/600);
							if (words[i].getY()>470){
								score.missedWord();
								words[i].resetWord();

							}

						}
						repaint();


					}
				});

				//for (int x =0; x<200;x++) {

					timer.setRepeats(true);
					// Aprox. 60 FPS
					timer.setDelay(16);
					timer.start();



				//}

			}

			public boolean answer(String input) {
				for (int i = 0; i < noWords; i++) {
					words[i].drop(words[i].getSpeed() / 600);
					if (words[i].getWord().equals(input)) {
						words[i].resetWord();
						score.caughtWord(input.length());
						return true;

					}

				}
				return false;
		}



}


