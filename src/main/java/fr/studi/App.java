package fr.studi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{


    @Override
    public void start(Stage stage) throws Exception {
            try {
                //chargement du fichier FXML
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("/meteo.fxml"));
                Parent root = loader.load();

                //création et initialisation de la scene
                Scene scene = new Scene(root);

                // ajouter ma scene à l'execution en cours
                stage.setTitle("Meteo App");
                stage.setScene(scene);
                stage.show();

            }catch(Exception e) {
                e.printStackTrace();
            }
    }
}
