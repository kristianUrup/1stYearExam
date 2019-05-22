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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.WindowConstants;

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
    private boolean bigPostItCheck = false;

    @FXML
    private Label departName;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FlowPane flowPane;
    ExpandedPostItNoteController epinc;
    PostItController picontroller;

    private Parent openPostIt;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
//        anchorPane.setStyle("-fx-opacity: 0");
        try
        {
            picontroller = new PostItController();
            epinc = new ExpandedPostItNoteController();
            OM = new OrderModel();
            dep = OM.getDepartment(OM.getConfig());
            departName.setText(dep.getName());
            orderNumbers = new ArrayList<>();
            setPostItNotes();
            updatePostItNotes();
            readJsonFile();
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

    public void openFXML12(Order order) throws IOException
    {
        Thread t = new Thread(() ->
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/PostIt.fxml"));
                Parent root = (Parent) loader.load();
                ExpandedPostItNoteController pic = loader.getController();
                pic.setDepartment(dep);
                pic.setOrderInfo(order);
                pic.getDoneButton().setOnAction(e ->
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
                Platform.runLater(() -> flowPane.getChildren().add(root));
            } catch (IOException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }

    private void orderList(List<Order> orders)
    {

        for (Order order : orders)
        {
            if (!orderNumbers.contains(order.getOrderNumber()))
            {
                openFXML(order);
                orderNumbers.add(order.getOrderNumber());
            }
        }
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
        Thread t = new Thread(() ->
        {
            try
            {
                List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
                OM.endDateSortedByAsc(orders);
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
    private void sortByEndDateDesc(ActionEvent event)
    {
        Thread t = new Thread(() ->
        {
            try
            {
                List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
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
        Thread t = new Thread(() ->
        {
            try
            {
                List<Order> orders = OM.getAllOrders(dep, OM.getOffSet());
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
        Thread t = new Thread(() ->
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/PostIt.fxml"));
                Parent root1 = (Parent) loader.load();
                PostItController pic = loader.getController();
                pic.setDepartment(dep);
                pic.setOrderInfo(order);
//                flowPane.getChildren().add(root1);
                pic.getAnchorPane().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent e)
                    {

                        ObservableList<Node> allNodes = flowPane.getChildren();
                        BoxBlur blur = new BoxBlur();
                        blur.setWidth(20);
                        blur.setHeight(20);
                        if (!bigPostItCheck)
                        {
                            for (Node child : allNodes)
                            {
                                child.setEffect(blur);
                            }

                            try
                            {
                                bigPostItCheck = true;
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/endgame/GUI/View/ExpandedPostItNote.fxml"));
                                openPostIt = (Parent) loader.load();
                                ExpandedPostItNoteController epincontroller = loader.getController();
                                epincontroller.setDepartment(dep);
                                epincontroller.setOrderInfo(order);
                                
                                Stage stage = new Stage();
                                Scene scene = new Scene(openPostIt);
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.show();
                                
                                epincontroller.getDoneButton().setOnAction(event ->
                                {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Dialog");
                                    alert.setHeaderText("You are about to set this task to done");
                                    alert.setContentText("Are you sure you want to do this?");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if ((result.isPresent()) && (result.get() == ButtonType.OK))
                                    {
                                        try
                                        {
                                            epinc.setDone();
                                        } catch (BllException ex)
                                        {
                                            Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        flowPane.getChildren().remove(root1);
                                        stage.close();
                                        blur.setHeight(-20);
                                        blur.setWidth(-20);
                                        bigPostItCheck = false;
                                    }
                                });
                                epincontroller.getCrossView().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
                                {
                                    @Override
                                    public void handle(MouseEvent event1)
                                    {
                                        if (bigPostItCheck)
                                        {
                                            stage.close();
                                            blur.setHeight(-20);
                                            blur.setWidth(-20);
                                            bigPostItCheck = false;
                                        }
                                    }
                                });
//
//                           flowPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
//                        {
//                            @Override
//                            public void handle(MouseEvent event1) {
//                                
//                                if(bigPostItCheck)
//                                {
//                                    flowPane.getChildren().remove(openPostIt);
////                                    blur.setHeight(-20);
////                                    blur.setWidth(-20);
//                                    bigPostItCheck = false;
//                                }
//                            }
//                        });
                            } catch (IOException ex)
                            {
                                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                Platform.runLater(() -> flowPane.getChildren().add(root1));

            } catch (IOException ex)
            {
                Logger.getLogger(PlatformController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }
    
    private void readJsonFile() {
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
    
    public FlowPane getFlowPane()
    {
        return flowPane;
    }

    private void handleCloseBtn(ActionEvent event)
    {
        System.exit(0);
    }

}
