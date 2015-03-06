package FBCrawl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
          .setOAuthAccessToken("CAACEdEose0cBALE7BUdNsrNHlcZCAdPt0Qzp3BlZAZByCZBXUuVvNLZBuKYZBUYH73XNDQZBeuyuvfAEFVJ3GfOIkWfk7x6LJElQLRf2xPrKZARfs7l0cLDM8vE0VcoKtpxXdWglXlOlRNy1tLlx359QnK3wZCh3KAUZCfiESt6TICO2uo2U4nZCm3EZByp4GSrIDJzQO72SC4wIm2wHv4AohSLh")
          .setOAuthPermissions("email,publish_stream");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        
    	int lowerBoundary = (int) timeFrom;
		int endDate = (int) timeTo;
		ResponseList<Event> results;
		String lowerBoundaryString;
		String upperBoundaryString;
		int help = 0;
		int i=0;
		long tsEvent = 0;
		int EventCounter = 0;
		java.util.Date newlastEventDate = null;
		long newlastEvent = 0;
		long oldlastEvent = 0;
		String file_name= query+".txt";
		PrintWriter writer = new PrintWriter(file_name, "UTF-8");
		
		while(lowerBoundary < endDate ){
			 lowerBoundaryString = Integer.toString(lowerBoundary);
			// upperBoundaryString = Integer.toString(upperBoundary);
			 
			 System.out.println("timestamp: "+lowerBoundaryString);
		        results = facebook.searchEvents(query, new Reading().since(lowerBoundaryString));
        boolean a = true;

        if( a == results.isEmpty()){
        	System.out.println("we're screwed");
        }
        else {;
        
        
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
        
        System.out.println("newlastevent" + newlastEvent);
        System.out.println("oldlastevent" + oldlastEvent);
        if (oldlastEvent == newlastEvent && oldlastEvent != 0){
        	 System.out.println("were in the if statement now");
             
        	writer.println("Number of ids:" +EventCounter);
    		writer.close();
    		break;
        }
        else{
        	if (newlastEventDate != null){
        oldlastEvent = newlastEventDate.getTime()/1000L;
        	}
        newlastEventDate = results.get((i2-1)).getStartTime();
        
       
        tsEvent = new Long(newlastEventDate.getTime()/1000L);
        newlastEvent = tsEvent;
        System.out.println("last timestamp:"+tsEvent);
        
        }
        
        i++;
        
        
        //upperBoundary = lowerBoundary + 86400;
        help++;
        }
        }
	
		

               
    }
}