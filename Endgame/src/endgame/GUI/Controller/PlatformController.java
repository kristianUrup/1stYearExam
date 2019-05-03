/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Department;
import endgame.BLL.DepartmentManager;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import endgame.BLL.FileManager;
import endgame.DAL.Exception.DalException;
import endgame.GUI.Model.OrderModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author Kristian Urup laptop
 */
public class PlatformController implements Initializable
{

    private DepartmentManager dMan;
    private Department dep;
    private FileManager fMan;
    private OrderModel OM;

    @FXML
    private Label departName;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FlowPane flowPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            OM = new OrderModel();
            dMan = new DepartmentManager();
            fMan = new FileManager();
            dep = dMan.getDepartment(fMan.getConfig());
            departName.setText(dep.getName());
            setPostItNotes();

        } catch (BllException | IOException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setPostItNotes()
    {
        try
        {
            List<Order> orders = OM.getAllOrders(dep);

            for (Order order : orders)
            {
                
                try
                {
                    if(!order.getIsDone())
                    {
                    openFXML(order);
                    }
                } catch (IOException ex)
                {
                    Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openFXML(Order order) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/PostIt.fxml"));
        Parent root = (Parent) loader.load();
        PostItController pic = loader.getController();
        pic.setOrderInfo(order);
        pic.setDepartment(dep);
        flowPane.getChildren().add(root);
        
        pic.getButton().setOnAction(e->{
            try
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dialog");
                alert.setHeaderText("You are about to set this task to done");
                alert.setContentText("Are you sure you want to do this?");

                String header = "You are about to set this task to done";
                String content = "Are you sure you want to do this?";
                Optional<ButtonType> result =alert.showAndWait();
                if((result.isPresent()) && (result.get()==ButtonType.OK))
                {
                    pic.setDone();
                    flowPane.getChildren().remove(root);
                }

            } catch (BllException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        });
    }
    

}
