module mwarehouse.warehouse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mwarehouse.warehouse to javafx.fxml;
    exports mwarehouse.warehouse;
    exports mwarehouse.warehouse.entity;
    opens mwarehouse.warehouse.entity to javafx.fxml;
    exports mwarehouse.warehouse.server;
    opens mwarehouse.warehouse.server to javafx.fxml;
    exports mwarehouse.warehouse.server.database;
    opens mwarehouse.warehouse.server.database to javafx.fxml;
    exports mwarehouse.warehouse.client;
    opens mwarehouse.warehouse.client to javafx.fxml;
    exports mwarehouse.warehouse.server.repository;
    opens mwarehouse.warehouse.server.repository to javafx.fxml;
}