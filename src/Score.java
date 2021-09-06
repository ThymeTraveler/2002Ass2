package skeletonCodeAssgnmt2;

import java.util.concurrent.atomic.AtomicInteger;

public class Score {
	//I changed all the integers to atomic integers to prevent some bad interleaving
	private AtomicInteger missedWords;
	private AtomicInteger caughtWords;
	private AtomicInteger gameScore;
	
	Score() {
		missedWords = new AtomicInteger(0);
		caughtWords = new AtomicInteger(0);
		gameScore = new AtomicInteger(0);
	}
		
	// all getters and setters must be synchronized
	//all of these functions are self explanatory and thus do not need javadoc comments
	
	public synchronized int getMissed() {
		return missedWords.get();
	}

	public synchronized int getCaught() {
		return caughtWords.get();
	}
	
	public synchronized int getTotal() {
		return (missedWords.get()+caughtWords.get());
	}

	public synchronized int getScore() {
		return gameScore.get();
	}
	
	public synchronized void missedWord() {
		missedWords.incrementAndGet();
	}

	public synchronized void caughtWord(int length) {//as this has two statements, it should be synchronized
		caughtWords.incrementAndGet();
		gameScore.addAndGet(length);
	}

	public void resetScore() {
		missedWords.set(0);
		caughtWords.set(0);
		gameScore.set(0);
	}
}
