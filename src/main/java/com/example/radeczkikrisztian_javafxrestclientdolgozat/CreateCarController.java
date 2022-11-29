package com.example.radeczkikrisztian_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreateCarController extends Controller{

    @FXML
    private TextField carnameField;
    @FXML
    private TextField ownerlField;
    @FXML
    private Spinner<Integer> carageField;
    @FXML
    private Button submitButton;

    @FXML
    private void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,30);
        carageField.setValueFactory(valueFactory);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent){
        String carname = carnameField.getText().trim();
        String owner = ownerlField.getText().trim();
        int age = carageField.getValue();
        if (carname.isEmpty()){
            warning("A kocsi név megadása kötelező");
            return;
        }
        if (owner.isEmpty()){
            warning("A tulajdonos név megadása kötelező");
            return;
        }
        Car newcar = new Car(0, carname, owner, age);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newcar);
        try {
            Response response = RequestHandler.post(CarApp.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                carnameField.setText("");
                ownerlField.setText("");
                carageField.getValueFactory().setValue(30);
            } else {
                error("Hiba történt a felvétel során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült kapcsolódni a szerverhez");
        }
    }

}
