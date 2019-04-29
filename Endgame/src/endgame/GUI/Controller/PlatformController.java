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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;


/**
 *
 * @author Kristian Urup laptop
 */
public class PlatformController implements Initializable
{
    DepartmentManager dMan;
    Department dep;
    FileManager fMan;
    PostItController pic;
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
        try
        {
            fMan = new FileManager();
        } catch (IOException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            departName.setText(dMan.getDepartment(fMan.getConfig()).getName());
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void makeList()
    {
        try
        {
            Department d = new Department(1, "test");
            List<Order> orders = OM.getAllOrders(d);
            
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
        flowPane.getChildren().add(root);
        
    }
    
}
