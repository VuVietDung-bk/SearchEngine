package api;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Article {
	
	private Long id;
	private String articleLink;
	private String resource;
	private String articleType;
	private String articleSummary;
	private String articleTitle;
	private String detailedArticleContent;
	private LocalDate creationDate;
	private Set<String> hashtags;
	private String authorName;
	
	public Article(JSONObject obj) {
		setId((Long) obj.get("id"));
		setArticleLink((String) obj.get("articleLink"));
		setArticleType((String) obj.get("articleType"));
		setArticleSummary((String) obj.get("articleSummary"));
		setArticleTitle((String) obj.get("articleTitle"));
		setDetailedArticleContent((String) obj.get("detailedArticleContent"));
		setAuthorName((String) obj.get("authorName"));
		
		JSONObject jsonResource = (JSONObject) obj.get("resource");
		setResource((String) jsonResource.get("resourceName"));
		
		String dateString = (String) obj.get("creationDate");
		LocalDate creationDate = LocalDate.parse(dateString);
		setCreationDate(creationDate);
		
		Set<String> hashtags = new HashSet<>();
		JSONArray arr = (JSONArray) obj.get("hashtags");
		for(Object tagObj : arr) {
			JSONObject jsonTag = (JSONObject) tagObj;
			hashtags.add((String) jsonTag.get("name"));
		}
		setHashtags(hashtags);
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArticleLink() {
		return articleLink;
	}

	public void setArticleLink(String articleLink) {
		this.articleLink = articleLink;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleSummary() {
		return articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getDetailedArticleContent() {
		return detailedArticleContent;
	}

	public void setDetailedArticleContent(String detailedArticleContent) {
		this.detailedArticleContent = detailedArticleContent;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Set<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
}

