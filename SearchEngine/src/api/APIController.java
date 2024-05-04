package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class APIController {
	
	private static String hostAddress = "http://localhost:5454/api/";
	
	public static String[] getAllTag() throws Exception {
		String urlString = hostAddress + "tags";
		//Nhận JSONArray từ local host
		JSONArray jsonArray = getJSONArray(urlString);
		
        List<String> tags = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;
            String tag = (String) jsonObj.get("name");
            tags.add(tag);
            
        }
        
        tags.sort(null);
        
        tags.add(0, "All");
        
        return tags.toArray(new String[0]);
	}
	
	public static String[] getAllResource() throws Exception {
		String urlString = hostAddress + "resources";
		//Nhận JSONArray từ local host
		JSONArray jsonArray = getJSONArray(urlString);
		
        List<String> resources = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;
            String res = (String) jsonObj.get("resourceName");
            resources.add(res);
        }
        resources.sort(null);
        
        resources.add(0, "All");
        
        return resources.toArray(new String[0]);
	}
	
	public static List<Article> getSimilarArticles(String id) throws Exception {
		String urlString = hostAddress + "articles/" + id + "/similar";
		
		//Nhận JSONArray từ local host
		JSONArray jsonArray = getJSONArray(urlString);
		
        List<Article> articlesList = new ArrayList<>();
        for(Object obj : jsonArray) {
        	Article article = new Article((JSONObject) obj);
        	articlesList.add(article);
        }

		return articlesList;
	}
	
	public static List<Article> getResultingArticles(String keyword, String resource, String type, LocalDate startDate, LocalDate endDate,
													String tag, int page, int size, String sortBy, String direction) throws Exception {
		//Nhận List<Article> từ local host
        List<Article> articlesList = getArticles("search" ,keyword, resource, type, startDate, endDate, tag, page, size, sortBy, direction);
		return articlesList;
	}

	public static List<Article> getArticlesByTag(String tag, int page, int size, String sortBy, String direction) throws Exception {
		List<Article> articlesList = getArticles("tag" , "", "All", "All", null, null, tag, page, size, sortBy, direction);
		return articlesList;
	}
	
	public static List<Article> getArticlesByResource(String resource, int page, int size, String sortBy, String direction) throws Exception {
		List<Article> articlesList = getArticles("resource" , "", resource, "All", null, null, "All", page, size, sortBy, direction);
		return articlesList;
	}
	
	private static List<Article> getArticles(String pathSegment,String keyword, String resource, String type, LocalDate startDate, LocalDate endDate,
											String tag, int page, int size, String sortBy, String direction) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		String urlString = hostAddress + "articles/";
		urlString += pathSegment + "?";
		if(!keyword.equals("")) urlString += "key=" + keyword + "&";
		if(!resource.equals("All")) urlString += "resource=" + resource + "&";
		if(!type.equals("All")) urlString += "type=" + type + "&";
		if(startDate != null) urlString += "startDate=" + startDate.format(formatter) + "&";
		if(endDate != null) urlString += "endDate=" + endDate.format(formatter) + "&";
		if(!tag.equals("All")) urlString += "tag=" + tag + "&";
		urlString += "page=" + page + "&";
		urlString += "size=" + size + "&";
		urlString += "sortBy=" + sortBy + "&"; //creationDate         
		urlString += "direction=" + direction; //desc
		
		//Nhận JSONArray từ local host
		JSONArray jsonArray = getJSONArray(urlString);
		
        List<Article> articlesList = new ArrayList<>();
        for(Object obj : jsonArray) {
        	Article article = new Article((JSONObject) obj);
        	articlesList.add(article);
        }

		return articlesList;
	}
	
	private static JSONArray getJSONArray(String urlString) throws Exception {
		URI uri = new URI(urlString);
		URL url = uri.toURL();
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
        
        // Nhận dữ liệu từ local host
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        //Phân tích JSON string thành JSONArray
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.toString());

        connection.disconnect();
        return jsonArray;
	}
	
	public static Article getArticleById(int i) throws Exception {
		String urlString = hostAddress + "articles/" + i;
		URI uri = new URI(urlString);
		URL url = uri.toURL();
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
        
        // Nhận dữ liệu từ local host
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        //Phân tích JSON string thành JSONObject
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());

        connection.disconnect();
        
        return new Article(jsonObject);
	}
	
	public static void main(String[] args) throws Exception {
		List<Article> articlesList = getResultingArticles("Spot", "All", "All", null, null, "All", 0, 15, "creationDate", "desc");
		//List<Article> articlesList = getArticlesByTag("bitcoin", 0, 15, "creationDate", "desc");
		/*String[] tags = getAllResource();
		for(String tag : tags) {
			System.out.println(tag);
		}*/
		for(Article article : articlesList) {
			System.out.println(article.getArticleLink());
		}
		System.out.println("Done!!!");
	}
}
