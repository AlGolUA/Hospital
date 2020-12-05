package alla.hospital;

import alla.hospital.view.SickOverview;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        initRootLayout();
        showPersonOverview();
    }

    private void showPersonOverview() {
        try {
            // ��������� �������� �� ���������.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ch.makery.address.MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();
            // �������� �������� �� ��������� � ����� ��������� ������.
            rootLayout.setCenter(personOverview);
            // ��� ����������� ������ � �������� ����������.
            SickOverview controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            // ��������� �������� ����� �� fxml �����.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ch.makery.address.MainApp.class.getResource("view/RootLayout.fxml"));
            this.rootLayout = loader.load();
            // ���������� �����, ���������� �������� �����.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
