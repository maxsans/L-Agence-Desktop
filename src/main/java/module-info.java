module com.eseo.lagence.lagence {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.tika.core;
    requires java.sql;

    opens com.eseo.lagence.lagence to javafx.fxml;
    exports com.eseo.lagence.lagence;
    exports com.eseo.lagence.lagence.services;
    opens com.eseo.lagence.lagence.services to javafx.fxml;
    exports com.eseo.lagence.lagence.models;
    opens com.eseo.lagence.lagence.models to javafx.fxml;
    exports com.eseo.lagence.lagence.views;
    opens com.eseo.lagence.lagence.views to javafx.fxml;
    exports com.eseo.lagence.lagence.utils;
    opens com.eseo.lagence.lagence.utils to javafx.fxml;
}