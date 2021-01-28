package de.failender.dgo.client.configure.tokens;

import de.failender.dgo.integration.HeldenService;
import de.failender.dgo.integration.user.UserRegistration;
import de.failender.dgo.integration.user.UserService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import static de.failender.ezql.EzqlConnector.singleRequest;

public class ConfigureTokenView extends VBox {

    private TableView<UserEntity> tableView;

    public ConfigureTokenView() {

        Button button = new Button("User anlegen");
        button.setOnAction(click -> addUser());
        getChildren().add(button);

        tableView = new TableView();
        TableColumn<UserEntity, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(nameColumn);

        TableColumn<UserEntity, String> tokenColumn = new TableColumn<>("Token");
        tokenColumn.setCellValueFactory(new PropertyValueFactory<>("token"));
        tableView.getColumns().add(tokenColumn);


        getChildren().add(tableView);
        this.loadUser();

    }

    private void loadUser() {

        tableView.getItems().setAll(singleRequest(UserRepositoryService::findAll));
    }

    private void addUser() {
        Dialog dialog = new Dialog();
        dialog.setTitle("User anlegen");
        VBox vBox = new VBox();

        TextField name = new TextField("Name");
        TextField token = new TextField("cead7ff39138dfb94171f19d8b46a487a4f1f53ad120ce819d6c0d86787b8c65");
        Window window = dialog.getDialogPane().getScene().getWindow();
        Button confirm = new Button("Ok");
        confirm.setOnAction(click -> {
            UserRegistration userRegistration = new UserRegistration(name.getText(), null, token.getText(), "Standard");
            try {
                HeldenService.SynchronizationResult result = singleRequest(() -> UserService.registerUser(userRegistration));
                window.hide();
                loadUser();


            } catch (Exception e) {
                System.out.println("Helden konnten nicht geladen werden");
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fehler beim anlegen, wahrscheinlich ist das Token falsch");
                alert.show();
            }

        });//cead7ff39138dfb94171f19d8b46a487a4f1f53ad120ce819d6c0d86787b8c65

        vBox.getChildren().addAll(name, token, confirm);

        dialog.getDialogPane().setContent(vBox);

        window.setOnCloseRequest(event -> window.hide());
        dialog.show();


    }
}
