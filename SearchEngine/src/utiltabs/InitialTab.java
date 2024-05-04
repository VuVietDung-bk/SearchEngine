package utiltabs;

import api.APIController;
import application.SearchTab;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InitialTab extends UtilTab {
	
	private static final int MAX_VISIBLE_ITEMS = 5;

	private String[] sorts = {"creationDate"};
	private String[] directions = {"desc", "asc"};
	private String[] types = {"All", "News", "type3"};
	
	
	private Label keyLabel, tagLabel, sortLabel, resourceLabel, typeLabel, dateLabel1, dateLabel2;
	private TextField keyField;
	private ComboBox<String> tagChoice, sortChoice, resourceChoice, typeChoice, directionChoice;
	private DatePicker fromDate, toDate;
	
	private Button searchButton = new Button("Search");
	
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
        sortChoice.setPrefWidth(120);
        
        directionChoice = createLimitedChoiceBox(directions);
        directionChoice.setPrefWidth(100);
        
        resourceLabel = new Label("Resource:");
        resourceLabel.setTextFill(Color.WHITE);
        try {
			resourceChoice = createLimitedChoiceBox(APIController.getAllResource());
		} catch (Exception e) {

		}
        resourceChoice.setPrefWidth(100);
        
        typeLabel = new Label("Type:");
        typeLabel.setTextFill(Color.WHITE);
        typeChoice = createLimitedChoiceBox(types);
        typeChoice.setPrefWidth(100);
        
        dateLabel1 = new Label("Date: From:");
        dateLabel1.setTextFill(Color.WHITE);
        fromDate = new DatePicker();
        fromDate.setPrefWidth(102);
        fromDate.setValue(null);
        
        dateLabel2 = new Label("To:");
        dateLabel2.setTextFill(Color.WHITE);
        toDate = new DatePicker();
        toDate.setPrefWidth(102);
        toDate.setValue(null);

        // Search Button Action
        searchButton.setOnAction(event -> searchAction());

        HBox keyWordBox = new HBox(10, keyLabel, keyField);
        VBox.setMargin(keyWordBox, new Insets(0, 0, 0, 250));
        
        HBox tagBox = new HBox(10, tagLabel, tagChoice);
        VBox.setMargin(tagBox, new Insets(0, 0, 0, 250));
        
        HBox dateBox = new HBox(10, dateLabel1, fromDate, dateLabel2, toDate);
        VBox.setMargin(dateBox, new Insets(0, 0, 0, 250));
        
        HBox resourceBox = new HBox(10, resourceLabel, resourceChoice);
        VBox.setMargin(resourceBox, new Insets(0, 0, 0, 250));
        
        HBox typeBox = new HBox(10, typeLabel, typeChoice);
        VBox.setMargin(typeBox, new Insets(0, 0, 0, 250));
        
        HBox sortBox = new HBox(10, sortLabel, sortChoice, directionChoice);
        VBox.setMargin(sortBox, new Insets(0, 0, 0, 250));
        
        VBox.setMargin(searchButton, new Insets(0, 0, 0, 250));
        VBox searchBox = new VBox(10, keyWordBox, tagBox, dateBox, resourceBox, typeBox, sortBox, searchButton);
        VBox tabContent = new VBox(100, directoryBox, searchBox);
        
        stackPane = new StackPane(tabContent);
        
        setBackground("bg1.png");

        tab.setContent(stackPane);
	}
	
	private void searchAction() {
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
	
	
}
