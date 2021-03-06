package mwarehouse.warehouse;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mwarehouse.warehouse.client.Shake;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.Command;
import mwarehouse.warehouse.entity.User;
import mwarehouse.warehouse.entity.UserProperty;
import mwarehouse.warehouse.server.repository.DatabaseHandler;
import mwarehouse.warehouse.singleton.ProgramLogger;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignIn_Button;

    @FXML
    private Button SignUp_Button;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
        SignIn_Button.setOnAction(actionEvent -> {  //тут мы получаем данные из полей login_field и password_field. Потом ниже они передадутся в метод loginUser
            System.out.println("Вы нажали на кнопку ВОЙТИ!");

            try {
                ProgramLogger.getProgramLogger().addLogInfo("Нажатие пользователем на кнопку ВОЙТИ.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            String loginText = login_field.getText().trim();  // trim() - удаляет лишние пробелы
            String loginPassword = password_field.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals("")) {  //если поля ЛОГИН и ПАРОЛЬ не пустые, то
                try {
                    ProgramLogger.getProgramLogger().addLogInfo("Получение введенных данных пользователем. Проверка на наличие аккаунта в БД...");
                    loginUser(loginText, loginPassword);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error! Login and password is empty!");

                try {
                    ProgramLogger.getProgramLogger().addLogInfo("Пользователь оставил поля ввода ЛОГИНА и/или ПАРОЛЯ пустыми.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        SignUp_Button.setOnAction(actionEvent -> {

            try {
                ProgramLogger.getProgramLogger().addLogInfo("Нажатие пользователем на кнопку ЗАРЕГИСТРИРОВАТЬСЯ. Переход на окно регистрации --> (Логика окна Регистрации описана в SecondController.java)");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Вы нажали на кнопку ЗАРЕГИСТРИРОВАТЬСЯ!");
            openNewScene("Registration.fxml", "Регистрация");

        });


    }

    private void loginUser(String loginText, String loginPassword) throws SQLException, IOException {  //функция для обработки введённых данных пользователем
        //DatabaseHandler dbHandler = new DatabaseHandler(); // создаем объект класса DatabaseHandler, чтобы мы могли обращаться к его методам

//        User user = new User();  // создаем новый объект класса User с именем user (этот объект user не связан с другим объектом user, который находится в другом классе. Просто одинаковое название)
//        user.setLogin(loginText);
//        user.setPassword(loginPassword);
        //ResultSet result = dbHandler.getUser(user); //с помощью getUser мы получаем данные
        //System.out.println("result = " + result);
//
//        int counter = 0;
//
//        try {
//            while (result.next()) {
//                counter ++;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("counter = " + counter);
//        if (counter == 1) { //если найденных пользователей > или = 1, то значит такой пользователь в нашей базе есть
//
//            ProgramLogger.getProgramLogger().addLogInfo("Успешно! Введенный аккаунт существует! ");
//
//            System.out.println("Success! Походу пользователь найден! :)");
//            System.out.println(result);

            try
            {
                DatabaseHandler dbHandler = new DatabaseHandler();
                ProgramLogger.getProgramLogger().addLogInfo("Запрос в БД для получения логина, пароля и СТАТУСА клиента...");

                Connection connection = dbHandler.getDbConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select users.login, users.password, statusUser from users");

                while (rs.next())
                {
                    String log = rs.getString("login");
                    String passw = rs.getString("password");
                    String stat = rs.getString("statusUser");

                    if(loginText.equals(log) && loginPassword.equals(passw)) {
                        System.out.println("Логин пользователя: " + log);
                        System.out.println("Пароль пользователя: " + passw);
                        System.out.println("Статус пользователя: " + stat);
                        String status = "0";

                        if (stat.equals(status)) {

                            ProgramLogger.getProgramLogger().addLogInfo("Результат запроса из БД : введенный аккаунт принадлежит Администратору! Переход на окно для Администратора -->");

                            System.out.println("Введенный аккаунт принадлежит Администратору! Перебрасываю на окно для Администратора --> (Логика окна Администратора описана в MainController.java)");
                            openNewScene("MainWindow.fxml", "Окно администратора");
                        } else {

                            ProgramLogger.getProgramLogger().addLogInfo("Результат запроса из БД: введенный аккаунт принадлежит Пользователю! Переход на окно для Пользователя -->(Логика окна Пользователя описана в MainUserController.java)");

                            System.out.println("Аккаунт имеет роль Пользователя! Перебрасываю на окно для Пользователя -->");
                            openNewScene("MainWindowUser.fxml", "Окно пользователя");
                        }
                    }

                    System.out.println(log + " " + stat + " ");
                    //System.out.println("rs = " + rs);
                }
                if(!rs.next()) {
                    Shake userLoginAnim = new Shake(login_field);
                    Shake userPassAnim = new Shake(password_field);
                    userLoginAnim.playAnim();
                    userPassAnim.playAnim();

                    System.out.println("Sorry! Мы не нашли такого пользователя :(");
                    ProgramLogger.getProgramLogger().addLogInfo("Внимание! Введенный аккаунт не найден!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//        } else {
//            Shake userLoginAnim = new Shake(login_field);
//            Shake userPassAnim = new Shake(password_field);
//            userLoginAnim.playAnim();
//            userPassAnim.playAnim();
//
//            System.out.println("Sorry! Мы не нашли такого пользователя :(");
//            ProgramLogger.getProgramLogger().addLogInfo("Внимание! Введенный аккаунт не найден!");
//        }
    }


    public void openNewScene(String window,String title){

        SignIn_Button.getScene().getWindow().hide();
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource(window));

        try {
            loader1.load(); // Метод System.load() загружает файл кода с указанным именем файла из локальной файловой системы в виде динамической библиотеки. Аргументом имени файла должно быть полное имя пути.
        } catch (IOException e) {
            System.out.println("er er er rrrr");
            e.printStackTrace();
        }

        Parent root = loader1.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.showAndWait();

    }
}
