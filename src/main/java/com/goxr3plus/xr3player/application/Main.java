/*
 * 
 */
package main.java.com.goxr3plus.xr3player.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXTabPane;
import com.teamdev.jxbrowser.chromium.az;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.java.com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import main.java.com.goxr3plus.xr3capture.application.CaptureWindow;
import main.java.com.goxr3plus.xr3player.application.database.DbManager;
import main.java.com.goxr3plus.xr3player.application.database.PropertiesDb;
import main.java.com.goxr3plus.xr3player.application.database.services.VacuumProgressService;
import main.java.com.goxr3plus.xr3player.application.modes.djmode.DJMode;
import main.java.com.goxr3plus.xr3player.application.modes.librarymode.LibraryMode;
import main.java.com.goxr3plus.xr3player.application.modes.loginmode.LoginMode;
import main.java.com.goxr3plus.xr3player.application.modes.loginmode.User;
import main.java.com.goxr3plus.xr3player.application.modes.loginmode.UserInformation;
import main.java.com.goxr3plus.xr3player.application.modes.loginmode.UserInformation.UserCategory;
import main.java.com.goxr3plus.xr3player.application.modes.moviemode.MovieModeController;
import main.java.com.goxr3plus.xr3player.application.presenter.BottomBar;
import main.java.com.goxr3plus.xr3player.application.presenter.EmotionsTabPane;
import main.java.com.goxr3plus.xr3player.application.presenter.OnlineMusicController;
import main.java.com.goxr3plus.xr3player.application.presenter.PlayListModesSplitPane;
import main.java.com.goxr3plus.xr3player.application.presenter.PlayListModesTabPane;
import main.java.com.goxr3plus.xr3player.application.presenter.SideBar;
import main.java.com.goxr3plus.xr3player.application.presenter.TopBar;
import main.java.com.goxr3plus.xr3player.application.presenter.UpdateScreen;
import main.java.com.goxr3plus.xr3player.application.presenter.WelcomeScreen;
import main.java.com.goxr3plus.xr3player.application.settings.ApplicationSettingsController;
import main.java.com.goxr3plus.xr3player.application.settings.ApplicationSettingsLoader;
import main.java.com.goxr3plus.xr3player.application.speciallists.EmotionListsController;
import main.java.com.goxr3plus.xr3player.application.speciallists.PlayedMediaList;
import main.java.com.goxr3plus.xr3player.application.systemtreeview.TreeViewContextMenu;
import main.java.com.goxr3plus.xr3player.application.systemtreeview.TreeViewManager;
import main.java.com.goxr3plus.xr3player.application.tools.ActionTool;
import main.java.com.goxr3plus.xr3player.application.tools.ActionTool.FileType;
import main.java.com.goxr3plus.xr3player.application.tools.InfoTool;
import main.java.com.goxr3plus.xr3player.application.tools.JavaFXTools;
import main.java.com.goxr3plus.xr3player.application.tools.NotificationType;
import main.java.com.goxr3plus.xr3player.application.tools.Util;
import main.java.com.goxr3plus.xr3player.application.updater.UpdateWindow;
import main.java.com.goxr3plus.xr3player.application.windows.ApplicationInformationWindow;
import main.java.com.goxr3plus.xr3player.application.windows.ConsoleWindowController;
import main.java.com.goxr3plus.xr3player.application.windows.EmotionsWindow;
import main.java.com.goxr3plus.xr3player.application.windows.ExportWindowController;
import main.java.com.goxr3plus.xr3player.application.windows.FileAndFolderChooser;
import main.java.com.goxr3plus.xr3player.application.windows.MediaDeleteWindow;
import main.java.com.goxr3plus.xr3player.application.windows.RenameWindow;
import main.java.com.goxr3plus.xr3player.application.windows.StarWindow;
import main.java.com.goxr3plus.xr3player.chromium.WebBrowserController;
import main.java.com.goxr3plus.xr3player.remote.dropbox.presenter.DropboxViewer;
import main.java.com.goxr3plus.xr3player.smartcontroller.enums.Genre;
import main.java.com.goxr3plus.xr3player.smartcontroller.media.MediaInformation;
import main.java.com.goxr3plus.xr3player.smartcontroller.presenter.MediaContextMenu;
import main.java.com.goxr3plus.xr3player.smartcontroller.presenter.SmartController;
import main.java.com.goxr3plus.xr3player.smartcontroller.services.MediaUpdaterService;
import main.java.com.goxr3plus.xr3player.smartcontroller.tags.TagWindow;
import main.java.com.goxr3plus.xr3player.xplayer.presenter.XPlayersList;
import main.java.com.goxr3plus.xr3player.xplayer.services.XPlayersFilterService;

