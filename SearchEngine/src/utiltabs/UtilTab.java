package utiltabs;

import application.SearchTab;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;

public class UtilTab {
	protected Button backButton = new Button("Back"), forwardButton = new Button("Forwards");
	protected Tab tab;
	protected StackPane stackPane;
	protected SearchTab searchTab;
	protected String tabTitle;
	
	public UtilTab(SearchTab searchTab) {
		this.searchTab = searchTab;
		backButton.setOnAction(e -> {
			back();
		});
		forwardButton.setOnAction(e -> {
			forward();
		});
		refresh();
	}
	
	public Tab getTab() {
		return tab;
	}
	
	protected void back() {
		searchTab.back();
	};
	
	protected void forward() {
		searchTab.forward();
	}

	public void refresh() {
		if(searchTab.getBackTabs().isEmpty()) {
			backButton.setVisible(false);
		} else backButton.setVisible(true);
		
		if(searchTab.getForwardTabs().isEmpty()) {
			forwardButton.setVisible(false);
		} else forwardButton.setVisible(true);
	}
	
	protected void setBackground(String path) {
        // Load the background image
        Image backgroundImage = new Image(path);

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );

        // Create a Background with the image
        Background background1 = new Background(background);

        // Set the background to the root pane
        stackPane.setBackground(background1);
    }
}
