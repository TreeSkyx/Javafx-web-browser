import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

public class TestWeb extends Application {

    @FXML
    private WebView webView = new WebView(); // Create WebView

    @FXML
    private TextField textField;

    @FXML
    private WebEngine engine; // Create WebEngine

    /* Create button */ 
    private Button btGoForward = new Button("Next");
    private Button btฺBackForward = new Button("Back");
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

        pane.setPadding(new Insets(5));

        pane.setHgap(5);
        pane.setVgap(5);
        search.setPrefColumnCount(25);
        tab.getChildren().addAll(btฺBackForward, btGoForward, btZoomIn, btZoomOut, search, btEnter, btSource);
        tab.setAlignment(Pos.TOP_CENTER);
        pane.add(tab, 0, 0);

        pane.setAlignment(Pos.CENTER);

        engine = webView.getEngine();
        engine.load("http://www.google.com");

        pane.add(webView, 0, 2);

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

        Scene scene = new Scene(pane, 800, 650);
        primaryStage.setTitle("Web");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void back() {
        WebHistory history = engine.getHistory();
        history.go(-1);
    }

    public void next() {
        WebHistory history = engine.getHistory();
        history.go(+1);
    }

    public void reload() {
        WebHistory history = engine.getHistory();
        history.go(0);

    }

    public void searchEngine() {
        String htLink = "http://";
        String input = search.getText();
        engine.load(htLink + input);
    }
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