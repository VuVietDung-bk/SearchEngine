package utiltabs;

import java.util.List;

import api.APIController;
import api.Article;
import application.DataLoader;
import application.SearchTab;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ContentTab extends UtilTab {
	
	private Label articleTitle, articleContent;

	private Button search;
	
	private VBox relevantBox = new VBox(), tagsBox = new VBox();
	
	private ScrollPane contentScrollPane = new ScrollPane();
	
	private Article article;
	private List<Article> relevantArticles;

	public ContentTab(SearchTab searchTab, String id) throws NumberFormatException, Exception {
		super(searchTab);
		
		article = APIController.getArticleById(Integer.parseInt(id));
		relevantArticles = APIController.getSimilarArticles(id);
		
		articleTitle = new Label(article.getArticleTitle());
		articleTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		articleTitle.setTextFill(Color.WHITE);
		articleTitle.setWrapText(true);
		articleTitle.setMaxWidth(500);
		articleTitle.setTextAlignment(TextAlignment.JUSTIFY);
		
	    articleContent = new Label(article.getDetailedArticleContent());
	    articleContent.setTextFill(Color.LIGHTGRAY);
	    articleContent.setWrapText(true);
	    articleContent.setMaxWidth(500);
	    articleContent.setTextAlignment(TextAlignment.JUSTIFY);
		
		tab = new Tab("Search Result");
		
		Image img = new Image("searchicon.png");
		ImageView imgView = new ImageView(img);
		
		imgView.setFitHeight(20);
		imgView.setFitWidth(20);
		
		search = new Button("Search More", imgView);
		search.setOnAction(e -> {
			search();
		});

		search.setPrefSize(110, 40);
		search.setTextAlignment(TextAlignment.CENTER);
		
		HBox directBox = new HBox(5, backButton, forwardButton);
		
		HBox headerBox = new HBox(250, directBox, search);
		 
		VBox.setMargin(headerBox, new Insets(20, 0, 30, 0));
		
		VBox articleBox = new VBox(10, articleTitle, articleContent);
		
		VBox.setMargin(articleBox, new Insets(0, 0, 0, 30));
		articleBox.setPadding(new Insets(0, 0, 0, 30));
		
		initiateRelevant();
		initiateTagBox();
		
		VBox additionBox = new VBox(10, relevantBox, tagsBox);
		
		VBox.setMargin(additionBox, new Insets(0, 10, 0 , 0));
		additionBox.setAlignment(Pos.TOP_LEFT);
		additionBox.setPadding(new Insets(10, 20, 0, 0));
		
		HBox.setHgrow(articleBox, Priority.ALWAYS); // Allow articleBox to grow horizontally
		HBox.setHgrow(additionBox, Priority.NEVER); // Prevent relevantBox from growing horizontally

		HBox mainBox = new HBox(50, articleBox, additionBox);
		mainBox.setAlignment(Pos.CENTER_LEFT); // Align mainBox content to the left

		contentScrollPane.setContent(mainBox);
        contentScrollPane.setFitToWidth(true);
        
        contentScrollPane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		
		VBox contentBox = new VBox(10, headerBox, contentScrollPane);
        
        stackPane = new StackPane(contentBox);
        
        setBackground("bg1.png");
        
        tab.setContent(stackPane);
	}
	
	private void initiateTagBox() {
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
}
