package FBCrawl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ListIterator;
import java.util.Random;

import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import org.json.simple.JSONValue;

import facebook4j.*;
import facebook4j.conf.ConfigurationBuilder;

public class facebook4J{
    private static final long serialVersionUID = -7453606094644144082L;
    Integer[] idList = new Integer[50000];
    
    public Integer[] returnList(){
    	return idList;
    }
    
    public void IDretrieval(String query, long timeFrom, long timeTo) throws FileNotFoundException, UnsupportedEncodingException, FacebookException, MalformedURLException, JSONException {
      
       
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthAppId("657861844318963")
          .setOAuthAppSecret("52ff9596f18ed0ee9ae7992fee481246")
          .setOAuthAccessToken("CAACEdEose0cBAIJZCZAsMaPi2AW521vJeLZAlSO7KeGZClRRuxT1D2OetLfZCyt46YV2nJP3o8s7Fqouuogy7052ZCLdvT24MY1yDJhTZA6MJSKRFy4wnyUtSvUsvAXSAdVGJRSAa0hozQMPalTtlBZAJsnFvUuHyIl2kdU3o3Kr4V0m00or7hFZC1EJZAnZCUXv08M6slNnDKKJZBgc73BymLvCvp2dd32RTvQZD")
          .setOAuthPermissions("email,publish_stream");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        
        String location = null;
        Location Secondlocation = null;
        String PlaceLocation = null;
        
    	int lowerBoundary = (int) timeFrom;
    	int lastBoundary = 0;
		int endDate = (int) timeTo;
		ResponseList<Event> results;
		ResponseList<Place> results2;
		Double city;
		String country;
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
        
        Boolean isLocation = true;
    	
       
        //My creation
        int i2=0;
        while(i2<results.size())
        {	
        	String CountryLocation = "";
        	String resultID = results.get(i2).getId();
        	idList[i2]=Integer.parseInt(resultID);
        	
        try{
        
        		location = results.get(i2).getLocation();
        		
        		if(location!=null){
        		results2 = facebook.searchPlaces(location);
            			
        		PlaceLocation = results2.get(0).getLocation().getCity();
    			CountryLocation = results2.get(0).getLocation().getCountry();
        			if(CountryLocation!=null){
		            	if(!CountryLocation.contains("")&&!CountryLocation.equals("Netherlands")){
		            		isLocation =false;
		            	}else{
		            		isLocation = true;	
		            	}
            		}else if(PlaceLocation!=null){
            			if(!PlaceLocation.contains("")&&!PlaceLocation.contains(query)){
		            		isLocation =false;
		            	}else{
		            		isLocation = true;
		            	}
            		}
            	}
            	else{
        			isLocation = false;
        		}
            	
            	
        		
        	}catch(IndexOutOfBoundsException e){
        		System.err.println();
        	}
        	if(isLocation){
        		
        		System.out.println(resultID+" "+location+" "+PlaceLocation);
            	writer.println(resultID);
            		
        	}
        	try {
        		
        		
        		 ResponseList<RSVPStatus> responseList = facebook.getRSVPStatusInAttending(results.get(i2).getId()) ;
        		 int i3 = 0;
        		 while(i3<responseList.size())
        		 {
        		 
        		 String name = responseList.get(i3).getName();
        		 System.out.println(name);
        		 String[] words = name.split(" ");  
        		 readGender(words[0]);
        		 
        		 i3++;
        		 }
        		//factory.createRSVPStatusList(get(buildURL(results.get(i2).getId(), "noreply")));
        	} catch (NullPointerException e) {
        	    System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        	}
        	catch (FacebookException ef){
        		System.err.println("IndexOutOfBoundsException: " + ef.getMessage());
        	}
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
    
   private void readGender(String name) throws JSONException, MalformedURLException {



    	  try {
    		  URL url = null;
    		  
    		  Random rand = new Random();

    		    // nextInt is normally exclusive of the top value,
    		    // so add 1 to make it inclusive
    		    int randomNum = rand.nextInt((3 - 1) + 1) + 1;
    if (randomNum == 1){
    		   url = new URL("https://gender-api.com/get?name="+ name);}
    else if (randomNum == 2){
    		   url = new URL("http://api.namsor.com/onomastics/api/json/gendre/"+ name +"/a");}
    else if (randomNum == 3){
    	     url = new URL("https://api.genderize.io/?name=" + name);}
    	     

    	  

    	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();



    	    if (conn.getResponseCode() != 200) {

    	     // throw new RuntimeException("Error: " + conn.getResponseCode());

    	    }



    	    InputStreamReader input = new InputStreamReader(conn.getInputStream());

    	    BufferedReader reader = new BufferedReader(input);
    	    
    	    StringBuilder responseStrBuilder = new StringBuilder();

		    String inputStr;
		    while ((inputStr = reader.readLine()) != null)
		        responseStrBuilder.append(inputStr);
		    
		    
		    Reader readerJson = new InputStreamReader(conn.getInputStream(), "UTF-8");
		    Object fileObjects= JSONValue.parse(readerJson);
		    JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
		    JSONArray arrayObjects =(JSONArray)fileObjects;

 
		  try{
    	    	    String gender = (String) jsonObject.get("gender");
    	    	    System.out.println("Gender: " + gender); // Gender: male
		    }
		  catch(ClassCastException e){
			  System.out.println("Gender: " + e.getMessage()); // Gender: male
		  };


    	   



    	    conn.disconnect();



    	    } catch (IOException e) {

    	      e.printStackTrace();

    	    }
   }
}



    	  

