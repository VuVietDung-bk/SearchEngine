package api;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Result {
	
	private Long totalItems;
	private Long totalPages;
	private Long currentPage;
	private List<Article> articles;
	
	public Result(JSONObject jsonObject) {
		setTotalItems((Long) jsonObject.get("totalItems"));
		setTotalPages((Long) jsonObject.get("totalPages"));
		setCurrentPage((Long) jsonObject.get("currentPage"));
		
		List<Article> articles = new ArrayList<>();
		JSONArray jsonArticles = (JSONArray) jsonObject.get("articles");
		for(Object obj : jsonArticles) {
			Article article = new Article((JSONObject) obj);
        	articles.add(article);
		}
		setArticles(articles);
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
}
