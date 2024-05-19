package application;

import java.time.LocalDate;

import api.APIController;
import api.Result;
import utiltabs.ListTab;

public class DataLoader {

	private int currentPage = 0;
	private String keywords, tag, sort, resource, type, direction;
	private LocalDate fromDate, toDate;
	private Result result;
	
	public void getAllTag() {
		//URL url = apiURL + "tags/";
	}
	
	public DataLoader() {
		
	}
	
	public void load(String keywords,String tag, String sort, String resource, String type, 
			LocalDate fromDate, LocalDate toDate, String direction) throws Exception {
		//send request to server
		this.keywords = keywords;
		this.tag = tag;
		this.sort = sort;
		this.resource = resource;
		this.type = type;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.direction = direction;
		fetch();
	}
	
	private void fetch() throws Exception {
		if(!keywords.equals("") || fromDate != null || toDate != null) {
			result = APIController.getResultingArticles(keywords, resource, 
					type, fromDate, toDate, tag, currentPage, ListTab.ARTICLES_PER_PAGE, sort, direction);
			return;
		}
		if(!tag.equals("All") && resource.equals("All") && type.equals("All")) {
			result = APIController.getArticlesByTag(tag, currentPage, ListTab.ARTICLES_PER_PAGE, sort, direction);
			return;
		}
		if(!resource.equals("All") && tag.equals("All") && type.equals("All")) {
			result = APIController.getArticlesByResource(resource, currentPage, ListTab.ARTICLES_PER_PAGE, sort, direction);
			return;
		}
		result = APIController.getResultingArticles(keywords, resource, 
				type, fromDate, toDate, tag, currentPage, ListTab.ARTICLES_PER_PAGE, sort, direction);
	}

	public int getNumberOfPage() {
		if(result == null) return 1;
		return result.getTotalPages().intValue();
	}
	
	public void setCurrentPage(int page) throws Exception {
		currentPage = page;
		fetch();
	}
	
	public String getArticleTitle(int i) {
		if(result == null || result.getArticles() == null || i >= result.getArticles().size()){
			return new String("");
		}
		return result.getArticles().get(i).getArticleTitle();
	}
	
	public String getArticleResource(int i) {
		if(result == null || result.getArticles() == null || i >= result.getArticles().size()){
			return new String("");
		}
		return result.getArticles().get(i).getResource();
	}
	
	public String getArticleContent(int i){
		if(result == null || result.getArticles() == null || i >= result.getArticles().size()){
			return new String("");
		}
		String content = result.getArticles().get(i).getArticleSummary();
		if (content == null || content.equals("")) {
			content = new String("No description");
		} else if(content.length() > 120) {
			content = content.substring(0, 117);
			content += "...";
		}
		return content;
	}
	
	public String getArticleID(int i) {
		if(result == null || result.getArticles() == null || i >= result.getArticles().size()){
			return new String("");
		}
		return result.getArticles().get(i).getId().toString();
	}
	
	public String[] getRelevantArticle(String url) {
		String[] relArt = new String[5];
		relArt[0] = "Article " + Integer.toString((int)(Math.random() * 1000));
		relArt[1] = "Article " + Integer.toString((int)(Math.random() * 1000));
		relArt[2] = "Article " + Integer.toString((int)(Math.random() * 1000));
		relArt[3] = "Article " + Integer.toString((int)(Math.random() * 1000));
		relArt[4] = "Article " + Integer.toString((int)(Math.random() * 1000));
		return relArt;
	}

	public String getArticleTitleFromID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getArticleContentFromID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
