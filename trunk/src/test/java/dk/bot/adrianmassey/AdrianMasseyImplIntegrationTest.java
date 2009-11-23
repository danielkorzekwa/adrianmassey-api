package dk.bot.adrianmassey;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import dk.bot.adrianmassey.model.MasseyHRMarket;

public class AdrianMasseyImplIntegrationTest {

	private AdrianMassey adrianMassey = new AdrianMasseyImpl();

	@Test
	public void testGetMarkets() throws ParseException {
		DateTime today = new DateTime(System.currentTimeMillis());
		List<MasseyHRMarket> markets = adrianMassey.getMarkets(today.toDate());
		assertEquals(true, markets.size() > 0);
	}
	
	@Test
	public void testGetMarketsIn5Days() throws ParseException {
		DateTime in5days = new DateTime(System.currentTimeMillis()).plusDays(5);
		List<MasseyHRMarket> markets = adrianMassey.getMarkets(in5days.toDate());
		assertEquals(0, markets.size());
	}
	

}