/**
 * The Main class from which the application is starting.
 *
 * @author GOXR3PLUS STUDIO
 */
public class Main extends Application {
	
	//------------------------------------------------------------------------
	
	/** Holds global application properties */
	public static final PropertiesDb applicationProperties = new PropertiesDb(InfoTool.getAbsoluteDatabasePathWithSeparator() + "ApplicationProperties.properties", true);
	public static final Logger logger = Logger.getGlobal();
	public static final Properties internalInformation = new Properties();
	public static final int APPLICATION_VERSION = 101;
	static {
		//Important for Web Browser allowing xss
		//System.setProperty("sun.net.http.allowRestrictedHeaders", "true")
		
		//----------Properties-------------
		internalInformation.put("Version", APPLICATION_VERSION);
		internalInformation.put("ReleasedDate", "Check updates window");
		
		System.out.println("Outside of Application Start Method");
		
		//Extract Location
		System.setProperty("jxbrowser.chromium.dir", InfoTool.getAbsoluteDatabaseParentFolderPathWithSeparator() + "Chromium");
	}
	
	//----------------START: The below have not dependencies on classes ---------------------------------//
	
	public static WelcomeScreen welcomeScreen;
	
	public static MediaDeleteWindow mediaDeleteWindow;
	
	/** The star window. */
	public static StarWindow starWindow;
	
	/** The rename window. */
	public static RenameWindow renameWindow;
	
	/** The rename window. */
	public static EmotionsWindow emotionsWindow;
	
	/**
	 * Audio Tagging Window
	 */
	public static TagWindow tagWindow;
	
	/**
	 * This window is being used to export files from the application to the outside world
	 */
	public static ExportWindowController exportWindow;
	
	/** The About Window of the Application */
	public static ApplicationInformationWindow aboutWindow;
	
	/** The console Window of the Application */
	public static ConsoleWindowController consoleWindow;
	
	/**
	 * This Window contains the settings for the whole application
	 */
	public static ApplicationSettingsController settingsWindow;
	
	/**
	 * This class is used to capture the computer Screen or a part of it [ Check XR3Capture package]
	 */
	public static CaptureWindow captureWindow;
	
	public static UpdateWindow updateWindow;
	
	//
	
	/** The Top Bar of the Application */
	public static TopBar topBar;
	
	/** The Bottom Bar of the Application */
	public static BottomBar bottomBar;
	
	/** The Side Bar of The Application */
	public static SideBar sideBar;
	
	/** Application Update Screen */
	public static UpdateScreen updateScreen;
	
	/** The TreeView of DJMode */
	public static TreeViewManager treeManager;
	
	/** The Constant advancedSearch. */
	//public static final AdvancedSearch advancedSearch = new AdvancedSearch()
	
	public static MediaInformation mediaInformation;
	//
	
	public static TreeViewContextMenu treeViewContextMenu;
	
	/** The Constant songsContextMenu. */
	public static MediaContextMenu songsContextMenu;
	
	/** The Constant EmotionListsController. */
	public static EmotionListsController emotionListsController;
	
	//
	
	/**
	 * The WebBrowser of the Application
	 */
	public static WebBrowserController webBrowser;
	
	//
	
	/** The Constant specialChooser. */
	public static FileAndFolderChooser specialChooser = new FileAndFolderChooser();;
	
	/** XPlayList holds the instances of XPlayerControllers */
	public static XPlayersList xPlayersList = new XPlayersList();
	
	/** The Constant . */
	public static PlayedMediaList playedSongs = new PlayedMediaList();
	
	//----------------END: The above have not dependencies on other classes ---------------------------------//
	
	//----------------START: Vary basic for the application---------------------------------------//
	
	/** The window. */
	public static Stage window;
	
	/** The scene. */
	public static BorderlessScene scene;
	
	/** The stack pane root. */
	public static final StackPane applicationStackPane = new StackPane();
	
	/** The root. */
	public static final BorderPane root = new BorderPane();
	
	/** The can save data. */
	public static boolean canSaveData = true;
	
	//---------------END:Vary basic for the application---------------------------------//
	
	// --------------START: The below have dependencies on others------------------------
	
	/** The Constant dbManager. */
	public static DbManager dbManager = new DbManager();
	
	/** The Constant libraryMode. */
	public static LibraryMode libraryMode;
	
	/** The Constant djMode. */
	public static DJMode djMode;
	
	public static OnlineMusicController onlineMusicController;
	
	public static DropboxViewer dropBoxViewer;
	
	public static EmotionsTabPane emotionsTabPane;
	
	/** The Search Window Smart Controller of the application */
	public static SmartController searchWindowSmartController;
	
	public static PlayListModesTabPane playListModesTabPane;
	
	/** The Constant multipleTabs. */
	public static PlayListModesSplitPane playListModesSplitPane;
	
