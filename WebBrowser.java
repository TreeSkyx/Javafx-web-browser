import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowser extends Application {

    @FXML
    private WebView webView = new WebView(); // Create WebView

    @FXML
    private TextField textField;

    @FXML
    private WebEngine engine; // Create WebEngine

    // Create Navigation bar
    private Button btGoForward = new Button("->");
    private Button btฺBackForward = new Button("<-");
    private Button btReload = new Button("Reload");
    private Button btZoomIn = new Button("+");
    private Button btZoomOut = new Button("-");
    private Button btEnter = new Button("Search");
    private Button btSource = new Button("View Source");
    private TextField search = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        HBox tab = new HBox(35);
        WebView webView = new WebView();
        
        // setStyle for Navigation bar
        tab.setStyle("-fx-padding: 2;" +
        "-fx-border-style: solid inside;" +
        "-fx-border-width: 2;" +
        "-fx-border-insets: 5;" +
        "-fx-border-radius: 5;" +
        "-fx-border-color: blue;" +
        "-fx-color: lightblue;");

        search.setPrefColumnCount(25);
        tab.getChildren().addAll(btฺBackForward, btGoForward, btZoomIn, btZoomOut,btReload, search, btEnter, btSource);  
        pane.add(tab, 0, 0);
        pane.setAlignment(Pos.CENTER);
        
        engine = webView.getEngine();
        // Load the Google web page (Homepage)
        engine.load("https://www.google.com");

        // Check web loading progess
        engine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue)->{
            if(newValue == Worker.State.SUCCEEDED){
                System.out.println("Page has been loaded");
                WebHistory history = engine.getHistory();
                ObservableList<WebHistory.Entry> entries = history.getEntries(); // set new url for serach bar
        search.setText(entries.get(history.getCurrentIndex()).getUrl());
            }else if(newValue == Worker.State.FAILED){
                System.out.println("Loading failed");
            }
        });

        // Add pane
        pane.add(webView, 0, 2);
        
        // Change title for specified webpage
        webView.getEngine().titleProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> ov,
                    final String oldvalue, final String newvalue) 
            {
                primaryStage.setTitle(newvalue);
            }
        });

        // Set button aciton
        btฺBackForward.setOnAction(e -> back());
        btGoForward.setOnAction(e -> next());
        btZoomIn.setOnAction(e -> webView.setZoom(webView.getZoom() + 0.25));
        btZoomOut.setOnAction(e -> webView.setZoom(webView.getZoom() - 0.25));
        btEnter.setOnAction(e -> searchEngine());
        btSource.setOnAction(e -> {
            try {
                viewSource();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        btReload.setOnAction(e-> engine.reload());

        // Create the scence
        Scene scene = new Scene(pane, 850, 650);
        // Set scence title
        primaryStage.setTitle("Web");
        // Add scence to stage
        primaryStage.setScene(scene);
        // Display the scence
        primaryStage.show();

    }
    // Go back medthod
    public void back() {
        WebHistory history = engine.getHistory();
        history.go(-1);
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        search.setText(entries.get(history.getCurrentIndex()).getUrl());
    }
    // Go next medthod
    public void next() {
        WebHistory history = engine.getHistory();
        history.go(+1);
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        search.setText(entries.get(history.getCurrentIndex()).getUrl());
    }
    // Reload medthod
    public void reload() {
        WebHistory history = engine.getHistory();
        history.go(0);
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        search.setText(entries.get(history.getCurrentIndex()).getUrl());

    }
    // Search medthod
    public void searchEngine() {
        String htLink = "https://";
        String input = search.getText();
        engine.load(htLink + input);
    }

    // View page source medthod (HTML page source)
    public void viewSource() throws IOException{
        String currentLink = engine.getLocation();
        System.out.println(getURLSource(currentLink));
    }
    public static String getURLSource(String url) throws IOException
    {
        URL urlObject = new URL(url);
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        return toString(urlConnection.getInputStream());
    }
    private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}