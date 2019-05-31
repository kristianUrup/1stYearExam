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
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Kristian Urup laptop
 */
public class PlatformController implements Initializable
{

    private Department dep;
    private OrderModel OM;
    private List<String> orderNumbers;

    @FXML
    private Label departName;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FlowPane flowPane;
    @FXML
    private ComboBox<Department> comboDepartment;
    @FXML
    private BorderPane borderPane;

    /**
     * initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            comboDepartment.setVisible(false);
            OM = new OrderModel();
            dep = OM.getDepartment(OM.getConfig());
            departName.setText(dep.getName());
            orderNumbers = new ArrayList<>();
            setPostItNotes();
            updatePostItNotes();
            readJsonFile();
            setManagement();
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sets all of the post it notes, 
     * and using order to set all of the data
     * and setting the right size of the List
     * 
     * @throws BllException 
     */
    public void setPostItNotes() throws BllException
    {
        List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
        for (Order order : orders)
        {
            if (!orderNumbers.contains(order.getOrderNumber()))
            {
                openFXML(order);
                flowPane.setVgap(10);
                flowPane.setHgap(10);
                orderNumbers.add(order.getOrderNumber());
            }
        }
        orders.clear();
    }

    /**
     * Uses a thread to update all of the Post It notes. 
     */
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

    private void orderList(List<Order> orders)
    {
        Thread t = new Thread(() ->
        {
            for (Order order : orders)
            {
                if (!orderNumbers.contains(order.getOrderNumber()))
                {
                    openFXML(order);
                    orderNumbers.add(order.getOrderNumber());
                }
            }
        });
        t.start();
    }

    private void updateUI(List<Order> orders)
    {
        flowPane.getChildren().clear();
        orderNumbers.clear();
        orderList(orders);
    }

