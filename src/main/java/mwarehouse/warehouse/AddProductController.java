package mwarehouse.warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_add;


    @FXML
    private TextField type_add;

    @FXML
    private TextField manufacturer_add;

    @FXML
    private TextField model_add;

    @FXML
    private TextField quantity_add;

    @FXML
    private TextField price_add;

    @FXML
    private TextField storage_add;

    @FXML
    private TextField qw_field;


    @FXML
    void Add(ActionEvent event) throws IOException {

        String manufacturer = manufacturer_add.getText();
        String type = type_add.getText();
        String model = model_add.getText();
        String quantity = quantity_add.getText();
        String price = price_add.getText();
        String storage = storage_add.getText();

        if (manufacturer.isEmpty() || type.isEmpty() || model.isEmpty() || quantity.isEmpty() || price.isEmpty() || storage.isEmpty()){
            qw_field.setText("Присутствуют пустые поля!");
            qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
        }
        else {
            System.out.println(manufacturer + " " + type + " " + model + " " + quantity + " " + price + " " + storage);

            MainUserController mainUserController = new MainUserController();
            mainUserController.displayName(manufacturer,
                    type,
                    model,
                    quantity,
                    price,
                    storage);
            qw_field.setText("Товар успешно добавлен!");
            qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
        }

    }

    @FXML
    void initialize() {

    }

}
