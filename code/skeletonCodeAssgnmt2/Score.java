package skeletonCodeAssgnmt2;

import java.util.concurrent.atomic.AtomicInteger;

public class Score {
	private AtomicInteger missedWords;
	private AtomicInteger caughtWords;
	private AtomicInteger gameScore;
	
	Score() {
		missedWords = new AtomicInteger(0);
		caughtWords = new AtomicInteger(0);
		gameScore = new AtomicInteger(0);
	}
		
	// all getters and setters must be synchronized
	
	public int getMissed() {
		return missedWords.get();
	}

	public int getCaught() {
		return caughtWords.get();
	}
	
	public int getTotal() {
		return (missedWords.get()+caughtWords.get());
	}

	public int getScore() {
		return gameScore.get();
	}
	
	public void missedWord() {
		missedWords.incrementAndGet();
	}

	public void caughtWord(int length) {
		caughtWords.incrementAndGet();
		gameScore.addAndGet(length);
	}

	public void resetScore() {
		missedWords.set(0);
		caughtWords.set(0);
		gameScore.set(0);
	}
}
