package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.EditTaskDemo;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    // TODO: 2019-03-15
    private File todoOptionsPopupFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopupFile = new File(todoActionsPopUpFXML);

    // TODO: 2019-03-15
    private JFXPopup todobarPopUp;
    private JFXPopup viewPopUp;


    @FXML
    private Label descriptionLabel;

    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;

    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;





    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: complete this method
        loadTodobarPopUp();
        loadTodobarPopUpActionListener();
        loadTodoOptionsPopUp();
        loadTodoOptionsPopUpActionListener();
    }

    private void loadTodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                viewPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        12,
                        15);
            }
        });
    }


    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopupFile.toURI().toURL());
            fxmlLoader.setController(new ViewOptionsPopUpController());
            viewPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadTodobarPopUpActionListener() {
        todoActionsPopUpContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todobarPopUp.show(todoActionsPopUpContainer,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        -12,
                        15);
            }
        });
    }


    // TODO: 2019-03-15
    private void loadTodobarPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopupFile.toURI().toURL());
            fxmlLoader.setController(new TodobarPopUpController());
            todobarPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    //todo
    class TodobarPopUpController {
        //todo
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarOptionsPopUpController", "TODO");
                    break;
                case 1:
                    Logger.log("TodobarOptionsPopUpController", "upnext");
                    break;
                case 2:
                    Logger.log("TodobarOptionsPopUpController", "in progress");
                    break;
                case 3:
                    Logger.log("TodobarOptionsPopUpController", "done");
                    break;
//                case 4:
//                    Logger.log("TodobarOptionsPopUpController", "pomodoro");
//                    break;
                default:
                    Logger.log("TodobarOptionsPopUpController", "pomodoro");
            }
            todobarPopUp.hide();
        }
    }

    // Inner class: view selector pop up controller
    class ViewOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarViewOptionsPopUpController", "edit");

                    // TODO: 2019-03-16
                    PomoTodoApp.setScene(new EditTask(task));



                    break;
                case 1:
                    Logger.log("TotobarViewOptionsPopUpController", "delete");

                    // TODO: 2019-03-16  find task from file
                    // delete task
                    PomoTodoApp.getTasks().remove(task);
                    JsonFileIO.write(PomoTodoApp.getTasks());

//                    selectedIndex
                    // TODO: refresh the ListView
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));

                    break;

                default:
                    Logger.log("ToolbarViewOptionsPopUpController", "No action is implemented for the selected option");
            }
            viewPopUp.hide();
        }
    }

}
