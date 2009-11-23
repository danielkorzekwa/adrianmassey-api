package dk.bot.adrianmassey;

import java.util.Date;
import java.util.List;

import dk.bot.adrianmassey.model.MasseyHRMarket;

/**Adapter to the adrianmassey.com website.
 * 
 * @author daniel
 *
 */
public interface AdrianMassey {

	/**
	 * 
	 * @param day Returns markets for a given day. 
	 * @return
	 */
	public List<MasseyHRMarket> getMarkets(Date day);
}
