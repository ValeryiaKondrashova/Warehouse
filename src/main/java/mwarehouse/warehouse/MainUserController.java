package mwarehouse.warehouse;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.*;
import mwarehouse.warehouse.singleton.ProgramLogger;

public class MainUserController {
    private ConnectionTCP connectionTCP;
    private final ObservableList<ProductProperty> tableProductProperties = FXCollections.observableArrayList();// вызовет конструктор 0

//    public ConnectionTCP Conn(){
//
//        ConnectionTCP connectionTCP = null;
//        try {
//            connectionTCP = new ConnectionTCP(new Socket("localhost", 6666));  // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
//            System.out.println("-----Нашел клиента и сокет готов для передачи! :) ");
//        } catch (IOException e) {
//            System.out.println("Не нашел клиента! :(");
//            e.printStackTrace();
//            System.exit(-1);
//        }
//        return connectionTCP;
//    }
    @FXML
    private TextField field_type;
    @FXML
    private TextField field_manufacturer;
    @FXML
    private TextField field_model;
    @FXML
    private TextField field_quantity;
    @FXML
    private TextField field_price;
    @FXML
    private TextField field_storage;




    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_view_byUser;

    @FXML
    private Button button_add_byUser;

    @FXML
    private Button button_edit_byUser;

    @FXML
    private Button button_delete_byUser;

    @FXML
    private Button button_exit_byUser;


    @FXML
    private ComboBox comb;
//    @FXML
//    private ComboBox<?> comb;

    @FXML
    private Label label;

    @FXML
    private TextField field_unCorrect_byUser;

    @FXML
    private TableView<ProductProperty> table_products;

    @FXML
    private TableColumn<ProductProperty, Integer> id_column_product;

    @FXML
    private TableColumn<ProductProperty, String> manufacturer_column_product;

    @FXML
    private TableColumn<ProductProperty, String> type_column_product;

    @FXML
    private TableColumn<ProductProperty, String> model_column_product;

    @FXML
    private TableColumn<ProductProperty, Integer> quantity_column_product;

    @FXML
    private TableColumn<ProductProperty, Double> price_column_product;

    @FXML
    private TableColumn<ProductProperty, String> storage_column_product;

    @FXML
    private TableColumn<ProductProperty, String> warehouse_column_product;

    @FXML
    private TextField filterField;

