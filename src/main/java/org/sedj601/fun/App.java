package org.sedj601.fun;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application
{
    @Override
    public void start(Stage stage)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = fxmlLoader.load();
            PrimaryController primaryController = fxmlLoader.getController();
            primaryController.initHostServices(getHostServices());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}