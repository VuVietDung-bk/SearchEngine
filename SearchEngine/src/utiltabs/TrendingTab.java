package utiltabs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import api.APIController;
import application.SearchTab;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class TrendingTab extends UtilTab {
	
	private Button search;
	private String[] tags;
	
	private LocalDate startDate, endDate;
	private Label trendingTitle, advancedTitle, startDateText, endDateText;
	
	private DatePicker fromDate = new DatePicker(), toDate = new DatePicker();
	private Button trendingButton;
	
	private ScrollPane contentScrollPane = new ScrollPane();
	
	private VBox trendingBox = new VBox(), advancedBox = new VBox();
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	public TrendingTab(SearchTab searchTab, LocalDate startDate, LocalDate endDate) {
		super(searchTab);
		this.startDate = startDate;
		this.endDate = endDate;

		tab = new Tab("Search Result");
		try {
			tags = APIController.getTrendingTags(startDate, endDate);
		} catch (Exception e) {
			
		}
		
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
		
		HBox.setHgrow(trendingBox, Priority.NEVER); 
		HBox.setHgrow(advancedBox, Priority.NEVER); 
		
		trendingBox.setSpacing(5);
		
		initiateTrendingTags();
		
		initiateAdvancedBox();
		
		trendingBox.setPadding(new Insets(0, 0, 0, 30));
		
		HBox mainBox = new HBox(50, trendingBox, advancedBox);
		mainBox.setAlignment(Pos.CENTER_LEFT); // Align mainBox content to the left
		 
		VBox.setMargin(headerBox, new Insets(20, 0, 30, 0));
		
		contentScrollPane.setContent(mainBox);
        contentScrollPane.setFitToWidth(true);
        
        contentScrollPane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		VBox contentBox = new VBox(10, headerBox, contentScrollPane);
        
        stackPane = new StackPane(contentBox);
		
		setBackground("bg2.png");
		
		tab.setContent(stackPane);
	}

	private void initiateAdvancedBox() {
		advancedBox.setSpacing(5);
		
		advancedTitle = new Label("Advanced options for trending:");
		advancedTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		advancedTitle.setTextFill(Color.WHITE);
		advancedTitle.setWrapText(true);
		advancedTitle.setMaxWidth(500);
		advancedTitle.setOnMouseEntered(e -> {
			advancedTitle.setUnderline(true);
    	});
		advancedTitle.setOnMouseExited(e -> {
			advancedTitle.setUnderline(false);
    	});
		advancedTitle.setOnMouseClicked(e -> {
    		showOptions();
    	});
		
		startDateText = new Label("Trending starts from:");
		startDateText.setTextFill(Color.WHITE);
		startDateText.setWrapText(true);
		startDateText.setMaxWidth(200);
		startDateText.setVisible(false);
		
		endDateText = new Label("Trending ends on:");
		endDateText.setTextFill(Color.WHITE);
		endDateText.setWrapText(true);
		endDateText.setMaxWidth(200);
		endDateText.setVisible(false);
		
		fromDate.setVisible(false);
		toDate.setVisible(false);
		
		Image img = new Image("trendingicon.png");
		ImageView imgView = new ImageView(img);
		
		imgView.setFitHeight(15);
		imgView.setFitWidth(15);
		
		trendingButton = new Button("Trending", imgView);
		trendingButton.setOnAction(e -> {
			refreshTexts();
		});
		trendingButton.setVisible(false);
		
		advancedBox.getChildren().addAll(advancedTitle, startDateText, fromDate, endDateText, toDate, trendingButton);
	}

	private void initiateTrendingTags() {
		trendingBox.getChildren().clear();
		if(startDate == null && endDate == null) {
			trendingTitle = new Label("Here are the most popular tags from the beginning of 2024:");
		} else if(endDate == null) {
			trendingTitle = new Label("Here are the most popular tags from " + formatter.format(startDate) + ":");
		} else if(startDate == null) {
			trendingTitle = new Label("Here are the most popular tags before" + formatter.format(endDate) + ":");
		} else {
			trendingTitle = new Label("Here are the most popular tags from " + formatter.format(startDate) + " to " + formatter.format(endDate) + ":");
		}
		trendingTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		trendingTitle.setTextFill(Color.WHITE);
		trendingTitle.setWrapText(true);
		trendingTitle.setMaxWidth(500);
		trendingTitle.setTextAlignment(TextAlignment.JUSTIFY);
		
		trendingBox.getChildren().add(trendingTitle);
		
		for(String tag : tags) {
			Label tagText = new Label(tag);
			tagText.setFont(Font.font(14));
			tagText.setOnMouseEntered(e -> {
				tagText.setUnderline(true);
        	});
        	
			tagText.setOnMouseExited(e -> {
				tagText.setUnderline(false);
        	});
			tagText.setMaxWidth(200);
        	tagText.setTextFill(Color.WHITE);
        	
        	tagText.setOnMouseClicked(e -> {
        		searchByTag(tag);
        	});
        	
        	trendingBox.getChildren().add(tagText);
		}
	}

	private void searchByTag(String tag) {
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new ListTab(searchTab, "", tag, "creationDate", "", "", null, null, "desc");
		searchTab.setTab(nextTab);
	}

	private void search() {
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new InitialTab(searchTab);
		searchTab.setTab(nextTab);
	}
	

	private void showOptions() {
		startDateText.setVisible(true);
		endDateText.setVisible(true);
		fromDate.setVisible(true);
		toDate.setVisible(true);
		trendingButton.setVisible(true);
	}

	private void refreshTexts() {
		startDate = fromDate.getValue();
		endDate = toDate.getValue();
		if(startDate != null && endDate != null && startDate.isAfter(endDate)) {
			return;
		}
		try {
			tags = APIController.getTrendingTags(startDate, endDate);
		} catch (Exception e) {

		}
		
		initiateTrendingTags();
	}
}