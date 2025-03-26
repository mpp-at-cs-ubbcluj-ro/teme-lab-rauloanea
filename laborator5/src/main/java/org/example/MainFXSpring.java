package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.ctrl.ComputerRepairShopController;
import org.example.services.ComputerRepairServices;
import org.example.services.ServicesException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainFXSpring extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RepairShopWindow.fxml"));

            ComputerRepairShopController ctrl = new ComputerRepairShopController();
            ctrl.setService(getService()); // Manually inject the service
            loader.setController(ctrl);

            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Computer Repairs Shop");
            primaryStage.show();
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app "+e);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    static ComputerRepairServices getService() throws ServicesException {
        // pentru configurare folosind XML (bd + fisiere)
//        ApplicationContext context=new ClassPathXmlApplicationContext("RepairShopConfig.xml");

        //pentru configurare folosind JavaConfig (bd + fisiere)
        ApplicationContext context=new AnnotationConfigApplicationContext(RepairShopConfig.class);
        ComputerRepairServices services=context.getBean(ComputerRepairServices.class);
        return services;

    }
}