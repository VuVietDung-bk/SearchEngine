package utiltabs;

import java.util.List;

import api.APIController;
import api.Article;
import application.SearchTab;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ContentTab extends UtilTab {
	
	private Label articleResource, articleTitle, articleSummary, articleDate, articleContent;

	private Button search;
	
	private VBox relevantBox = new VBox(), tagsBox = new VBox();
	
	private ScrollPane contentScrollPane = new ScrollPane();
	
	private Article article;
	private List<Article> relevantArticles;

	public ContentTab(SearchTab searchTab, String id) throws NumberFormatException, Exception {
		super(searchTab);
		
		article = APIController.getArticleById(Integer.parseInt(id));
		relevantArticles = APIController.getSimilarArticles(id);
		
		articleResource = new Label(article.getResource());
		articleResource.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		articleResource.setTextFill(Color.GREENYELLOW);
		articleResource.setOnMouseEntered(e -> {
			articleResource.setUnderline(true);
    	});
		articleResource.setOnMouseExited(e -> {
			articleResource.setUnderline(false);
    	});
		articleResource.setOnMouseClicked(e -> {
    		searchByResource(article.getResource());
    	});
		
		articleTitle = new Label(article.getArticleTitle());
		articleTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		articleTitle.setTextFill(Color.WHITE);
		articleTitle.setWrapText(true);
		articleTitle.setMaxWidth(500);
		articleTitle.setTextAlignment(TextAlignment.JUSTIFY);
		
		articleDate = new Label(article.getCreationDate() + ". By " + article.getAuthorName());
		articleDate.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		articleDate.setTextFill(Color.WHITE);
		articleDate.setWrapText(true);
		articleDate.setMaxWidth(500);
		articleDate.setTextAlignment(TextAlignment.JUSTIFY);
		
		articleSummary = new Label(article.getArticleSummary());
		articleSummary.setFont(Font.font("Arial", 14));
		articleSummary.setTextFill(Color.CYAN);
		articleSummary.setWrapText(true);
		articleSummary.setMaxWidth(500);
		articleSummary.setTextAlignment(TextAlignment.JUSTIFY);
		
	    articleContent = new Label(article.getDetailedArticleContent());
	    articleContent.setFont(Font.font("Arial", 13));
	    articleContent.setTextFill(Color.IVORY);
	    articleContent.setWrapText(true);
	    articleContent.setMaxWidth(500);
	    articleContent.setTextAlignment(TextAlignment.JUSTIFY);
		
		tab = new Tab("Search Result");
		
		Image img = new Image("searchicon.png");
		ImageView imgView = new ImageView(img);
		
		imgView.setFitHeight(15);
		imgView.setFitWidth(15);
		
		search = new Button("Search More", imgView);
		search.setOnAction(e -> {
			search();
		});

		search.setPrefSize(110, 40);
		search.setTextAlignment(TextAlignment.CENTER);
		search.setPadding(new Insets(-10, 0, -10, 0));
		
		HBox directBox = new HBox(5, backButton, forwardButton);
		
		HBox headerBox = new HBox(250, directBox, search);
		 
		VBox.setMargin(headerBox, new Insets(20, 0, 30, 0));
		
		VBox articleBox = new VBox(10, articleResource, articleTitle, articleDate, articleSummary, articleContent);
		
		VBox.setMargin(articleBox, new Insets(0, 0, 0, 30));
		articleBox.setPadding(new Insets(0, 0, 0, 30));
		
		Color black = Color.rgb(0, 0, 0, 0.5);
        BackgroundFill backgroundFill1 = new BackgroundFill(black, CornerRadii.EMPTY, new Insets(0, 0, 0, 30));
        Background background1 = new Background(backgroundFill1);
		articleBox.setBackground(background1);
		
		initiateRelevant();
		initiateTagBox();
		
		VBox additionBox = new VBox(10, relevantBox, tagsBox);
		
		VBox.setMargin(additionBox, new Insets(0, 10, 0 , 0));
		additionBox.setAlignment(Pos.TOP_LEFT);
		additionBox.setPadding(new Insets(10, 20, 0, 0));
		
        BackgroundFill backgroundFill2 = new BackgroundFill(black, CornerRadii.EMPTY, new Insets(0, 20, 0, 0));
        Background background2 = new Background(backgroundFill2);
		additionBox.setBackground(background2);
		
		HBox.setHgrow(articleBox, Priority.NEVER); // Allow articleBox to grow horizontally
		HBox.setHgrow(additionBox, Priority.NEVER); // Prevent relevantBox from growing horizontally

		HBox mainBox = new HBox(50, articleBox, additionBox);
		mainBox.setAlignment(Pos.CENTER_LEFT); // Align mainBox content to the left

		contentScrollPane.setContent(mainBox);
        contentScrollPane.setFitToWidth(true);
        
        contentScrollPane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		VBox contentBox = new VBox(10, headerBox, contentScrollPane);
        
        stackPane = new StackPane(contentBox);
        
        setBackground("bg2.png");
        
        tab.setContent(stackPane);
	}

	private void initiateTagBox() {
		if(article.getHashtags() == null || article.getHashtags().size() == 0) return;
		Label tagLabel = new Label("Tags:");
		tagLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		tagLabel.setTextFill(Color.WHITE);
		tagsBox.getChildren().add(tagLabel);
		
		for(String str : article.getHashtags()) {
			Label tag = new Label(str);
			tag.setOnMouseEntered(e -> {
				tag.setUnderline(true);
        	});
        	
			tag.setOnMouseExited(e -> {
				tag.setUnderline(false);
        	});
			tag.setMaxWidth(200);
        	tag.setTextFill(Color.WHITE);
        	
        	tag.setOnMouseClicked(e -> {
        		searchByTag(str);
        	});
        	
        	tagsBox.getChildren().add(tag);
		}
	}

	private void initiateRelevant() {
		if(relevantArticles == null || relevantArticles.size() == 0) return;
		Label relevantTitle = new Label("Relevant Articles:");
		relevantTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		relevantTitle.setTextFill(Color.WHITE);
		relevantBox.getChildren().add(relevantTitle);
		
		for (int i = 0; i <= 4; i++) {
			if(i >= relevantArticles.size()){
				break;
			}
        	Label title = new Label(relevantArticles.get(i).getArticleTitle());
        	title.setOnMouseEntered(e -> {
        		title.setUnderline(true);
        	});
        	
        	title.setOnMouseExited(e -> {
        		title.setUnderline(false);
        	});
        	
        	title.setMaxWidth(200);
        	title.setTextFill(Color.WHITE);
        	String tempString = new String(relevantArticles.get(i).getId().toString());
        	title.setOnMouseClicked(e -> {
        		try {
					goToRelevant(tempString);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	});
        	
        	relevantBox.getChildren().add(title);
        }
	}
	
	private void search() {
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new InitialTab(searchTab);
		searchTab.setTab(nextTab);
	}

	private void goToRelevant(String url) throws NumberFormatException, Exception {
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new ContentTab(searchTab, url);
		searchTab.setTab(nextTab);
	}
	
	private void searchByTag(String tag) {
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new ListTab(searchTab, "", tag, "creationDate", "", "", null, null, "desc");
		searchTab.setTab(nextTab);
	}
	
	private void searchByResource(String resource) {
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new ListTab(searchTab, "", "All", "creationDate", resource, "All", null, null, "desc");
		searchTab.setTab(nextTab);
	}
}
