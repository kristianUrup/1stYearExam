/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Department;
import endgame.BLL.DepartmentManager;
import endgame.BE.Order;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
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
    PostItController pic;
    
    @FXML
    private Label departName;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FlowPane flowPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        dMan = new DepartmentManager();
        departName.setText(dMan.getDepartment().getName());
    }    
    
    public void makeList() throws IOException
    {
        List<Order> orders = new ArrayList();
        
        for (Order order : orders)
        {
            
        }
    }
    
    public void openFXML(Order order) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/Teacher/AbsenceSummary.fxml"));
            Parent root = (Parent) loader.load();
            pic = loader.getController();
            pic.setOrderInfo(dMan.getOrder);
        
    }
    
}
