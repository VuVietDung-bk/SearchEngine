package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int MAX_TABS = 10;
    private List<SearchTab> searchTabs = new ArrayList<>();
    private TabPane tabPane = new TabPane();

    @Override
    public void start(Stage primaryStage) {
    	searchTabs.add(new SearchTab(tabPane));
    	Image icon = new Image("logo.png");
    	primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Blockchain Browser");

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB); // Tab closing

        // Initial tab
        Tab initialTab = searchTabs.get(0).getTab();
        tabPane.getTabs().add(initialTab);

        // "+" Button to add tabs
        Tab plusTab = new Tab("+");
        tabPane.getTabs().add(plusTab);
        plusTab.setOnSelectionChanged(event -> {
            if (tabPane.getTabs().size() < MAX_TABS) {
            	SearchTab searchTab = new SearchTab(tabPane);
                Tab newTab = searchTab.getTab();
                newTab.setOnCloseRequest(closeEvent -> {
                    removeSearchTab(newTab);
                });
                searchTabs.add(searchTab);
                tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
                tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
            } else {
                // Alert when maximum tabs reached
            	tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Maximum Tabs Reached");
                alert.setHeaderText(null);
                alert.setContentText("You have reached the maximum number of tabs.");
                alert.showAndWait();
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tabPane);

        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void removeSearchTab(Tab newTab) {
    	for (SearchTab searchTab : searchTabs) {
            if (searchTab.getTab() == newTab) {
                searchTabs.remove(searchTab);
                break;
            }
        }
	}

	public static void main(String[] args) {
        launch(args);
    }
}
