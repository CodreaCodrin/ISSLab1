package iss.Controllers;

import iss.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BossController implements BossObserver {
    Stage stage;
    Service service;
    Boss user;

    public void setService(Service service, Boss user) {
        this.service = service;
        this.user = user;

        loadData(service.getAllPresentWorkers());
        service.addBossObserver(this);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void update(PresentWorker worker) {
        PresentWorker existingWorker = model.stream()
                .filter(w -> w.getId() == worker.getId())
                .findFirst()
                .orElse(null);

        if (existingWorker != null) {
            model.remove(existingWorker);
            Alert info= new Alert(Alert.AlertType.INFORMATION, "O plecat " + worker.getWorker().getName(), new ButtonType("Ok"));
            info.show();
        } else {
            model.add(worker);
        }

        workerTableView.refresh();
    }

    ObservableList<PresentWorker> model = FXCollections.observableArrayList();
    @FXML
    private TableView<PresentWorker> workerTableView;
    @FXML
    private TableColumn<PresentWorker, String> nameColumn;
    @FXML
    private TableColumn<PresentWorker, String> timeColumn;

    private void loadData(Iterable<PresentWorker> data) {
        model.clear();
        data.forEach(model::add);
        workerTableView.setItems(model);
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWorker().getName()));
        timeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStringTime()));

        workerTableView.setItems(model);
    }

    @FXML
    private TextField taskField;
    @FXML
    private void hadleSendTask() {
        PresentWorker selectedWorker = workerTableView.getSelectionModel().getSelectedItem();
        if (selectedWorker != null) {
            service.sendTask(selectedWorker.getId(), new Task(taskField.getText()));
        }
        else return;

        Alert info= new Alert(Alert.AlertType.INFORMATION, "Task sent", new ButtonType("Ok"));
        info.show();
    }

    @FXML
    private void handleRegisterWorker() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
            Parent root = loader.load();

            Stage registerStage = new Stage();
            registerStage.setTitle("Register Worker");
            registerStage.setScene(new Scene(root));
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.initOwner(stage);

            RegisterController registerController = loader.getController();
            registerController.setService(service);
            registerController.setStage(registerStage);

            registerStage.showAndWait();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error loading register view", new ButtonType("Ok"));
            errorAlert.show();
        }
    }

    @FXML
    private void handleLogout(){
        service.removeBossObserver(this);
        stage.close();
    }
}
