package mwarehouse.warehouse;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mwarehouse.warehouse.server.repository.DatabaseHandler;

public class SavingFileUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_saveOtchet;

    @FXML
    private TextField field_nameFile;

    @FXML
    private TextField field_unCorrect_nameFile;

    @FXML
    void SaveFile(ActionEvent event) throws IOException {

        String fileName;
        Boolean q,w,o,r,t,y,u,p;
        fileName = field_nameFile.getText();

        q=fileName.contains("/");
        w =fileName.contains("\"");
        o=fileName.contains(":");
        r=fileName.contains("*");
        t=fileName.contains("<");
        y=fileName.contains(">");
        u=fileName.contains("|");
        p=fileName.contains("?");

        if(q==true ||w==true || p==true || r==true || t==true || y==true|| u==true || o==true){

            System.out.println(q+ " " + w +" " +  o + " " + r + " " + t + " " + y + " " + u + " " +  p);
            field_unCorrect_nameFile.setText("Некорректное имя файла!");
            field_unCorrect_nameFile.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            return;
        }
        else{
            field_unCorrect_nameFile.setText("Файл успешно сохранен!");
            field_unCorrect_nameFile.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            fileName+=".txt";
        }


        DatabaseHandler dbHandler = new DatabaseHandler();

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = dbHandler.getDbConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT manufacturer, type, model, products.quantity_product, price, storage, warehouse FROM products JOIN manufacturers ON products.manufacturer_product = manufacturers.idManufacturer JOIN types ON products.type_product = types.idType JOIN models ON products.model_product = models.idModel JOIN storages ON  products.storage_product = storages.idStorage JOIN warehouses ON storages.warehouseStorage = warehouses.idWarehouse");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(fileName, false);
            while (rs.next()) {

                String manufacturer = rs.getString("manufacturer");
                String type = rs.getString("type");
                String model = rs.getString("model");
                String quantity_product = rs.getString("quantity_product");
                String price = rs.getString("price");
                String storage = rs.getString("storage");
                String warehouse = rs.getString("warehouse");

                fileWriter.write("Модель : " + model + "\n" +
                        "\tПроизводитель : " + manufacturer + "\n" +
                        "\tВид : " + type + "\n" +

                        "\tКоличество : " + quantity_product + "\n" +
                        "\tЦена : " + price + "\n" +
                        "\tХранилище : " + storage + "\n" +

                        "\tСклад = " + warehouse + ".\n\n");


            }
            fileWriter.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

}