	/**
	 * The Login Mode where the user of the applications has to choose an account to login
	 */
	public static LoginMode loginMode;
	
	/**
	 * Entering in this mode you can change the user settings and other things that have to do with the user....
	 */
	public static UserInformation userInfoMode;
	
	/**
	 * This JavaFX TabPane represents a TabPane for Navigation between application Modes
	 */
	public static JFXTabPane specialJFXTabPane;
	
	// --------------END: The below have dependencies on others------------------------
	
	@Override
	public void start(Stage primaryStage) {
		
		//----------------START: The below have not dependencies on other ---------------------------------//
		
		welcomeScreen = new WelcomeScreen();
		
		mediaDeleteWindow = new MediaDeleteWindow();
		
		/** The star window. */
		starWindow = new StarWindow();
		
		/** The rename window. */
		renameWindow = new RenameWindow();
		
		/** The rename window. */
		emotionsWindow = new EmotionsWindow();
		
		/**
		 * Audio Tagging Window
		 */
		tagWindow = new TagWindow();
		
		/**
		 * This window is being used to export files from the application to the outside world
		 */
		exportWindow = new ExportWindowController();
		
		/** The About Window of the Application */
		aboutWindow = new ApplicationInformationWindow();
		
		/** The console Window of the Application */
		consoleWindow = new ConsoleWindowController();
		
		/**
		 * This Window contains the settings for the whole application
		 */
		settingsWindow = new ApplicationSettingsController();
		
		/**
		 * This class is used to capture the computer Screen or a part of it [ Check XR3Capture package]
		 */
		captureWindow = new CaptureWindow();
		
		updateWindow = new UpdateWindow();
		
		//
		
		/** The Top Bar of the Application */
		topBar = new TopBar();
		
		/** The Bottom Bar of the Application */
		bottomBar = new BottomBar();
		
		/** The Side Bar of The Application */
		sideBar = new SideBar();
		
		/** Application Update Screen */
		updateScreen = new UpdateScreen();
		
		/** The TreeView of DJMode */
		treeManager = new TreeViewManager();
		
		/** The Constant advancedSearch. */
		//public static final AdvancedSearch advancedSearch = new AdvancedSearch()
		
		mediaInformation = new MediaInformation();
		//
		
		treeViewContextMenu = new TreeViewContextMenu();
		
		/** The Constant songsContextMenu. */
		songsContextMenu = new MediaContextMenu();
		
		//
		
		/** The Constant EmotionListsController. */
		emotionListsController = new EmotionListsController();
		
		//
		
		//----------------END: The above have not dependencies on other ---------------------------------//
		
		// --------------START: The below have depencitdependenciesies on others------------------------
		
		/** The Constant libraryMode. */
		libraryMode = new LibraryMode();
		
		/** The Constant djMode. */
		djMode = new DJMode();
		
		onlineMusicController = new OnlineMusicController();
		
		emotionsTabPane = new EmotionsTabPane(emotionListsController);
		
		/** The Search Window Smart Controller of the application */
		searchWindowSmartController = new SmartController(Genre.SEARCHWINDOW, "Searching any Media", null);
		
		playListModesTabPane = new PlayListModesTabPane();
		
		/** The Constant multipleTabs. */
		playListModesSplitPane = new PlayListModesSplitPane();
		
		/**
		 * The Login Mode where the user of the applications has to choose an account to login
		 */
		loginMode = new LoginMode();
		
		/**
		 * Entering in this mode you can change the user settings and other things that have to do with the user....
		 */
		userInfoMode = new UserInformation(UserCategory.LOGGED_IN);
		
		/**
		 * This JavaFX TabPane represents a TabPane for Navigation between application Modes
		 */
		specialJFXTabPane = new JFXTabPane();
		
		// --------------END: The below have dependencies on others------------w------------
		
		System.out.println("Entered JavaFX Application Start Method");
		Platform.setImplicitExit(false);
		
		// --------Window---------
		window = primaryStage;
		window.setTitle("XR3Player V." + internalInformation.get("Version"));
		double width = InfoTool.getVisualScreenWidth();
		double height = InfoTool.getVisualScreenHeight();
		
		//---------------------------------------
		//TODO
		//width = 1280;
		//height = 600;
		//TODO
		//Testing on Bigger Screens
		//---------------------------------------
		
		window.setWidth(width * 0.95);
		window.setHeight(height * 0.95);
		window.centerOnScreen();
		window.getIcons().add(InfoTool.getImageFromResourcesFolder("icon.png"));
		window.centerOnScreen();
		window.setOnCloseRequest(exit -> {
			confirmApplicationExit();
			exit.consume();
		});
		
		// Create BorderlessScene 
		final int screenMinWidth = 800 , screenMinHeight = 600;
		scene = new BorderlessScene(window, StageStyle.UNDECORATED, applicationStackPane, screenMinWidth, screenMinHeight);
		scene.setMoveControl(loginMode.getXr3PlayerLabel());
		scene.getStylesheets().add(getClass().getResource(InfoTool.STYLES + InfoTool.APPLICATIONCSS).toExternalForm());
		window.setScene(scene);
		window.show();
		window.close();
		
		//welcomeScreen
		scene.setMoveControl(welcomeScreen.getTopHBox());
		
		//Continue
		startPart2();
		
		//Show the Window
		window.show();
		
		//---Login Mode---- It must be set after the window has been shown
		loginMode.getSplitPane().setDividerPositions(0.65, 0.35);
		
		//Load the informations about every user
		loginMode.usersInfoLoader.start();
		
		//Check for updates
		updateWindow.searchForUpdates(false);
		
		//Delete AutoUpdate if it exists
		ActionTool.deleteFile(new File(InfoTool.getBasePathForClass(Main.class) + "XR3PlayerUpdater.jar"));
		
		//============= ApplicationProperties GLOBAL
		Properties properties = applicationProperties.loadProperties();
		
		//WelcomeScreen
		welcomeScreen.getVersionLabel().setText("XR3Player V." + APPLICATION_VERSION);
		if (properties.getProperty("Show-Welcome-Screen") == null)
			welcomeScreen.showWelcomeScreen();
		else
			Optional.ofNullable(properties.getProperty("Show-Welcome-Screen")).ifPresent(value -> {
				welcomeScreen.getShowOnStartUp().setSelected(Boolean.valueOf(value));
				if (welcomeScreen.getShowOnStartUp().isSelected())
					welcomeScreen.showWelcomeScreen();
				else
					welcomeScreen.hideWelcomeScreen();
			});
		
		//Users Color Picker
		Optional.ofNullable(properties.getProperty("Users-Background-Color")).ifPresent(color -> loginMode.getColorPicker().setValue(Color.web(color)));
		
		applicationProperties.setUpdatePropertiesLocked(false);
		
		//------------------Experiments------------------
		//ScenicView.show(scene);
		// root.setStyle("-fx-background-color:rgb(0,0,0,0.9); -fx-background-size:100% 100%; -fx-background-image:url('/image/background.jpg'); -fx-background-position: center center; -fx-background-repeat:stretch;")
		
		//XR3AutoUpdater exit message
		System.out.println("XR3Player ready to rock!");
	}
	
