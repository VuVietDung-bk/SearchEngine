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
	
	public static Article getArticleById(int i) throws Exception {
		String urlString = hostAddress + "articles/" + i;
        
        //Phân tích JSON string thành JSONObject
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(getJSONString(urlString));
        
        return new Article(jsonObject);
	}
	
	public static String[] getAllTag() throws Exception {
		String urlString = hostAddress + "tags";
		
		//Nhận JSONArray từ local host
		JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(getJSONString(urlString));
		
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
		JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(getJSONString(urlString));
		
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
		JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(getJSONString(urlString));
		
        List<Article> articlesList = new ArrayList<>();
        for(Object obj : jsonArray) {
        	Article article = new Article((JSONObject) obj);
        	articlesList.add(article);
        }

		return articlesList;
	}
	
	public static Result getResultingArticles(String keyword, String resource, String type, LocalDate startDate, LocalDate endDate,
													String tag, int page, int size, String sortBy, String direction) throws Exception {
        return getArticles("search" ,keyword, resource, type, startDate, endDate, tag, page, size, sortBy, direction);
	}

	public static Result getArticlesByTag(String tag, int page, int size, String sortBy, String direction) throws Exception {
		return getArticles("tag" , "", "All", "All", null, null, tag, page, size, sortBy, direction);
	}
	
	public static Result getArticlesByResource(String resource, int page, int size, String sortBy, String direction) throws Exception {
		return getArticles("resource" , "", resource, "All", null, null, "All", page, size, sortBy, direction);
	}
	
	private static Result getArticles(String pathSegment,String keyword, String resource, String type, LocalDate startDate, LocalDate endDate,
											String tag, int page, int size, String sortBy, String direction) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		String urlString = hostAddress + "articles/";
		urlString += pathSegment + "?";
		if(keyword != "") urlString += "key=" + keyword + "&";
		if(resource != "All") urlString += "resource=" + resource + "&";
		if(type != "All") urlString += "type=" + type + "&";
		if(startDate != null) urlString += "startDate=" + startDate.format(formatter) + "&";
		if(endDate != null) urlString += "endDate=" + endDate.format(formatter) + "&";
		if(tag != "All") urlString += "tag=" + tag + "&";
		urlString += "page=" + page + "&";
		urlString += "size=" + size + "&";
		urlString += "sortBy=" + sortBy + "&"; //creationDate         
		urlString += "direction=" + direction; //desc
		
		//Nhận kết quả từ local host
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(getJSONString(urlString));

		return new Result(jsonObject);
	}
	
	private static String getJSONString(String urlString) throws Exception {
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
        connection.disconnect();
        
        return response.toString();
	}
	
	public static void main(String[] args) throws Exception {
		//Result result = getResultingArticles("Spot", "All", "All", null, null, "All", 1, 15, "creationDate", "desc");
		Result result = getArticlesByResource("CNBC", 0, 15, "creationDate", "desc");
		
		System.out.println(result.getTotalPages());
		for(Article article : result.getArticles()) {
			System.out.println(article.getArticleTitle());
		}
		
//		Article article = getArticleById((long) 100);
//		System.out.println(article.getArticleTitle());
		
		System.out.println("Done!!!");
	}
}
