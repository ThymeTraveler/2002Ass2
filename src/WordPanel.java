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
import java.lang.Math;
import java.util.concurrent.TimeUnit;

public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		public Timer timer;
		private WordRecord[] words;
		private int noWords;
		private int maxY;
		Graphics g;
		Score score;

		/**                                                                                                                                                                                      
   * This populates the jframe                                                                 
   * @param g the frame to populate                                                                                                                  
   * @return void                                                                                       
   */
		
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
		    	g.drawString(words[i].getWord() ,words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words

		    }
		   
		  }

		/**                                                                                                                                                                                      
		 * This id the constructor for the wordPanel class                                                                
		 * @param words the words to populate the panel with
		 * @param maxY the height of the frame
		 * @param score the score object so that this class can manipulat the score                                                                                                                     
		 * @return void                                                                                       
		 */
		
		WordPanel(WordRecord[] words, int maxY,Score score) {
			this.words=words; //will this work? - Apparently it does
			noWords = words.length;
			done=false;
			this.maxY=maxY;
			this.score = score;
		}
		

		/**                                                                                                                                                                                      
		 * This is the implementation of runnable.run() to allow this to run in its own thread 
		 * This particular method controls the animation of the application
		 * the framerate is currently set to 30 frames per second
		 * unfortunately the game speed is tied to the Framerate (correcting this would be simple but outside the scope of what this assignment calls for)                                                                                                                                                                               
		 * @return void                                                                                       
		 */
		public void run() {
			//add in code to animate this
			done =false;//This variable is used to ensure the stopping of the timers across classes
			for (int i=0;i<noWords;i++){
				words[i].resetWord(); //resets all words on start 

			}

				 timer = new Timer(0, new ActionListener() { //This timer controls the falling animation and increments score.missedwords should a word be misssesd
					@Override
					public void actionPerformed(ActionEvent e) {

						for (int i = 0; i < noWords; i++) {
							words[i].drop((int) Math.round(words[i].getSpeed()/600.0));
							if (words[i].getY()>470){//470 pixels down seemed to be a fair spot to consider the cutoff for a word being missed
								score.missedWord();//increments missedwords
								words[i].resetWord();//Resets the word if missed
							}
						}
						repaint();//repaints the frame for every one of the timer's cycles
					}
				});

					timer.setRepeats(true); //enables the timer

					timer.setDelay(33);//sets the delay to 33ms for aproximately 30 fps 
					timer.start();//starts the timer



			}

			/**                                                                                                                                                                                      
			 * This checks if an answer is correct and if so, controls the wordpanel to act accordingly as well as updating the score object                                                            
			 * @param input the string of an input answer                                                                                                                
			 * @return void                                                                                       
			 */

			public boolean answer(String input) {
				for (int i = 0; i < noWords; i++) {
					if (words[i].getWord().equals(input)) {//checks if word[i] is equal to the input
						words[i].resetWord();//if it is then the word is reset
						score.caughtWord(input.length());//and the score is increased by the length of the word
						//the line above was the only place I saw a concurrency issue pop up (bad interleaving I think), 
						//which I fixed by using the length of the input string rather than the length of words[i].getword
						return true;

					}

				}
				return false;//although this has a return type, I no longer require it
		}



}


