package main.java.com.goxr3plus.xr3player.application.modes.librarymode;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import main.java.com.goxr3plus.xr3player.application.Main;
import main.java.com.goxr3plus.xr3player.application.tools.InfoTool;

public class LibraryTabContextMenu extends ContextMenu {
	
	//--------------------------------------------------------------
	
	@FXML
	private MenuItem createLibrary;
	
	@FXML
	private MenuItem showTheLibrary;
	
	@FXML
	private MenuItem closeOtherTabs;
	
	@FXML
	private MenuItem closeTabsRight;
	
	@FXML
	private MenuItem closeTabsLeft;
	
	@FXML
	private MenuItem closeTab;
	
	// -------------------------------------------------------------
	
	/** The logger. */
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private final Tab tab;
	
	/**
	 * Constructor.
	 */
	public LibraryTabContextMenu(Tab tab) {
		this.tab = tab;
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(InfoTool.FXMLS + "LibraryTabContextMenu.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "", ex);
		}
		
	}
	
	/**
	 * Called as soon as .FXML is loaded from FXML Loader
	 */
	@FXML
	private void initialize() {
		
		//showTheLibrary
		showTheLibrary
				.setOnAction(a -> Main.libraryMode.teamViewer.getViewer().setCenterIndex(Main.libraryMode.getLibraryWithName(tab.getTooltip().getText()).get().getPosition()));
		
		//closeTabsRight
		closeTabsRight.setOnAction(a -> Main.libraryMode.openedLibrariesViewer.closeTabsToTheRight(tab));
		
		//closeTabsLeft
		closeTabsLeft.setOnAction(a -> Main.libraryMode.openedLibrariesViewer.closeTabsToTheLeft(tab));
		
		//closeOtherTabs
		closeOtherTabs.setOnAction(a -> {
			Main.libraryMode.openedLibrariesViewer.closeTabsToTheLeft(tab);
			Main.libraryMode.openedLibrariesViewer.closeTabsToTheRight(tab);
		});
		
		//closeTab
		closeTab.setOnAction(a -> Main.libraryMode.openedLibrariesViewer.removeTab(tab));
		
		//createLibrary
		this.createLibrary.setOnAction(a->Main.libraryMode.createNewLibrary(tab.getGraphic(), true));		
	}
}
