package iss.Controllers;

import iss.Service;
import iss.Worker;
import iss.WorkerObserver;
import iss.Task;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WorkerController implements WorkerObserver {
    Stage stage;
    Service service;
    Worker user;

    public void setService(Service service, Worker user) {
        this.service = service;
        this.user = user;

        service.addWorkerObserver(this);
        taskTableView.setItems(model);
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(this::handleWindowClose);
    }

    @Override
    public void update(int id, Task task) {
        if(user.getId() != id) return;

        user.addTask(task);
        model.add(task);
        taskTableView.refresh();
    }

    ObservableList<Task> model = FXCollections.observableArrayList();
    @FXML
    private TableView<Task> taskTableView;
    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private void initialize() {
        descriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    @FXML
    private void handleTaskFinished(){
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if(selectedTask != null){
            user.finishTask(selectedTask);
            model.remove(selectedTask);
        }
        else return;
    }

    private void handleWindowClose(WindowEvent windowEvent) {
        this.logOut();
    }
    @FXML
    public void logOut(){
        service.logoutWorker(user.getId());
        service.removeWorkerObserver(this);
        stage.close();
    }
}
