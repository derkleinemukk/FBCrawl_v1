package FBCrawl;

import java.io.IOException;





import java.net.URL;
import java.util.ListIterator;

import facebook4j.*;
import facebook4j.conf.ConfigurationBuilder;

public class facebook4J{
    private static final long serialVersionUID = -7453606094644144082L;

    public static void main(String args[]) throws IOException, FacebookException {
      
       
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthAppId("572563469546265")
          .setOAuthAppSecret("352d672c3e2830660e00a1e39a5a2116")
          .setOAuthAccessToken("CAACEdEose0cBAHiLk1e2AlbmPHzte7IkgvpdAqMw6zFrbTwMoUjCWszpPErgZBQqHE3fNWmo6ZBY9G4yISPSp1F5LjFdd0BlkKkFuu951GAASxXZCuQotkfuSgojOwEZAgYfStcakbjJlWBG95pjKZB2yHxaZCVAyZCtG8yqNbQzAG7RP7aLx6MytZAUPo3LZBrYjgnPy7wlvWVF40N13GeZCA")
          .setOAuthPermissions("email,publish_stream");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();
        
    	int lowerBoundary = 1388534400;
		int upperBoundary = lowerBoundary + 604800;
		ResponseList<Event> results;
		String lowerBoundaryString;
		String upperBoundaryString;
		int help = 0;
		while(lowerBoundary < 1425440000 ){
			 lowerBoundaryString = Integer.toString(lowerBoundary);
			 upperBoundaryString = Integer.toString(upperBoundary);
        results = facebook.searchEvents("Amsterdam", new Reading().since(lowerBoundaryString).until(upperBoundaryString));
        System.out.println(results.size());
        //ListIterator<Event> list = results.listIterator();
        String eventID;
        
        lowerBoundary = upperBoundary;
        upperBoundary = lowerBoundary + 604800;
        help++;
        }
		System.out.println(help);
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