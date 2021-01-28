package de.failender.dgo.client;

import de.failender.dgo.integration.Beans;
import de.failender.dgo.integration.HeldenService;
import de.failender.dgo.persistance.gruppe.GruppeRepositoryService;
import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.ezql.EzqlConnector;
import de.failender.fantasygrounds.CampaignInformation;
import de.failender.fantasygrounds.Character;
import de.failender.fantasygrounds.FantasyGroundsConverterService;
import de.failender.heldensoftware.api.requests.ReturnHeldDatenWithEreignisseRequest;
import de.failender.heldensoftware.xml.datenxml.Daten;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.failender.ezql.EzqlConnector.singleRequest;

public class HeldenView extends VBox {


    private TableView<HeldEntity> heldEntityTableView;


    public HeldenView() {

        heldEntityTableView = new TableView();
        heldEntityTableView.setEditable(true);
        TableColumn<HeldEntity, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        heldEntityTableView.getColumns().add(nameColumn);

        TableColumn<HeldEntity, Boolean> activeColumn = new TableColumn<>("Aktiv");
        activeColumn.setCellValueFactory(value -> new SimpleBooleanProperty(value.getValue().isActive()));
        activeColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        TableColumn<HeldEntity, HeldEntity> toggleColumn = new TableColumn<>("Toggle");
        toggleColumn.setCellFactory(tc -> new ButtonCell());
        toggleColumn.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));


        heldEntityTableView.getColumns().add(activeColumn);
        heldEntityTableView.getColumns().add(toggleColumn);
        Button updateCampaign = new Button("Kampagne aktualisieren");
        updateCampaign.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(this.getScene().getWindow());
            if (file != null) {
                try {
                    updateDbXml(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button updateHelden = new Button("Helden aktualisieren");
        updateHelden.setOnAction(e -> {
            EzqlConnector.allocateConnection();
            List<HeldenService.SynchronizationResult> results = UserRepositoryService.findAll()
                    .stream().map(HeldenService::updateHeldenForUser)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            EzqlConnector.releaseConnection();
            int created = results.stream().map(HeldenService.SynchronizationResult::getCreated).reduce(0, (a, b) -> a + b);
            int updated = results.stream().map(HeldenService.SynchronizationResult::getUpdated).reduce(0, (a, b) -> a + b);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Updated: " + updated + "Created: " + created);
            alert.show();
        });


        getChildren().addAll(heldEntityTableView, updateCampaign, updateHelden);

        loadHelden();

    }

    private void updateDbXml(File file) throws IOException {
        FileUtils.copyFile(file, new File(file.getParent(), "db" + System.currentTimeMillis() + ".xml"));
        CampaignInformation campaignInformation = FantasyGroundsConverterService.parseCampaignInformation(new FileInputStream(file));
        List<Daten> heldenDaten = new ArrayList<>();
        List<Character> exportCharacters = new ArrayList<>();
        List<HeldEntity> activeHelden = heldEntityTableView.getItems().stream().filter(HeldEntity::isActive).collect(Collectors.toList());
        for (HeldEntity character : activeHelden) {
            VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(HeldRepositoryService.findById(character.getId()));
            Daten daten = Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(character.getId(), null, versionEntity.getCacheId())).block();
            heldenDaten.add(daten);
            //if (character.getCampaignId() != null) {
            campaignInformation.getCharacters().stream().filter(entry -> entry.getId() == character.getId()).findFirst().ifPresent(campaignCharacter -> {
                exportCharacters.add(campaignCharacter);
                campaignCharacter.setName(daten.getAngaben().getName());
            });
            //}
        }
        String xml = FantasyGroundsConverterService.convert(heldenDaten, campaignInformation.getXml(), exportCharacters);
        FileUtils.write(file, xml, "UTF-8");

    }

    private void loadHelden() {
        heldEntityTableView.getItems().setAll(singleRequest(() ->
                HeldRepositoryService.findByGruppeId(GruppeRepositoryService.findAll().get(0).getId(), true, true)));
    }

    class ButtonCell extends TableCell<HeldEntity, HeldEntity> {
        final Button button = new Button("Toggle");

        ButtonCell() {
            setGraphic(button);
            button.setOnAction(arg0 -> {
                HeldEntity heldEntity = getItem();
                if (heldEntity == null) {
                    return;
                }
                EzqlConnector.allocateConnection();
                HeldRepositoryService.updateActive(heldEntity, !heldEntity.isActive());
                EzqlConnector.releaseConnection();
                loadHelden();
            });
        }


    }
}
