package utiltabs;

import java.time.LocalDate;

import api.APIController;
import application.SearchTab;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InitialTab extends UtilTab {
	
	private static final int MAX_VISIBLE_ITEMS = 5;

	private String[] sorts = {"creationDate", "articleTitle"};
	private String[] directions = {"desc", "asc"};
	private String[] types = {"All", "News", "Blogs", "Tweets"};
	
	private Label keyLabel, tagLabel, sortLabel, resourceLabel, typeLabel, dateLabel1, dateLabel2;
	private TextField keyField;
	private ComboBox<String> tagChoice, sortChoice, resourceChoice, typeChoice, directionChoice;
	private DatePicker fromDate, toDate;
	
	private Button searchButton, trending;
	
	public InitialTab(SearchTab searchTab) {
		super(searchTab);
		// TODO Auto-generated constructor stub
		tab = new Tab("New Tab");

		HBox directoryBox = new HBox(5, backButton, forwardButton);
		
		VBox.setMargin(directoryBox, new Insets(20, 0, 0, 0));

        // Search Components
        keyLabel = new Label("Keywords:");
        keyLabel.setTextFill(Color.WHITE);
        keyField = new TextField();
        keyField.setPrefWidth(200);
        
        tagLabel = new Label("Tag:");
        tagLabel.setTextFill(Color.WHITE);
        try {
			tagChoice = createLimitedChoiceBox(APIController.getAllTag());
		} catch (Exception e1) {
			
		}
        tagChoice.setPrefWidth(250);
        
        sortLabel = new Label("Sort by:");
        sortLabel.setTextFill(Color.WHITE);
        sortChoice = createLimitedChoiceBox(sorts);
        sortChoice.setPrefWidth(140);
        
        directionChoice = createLimitedChoiceBox(directions);
        directionChoice.setPrefWidth(100);
        
        resourceLabel = new Label("Resource:");
        resourceLabel.setTextFill(Color.WHITE);
        try {
			resourceChoice = createLimitedChoiceBox(APIController.getAllResource());
		} catch (Exception e) {

		}
        resourceChoice.setPrefWidth(250);
        
        typeLabel = new Label("Type:");
        typeLabel.setTextFill(Color.WHITE);
        typeChoice = createLimitedChoiceBox(types);
        typeChoice.setPrefWidth(250);
        
        dateLabel1 = new Label("Date: From:");
        dateLabel1.setTextFill(Color.WHITE);
        fromDate = new DatePicker();
        fromDate.setPrefWidth(107);
        fromDate.setValue(null);
        
        dateLabel2 = new Label("To:");
        dateLabel2.setTextFill(Color.WHITE);
        toDate = new DatePicker();
        toDate.setPrefWidth(107);
        toDate.setValue(null);

        Image img = new Image("searchicon.png");
		ImageView imgView = new ImageView(img);
		
		imgView.setFitHeight(15);
		imgView.setFitWidth(15);
		
		searchButton = new Button("Search", imgView);
        searchButton.setOnAction(event -> searchAction());
        
        img = new Image("trendingicon.png");
		imgView = new ImageView(img);
		
		imgView.setFitHeight(15);
		imgView.setFitWidth(15);
		
		trending = new Button("Trending", imgView);
		trending.setOnAction(e -> {
			goToTrending(fromDate.getValue(), toDate.getValue());
		});
		
		HBox buttonBox = new HBox(10, searchButton, trending);
        
        VBox.setMargin(buttonBox, new Insets(0, 0, 0, 360));
        
        VBox labelBox = new VBox(19, keyLabel, tagLabel, dateLabel1, resourceLabel, typeLabel, sortLabel);
        
        HBox restOfDateContentBox = new HBox(10, fromDate, dateLabel2, toDate);
        HBox restOfSortBox = new HBox(10, sortChoice, directionChoice);
        
        VBox optionBox = new VBox(10, keyField, tagChoice, restOfDateContentBox, resourceChoice, typeChoice, restOfSortBox);
        
        HBox contentBox = new HBox(10, labelBox, optionBox);
        VBox.setMargin(contentBox, new Insets(0, 0, 0, 230));
        
        VBox searchBox = new VBox(20, contentBox, buttonBox);
        VBox tabContent = new VBox(100, directoryBox, searchBox);
        
        stackPane = new StackPane(tabContent);
        
        setBackground("bg1.png");

        tab.setContent(stackPane);
	}

	private void searchAction() {
		if(fromDate.getValue() != null && toDate.getValue() != null && fromDate.getValue().isAfter(toDate.getValue())) {
			return;
		}
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new ListTab(searchTab, keyField.getText(), tagChoice.getValue(), sortChoice.getValue(), resourceChoice.getValue(), 
				typeChoice.getValue(), fromDate.getValue(), toDate.getValue(), directionChoice.getValue());
		searchTab.setTab(nextTab);
	}

	private ComboBox<String> createLimitedChoiceBox(String[] items) {
		ComboBox<String> comboBox = new ComboBox<>();
        for (int i = 0; i < items.length; i++) {
            comboBox.getItems().add(items[i]);
        }
        
        comboBox.getSelectionModel().select(0);
        comboBox.setVisibleRowCount(MAX_VISIBLE_ITEMS);

        return comboBox;
    }
	
	private void goToTrending(LocalDate startDate, LocalDate endDate) {
		if(startDate != null && endDate != null && startDate.isAfter(endDate)) {
			return;
		}
		searchTab.getBackTabs().push(this);
		searchTab.getForwardTabs().clear();
		UtilTab nextTab = new TrendingTab(searchTab, startDate, endDate);
		searchTab.setTab(nextTab);
	}
}
