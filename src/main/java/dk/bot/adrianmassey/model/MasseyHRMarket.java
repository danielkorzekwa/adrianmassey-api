package dk.bot.adrianmassey.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**Data model for horse win market
 * 
 * @author daniel
 *
 */
public class MasseyHRMarket implements Serializable{

	/** e.g. Kempton or Warwick*/
	private String meetingName;
	
	/** e.g. 03.11.2008 13:50*/
	private Date marketTime;
	
	List<MasseyHRRunner> marketRunners;

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public Date getMarketTime() {
		return marketTime;
	}

	public void setMarketTime(Date marketTime) {
		this.marketTime = marketTime;
	}

	public List<MasseyHRRunner> getMarketRunners() {
		return marketRunners;
	}

	public void setMarketRunners(List<MasseyHRRunner> marketRunners) {
		this.marketRunners = marketRunners;
	}

}
