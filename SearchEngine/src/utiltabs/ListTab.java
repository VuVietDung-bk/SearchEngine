package utiltabs;

import java.time.LocalDate;

import application.DataLoader;
import application.SearchTab;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ListTab extends UtilTab {
	
	public static final int ARTICLES_PER_PAGE = 7;

	private int numberOfPage;
	
	private VBox articlesBox = new VBox();
	private Pagination pagination = new Pagination();
	private DataLoader dataLoader = new DataLoader();

	public ListTab(SearchTab searchTab, String keyValue, String tagValue, String sortValue, String resourceValue, 
			String typeValue, LocalDate fromDate, LocalDate toDate, String direction) {
		super(searchTab);

		try {
			dataLoader.load(keyValue, tagValue, sortValue, resourceValue, typeValue, fromDate, toDate, direction);
		} catch (Exception e) {

		}
		numberOfPage = dataLoader.getNumberOfPage();
		
		tab = new Tab("Search Result");
		
		HBox directoryBox = new HBox(5, backButton, forwardButton);
		
		VBox.setMargin(directoryBox, new Insets(20, 0, 0, 0));
		
		// Articles Display
        articlesBox.setSpacing(1);
        articlesBox.setPadding(new Insets(20, 0, -6, 40));

        // Pagination
        initializePagination();
        
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            pageUpdate();
        });
        
        VBox tabContent = new VBox(10, directoryBox, articlesBox, pagination);
        
        stackPane = new StackPane(tabContent);
        
        setBackground("bg1.png");

        tab.setContent(stackPane);
	}
	
	private void initializePagination() {
		pagination.setMaxPageIndicatorCount(10);
		pagination.setPageCount(numberOfPage);
		
		pagination.setStyle("-fx-page-information-visible: false;");
		
		for (int i = 0; i < ARTICLES_PER_PAGE; i++) {
			Label resource = new Label(dataLoader.getArticleResource(i));
			resource.setTextFill(Color.WHITE);
			resource.setFont(Font.font(10));
			articlesBox.getChildren().add(resource);
			
        	Label title = new Label(dataLoader.getArticleTitle(i));
        	title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        	title.setTextFill(Color.WHITE);
        	title.setOnMouseEntered(e -> {
        		title.setUnderline(true);
        	});
        	
        	title.setOnMouseExited(e -> {
        		title.setUnderline(false);
        	});
        	String tempString = new String("" + dataLoader.getArticleID(i));
        	title.setOnMouseClicked(e -> {
        		showContent(tempString);
        	});
            articlesBox.getChildren().add(title);
            Label content = new Label(dataLoader.getArticleContent(i));
            content.setTextFill(Color.WHITE);
            content.setPadding(new Insets(0, 0, 8, 0));
            articlesBox.getChildren().add(content);
        }
        pagination.setCurrentPageIndex(0);
	}

	private void pageUpdate() {
		articlesBox.getChildren().clear();
		int currentPage = pagination.getCurrentPageIndex();
		
		try {
			dataLoader.setCurrentPage(currentPage);
		} catch (Exception e) {

		}
		
        for (int i = 0; i < ARTICLES_PER_PAGE; i++) {
        	Label resource = new Label(dataLoader.getArticleResource(i));
			resource.setTextFill(Color.WHITE);
			resource.setFont(Font.font(10));
			articlesBox.getChildren().add(resource);
			
        	Label title = new Label(dataLoader.getArticleTitle(i));
        	title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        	title.setTextFill(Color.WHITE);
        	title.setOnMouseEntered(e -> {
        		title.setUnderline(true);
        	});
        	
        	title.setOnMouseExited(e -> {
        		title.setUnderline(false);
        	});
        	String tempString = new String("" + dataLoader.getArticleID(i));
        	title.setOnMouseClicked(e -> {
        		showContent(tempString);
        	});
            articlesBox.getChildren().add(title);
            Label content = new Label(dataLoader.getArticleContent(i));
            content.setTextFill(Color.WHITE);
            content.setPadding(new Insets(0, 0, 8, 0));
            articlesBox.getChildren().add(content);
        }
	}

	protected void showContent(String id) {
		// TODO Auto-generated method stub
		
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab;
		try {
			nextTab = new ContentTab(searchTab, id);
			searchTab.setTab(nextTab);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
