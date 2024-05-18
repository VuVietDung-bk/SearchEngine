package api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	
	public Article(JSONObject jsonObject) {
		setId((Long) jsonObject.get("id"));
		setArticleLink((String) jsonObject.get("articleLink"));
		setArticleType((String) jsonObject.get("articleType"));
		setArticleSummary((String) jsonObject.get("articleSummary"));
		setArticleTitle((String) jsonObject.get("articleTitle"));
		setDetailedArticleContent((String) jsonObject.get("detailedArticleContent"));
		setAuthorName((String) jsonObject.get("authorName"));
		
		JSONObject jsonResource = (JSONObject) jsonObject.get("resource");
		setResource((String) jsonResource.get("resourceName"));
		
		String dateString = (String) jsonObject.get("creationDate");
		LocalDate creationDate = LocalDate.parse(dateString);
		setCreationDate(creationDate);
		
		Set<String> hashtags = new HashSet<>();
		JSONArray jsonTags = (JSONArray) jsonObject.get("hashtags");
		for(Object obj : jsonTags) {
			JSONObject jsonTag = (JSONObject) obj;
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

	public String getCreationDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return creationDate.format(formatter);
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
