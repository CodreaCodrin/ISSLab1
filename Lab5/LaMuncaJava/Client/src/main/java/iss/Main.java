package iss;

import iss.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/ClientConfig.xml");
        Service service = context.getBean(Service.class);

        initView(stage, service);
        stage.setTitle("LaMunca");
        stage.show();
    }

    private void initView(Stage stage, Service service) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        VBox loginView = fxmlLoader.load();
        Scene scene = new Scene(loginView);

        stage.setScene(scene);

        LoginController controller = fxmlLoader.getController();
        controller.setService(service);
    }

    public static void main(String[] args) {
        launch();
    }
}