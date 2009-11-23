package dk.bot.adrianmassey;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import dk.bot.adrianmassey.model.MasseyHRMarket;
import dk.bot.adrianmassey.model.MasseyHRRunner;

public class AdrianMasseyImpl implements AdrianMassey {

	private final Log log = LogFactory.getLog(AdrianMasseyImpl.class.getSimpleName());
	
	private HttpClient client;

	public AdrianMasseyImpl() {
		client = new HttpClient();
		client.getParams().setSoTimeout(10000);
	}

	public List<MasseyHRMarket> getMarkets(Date day) {
		
				
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a dd MMM yyyy");
		List<MasseyHRMarket> markets = new ArrayList<MasseyHRMarket>();

		
		// Create a method instance.
		GetMethod method = new GetMethod("http://www.adrianmassey.com/" + new DateTime(day).getDayOfMonth() + "/rating.php");

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				throw new IOException("GET error: " + statusCode);
			}

			// Read the response body.
			String responseBody = method.getResponseBodyAsString(Integer.MAX_VALUE);
			Source source = new Source(new StringReader(responseBody));
			
			Element body = source.getAllElements("body").get(0);
			for(Element meeting: body.getChildElements()) {
				/**Parse meeting element*/
				if(meeting.getAttributeValue("id")!=null && meeting.getAttributeValue("id").startsWith("cse")) {
					
					for(int i=0;i<meeting.getChildElements().size();i++) {
						Element meetingChild = meeting.getChildElements().get(i);
						/**Parse meeting market*/
						if(meetingChild.getName().equals("table")) {
							Element meetingRunners = meeting.getChildElements().get(i+1);
							if(!meetingRunners.getName().equals("div") || !meetingRunners.getAttributeValue("id").startsWith("race")) {
								throw new AdrianMasseyException("Format not recognized");
							}
							
							/**Parse meeting name and market date*/
							Element meetingHeader = meetingChild.getAllElements("tr").get(0).getAllElements("th").get(0);
							String[] meetingHeaderArray = meetingHeader.getTextExtractor().toString().split(" ");
							String meetingName = meetingHeaderArray[2];
							DateTime marketTime = new DateTime(df.parse(meetingHeaderArray[0] + " pm " + meetingHeaderArray[5] + " " + meetingHeaderArray[6] + " " + meetingHeaderArray[7]));
							/**Return empty list if parsed marketDate doesn't match the date that the markets were asked for.*/
							if(marketTime.getMonthOfYear() != new DateTime(day).getMonthOfYear()) {
								return new ArrayList<MasseyHRMarket>();
							}
														
							List<MasseyHRRunner> runners = new ArrayList<MasseyHRRunner>();
							/**Parse market runners*/
							List<Element> meetingRunnersList = meetingRunners.getAllElements("table").get(0).getChildElements();
							for(int j=1;j<meetingRunnersList.size();j++) {
								Element meetingRunner = meetingRunnersList.get(j);
								String selectionName = meetingRunner.getChildElements().get(1).getTextExtractor().toString();
								String ratingText = meetingRunner.getChildElements().get(meetingRunner.getChildElements().size()-2).getTextExtractor().toString();
								double rating=0;
								try {
								rating = Double.parseDouble(ratingText);
								}
								catch(NumberFormatException e) {
									log.warn("Can't parse ratingValue:" + ratingText);
								}
								MasseyHRRunner runner = new MasseyHRRunner();
								runner.setRunnerName(selectionName);
								runner.setRating(rating);
								runners.add(runner);
							}
							
							MasseyHRMarket horseWinMarket = new MasseyHRMarket();
							horseWinMarket.setMeetingName(meetingName);
							horseWinMarket.setMarketTime(marketTime.toDate());
							horseWinMarket.setMarketRunners(runners);
							markets.add(horseWinMarket);
						}
					}
				}
			}
			
		return markets;	

		} catch (Exception e) {
			throw new AdrianMasseyException(e);
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
	}

}
