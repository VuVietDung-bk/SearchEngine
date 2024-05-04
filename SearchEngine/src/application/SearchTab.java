package application;

import java.util.Stack;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import utiltabs.*;

public class SearchTab {
	
	private TabPane tabPane;
	
	private Stack<UtilTab> backTabs = new Stack<UtilTab>(), forwardTabs = new Stack<UtilTab>();
	private UtilTab currTab;
	
	public SearchTab(TabPane tabPane) {
		this.tabPane = tabPane;
		currTab = new InitialTab(this);
	}
	
	public Tab getTab() {
		return currTab.getTab();
	}
	
	public void setTab(UtilTab newTab) {
		for(int i = 0; i < tabPane.getTabs().size(); i++) {
			if(tabPane.getTabs().get(i).equals(currTab.getTab())) {
				tabPane.getTabs().set(i, newTab.getTab());
				tabPane.getSelectionModel().select(i);
			}
		}
		currTab = newTab;
	}

	public Stack<UtilTab> getBackTabs() {
		return backTabs;
	}


	public Stack<UtilTab> getForwardTabs() {
		return forwardTabs;
	}
	
	public void back() {
		if(!backTabs.isEmpty()) {
			forwardTabs.push(currTab);
			setTab(backTabs.pop());
			currTab.refresh();
		}
	}
	
	public void forward() {
		if(!forwardTabs.isEmpty()) {
			backTabs.push(currTab);
			setTab(forwardTabs.pop());
			currTab.refresh();
		}
	}
}
