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
import javafx.scene.control.DialogPane;
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

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
//        anchorPane.setStyle("-fx-opacity: 0");
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

    public FlowPane getFlowPane()
    {
        return flowPane;
    }

    private void handleCloseBtn(ActionEvent event)
    {
        System.exit(0);
    }

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

    private void setManagement()
    {
        if (dep.getName().equals("Management"))
        {
            comboDepartment.setVisible(true);
            setCombobox();
        }
    }

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
