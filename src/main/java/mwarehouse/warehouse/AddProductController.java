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
    private  TextField warehouse_add;

//    @FXML
//    private TextField qw_field;



    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void Add(ActionEvent event) throws IOException {

        String manufacturer = manufacturer_add.getText();
        String type = type_add.getText();
        String model = model_add.getText();
        String quantity = quantity_add.getText();
        String price = price_add.getText();
        String storage = storage_add.getText();
        String warehouse = warehouse_add.getText();

        System.out.println(manufacturer + " " + type + " " + model + " " + quantity + " " + price + " " + storage + " " + warehouse);

        MainUserController mainUserController = new MainUserController();
        mainUserController.displayName(manufacturer,
                type,
                model,
                quantity,
                price,
                storage,
                warehouse);
    }

    @FXML
    void initialize() {
//        add_add.setOnAction(event -> {
//            qw_field.setText("Товар успешно добавлен!");
//        });
    }

}
