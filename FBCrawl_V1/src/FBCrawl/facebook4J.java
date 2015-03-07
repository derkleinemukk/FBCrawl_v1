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
          .setOAuthAccessToken("CAACEdEose0cBALIZCZAzoB8Y9ZCwwlutcgV5AtYcrqyuobsBRLgDybIl2gRI4mdQzlQsfXVZCLA8RimUQo5rljHMSPGuzDH50UpUZCOoHGFuTxMtsn8iLFKvB45K4MC8GZA7lIVfn5TviAMOtSfXBXrR5MR0JdIFQWU3VaRhYGAIYjSdSEqQeJ0rpNkW2b52Vrg5HTowDVSUuZAuBxRawtp")
          .setOAuthPermissions("email,publish_stream");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        
    	int lowerBoundary = (int) timeFrom;
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
		String file_name= query+timeFrom+timeTo+".txt";
		PrintWriter writer = new PrintWriter(file_name, "UTF-8");
		
		while(endDate > lowerBoundary){
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
        
        
        //upperBoundary = lowerBoundary + 86400;
        help++;
        }
		

               
    }
}