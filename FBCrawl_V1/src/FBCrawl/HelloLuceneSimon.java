package FBCrawl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HelloLuceneSimon {
	String input = "";
	IndexWriter w;
	StandardAnalyzer analyzer = null;
Directory index = null;
    

    IndexWriter indexWriter = null;

    public HelloLuceneSimon() throws  IOException, ParseException   {
    
    
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
    	
		doc.add(new TextField("description", jsonObject.getString("description"), Field.Store.YES));
		System.out.println(jsonObject.getString("description"));
		//add string 2
		doc.add(new TextField("id", jsonObject.getString("id"), Field.Store.YES));
		// use a string field for isbn because we don't want it tokenized
		System.out.println(jsonObject.getString("id"));
		doc.add(new TextField("name", jsonObject.getString("name"), Field.Store.YES));
		System.out.println(jsonObject.getString("name"));
		doc.add(new TextField("start_time", jsonObject.getString("start_time"), Field.Store.YES));
		
		w.addDocument(doc);
    	
    	}
    	  
    
    
    }
    
    public void search(String[] searchTerm) throws IOException{
    	
   
    	// the "title" arg specifies the default field to use
    	// when no field is explicitly specified in the query.
    	String querystr = searchTerm.length > 0 ? searchTerm[0] : "lucene";
    	
    	Query q = null;
    	try {
    		q = new QueryParser(Version.LUCENE_40, "description", analyzer).parse(querystr);
    	} catch (org.apache.lucene.queryparser.classic.ParseException e) {
    		e.printStackTrace();
    	}
//
    	// 3. search
    	int hitsPerPage = 10;
    	IndexReader reader = DirectoryReader.open(this.index);
    	IndexSearcher searcher = new IndexSearcher(reader);
    	TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
    	searcher.search(q, collector);
    	System.out.println(q);
    	ScoreDoc[] hits = collector.topDocs().scoreDocs;
    	
    	System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("id") + "\t" + d.get("name"));
		}
    	
    	
    }  
    
    public void close() throws IOException{
    	
    	w.close();
    }
    
    
    public void readFromFile() throws IOException{
    	
    	     	
      	 File path = new File("uniqueName");
       	Directory public_index = new MMapDirectory(path);
       	this.index = public_index;
      	
    	
    }
    
    public void deleteAndUpdate(String id) throws IOException{
    	
String querystr = id;
    	
    	Query q = null;
    	try {
    		q = new QueryParser(Version.LUCENE_40, "id", analyzer).parse(querystr);
    	} catch (org.apache.lucene.queryparser.classic.ParseException e) {
    		e.printStackTrace();
    	}
    	
    	w.deleteDocuments(q);
    }
    
    
}
