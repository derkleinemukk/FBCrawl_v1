package FBCrawl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class HelloLuceneSimon implements Comparable {
	String input = "";
	IndexWriter w;
	StandardAnalyzer analyzer = null;
	Directory index = null;
    

    IndexWriter indexWriter = null;

    public HelloLuceneSimon()  throws  IOException, ParseException   {
    
    
        StandardAnalyzer public_analyzer = new StandardAnalyzer(Version.LUCENE_40);
        this.analyzer = public_analyzer;

    	// 1. create the index
        readFromFile();

    	IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);

    	this.w = new IndexWriter(index, config);
    	

    	
    }
    
    public void addDocument(JSONObject jsonObject, JSONArray arrayObjects) throws IOException, ParseException, JSONException{
    	int i = 2;
    	if ( i == 2 ){
            	
    	//get data out of json object
    	String title = "";
    	String isbn = "";
    	
    	
    	Document doc = new Document();
    	//add string 1
    	
    	System.out.println("id" + jsonObject.getString("id"));	
    	//System.out.println("name" + jsonObject.getString("name"));
    	//System.out.println("description" + jsonObject.getString("description"));
    	//System.out.println("attending_count" + jsonObject.getString("attending_count"));
		//System.out.println("declined_count" + jsonObject.getString("declined_count"));
		//System.out.println("invited_count" + jsonObject.getString("invited_count"));
		System.out.println("start_time" + jsonObject.getString("start_time"));
	
		
	doc.add(new TextField("description", jsonObject.getString("description"), Field.Store.YES));
		doc.add(new TextField("id", jsonObject.getString("id"), Field.Store.YES));
		doc.add(new TextField("name", jsonObject.getString("name"), Field.Store.YES));
		String startTime = jsonObject.getString("start_time");
		String strOut = startTime.substring(0,10);
		System.out.println(strOut);
		String date = strOut.replace("-", "");
		
		doc.add(new TextField("start_time", date, Field.Store.YES));
		   doc.add(new IntField("attending_count", jsonObject.getInt("attending_count"), Field.Store.YES));
		   doc.add(new IntField("declined_count", jsonObject.getInt("declined_count"), Field.Store.YES));
		   doc.add(new IntField("invited_count", jsonObject.getInt("invited_count"), Field.Store.YES));
		 w.addDocument(doc);
		   
    	
    	}
    	  
    
    
    }
    
    public void search(String[] input2) throws IOException{
    	
   
    	// the "title" arg specifies the default field to use
    	// when no field is explicitly specified in the query.
    	String querystr = input2.length > 0 ? input2[0] : "lucene";
    	
    	Query q = null;
    	try {
    		q = new QueryParser(Version.LUCENE_40, "description", analyzer).parse(querystr);
    	} catch (org.apache.lucene.queryparser.classic.ParseException e) {
    		e.printStackTrace();
    	}
//
    	// 3. search
    	int hitsPerPage = 100;
    	IndexReader reader = DirectoryReader.open(this.index);
    	IndexSearcher searcher = new IndexSearcher(reader);
    	TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
    	
    	searcher.search(q, collector);

       	
    	System.out.println(q);
    	ScoreDoc[] scoreDocs = collector.topDocs().scoreDocs;
    	System.out.println("Found " + scoreDocs.length + " hits.");
    	
		for (int i = 0; i < scoreDocs.length; ++i) {
			int docId = scoreDocs[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("id") + "\t" + d.get("name") + d.get("attending_count") + "\t" + d.get("attending_count") + "\t"+ d.get("declined_count") + "\t"+ d.get("invited_count") + "\t"+ d.get("start_time") + "\t");

		}
		
		
		
		Sort sorter = new Sort(); // new sort object

    	String field = "attending_count"; 
    	Type type = Type.INT;
    	boolean descending = true; 

    	SortField sortField = new SortField(field, type, descending);
    	sorter.setSort(sortField, SortField.FIELD_SCORE);
    	
		
		System.out.println("Sorted");    	
		TopFieldDocs docs = searcher.search(q,20, sorter);
		ScoreDoc[] hits= docs.scoreDocs;

		for (int j=0; j < hits.length; j++) {
			int docId = hits[j].doc;
			Document d = searcher.doc(docId);
			System.out.println((j+ 1) + ". " + d.get("id") + "\t" + d.get("name") + d.get("attending_count") + "\t" + d.get("attending_count") + "\t"+ d.get("declined_count") + "\t"+ d.get("invited_count") + "\t"+ d.get("start_time") + "\t");
		}
		System.out.println("yep");

    	

    	
    }  
    
    public void countDailyEvents(String[] input2) throws IOException{
    	
    	   
    	// the "title" arg specifies the default field to use
    	// when no field is explicitly specified in the query.
    	String querystr = input2.length > 0 ? input2[0] : "lucene";
    	
    	Query q = null;
    	try {
    		q = new QueryParser(Version.LUCENE_40, "start_time", analyzer).parse(querystr);
    	} catch (org.apache.lucene.queryparser.classic.ParseException e) {
    		e.printStackTrace();
    	}
//
    	// 3. search
    	IndexReader reader = DirectoryReader.open(this.index);
    	IndexSearcher searcher = new IndexSearcher(reader);
    	TotalHitCountCollector totalHitCountCollector = new TotalHitCountCollector();
    	  searcher.search(q,totalHitCountCollector);
    	  int dailyEvents =  totalHitCountCollector.getTotalHits();
    	  
    		  	System.out.println("Date: " + querystr + " events: " + dailyEvents);

    	
    }  
    

    //public void sortedSearch(String[] input2)     	
    	//throws IOException, ParseException{
    	 		
    	
    //	IndexReader reader = DirectoryReader.open(this.index);
    	//IndexSearcher searcher = new IndexSearcher(reader);
    	  //Query q=new MatchAllDocsQuery();
    	
    	  //SortField sortField= new SortField("attending_count",SortField.INT,true);
//    	  Sort sort=new Sort(sortField);
//    	  TopFieldDocs docs = searcher.search(q,10,sort);
//    	  ScoreDoc[] hits=docs.scoreDocs;
//    	  for (int i=0; i < hits.length; i++) {
//    	    System.out.println(hits[i]);
//    	  }
//
//    }
    
    public void sortAttendance(){
    	Sort sorter = new Sort(); // new sort object

    	String field = "attending_count"; 
    	Type type = Type.INT;
    	boolean descending = true; 

    	SortField sortField = new SortField(field, type, descending);
    	sorter.setSort(sortField, SortField.FIELD_SCORE);
    	
    	
    }
    public void close() throws IOException{
    	
    	w.close();
    }
    
    
    public void readFromFile() throws IOException{
    	
    	     	
      	 File path = new File("uniqueName");
       	Directory public_index = new MMapDirectory(path);
       	this.index = public_index;
      	
    	
    }
    
//    public void deleteAndUpdate(String id) throws IOException{
//    	
//String querystr = id;
//    	
//    	Query q = null;
//    	try {
//    		q = new QueryParser(Version.LUCENE_40, "id", analyzer).parse(querystr);
//    	} catch (org.apache.lucene.queryparser.classic.ParseException e) {
//    		e.printStackTrace();
//    	}
//    	
//    	w.deleteDocuments(q);
//    }

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
    
    
}