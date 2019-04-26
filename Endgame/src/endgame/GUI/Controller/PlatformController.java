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
            openFXML();
            FlowPane loadedPane = FXMLLoader.load(getClass().getResource("/endgame/GUI/View/PostIt.fxml"));
            flowPane.getChildren().add(loadedPane);
        }
    }
    
    public void openFXML()
    {
        
    }
    
}
