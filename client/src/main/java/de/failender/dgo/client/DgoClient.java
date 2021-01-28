package de.failender.dgo.client;

import de.failender.dgo.client.configure.tokens.ConfigureTokenView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DgoClient extends Application {

    private VBox vBox;
    private StackPane root;

    @Override
    public void start(Stage primaryStage) {

        root = new StackPane();

        vBox = new VBox(setupMenuBar());
        setContentArea(new HeldenView());

        Scene scene = new Scene(vBox, 1000, 500);
        vBox.getChildren().add(root);

        primaryStage.setTitle("DSA Gruppen Online!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setContentArea(Node node) {
        root.getChildren().clear();
        root.getChildren().add(node);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private MenuBar setupMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu configureMenu = new Menu("Konfigurieren");
        menuBar.getMenus().add(configureMenu);

        MenuItem accesTokens = new MenuItem("Zugriffstokens");
        accesTokens.setOnAction(event -> setContentArea(new ConfigureTokenView()));
        configureMenu.getItems().add(accesTokens);

        MenuItem helden = new MenuItem("Helden");
        helden.setOnAction(event -> setContentArea(new HeldenView()));
        configureMenu.getItems().add(helden);

//        MenuItem campaigns = new MenuItem("Kampagnen");
//        campaigns.setOnAction(event -> setContentArea(new ConfigureCampaignsView()));
//        configureMenu.getItems().add(campaigns);


        return menuBar;
    }
}
