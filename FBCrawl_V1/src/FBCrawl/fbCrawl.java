package FBCrawl;
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.junit.Test;
//import static org.junit.Assert.*;
import org.json.simple.JSONValue;

 
public class fbCrawl {
 
	private final String USER_AGENT = "Mozilla/5.0";
	public final String INDEX_PATH = "";
   public String jsonFilePath = "/Users/Simon/git/FBCrawl_v2/FBCrawl_V1/src/test.txt";
 public JSONObject json;

	public static void main(String[] args) throws Exception {
		//initialise object, get accesstoken to access facebook
		fbCrawl http = new fbCrawl();
         String new_accesstoken = http.getAccessToken();
		System.out.println("Testing 1 - Send Http GET request");
		
		
		facebook4J facebook = new facebook4J();
		//first try, copied from internet, doesn't reall work
		//		http.sendGet(new_accesstoken);
		
		//second try from the scratch
		
		//Initialize index
//		HelloLuceneSimon hls = new HelloLuceneSimon();
//		http.GetAndAddToIndex(new_accesstoken, hls, "1542620855978885");
		//http.GetAndAddToIndex(new_accesstoken, hls, "1560282070877106");
		String[] query;
	     query = new String[5];
	     query[0] = "oranje";
	     query[1] = "snow";
	     
//	     	
//	     hls.deleteAndUpdate("1542620855978885");
//	     hls.close();
//		hls.search(query);
//		
		
		
		
		//http.testWriteIndex();
		
		
	
		//System.out.println("\nTesting 2 - Send Http POST request");
		//http.sendPost();
 
	}

	public String getAccessToken() throws Exception {

			String url = "https://graph.facebook.com/oauth/access_token?grant_type=client_credentials&client_id=572563469546265&client_secret=352d672c3e2830660e00a1e39a5a2116";
			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestProperty("User-Agent", USER_AGENT);
 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			      new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result,
			System.out.println(response.toString());
			return (response.toString());
}

 
	// HTTP GET request
	private void sendGet(String new_accesstoken, String id) throws Exception {

		
		String url = "https://graph.facebook.com/";
		url += id;
		url += "?";
		url += new_accesstoken;
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		      new InputStreamReader(con.getInputStream()));
		Reader input =  new InputStreamReader(con.getInputStream());
		
		testWriteIndex(input);
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		
 
		
			try {
		    json = new JSONObject(response.toString());

		    String title = (String) json.get("name");
		    System.out.println(title);

		} catch (JSONException e) {
		    e.printStackTrace();
		}
			
			 
//		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("1406653962962329.json"))) {
//		    oos.writeObject(json);
//		  }
//		FileOutputStream fos = new FileOutputStream("1406653962962329.json");
//		ObjectOutputStream oos = new ObjectOutputStream(fos);
//		ObjectOutputStream.writeObject(json);
	//	testWriteIndex();
	}
	
	private void GetAndAddToIndex(String new_accesstoken, HelloLuceneSimon hls, String id) throws Exception {
		//get data from fb
		String inputLine;
		String url = "https://graph.facebook.com/";
		url += id;
		url += "?";
		url += new_accesstoken;
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		   BufferedReader streamReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		    StringBuilder responseStrBuilder = new StringBuilder();

		    String inputStr;
		    while ((inputStr = streamReader.readLine()) != null)
		        responseStrBuilder.append(inputStr);
		    
		    
		    Reader readerJson = new InputStreamReader(con.getInputStream(), "UTF-8");
		    Object fileObjects= JSONValue.parse(readerJson);
		    JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
		    JSONArray arrayObjects =(JSONArray)fileObjects;
//		BufferedReader in = new BufferedReader(
//		      new InputStreamReader(con.getInputStream()));
//		
//		StringBuffer response = new StringBuffer();
//		 	
// 		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}
//		
//		in.close();
		    
		try {

		hls.addDocument(jsonObject, arrayObjects); 
		   } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		}
	



    //@Test
    public void testWriteIndex(Reader input){
    	
    	 try {      	
            
            
            LuceneIndexWriter lw = new LuceneIndexWriter(INDEX_PATH, jsonFilePath);
            lw.createIndex(input);

            //Check the index has been created successfully
            Directory indexDirectory = FSDirectory.open(new File(INDEX_PATH));
            IndexReader indexReader = DirectoryReader.open(indexDirectory);

            int numDocs = indexReader.numDocs();
            //assertEquals(numDocs, 3);

            for ( int i = 0; i < numDocs; i++)
            {
                Document document = indexReader.document( i);
                System.out.println( "d=" +document);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    	 
    	  
    	    

}
  
}
//private static String readAll(Reader rd) throws IOException {
//    StringBuilder sb = new StringBuilder();
//    int cp;
//    while ((cp = rd.read()) != -1) {
//      sb.append((char) cp);
//    }
//    return sb.toString();
//  }

//    public JSONArray parseJSONFile(){
//
//        //Get the JSON file, in this case is in ~/resources/test.json
//        InputStream jsonFile =  getClass().getResourceAsStream(jsonFilePath);
//        Reader readerJson = new InputStreamReader(jsonFile);
//
//        //Parse the json file using simple-json library
//        Object fileObjects= JSONValue.parse(readerJson);
//        JSONArray arrayObjects=(JSONArray)fileObjects;
//
//        return arrayObjects;
//
//    }
    

//    
//      public static JSONObject readJsonFromUrl(String url) throws IOException, JarException {
//        InputStream is = new URL(url).openStream();
//        try {
//          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//          Map jsonText = readAll(rd);
//          JSONObject json = new JSONObject(jsonText);
//          return json;
//        } finally {
//          is.close();
//        }
//      }
//}