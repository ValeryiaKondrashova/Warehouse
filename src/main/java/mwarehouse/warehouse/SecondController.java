package mwarehouse.warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mwarehouse.warehouse.entity.User;
import mwarehouse.warehouse.server.repository.DatabaseHandler;
import mwarehouse.warehouse.singleton.ProgramLogger;

public class SecondController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignUp_Button_registration;

    @FXML
    private Button back_button;

    @FXML
    private TextField login_registration_field;

    @FXML
    private TextField name_registration_field;

    @FXML
    private PasswordField password_registration_field;

    @FXML
    void initialize() {

        SignUp_Button_registration.setOnAction(event -> {

            try {
                signUpNewUser();  //вызов функции для добавления пользователя в БД
            } catch (IOException e) {
                e.printStackTrace();
            }
                SignUp_Button_registration.getScene().getWindow().hide();
                FXMLLoader loader2 = new FXMLLoader();
                loader2.setLocation(getClass().getResource("MainWindowUser.fxml"));

                try {
                    loader2.load();
                } catch (IOException e) {
                    System.out.println("er er er rrrr222222");
                    e.printStackTrace();
                }

                Parent root = loader2.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Окно пользователя :)");
                stage.show();
        });
    }

    private void signUpNewUser() throws IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String name = name_registration_field.getText(); // получаем данные что ведет данные при регистрации
        String login = login_registration_field.getText();
        String password = password_registration_field.getText();
        String status = "Пользователь";

        User user = new User(name, login, password, status); //передали полученные данные в объект user

        ProgramLogger.getProgramLogger().addLogInfo("Регистрация клиента... Добавление пользователя в БД");
        dbHandler.signUpUser(user); // передали юзера в функцию добавления в бд

        ProgramLogger.getProgramLogger().addLogInfo("Клиент успешно добавлен! DB: warehouse; Table: users :)");

        System.out.println("Пользователь успешно добавлен! DB: warehouse; Table: users :)");
    }

//    public void openNewScene(String window, String title){
//
//        SignUp_Button_registration.getScene().getWindow().hide();
//        FXMLLoader loader1 = new FXMLLoader();
//        loader1.setLocation(getClass().getResource(window));
//
//        try {
//            loader1.load();
//        } catch (IOException e) {
//            System.out.println("er er er rrrr222222");
//            e.printStackTrace();
//        }
//
//        Parent root = loader1.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setTitle(title);
//        stage.showAndWait();
//
//    }

}
