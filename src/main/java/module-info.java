module com.eseo.lagence.lagence {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.eseo.lagence.lagence to javafx.fxml;
    exports com.eseo.lagence.lagence;
}