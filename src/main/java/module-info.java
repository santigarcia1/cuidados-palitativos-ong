module com.cuidados.paliativos.ong.cuidadospalitativosong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.cuidados.paliativos to javafx.fxml;
    opens com.cuidados.paliativos.controlador to javafx.fxml;
    exports com.cuidados.paliativos;
    exports com.cuidados.paliativos.controlador;
}