    /**
     * Using eventhandler to sort by endDate,
     * so the endDate that is closest to now
     * is at the bottom
     * 
     * @param event 
     */
    @FXML
    private void sortByEndDateAsc(ActionEvent event)
    {
        try
        {
            Department depa;
            if (dep.getName().toLowerCase().equals("management"))
            {
                depa = comboDepartment.getSelectionModel().getSelectedItem();
            }
            else
            {
                depa = dep;
            }
            List<Order> orders = OM.getAllOrders(depa, OM.getOffSet());
            Thread t = new Thread(() ->
            {
                OM.endDateSortedByAsc(orders);
                Platform.runLater(
                        () -> updateUI(orders));
            });
            t.start();
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Using eventhandler to sort by endDate,
     * so the endDate that is closest to now
     * is at the top.
     * 
     * @param event 
     */
    @FXML
    private void sortByEndDateDesc (ActionEvent event)
    {
        Department depa;
        if (dep.getName().toLowerCase().equals("management"))
        {
            depa = comboDepartment.getSelectionModel().getSelectedItem();
            
        } 
        else
        {
            depa = dep;
        }
        Thread t = new Thread(() ->
        {
            try
            {
                List<Order> orders = OM.getAllOrders(depa, OM.getOffSet());
                OM.endDateSortedByDesc(orders);
                Platform.runLater(
                        () -> updateUI(orders));
            } catch (BllException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }

                
            });
            t.start();
        }

    /** 
     * Sorting the list, like it was normally
     * this is like when you just opened the program.
     * 
     * @param event 
     */
    @FXML
    private void sortByDefault(ActionEvent event)
    {
        Department depa;
        if (dep.getName().toLowerCase().equals("management"))
        {
            depa = comboDepartment.getSelectionModel().getSelectedItem();
            
        } else
        {
            depa = dep;
        }
        Thread t = new Thread(() ->
        {
            try
            {

                List<Order> orders = OM.getAllOrders(depa, OM.getOffSet());
                Platform.runLater(
                        () -> updateUI(orders));

            } catch (BllException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
        
    }

    /**
     * This is opening one FXML file,
     * with all of the data (order and department)
     * It makes a BoxBlur to set the flowPane blurred,
     * when expandedPostItNote opens.
     * ExpandedPostItNote only opens if you click on the PostIt pane.
     * 
     * @param order 
     */
    private void openFXML(Order order)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/PostIt.fxml"));
            Parent root1 = (Parent) loader.load();
            PostItController pic = loader.getController();
            pic.setDepartment(dep);
            pic.setOrderInfo(order);
            pic.getAnchorPane().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) ->
            {
                ObservableList<Node> allNodes = flowPane.getChildren();
                BoxBlur blur = new BoxBlur();
                blur.setWidth(20);
                blur.setHeight(20);
                for (Node child : allNodes)
                {
                    child.setEffect(blur);
                }
                try
                {
                    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/endgame/GUI/View/ExpandedPostItNote.fxml"));
                    Parent openPostIt = (Parent) loader1.load();
                    ExpandedPostItNoteController epincontroller = loader1.getController();
                    epincontroller.setDepartment(dep);
                    epincontroller.setOrderInfo(order);
                    Stage stage = new Stage();
                    Scene scene = new Scene(openPostIt);
                    stage.setScene(scene);
                    
                    Stage platformStage = (Stage) scrollPane.getScene().getWindow();
                    stage.initOwner(platformStage);
                    stage.initModality(Modality.WINDOW_MODAL);
                    
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    stage.setOnHidden((WindowEvent event1) ->
                    {

                        blur.setWidth(-20);
                        blur.setHeight(-20);
                        if (epincontroller.getState())
                        {
                            flowPane.getChildren().remove(root1);
                            
                        }
                    });
                }catch (IOException ex)
                {
                    Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            Platform.runLater(() -> flowPane.getChildren().add(root1));
        } catch (IOException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * runs the getJsonFile method in a thread
     */
    private void readJsonFile()
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
                        OM.getJsonFile();

                    } catch (BllException ex)
                    {
                        Logger.getLogger(PlatformController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        };
        Timer timer = new Timer();

        long delay = 5000L;
        long period = 5000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    /**
     * Gets the FlowPane of PlatformController
     * 
     * @return 
     */
    public FlowPane getFlowPane()
    {
        return flowPane;
    }

    /**
     * Closes the system
     * 
     * @param event 
     */
    private void handleCloseBtn(ActionEvent event)
    {
        System.exit(0);
    }

    /**
     * Sets the items in the comboBox.
     */
    private void setCombobox()
    {
        try
        {
            comboDepartment.setItems(OM.getManagementDepartments());
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    private void setManagement()
//    {
//        if(dep.getName().equals("Management"))
//        {
//            comboDepartment.setVisible(true);
//            setCombobox();
//            
//            try
//            {
//                Department department = comboDepartment.getSelectionModel().getSelectedItem();
//                
//                setPostItNotes();
//                updatePostItNotes();
//                
//                
//                
//            } catch (BllException ex)
//            {
//                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    /**
     * Sets the comboBox visibility to true if
     * it is logged in as Management.
     */
    private void setManagement()
    {
        if (dep.getName().equals("Management"))
        {
            comboDepartment.setVisible(true);
            setCombobox();
        }
    }

    /**
     * Clears the FlowPane, so when you choose
     * another department, it sets the orders for
     * that department. 
     * 
     * @param event 
     */
    @FXML
    private void ComboChoice(ActionEvent event)
    {
        Department department = comboDepartment.getSelectionModel().getSelectedItem();
        dep = department;
        flowPane.getChildren().clear();
        try
        {
            List<Order> orders = OM.getAllOrders(department, OM.getOffSet());
            for (Order order : orders)
            {
                openFXML(order);
                flowPane.setVgap(10);
                flowPane.setHgap(10);
                orderNumbers.add(order.getOrderNumber());
            }
            orders.clear();
        } catch (BllException ex)
        {
            Logger.getLogger(PlatformController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
