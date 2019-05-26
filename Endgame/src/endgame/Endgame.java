/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame;

import endgame.GUI.Controller.PostItController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Kristian Urup laptop
 */
public class Endgame extends Application
{
    PostItController pic = new PostItController();

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/endgame/GUI/View/Platform.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//          public void handle(WindowEvent we) {
//                System.exit(0);
//          }
//      });  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
