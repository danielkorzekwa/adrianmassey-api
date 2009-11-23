package dk.bot.adrianmassey.model;

import java.io.Serializable;

/**
 * Data model for horse win market runner
 * 
 * @author daniel
 * 
 */
public class MasseyHRRunner implements Serializable{

	private String runnerName;

	/** Go to adrianmassey.com for more details.*/
	private double rating;
	
	public MasseyHRRunner() {
	}
	
	public MasseyHRRunner(String runnerName,double rating) {
		this.runnerName = runnerName;
		this.rating = rating;
	}

	public String getRunnerName() {
		return runnerName;
	}

	public void setRunnerName(String runnerName) {
		this.runnerName = runnerName;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
}
