/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import endgame.GUI.Model.OrderModel;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class PostItController implements Initializable
{
    Order ordersForDepartment;
    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblDeliveryDate;
    @FXML
    private Label lblLastActive;
    @FXML
    private ProgressBar estimatedProgress;
    
    OrderModel OMO;
    @FXML
    private Label lblEstimatedStartDate;
    @FXML
    private Label lblEstimatedEndDate;
    @FXML
    private Label lblEstimatedTimeUsed;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            OMO = new OrderModel();
            //setProgressBar();
        } catch (BllException ex)
        {
            Logger.getLogger(PostItController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setOrderInfo(Order order)
    {
        ordersForDepartment = order;
        lblOrderNumber.setText(ordersForDepartment.getOrderNumber());
        lblCustomer.setText(ordersForDepartment.getCustomer());
        
        Date date = ordersForDepartment.getDeliveryDate();
        
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String output = outputFormatter.format(date);
        
        lblDeliveryDate.setText(output);
        
        setProgressBar();
    }

    @FXML
    private void handleDoneBtn(ActionEvent event)
    {
        lblDeliveryDate.setText(ordersForDepartment.getDeliveryDate().toString());
        
        Date date = ordersForDepartment.getDeliveryDate();
        
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String output = outputFormatter.format(date);
        
        lblDeliveryDate.setText(output);
    }
    
    public void setProgressBar()
    {
        try
        {
            estimatedProgress.setProgress(OMO.getProgressedTimeInProcent(ordersForDepartment));
        } catch (BllException ex)
        {
            Logger.getLogger(PostItController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
