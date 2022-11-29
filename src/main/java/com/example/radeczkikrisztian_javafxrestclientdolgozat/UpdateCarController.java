package com.example.radeczkikrisztian_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class UpdateCarController extends Controller{

    @FXML
    private TextField carnameField;
    @FXML
    private TextField ownerlField;
    @FXML
    private Spinner<Integer> carageField;

    @FXML
    private Button updateButton;

    private Car car;

    public void setCar(Car car){
        this.car = car;
        carnameField.setText(this.car.getCarname());
        ownerlField.setText(this.car.getOwner());
        carageField.getValueFactory().setValue(this.car.getCarage());
    }

    @FXML
    public void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 30);
        carageField.setValueFactory(valueFactory);
    }

    @FXML
    public void updateClick(ActionEvent actionEvent){
        String carname= carnameField.getText().trim();
        String owner = ownerlField.getText().trim();
        int carage = carageField.getValue();
        if (carname.isEmpty()){
            warning("Az autó márkájának megadása kötelező");
            return;
        }
        if (owner.isEmpty()){
            warning("Az autó tuladonosát megadni kötelező!");
            return;
        }
        this.car.setCarname(carname);
        this.car.setOwner(owner);
        this.car.setCarage(carage);
        Gson converter = new Gson();
        String json = converter.toJson(this.car);
        try {
            String url = CarApp.BASE_URL + "/" + this.car.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            } else {
                error("Hiba történt a módosítás során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült kapcsolódni a szerverhez");
        }
    }




}
