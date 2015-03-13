package FBCrawl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.ListIterator;

import facebook4j.*;
import facebook4j.conf.ConfigurationBuilder;

public class facebook4J{
    private static final long serialVersionUID = -7453606094644144082L;

    public void IDretrieval(String query, long timeFrom, long timeTo) throws FileNotFoundException, UnsupportedEncodingException, FacebookException {
      
       
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthAppId("657861844318963")
          .setOAuthAppSecret("52ff9596f18ed0ee9ae7992fee481246")
          .setOAuthAccessToken("CAACEdEose0cBANxEo6gOHVwcTobZBqv9ZBr7IdM3vLfqlmaopZCC9pW3z8HwZAsXKROZCovpvhROAzSQOorvGpWwBcw4ZAkjm807YCpdUpALwub9Msp1DbRdslQ3lAGatHLTI72CrXTXDcrPnQ0uEl9BTu2ZA9HuARuJLyKWHsjAhJSMSjLfjVMImGTGdnaQfq7kvaP8EKmHZCaC1xPYPN2ckxE0H5a0L40ZD")
          .setOAuthPermissions("email,publish_stream");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        
    	int lowerBoundary = (int) timeFrom;
    	int lastBoundary = 0;
		int endDate = (int) timeTo;
		ResponseList<Event> results;
		String lowerBoundaryString;
		String ImplEndDate;
		int help = 0;
		int i=0;
		long tsEvent = 0;
		int EventCounter = 0;
		java.util.Date newlastEventDate = null;
		long newlastEvent = 0;
		long oldlastEvent = 0;
		String file_name= query+".txt";
		PrintWriter writer = new PrintWriter(file_name, "UTF-8");
		
		while(endDate > lowerBoundary){
			if(lowerBoundary == lastBoundary){
				System.out.println("end");
				break;
			}else{
			System.out.println("end:" +endDate);
			System.out.println("lower:"+lowerBoundary);
			ImplEndDate = Integer.toString(endDate);
			// upperBoundaryString = Integer.toString(upperBoundary);
			 
			 System.out.println("timestamp: "+ImplEndDate);
		        results = facebook.searchEvents(query, new Reading().until(ImplEndDate));
        boolean a = true;

        if( a == results.isEmpty()){
        	System.out.println("we're screwed");
        	break;
        }
        else {
        
        
        //My creation
        int i2=0;
        while(i2<results.size())
        {
        	String resultID = results.get(i2).getId();
        	System.out.println(resultID);
        	writer.println(resultID);
        	i2++;
        	EventCounter++;
        }
        
        
        Date lastEvent = results.get(i2-1).getStartTime();
        tsEvent = new Long(lastEvent.getTime()/1000);
        System.out.println("last timestamp:"+tsEvent);
        writer.println("last timestamp :"+tsEvent);
        endDate = (int) tsEvent;
        
        }
        System.out.println("done");
        writer.println("Number of ids:" +EventCounter);
		writer.close();
        
        i++;
        
        lastBoundary=lowerBoundary;
        //upperBoundary = lowerBoundary + 86400;
        help++;
        }
		
		}
               
    }
}