    public void ShowDialogForSaving(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SavingFileUser.fxml"));
            stage.setTitle("Создание текстового отчета");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ShowDialog(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
            stage.setTitle("Добавление товара");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayName(String manufacturer, String type, String model, String quantity, String price, String storage){
        //получение данных о товаре с модального окна (Добавление товара) для дальнейшего добавления в таблицу
        String manufacturer_product  = manufacturer;
        String type_product  = type;
        String model_product  = model;
        Integer quantity_product = Integer.parseInt(quantity);
        Double price_product = Double.parseDouble(price);
        String storage_product  = storage;

        System.out.println(manufacturer_product + " " + type_product + " " + model_product + " " + quantity_product + " " + price_product + " " + storage_product);

        // добавление товара в БД
        try {
            connectionTCP = new ConnectionTCP(new Socket("localhost", 6666));  // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
            // System.out.println("Нашел клиента и сокет готов для передачи! :)1");
        } catch (IOException e) {
            // System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            Product product = new Product(manufacturer_product,
                    type_product,
                    model_product,
                    quantity_product,
                    price_product,
                    storage_product);
            System.out.println("product = " + product);
            System.out.println("product manufacturer_product  = " + product.getManufacturer());

            connectionTCP.writeObject(Command.CREATE1);
            connectionTCP.writeObject(product);

            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Select(ActionEvent event) {
        String s = comb.getSelectionModel().getSelectedItem().toString();
        label.setText(s);
    }

    @FXML
    void initialize() {
//        ConnectionTCP connectionTCP2 = Conn();
//        System.out.println("connectionTCP2 = " + connectionTCP2);
        field_type.setDisable(true);
        field_manufacturer.setDisable(true);
        field_model.setDisable(true);
        field_quantity.setDisable(true);
        field_price.setDisable(true);
        field_storage.setDisable(true);

        try {
            connectionTCP = new ConnectionTCP(new Socket("localhost", 6666));  // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
            System.out.println("Нашел клиента и сокет готов для передачи! :)0");
        } catch (IOException e) {
            System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }

        id_column_product.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        manufacturer_column_product.setCellValueFactory(cellValue -> cellValue.getValue().manufacturerProperty());
        type_column_product.setCellValueFactory(cellValue -> cellValue.getValue().typeProperty());
        model_column_product.setCellValueFactory(cellValue -> cellValue.getValue().modelProperty());
        quantity_column_product.setCellValueFactory(cellValue -> cellValue.getValue().quantityProperty().asObject());
        price_column_product.setCellValueFactory(cellValue -> cellValue.getValue().priceProperty().asObject());
        storage_column_product.setCellValueFactory(cellValue -> cellValue.getValue().storageProperty());
        warehouse_column_product.setCellValueFactory(cellValue -> cellValue.getValue().warehouseProperty());

        button_view_byUser.setOnAction(event -> {
            System.out.println("Клиент нажал на  ПРОСМОТРЕТЬ (MainUserController)");
            try {
                ProgramLogger.getProgramLogger().addLogInfo("Пользователь нажал на ПРОСМОТРЕТЬ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            tableProductProperties.clear();// чтобы не добавлять каждый раз к существующему списку

            try {
                ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.READ): запись списка пользователей в поток... Получение всех пользователей из БД");
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectionTCP.writeObject(Command.READ1);
            List<Product> allProductss = (List<Product>) connectionTCP.readObject();
            for (int i = 0; i < allProductss.size(); i++) {
                ProductProperty e = new ProductProperty(allProductss.get(i));
                tableProductProperties.add(e);
            }
            table_products.setItems(tableProductProperties);// устанавливаем значение обсёрвабл листа в таблицу
        });

        button_edit_byUser.setOnAction(event -> {
            field_type.setDisable(false);
            field_manufacturer.setDisable(false);
            field_model.setDisable(false);
            field_quantity.setDisable(false);
            field_price.setDisable(false);
            field_storage.setDisable(false);
            button_exit_byUser.setText("Редактировать");
        });

        button_delete_byUser.setOnAction(event -> {
            System.out.println("Клиент нажал на  УДАЛИТЬ (MainUserController)");
            try {
                field_unCorrect_byUser.setText("");
                int id = table_products.getSelectionModel().getSelectedItem().getId();
                connectionTCP.writeObject(Command.DELETE1);
                connectionTCP.writeObject(id);
            } catch (NullPointerException e) {// если 0
                field_unCorrect_byUser.setText("Выберите строку!");
            }
        });
//        button_exit_byUser.setOnAction(event -> {
//            System.out.println("Клиент нажал на  ВЫХОД (MainUserController)");
//                connectionTCP.writeObject(Command.EXIT);
//                connectionTCP.close();
//                System.exit(0);
//                });
        button_exit_byUser.setOnAction(event -> {
            if(button_exit_byUser.getText().equals("Выход")){
                System.out.println("Клиент нажал на  ВЫХОД (MainUserController)");
                connectionTCP.writeObject(Command.EXIT);
                connectionTCP.close();
                System.exit(0);
            }
            else{
                try {
                    field_unCorrect_byUser.setText("");
                    Product product = table_products.getSelectionModel().getSelectedItem().toWWarehouse();

                    String type = field_type.getText();
                    if (!type.isEmpty()) {
                        product.setType(type);
                    }
                    String manufacturer = field_manufacturer.getText();
                    if (!manufacturer.isEmpty()) {
                        product.setManufacturer(manufacturer);
                    }
                    String model = field_model.getText();
                    if (!model.isEmpty()) {
                        product.setModel(model);
                    }
                    String quantity = field_quantity.getText();
                    if (!quantity.isEmpty()) {
                        product.setQuantity(Integer.parseInt(quantity));
                    }
                    String price = field_price.getText();
                    if (!price.isEmpty()) {
                        product.setPrice(Double.parseDouble(price));
                    }
                    String storage = field_storage.getText();
                    if (!storage.isEmpty()) {
                        product.setStorage(storage);
                    }

                    field_type.setText("");
                    field_manufacturer.setText("");
                    field_model.setText("");
                    field_quantity.setText("");
                    field_price.setText("");
                    field_storage.setText("");
                    field_unCorrect_byUser.setText("");

                    connectionTCP.writeObject(Command.UPDATE1);
                    connectionTCP.writeObject(product);

                    field_type.setDisable(true);
                    field_manufacturer.setDisable(true);
                    field_model.setDisable(true);
                    field_quantity.setDisable(true);
                    field_price.setDisable(true);
                    field_storage.setDisable(true);

                    button_exit_byUser.setText("Выход");

                } catch (NullPointerException e) {
                    field_unCorrect_byUser.setText("Выберите строку!");
                } catch (RuntimeException e) {
                    field_unCorrect_byUser.setText("Некорректный ввод данных. Повторите попытку!");
                }
            }


        });


        FilteredList<ProductProperty> filterData = new FilteredList<>(tableProductProperties,b->true);
        filterField.textProperty().addListener((observable,oldValue,newValue) -> {
            filterData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(product.getManufacturer().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        product.getType().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        product.getModel().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        String.valueOf(product.getQuantity()).indexOf(lowerCaseFilter) != -1 ||
                        String.valueOf(product.getPrice()).indexOf(lowerCaseFilter) != -1 ||
                        product.getStorage().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        product.getWarehouse().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else { return false; }
            });
            SortedList<ProductProperty> sortedDate = new SortedList<>(filterData);
            sortedDate.comparatorProperty().bind(table_products.comparatorProperty());

            System.out.println("sortedDate = " + sortedDate);
            table_products.setItems(sortedDate);
        });

        ObservableList<String> list = FXCollections.observableArrayList("JavaFX", "SceneBuilder","Laravel","Python");
        comb.setItems(list);

    }















}
