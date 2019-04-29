/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Department;
import endgame.BLL.DepartmentManager;
import endgame.BE.Order;
import endgame.GUI.Model.OrderModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


/**
 *
 * @author Kristian Urup laptop
 */
public class PlatformController implements Initializable
{
    DepartmentManager dMan;
    Department dep;
    OrderModel OM;
    
    @FXML
    private Label departName;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FlowPane flowPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        OM = new OrderModel();
        dMan = new DepartmentManager();
        departName.setText(dMan.getDepartment().getName());
        makeList();
    }    
    
    public void makeList()
    {
        List<Order> orders = new ArrayList();
        orders.add(OM.getOrder());
        
        for (Order order : orders)
        {
            try
            {
                openFXML(order);
            } catch (IOException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void openFXML(Order order) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/PostIt.fxml"));
        Parent root = (Parent) loader.load();
        PostItController pic = loader.getController();
        pic.setOrderInfo(order);
        flowPane.getChildren().add(root);
        
    }
    
}
