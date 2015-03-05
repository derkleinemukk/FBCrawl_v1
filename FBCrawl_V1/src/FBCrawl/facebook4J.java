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

    public void IDretrieval(String query) throws FileNotFoundException, UnsupportedEncodingException, FacebookException {
      
       
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthAppId("657861844318963")
          .setOAuthAppSecret("52ff9596f18ed0ee9ae7992fee481246")
          .setOAuthAccessToken("CAACEdEose0cBACt6eqE9D1yhlKafF5DvzNihvGcf3T2ChY5wtQLKzxSZBZCwKpVkpEz0nSvSt1J5yc0LpT1EuXXHuVuD8PMKw5RGYBTew48CRruCeWBiAq3aZCupz3zc33tEJP4Aj623o3ECp8LoYMFi8iYhv19KSe1sjIRA1XlG3itr4H1hA8pDrZBZAZCsWGmneyoLcAyn2i6pg4TzdW")
          .setOAuthPermissions("email,publish_stream");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        
    	int lowerBoundary = 1422748800;
		int upperBoundary = lowerBoundary + 86400;
		ResponseList<Event> results;
		String lowerBoundaryString;
		String upperBoundaryString;
		int help = 0;
		int i=0;
		
		String file_name= query+".txt";
		PrintWriter writer = new PrintWriter(file_name, "UTF-8");
		
		while(lowerBoundary < 1423008000 ){
			 lowerBoundaryString = Integer.toString(lowerBoundary);
			 upperBoundaryString = Integer.toString(upperBoundary);
			 
	   System.out.println(lowerBoundaryString);
        results = facebook.searchEvents(query, new Reading().since(lowerBoundaryString).until(upperBoundaryString));
        boolean a = true;
        System.out.println(results.isEmpty());
        System.out.println(results.getCount());
        if( a == results.isEmpty()){
        	System.out.println("we're screwed");
        }
        else {;
        
        System.out.println(results.toString());
        int counter = 0;

      
//        while( counter < 4){ 
//      
//        writer.println(results.get(counter).getId());
//        System.out.println(results.get(counter).getId());
//        //ListIterator<Event> list = results.listIterator();
//        counter++;
//        }
   
        }
        i++;
        lowerBoundary = upperBoundary;
        upperBoundary = lowerBoundary + 86400;
        help++;
        }
		
		writer.close();
		
		
//        int i = results.getCount();
     
//int i = 0;

//     while (i < results.size()){
//    	 eventID = results.get(i).getId();
//    	 System.out.println(eventID);
//    	 i++;
//     }
     System.out.println("page1done");
     
//      Paging<Event> page1 = results.getPaging();
////      ResponseList<Event> page2 = facebook.fetchNext(page1);
//      ResponseList<Event> page0 = facebook.fetchPrevious(page1);
//      
////      i = 0;
////      while (i < page2.size()){
////     	 eventID = page2.get(i).getId();
////     	 System.out.println(eventID);
////     	 i++;
////      }
//      i = 0;
//      while (i < page0.size()){
//      	 eventID = page0.get(i).getId();
//      	 System.out.println(eventID);
//      	 i++;
//       }
//      System.out.println("page0done");
               
    }
}