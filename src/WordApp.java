package skeletonCodeAssgnmt2;

import javax.swing.JOptionPane;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;

public class WordApp {
	// shared variables
	static int noWords = 4;
	static int totalWords;

	static int frameX = 1000;
	static int frameY = 600;
	static int yLimit = 480;

	static WordDictionary dict = new WordDictionary(); // use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done; // must be volatile
	static Score score = new Score();

	static WordPanel w;
	public static Timer timer;


	/**                                                                                                                                                                                      
   * This sets the GUI up and also manages some of its functionality using a timer                                                                  
   * @param frameX the width of the frame
   * @param frameY the height of the frame
   * @param yLimit sets the position of the red box                                                                                                                     
   * @return void                                                                                       
   */

	public static void setupGUI(int frameX, int frameY, int yLimit) {
		// Frame init and dimensions
		JFrame frame = new JFrame("WordGame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameX, frameY);
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
		g.setSize(frameX, frameY);

		w = new WordPanel(words, yLimit, score);
		w.setSize(frameX, yLimit + 100);
		g.add(w);

		JPanel txt = new JPanel();
		txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));
		JLabel caught = new JLabel("Caught: " + score.getCaught() + "    ");
		JLabel missed = new JLabel("Missed:" + score.getMissed() + "    ");
		JLabel scr = new JLabel("Score:" + score.getScore() + "    ");
		txt.add(caught);
		txt.add(missed);
		txt.add(scr);

		// [snip]

		final JTextField textEntry = new JTextField("", 20);
		textEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String text = textEntry.getText();
				// [snip]
				w.answer(text);
				textEntry.setText("");
				textEntry.requestFocus();
			}
		});

		txt.add(textEntry);
		txt.setMaximumSize(txt.getPreferredSize());
		g.add(txt);

		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		JButton startB = new JButton("Start");
		;

		// add the listener to the jbutton to handle the "pressed" event
		startB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// [snip]
				textEntry.requestFocus(); // return focus to the text entry field
				if (!Objects.isNull(w.timer)) {
					w.timer.stop();
					timer.stop();
				}
				score.resetScore();
				w.run();

				timer.setRepeats(true);
				// Aprox. 25 FPS
				timer.setDelay(40);
				timer.start();

			}
		});
		JButton endB = new JButton("End");
		;

		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// [snip]]
				if (!Objects.isNull(w.timer)) {//checks if the timers exist before trying to change their properties
					timer.stop();// stops this class's timer
					w.timer.stop();//stops the wordpanel's timer

					String message = "";
					float ratio = score.getCaught() / (float) totalWords;
					if (ratio < 0.25) {
						message = "Damn bro that's so low it's kinda cringe \n" + score.getCaught() + "/" + totalWords;
					}
					if (ratio >= 0.25 && ratio < 0.5) {
						message = "Not great, lots of room to improve. \n" + score.getCaught() + "/" + totalWords;
					}
					if (ratio < 0.75 && ratio >= 0.5) {
						message = "Decent performance, could be better. \n" + score.getCaught() + "/" + totalWords;
					}
					if (ratio >= 0.75 && ratio < 1) {
						message = "Great performance \n" + score.getCaught() + "/" + totalWords;
					}
					if (ratio == 1) {
						message = "Absolutely perfect! \n" + score.getCaught() + "/" + totalWords;
					}

					JOptionPane.showMessageDialog(frame, message);
				}

			}
		});

		JButton quitB = new JButton("Quit");
		;

		// add the listener to the jbutton to handle the "pressed" event
		quitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// [snip]]
				System.exit(0);

			}
		});

		timer = new Timer(0, new ActionListener() {//this timer controls the score display and ends the game if the word limit has been reached
			@Override
			public void actionPerformed(ActionEvent e) {

				scr.setText("Score:" + score.getScore() + "    ");
				caught.setText("Caught: " + score.getCaught() + "    ");
				missed.setText("Missed:" + score.getMissed() + "    ");

				if (score.getCaught() + score.getMissed() == totalWords) {
					w.done = true;
				}

				if (w.done == true) {
					// game over
					w.timer.stop();
					endB.doClick();
					timer.stop();

				}
			}
		});
		timer.setRepeats(true);
		// Aprox. 60 FPS
		timer.setDelay(16);
		timer.start();

		b.add(startB);
		b.add(endB);
		b.add(quitB);

		g.add(b);

		frame.setLocationRelativeTo(null); // Center window on screen.
		frame.add(g); // add contents to window
		frame.setContentPane(g);
		// frame.pack(); // don't do this - packs it into small space
		frame.setVisible(true);
	}

	/**                                                                                                                                                                                      
   * This sets up the WordDictionary object and also reads the input file into the dictionary                                                              
   * @param filename the name of the file containiing the words                                                                                                            
   * @return Array of String                                                                                       
   */

	public static String[] getDictFromFile(String filename) {
		String[] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();

			dictStr = new String[dictLength];
			for (int i = 0; i < dictLength; i++) {
				dictStr[i] = new String(dictReader.next());
				// System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
			System.err.println("Problem reading file " + filename + " default dictionary will be used");
		}
		return dictStr;
	}

	/**                                                                                                                                                                                      
   * This is the main function of the package.Essentially just starts the program                                                                
   * @args totalWords to fall
   * @args noWords to fall at any given time
   * @args filename of file containing words                                                                                                                     
   * @return void                                                                                       
   */
	public static void main(String[] args) {

		// deal with command line arguments
		totalWords = Integer.parseInt(args[0]); // total words to fall
		noWords = Integer.parseInt(args[1]); // total words falling at any point
		assert (totalWords >= noWords); // this could be done more neatly
		String[] tmpDict = getDictFromFile(args[2]); // file of words
		if (tmpDict != null)
			dict = new WordDictionary(tmpDict);

		WordRecord.dict = dict; // set the class dictionary for the words.

		words = new WordRecord[noWords]; // shared array of current words

		// [snip]

		setupGUI(frameX, frameY, yLimit);
		// Start WordPanel thread - for redrawing animation

		int x_inc = (int) frameX / noWords;
		// initialize shared array of current words

		for (int i = 0; i < noWords; i++) {
			words[i] = new WordRecord(dict.getNewWord(), i * x_inc, yLimit);
		}

	}
}