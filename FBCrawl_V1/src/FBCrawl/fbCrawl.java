package FBCrawl;
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.InputStreamReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.lucene.store.FSDirectory;
import javax.net.ssl.HttpsURLConnection;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
//import org.junit.Test;
//import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
 
public class fbCrawl {
 
	private final String USER_AGENT = "Mozilla/5.0";
	public final String INDEX_PATH = "src";
    public final String JSON_FILE_PATH = "\test.json";
 
	public static void main(String[] args) throws Exception {
 
		fbCrawl http = new fbCrawl();
 
	
        String new_accesstoken = http.getAccessToken();
		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet(new_accesstoken);
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
	private void sendGet(String new_accesstoken) throws Exception {

 
		String url = "https://graph.facebook.com/1406653962962329?";
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
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		
		try (PrintStream out = new PrintStream(new FileOutputStream("1406653962962329.txt"))) {
		    out.print(response.toString());

	} 


	}


    //@Test
    public void testWriteIndex(){
    	
    	 try {      	
            
            
            LuceneIndexWriter lw = new LuceneIndexWriter(INDEX_PATH, JSON_FILE_PATH);
            lw.createIndex();

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