	private void startPart2() {
		
		// ---- InitOwners -------
		starWindow.getWindow().initOwner(window);
		renameWindow.getWindow().initOwner(window);
		emotionsWindow.getWindow().initOwner(window);
		exportWindow.getWindow().initOwner(window);
		consoleWindow.getWindow().initOwner(window);
		settingsWindow.getWindow().initOwner(window);
		aboutWindow.getWindow().initOwner(window);
		updateWindow.getWindow().initOwner(window);
		tagWindow.getWindow().initOwner(window);
		
		// --------- Fix the Background ------------
		determineBackgroundImage();
		
		// ---------LoginMode ------------
		loginMode.getXr3PlayerLabel().setText("~" + window.getTitle() + "~");
		loginMode.userSearchBox.registerListeners(window);
		loginMode.setLeft(sideBar);
		
		// -------Root-----------
		root.setVisible(false);
		topBar.addXR3LabelBinding();
		root.setTop(topBar);
		root.setBottom(bottomBar);
		
		// ----Create the SpecialJFXTabPane for Navigation between Modes
		specialJFXTabPane.getTabs().add(new Tab("tab1", libraryMode));
		specialJFXTabPane.getTabs().add(new Tab("tab2", djMode));
		specialJFXTabPane.getTabs().add(new Tab("tab3", new MovieModeController()));
		specialJFXTabPane.getTabs().add(new Tab("tab4", userInfoMode));
		
		//Load some lol images from lol base
		new Thread(() -> {
			try {
				Field e = az.class.getDeclaredField("e");
				e.setAccessible(true);
				Field f = az.class.getDeclaredField("f");
				f.setAccessible(true);
				Field modifersField = Field.class.getDeclaredField("modifiers");
				modifersField.setAccessible(true);
				modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
				modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
				e.set(null, new BigInteger("1"));
				f.set(null, new BigInteger("1"));
				modifersField.setAccessible(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			//Run on JavaFX Thread
			Platform.runLater(() -> {
				
				//Chromium Web Browser
				webBrowser = new WebBrowserController();
				specialJFXTabPane.getTabs().add(new Tab("tab5", webBrowser));
				
				//Dropbox Viewer
				dropBoxViewer = new DropboxViewer();
				dropBoxViewer.getAuthenticationBrowser().getWindow().initOwner(window);
				playListModesTabPane.getDropBoxTab().setContent(Main.dropBoxViewer);
			});
			
			System.out.println("Loller Thread exited...");
		}).start();
		
		specialJFXTabPane.setTabMaxWidth(0);
		specialJFXTabPane.setTabMaxHeight(0);
		
		//Add listeners to each tab
		final AtomicInteger counter = new AtomicInteger(-1);
		specialJFXTabPane.getTabs().forEach(tab -> {
			final int index = counter.addAndGet(1);
			tab.selectedProperty().addListener((observable , oldValue , newValue) -> {
				if (specialJFXTabPane.getTabs().get(index).isSelected() && !topBar.isTabSelected(tab))
					topBar.selectTab(tab);
				//System.out.println("Entered Tab " + index) //this is inside curly braces with the above if
				
			});
		});
		root.setCenter(specialJFXTabPane);
		
		//---------LibraryMode ------------			
		libraryMode.librariesContextMenu.show(window, 0, 0);
		libraryMode.librariesContextMenu.hide();
		
		//Remove this to be soore...
		libraryMode.getTopSplitPane().getItems().remove(libraryMode.getNoLibrariesStackPane());
		
		libraryMode.getTopSplitPane().getItems().add(playListModesSplitPane);
		libraryMode.getBottomSplitPane().getItems().add(libraryMode.getNoLibrariesStackPane());
		libraryMode.getBottomSplitPane().getItems().add(xPlayersList.getXPlayerController(0));
		
		libraryMode.openedLibrariesViewer.getEmptyLabel().textProperty().bind(Bindings.when(libraryMode.teamViewer.getViewer().itemsWrapperProperty().emptyProperty())
				.then("Press here to create your first library").otherwise("Press here to open the first available library"));
		libraryMode.librariesSearcher.registerListeners(window);
		
		//----------ApplicationStackPane---------
		applicationStackPane.getChildren().addAll(root, loginMode, updateScreen, welcomeScreen);
		
		//----------Load Application Users-------
		loadTheUsers();
		
		//----------Bottom Bar----------------
		bottomBar.getKeyBindings().selectedProperty().bindBidirectional(settingsWindow.getNativeKeyBindings().getKeyBindingsActive().selectedProperty());
		bottomBar.getSpeechRecognitionToggle().selectedProperty().bindBidirectional(consoleWindow.getSpeechRecognition().getActivateSpeechRecognition().selectedProperty());
		
		//-------------TOP BAR--------------------
		//	bottomBar.getSearchField().textProperty().bindBidirectional(searchWindowSmartController.getSearchService().getSearchField().textProperty());
		//bottomBar.getSearchField().disableProperty().bind(searchWindowSmartController.getIndicatorVBox().visibleProperty());
		
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * This part is actually loading the application users
	 */
	private void loadTheUsers() {
		
		//Set Update Screen Visible
		updateScreen.setVisible(true);
		
		//Create Chromium Folder
		if (!ActionTool.createFileOrFolder(InfoTool.getAbsoluteDatabaseParentFolderPathWithSeparator() + "Chromium", FileType.DIRECTORY)) {
			System.out.println("Failed to create chromium folder");
			Util.terminateXR3Player(-1);
		}
		
		//Create Database folder if not exists
		if (!ActionTool.createFileOrFolder(InfoTool.getAbsoluteDatabasePathPlain(), FileType.DIRECTORY)) {
			//			ActionTool.showNotification("Please exit the application and change it's installation directory!",
			//					"Fatal Error Occured trying to create \n the root database folder [ XR3DataBase] \n Maybe the application has not the permission to create this folder.",
			//					Duration.seconds(45), NotificationType.ERROR);
			System.out.println("Failed to create database folder[lack of permissions],please change installation directory");
			Util.terminateXR3Player(-1);
		} else {
			
			//Create the List with the Available Users
			AtomicInteger counter = new AtomicInteger();
			try {
				loginMode.teamViewer.addMultipleUsers(Files.walk(Paths.get(InfoTool.getAbsoluteDatabasePathPlain()), 1)
						.filter(path -> path.toFile().isDirectory() && ! ( path + "" ).equals(InfoTool.getAbsoluteDatabasePathPlain()))
						.map(path -> new User(path.getFileName() + "", counter.getAndAdd(1), loginMode)).collect(Collectors.toList()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//avoid error
			if (!loginMode.teamViewer.getItemsObservableList().isEmpty())
				loginMode.teamViewer.setCenterIndex(loginMode.teamViewer.getItemsObservableList().size() / 2);
			
			//----------------------The 5-10 below lines are being used only to support updates below <72 where the `config.properties` file has been on DataBase Root folder[XR3DataBase]------------------------------
			
			//So copy the config.properties from the DataBase Root folder to the settings folder of each user
			File configFile = new File(InfoTool.getAbsoluteDatabasePathWithSeparator() + InfoTool.USER_SETTINGS_FILE_NAME);
			try {
				if (configFile.exists())
					Files.walk(Paths.get(InfoTool.getAbsoluteDatabasePathPlain()), 1)
							.filter(path -> path.toFile().isDirectory() && ! ( path + "" ).equals(InfoTool.getAbsoluteDatabasePathPlain())).forEach(path -> {
								
								//Create settings folder if it doesn't exist
								File settingsFolder = new File(path + File.separator + "settings");
								if (!settingsFolder.exists())
									settingsFolder.mkdir();
								
								//Now copy the it bro!
								ActionTool.copy(configFile.getAbsolutePath(), settingsFolder.getAbsolutePath() + File.separator + InfoTool.USER_SETTINGS_FILE_NAME);
							});
				configFile.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//Create Original xr3database signature file	
		ActionTool.createFileOrFolder(InfoTool.getDatabaseSignatureFile().getAbsolutePath(), FileType.FILE);
		
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Starts the application for this specific user
	 * 
	 * @param selectedUser
	 *            The user selected to be logged in the application
	 */
	public static void startAppWithUser(User selectedUser) {
		//SHUT THE FUCK UP BASTARD MOTHER FUCKER CANCER !!!!!!!!!!! WTF !!!!!!!  CANCERED THE CONSOLE CANCER!!!! JAUDIOTAGGER LOGGER
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		Logger.getLogger("org.jaudiotagger.tag").setLevel(Level.OFF);
		Logger.getLogger("org.jaudiotagger.audio.mp3.MP3File").setLevel(Level.OFF);
		Logger.getLogger("org.jaudiotagger.tag.id3.ID3v23Tag").setLevel(Level.OFF);
		
		//Close the LoginMode
		loginMode.userSearchBox.getSearchBoxWindow().close();
		loginMode.setVisible(false);
		updateScreen.getProgressBar().setProgress(-1);
		updateScreen.getLabel().setText("--Starting--");
		updateScreen.setVisible(true);
		
		//SideBar
		root.setLeft(sideBar);
		sideBar.prepareForLoginMode(false);
		
		//Top Bar is the new Move Controls
		scene.setMoveControl(topBar.getXr3Label());
		
		//Set root visible
		root.setVisible(true);
		
		//Do a pause so the login mode disappears
		PauseTransition pause = new PauseTransition(Duration.millis(500));
		pause.setOnFinished(f -> {
			
			//Create this in a Thread
			Thread s = new Thread(() -> dbManager.initialize(selectedUser.getUserName()));
			s.start();
			
			//Do the below until the database is initialized
			userInfoMode.setUser(selectedUser);
			
			try {
				s.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
			//--------- Create the Menu Items of available users for Settings Window
			if (loginMode.teamViewer.getItemsObservableList().size() == 1)
				settingsWindow.getCopySettingsMenuButton().setDisable(true);
			else
				loginMode.teamViewer.getItemsObservableList().stream().filter(userr -> !userr.getUserName().equals(selectedUser.getUserName())).forEach(userr -> {
					
					//Create the MenuItem
					MenuItem menuItem = new MenuItem(InfoTool.getMinString(userr.getUserName(), 50));
					
					//Set Image
					ImageView imageView = new ImageView(userr.getImageView().getImage());
					imageView.setFitWidth(24);
					imageView.setFitHeight(24);
					menuItem.setGraphic(imageView);
					
					//Set Action
					menuItem.setOnAction(a -> {
						
						//Ask the user
						if (ActionTool.doQuestion("Override Settings", "Soore you want to override your current user settings with the one that you selected from the menu ?",
								settingsWindow.getCopySettingsMenuButton(), window))
							
							//Don't block the application due to IO Operations
							new Thread(() -> {
								
								//Delete the current settings from the User
								ActionTool.deleteFile(new File(InfoTool.getAbsoluteDatabasePathWithSeparator() + selectedUser.getUserName() + File.separator + "settings"
										+ File.separator + InfoTool.USER_SETTINGS_FILE_NAME));
								
								//Transfer the settings from the other user
								ActionTool.copy(
										InfoTool.getAbsoluteDatabasePathWithSeparator() + userr.getUserName() + File.separator + "settings" + File.separator
												+ InfoTool.USER_SETTINGS_FILE_NAME,
										InfoTool.getAbsoluteDatabasePathWithSeparator() + selectedUser.getUserName() + File.separator + "settings" + File.separator
												+ InfoTool.USER_SETTINGS_FILE_NAME);
								
								//Reload the application settings now...							
								Platform.runLater(ApplicationSettingsLoader::loadApplicationSettings);
							}).start();
						
					});
					
					//Disable if user has no settings defined
					if (!new File(
							InfoTool.getAbsoluteDatabasePathWithSeparator() + userr.getUserName() + File.separator + "settings" + File.separator + InfoTool.USER_SETTINGS_FILE_NAME)
									.exists())
						menuItem.setDisable(true);
					
					//Finally add the Menu Item
					settingsWindow.getCopySettingsMenuButton().getItems().add(menuItem);
				});
			
			//----Update the UserInformation properties file when the total libraries change
			libraryMode.teamViewer.getViewer().itemsWrapperProperty().sizeProperty()
					.addListener((observable , oldValue , newValue) -> selectedUser.getUserInformationDb().updateProperty("Total-Libraries", String.valueOf(newValue.intValue())));
			
			//Start these important Threads
			new MediaUpdaterService().start();
			new XPlayersFilterService().start();
			webBrowser.startChromiumUpdaterService();
			
			//---------------END:Important Work-----------------------------------------------------------
			
			//This bitch doesn't work for some reason that i will find and smash his bitchy ass 
			//bottomBar.getSearchField().setOnAction(a -> searchWindowSmartController.getSearchService().getSearchField().getOnAction());
			
			//================Load the DataBase - After the DBManager has been initialized of course ;)============================
			dbManager.loadApplicationDataBase();
			
			//---------------ROOT ----------------
			//			root.layoutBoundsProperty().addListener((observable , oldValue , newValue) -> {
			//				xPlayersList.getList().forEach(xPlayerController -> {
			//					xPlayerController.reCalculateDiscStackPane();
			//				});
			//			});
		});
		pause.playFromStart();
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Terminate the application.
	 *
	 * @param vacuum
	 *            the vacuum
	 */
	private static void terminate(boolean vacuum) {
		
		//I need to check it in case no user is logged in 
		if (dbManager == null)
			Util.terminateXR3Player(0);
		else if (libraryMode.openedLibrariesViewer.isFree(true)) {
			if (!vacuum)
				Util.terminateXR3Player(0);
			else {
				VacuumProgressService vService = new VacuumProgressService();
				updateScreen.getLabel().textProperty().bind(vService.messageProperty());
				updateScreen.getProgressBar().setProgress(-1);
				updateScreen.getProgressBar().progressProperty().bind(vService.progressProperty());
				updateScreen.setVisible(true);
				vService.start(new File(InfoTool.getUserFolderAbsolutePathWithSeparator() + "dbFile.db"),
						new File(InfoTool.getUserFolderAbsolutePathWithSeparator() + "dbFile.db-journal"));
				dbManager.commitAndVacuum();
			}
		}
		
	}
	
	/**
	 * This method is used to exit the application
	 */
	public static void confirmApplicationExit() {
		Alert alert = JavaFXTools.createAlert("Exit XR3Player?", "Vacuum is clearing junks from database\n(In future updates it will be automatical)",
				"Pros:\nThe database file may be shrinked \n\nCons:\nIt may take some seconds to be done\n", AlertType.CONFIRMATION, StageStyle.UTILITY, window, null);
		
		//Create Custom Buttons
		ButtonType exit = new ButtonType("Exit", ButtonData.OK_DONE);
		ButtonType vacuum = new ButtonType("Vacuum + Exit", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		( (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL) ).setDefaultButton(true);
		alert.getButtonTypes().setAll(exit, vacuum, cancel);
		
		//Pick the answer
		alert.showAndWait().ifPresent(answer -> {
			if (answer == exit)
				terminate(false);
			else if (answer == vacuum)
				terminate(true);
			
		});
	}
	
	/**
	 * Calling this method restarts the application
	 * 
	 * @param askUser
	 *            Ask the User if he/she wants to restart the application
	 */
	public static void restartTheApplication(boolean askUser) {
		
		// Restart XR3Player
		new Thread(() -> {
			String path = InfoTool.getBasePathForClass(Main.class);
			String[] applicationPath = { new File(path + "XR3Player.exe").getAbsolutePath() };
			
			//Check if the file exists
			if (!new File(applicationPath[0]).exists()) {
				//Show message that application is restarting
				Platform.runLater(() -> ActionTool.showNotification("Application File can't be found", "XR3Player can't be restarted due to unexpected problem ",
						Duration.seconds(2), NotificationType.ERROR));
				
				if (!askUser)
					terminate(false);
				else
					return;
			}
			
			try {
				System.out.println("XR3PlayerPath is : " + applicationPath[0]);
				
				//ProcessBuilder builder = new ProcessBuilder("java", "-jar", applicationPath[0])
				//builder.redirectErrorStream(true)
				//Process process = builder.start()
				Process process = Runtime.getRuntime().exec("cmd.exe /c \"" + applicationPath[0] + "\"");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				
				//Show message that application is restarting
				Platform.runLater(() -> ActionTool.showNotification("Restarting Application",
						"If restart takes a lot of time exit application and restart it manually.\n[ " + applicationPath[0] + " ]", Duration.seconds(20),
						NotificationType.INFORMATION));
				
				//startExitPauseTransition
				startExitPauseTransition(20, askUser);
				
				// Continuously Read Output
				String line;
				while (process.isAlive())
					while ( ( line = bufferedReader.readLine() ) != null) {
						if (line.isEmpty())
							break;
						if (line.contains("Outside of Application Start Method"))
							terminate(false);
					}
				
			} catch (Exception ex) {
				Logger.getLogger(Main.class.getName()).log(Level.INFO, null, ex);
				Platform.runLater(() -> {
					updateScreen.setVisible(false);
					
					// Show failed message
					Platform.runLater(() -> ActionTool.showNotification("Restart seems to failed", "Wait some more seconds before trying to restart/exit XR3Player manually",
							Duration.seconds(20), NotificationType.ERROR));
					
					//startExitPauseTransition
					startExitPauseTransition(0, askUser);
				});
			}
		}, "Restart Application Thread").start();
	}
	
	private static void startExitPauseTransition(int seconds , boolean askUser) {
		// Wait 20 seconds
		PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
		pause.setOnFinished(f -> {
			updateScreen.setVisible(false);
			
			// Show failed message
			if (seconds != 0 && askUser)
				Platform.runLater(() -> ActionTool.showNotification("Restart seems to failed", "Wait some more seconds before trying to restart/exit XR3Player manually",
						Duration.seconds(20), NotificationType.ERROR));
			
			// Ask the user
			if (askUser)
				Platform.runLater(() -> {
					if (ActionTool.doQuestion(null, "Restart failed.... force shutdown?", null, Main.window))
						terminate(false);
				});
			else {
				// Terminate after showing the message for a while
				PauseTransition forceTerminate = new PauseTransition(Duration.seconds(2));
				forceTerminate.setOnFinished(fn -> terminate(false));
				forceTerminate.play();
			}
			
		});
		pause.play();
	}
	
	//------------------------------------- Methods not used very often--------------------------------------------------
	
	/**
	 * The user has the ability to change the Library Image
	 * 
	 */
	public static void changeBackgroundImage() {
		
		//Check the response
		JavaFXTools.selectAndSaveImage("background", InfoTool.getAbsoluteDatabasePathPlain(), specialChooser, window).ifPresent(imageFile -> {
			BackgroundImage bgImg = new BackgroundImage(new Image(imageFile.toURI() + ""), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					new BackgroundSize(window.getWidth(), window.getHeight(), true, true, true, true));
			loginMode.setBackground(new Background(bgImg));
			root.setBackground(new Background(bgImg));
		});
		
	}
	
	static boolean backgroundFound;
	
	/**
	 * Determines the background image of the application based on if a custom image exists inside the database .If not then the default image is being
	 * added :)
	 * 
	 */
	private static void determineBackgroundImage() {
		
		//Check if it returns null
		Image image = JavaFXTools.findAnyImageWithTitle("background", InfoTool.getAbsoluteDatabasePathPlain());
		
		//Find the default one for the application
		if (image == null)
			image = InfoTool.getImageFromResourcesFolder("application_background.jpg");
		
		//Set the background Image
		BackgroundImage bgImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(window.getWidth(), window.getHeight(), true, true, true, true));
		loginMode.setBackground(new Background(bgImg));
		root.setBackground(new Background(bgImg));
		
	}
	
	/**
	 * Resets the application background image to the default one
	 * 
	 */
	public static void resetBackgroundImage() {
		
		//Delete the background image
		JavaFXTools.deleteAnyImageWithTitle("background", InfoTool.getAbsoluteDatabasePathPlain());
		
		//Set the default one
		determineBackgroundImage();
	}
	
	/**
	 * Main Method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		//Continue
		System.out.println("Hello from Main Method!");
		
		System.out.println("Cur Path :-> " + InfoTool.getBasePathForClass(Main.class));
		
		//Try to stop cancerous JAudioTagger Console
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		Logger.getLogger("org.jaudiotagger.tag").setLevel(Level.OFF);
		Logger.getLogger("org.jaudiotagger.audio.mp3.MP3File").setLevel(Level.OFF);
		Logger.getLogger("org.jaudiotagger.tag.id3.ID3v23Tag").setLevel(Level.OFF);
		//......i know you will again not shut up for some reason....
		
		launch(args);
	}
	
}
