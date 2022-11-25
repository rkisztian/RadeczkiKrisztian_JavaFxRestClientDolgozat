package com.example.radeczkikrisztian_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

import static sun.security.ssl.SSLLogger.warning;

public class ListCarController {

    private Button insertButton;
    private Button updateButton;
    private Button deleteButton;
    private TableView<Car> carTable;
    private TableColumn<Car, Integer> idCol;
    private TableColumn<Car, String> carnameCol;
    private TableColumn<Car, String> ownerCol;
    private TableColumn<Car, Integer> carageCol;

    @FXML
    private void initialize(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        carnameCol.setCellValueFactory(new PropertyValueFactory<>("carname"));
        ownerCol.setCellValueFactory(new PropertyValueFactory<>("owner"));
        carageCol.setCellValueFactory(new PropertyValueFactory<>("carage"));
        Platform.runLater(() -> {
            try {
                loadPeopleFromServer();
            } catch (IOException e) {
                e.getStackTrace();
                Platform.exit();
            }
        });
    }

    private void loadPeopleFromServer() throws IOException {
        Response response = RequestHandler.get(CarApp.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Car[] buses = converter.fromJson(content, Car[].class);
        carTable.getItems().clear();
        for (Car person : buses) {
            carTable.getItems().add(person);
        }
    }

    public void insertClick(ActionEvent actionEvent){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(CarApp.class.getResource("create-car-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),640,480);
            Stage stage = new Stage();
            stage.setTitle("Creat Car and Owner");
            stage.setScene(scene);
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            stage.setOnCloseRequest(event -> {
                insertButton.setDisable(false);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                try {
                    loadPeopleFromServer();
                }catch (IOException e){
                    e.getMessage();
                }
            });
        }catch (IOException e){
            e.getMessage();
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        Car selected = carTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            warning("Módosításhoz előbb válasszon ki egy elemet!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarApp.class.getResource("update-person-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            UpdateCarController controller = fxmlLoader.getController();
            controller.setCar(selected);
            Stage stage = new Stage();
            stage.setTitle("Update "+ selected.getCarname());
            stage.setScene(scene);
            stage.setOnHidden(event -> {
                try {
                    loadPeopleFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
        }
    }

}
