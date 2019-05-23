/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endgame.GUI.Controller;

import endgame.BE.Department;
import endgame.BE.Order;
import endgame.BLL.Exception.BllException;
import endgame.GUI.Model.OrderModel;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.ImageIcon;

/**
 * FXML Controller class
 *
 * @author Kristian Urup laptop
 */
public class PostItController implements Initializable
{

    Department department;
    Order ordersForDepartment;
    PlatformController pfcontroller;
    OrderModel OMO;
    ExpandedPostItNoteController epinc;

    @FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lblDeliveryDate;
    @FXML
    private AnchorPane anchorPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        try
        {
            epinc = new ExpandedPostItNoteController();
            pfcontroller = new PlatformController();
            OMO = new OrderModel();
        } catch (BllException ex)
        {
            OMO.setLastActivity(ordersForDepartment, department, ex.getMessage());
        }

    }

    public void setOrderInfo(Order order)
    {
        ordersForDepartment = order;
        lblOrderNumber.setText(order.getOrderNumber());
        lblCustomer.setText(order.getCustomer());
        Date date = order.getDeliveryDate();
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String output = outputFormatter.format(date);
        lblDeliveryDate.setText(output);
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }


    public void showDeliveryDate(Order order) throws BllException
    {
        lblDeliveryDate.setText(ordersForDepartment.getDeliveryDate().toString());

        Date date = ordersForDepartment.getDeliveryDate();

        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String output = outputFormatter.format(date);

        lblDeliveryDate.setText(output);
    }

    private void handleMouseAnchorPane(MouseEvent event) throws IOException
    {
        
//        ObservableList<Node> allNodes = anchorPane.getChildren();
//        BoxBlur blur = new BoxBlur();
//        blur.setWidth(25);
//        blur.setHeight(25);
//        for (Node node : allNodes)
//        {
//            node.setEffect(blur);
//        }
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/ExpandedPostItNote.fxml"));
//        Parent root = (Parent) loader.load();
//        ExpandedPostItNoteController epincontroller = loader.getController();
//        epincontroller.setDepartment(department);
//        epincontroller.setOrderInfo(ordersForDepartment);
//        pfcontroller.getFlowPane().getChildren().add(root);
//        epinc.getButton().setOnAction(e ->
//        {
//            try
//            {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Dialog");
//                alert.setHeaderText("You are about to set this task to done");
//                alert.setContentText("Are you sure you want to do this?");
//
//                String header = "You are about to set this task to done";
//                String content = "Are you sure you want to do this?";
//                Optional<ButtonType> result = alert.showAndWait();
//                if ((result.isPresent()) && (result.get() == ButtonType.OK))
//                {
//                    epinc.setDone();
//                    pfcontroller.getFlowPane().getChildren().remove(root);
//                }
//
//            } catch (BllException ex)
//            {
//                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        });

        
    }
    
    public AnchorPane getAnchorPane()
    {
        return anchorPane;
    }

    
}