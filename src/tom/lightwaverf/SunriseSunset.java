package tom.lightwaverf;

import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SunriseSunset {

	private final static long DAY = 1000 * 60 * 60 * 24;
	
	private static long timeSinceLastCheck= 0;
	private static String sunsetTime = "20:30";
	private static String sunriseTime = "06:30";
	
	public static final String SUNRISE = "SUNRISE";
	public static final String SUNSET = "SUNSET";

	
	public static String getSunriseTime()
	{
		refreshDataIfRequired();
		return sunriseTime;
	}
	
	
	public static String getSunsetTime()
	{
		refreshDataIfRequired();
		return sunsetTime;
	}
	
	private static void refreshDataIfRequired()
	{
		if (timeSinceLastCheck + DAY < System.currentTimeMillis() || timeSinceLastCheck == 0)
		{
			timeSinceLastCheck = System.currentTimeMillis();
			Calendar calendar = Calendar.getInstance();
			
			try {
				String url = "http://www.earthtools.org/sun/52.470353/-1.747943/" 
						+ calendar.get(Calendar.DATE)+ "/" 
						+ (calendar.get(Calendar.MONTH) + 1) + "/0/0";
						
				System.out.println(url);
				URLConnection conn = new URL(url).openConnection();
							
			    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			    Document doc = db.parse(conn.getInputStream());			
			    NodeList nodes = doc.getElementsByTagName("civil");

			        sunriseTime = ((Element) nodes.item(0)).getTextContent().substring(0, 5);
			        sunsetTime = ((Element) nodes.item(1)).getTextContent().substring(0, 5);

			        System.out.println(sunriseTime);
			        System.out.println(sunsetTime);

				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
	}
	
	
	public static void main(String[] args) {
		getSunriseTime();
	}
}
