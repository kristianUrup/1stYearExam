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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author Kristian Urup laptop
 */
public class PlatformController implements Initializable
{

    private Department dep;
    private OrderModel OM;
    private List<String> orderNumbers;
    private Order order;

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
            dep = OM.getDepartment(OM.getConfig());
            departName.setText(dep.getName());
            orderNumbers = new ArrayList<>();
            setPostItNotes();
            updatePostItNotes();

        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setPostItNotes() throws BllException
    {
        List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
        for (Order order : orders)
        {
            try
            {
                if (!orderNumbers.contains(order.getOrderNumber()))
                {
                    openFXML(order);
                    flowPane.setVgap(10);
                    flowPane.setHgap(10);
                    orderNumbers.add(order.getOrderNumber());
                }
            } catch (IOException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        orders.clear();
    }

    public void updatePostItNotes()
    {
        TimerTask repeatedTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Platform.runLater(() ->
                {
                    try
                    {
                        setPostItNotes();
                    } catch (BllException ex)
                    {
                        Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        };
        Timer timer = new Timer();

        long delay = 5000L;
        long period = 5000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    public void openFXML(Order order) throws IOException
    {
        Thread t = new Thread(()->{
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/PostIt.fxml"));
                Parent root = (Parent) loader.load();
                PostItController pic = loader.getController();
                pic.setDepartment(dep);
                pic.setOrderInfo(order);
                
                pic.getButton().setOnAction(e ->
                {
                    try
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Dialog");
                        alert.setHeaderText("You are about to set this task to done");
                        alert.setContentText("Are you sure you want to do this?");
                        
                        String header = "You are about to set this task to done";
                        String content = "Are you sure you want to do this?";
                        Optional<ButtonType> result = alert.showAndWait();
                        if ((result.isPresent()) && (result.get() == ButtonType.OK))
                        {
                            pic.setDone();
                            flowPane.getChildren().remove(root);
                        }
                        
                    } catch (BllException ex)
                    {
                        Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                Platform.runLater(()->flowPane.getChildren().add(root));
            } catch (IOException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }
    
    private void orderList(List<Order> orders){
        
        for (Order order : orders)
        {
            if (!orderNumbers.contains(order.getOrderNumber()))
            {
                try
                {
                    openFXML(order);
                    orderNumbers.add(order.getOrderNumber());
                } catch (IOException ex)
                {
                    Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void updateUI(List<Order> orders){
        
        flowPane.getChildren().clear();
        orderNumbers.clear();
        orderList(orders);
    }
    
    @FXML
    private void sortByEndDateAsc(ActionEvent event)
    {
        Thread t = new Thread(()->{
        try
        {
            List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
            OM.endDateSortedByAsc(orders);
            Platform.runLater(
                    ()->updateUI(orders));
            
            
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
        });
        t.start();
    }

    @FXML
    private void sortByEndDateDesc(ActionEvent event)
    {
        Thread t = new Thread(()->{
        try
        {
            List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
            OM.endDateSortedByDesc(orders);
            Platform.runLater(
                    ()->updateUI(orders));
            
            
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
        });
        t.start();
    }

    @FXML
    private void sortByDefault(ActionEvent event)
    {
        Thread t = new Thread(()->{
        try
        {
            List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
            Platform.runLater(
                    ()->updateUI(orders));
            
            
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
        });
        t.start();
    }

}
