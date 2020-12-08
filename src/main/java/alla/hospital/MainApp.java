package alla.hospital;

import alla.hospital.data.Database;
import alla.hospital.model.Bulk;
import alla.hospital.model.Doctor;
import alla.hospital.model.Sick;
import alla.hospital.model.SickView;
import alla.hospital.view.SickOverview;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final String DATABASE_FILENAME = "hospital.txt";
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<SickView> sickData = FXCollections.observableArrayList();
    private ObservableList<Bulk> bulkData = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctorData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    private void initializeData() throws IOException {
        Database database = Database.getInstance();
        database.readFromFile("database.txt"); //DATABASE_FILENAME);
        sickData.clear();
        for (Sick sick : database.getSicks()) {
            sickData.add(new SickView(database, sick));
        }
        bulkData.setAll(database.getBulks());
        doctorData.setAll(database.getDoctors());
    }

    private void keepData() throws IOException {
        Database database = Database.getInstance();
        database.getSicks().clear();
        database.getSicks().addAll(sickData);
        database.getBulks().clear();
        database.getBulks().addAll(bulkData);
        database.getDoctors().clear();
        database.getDoctors().addAll(doctorData);
        database.saveToFile(DATABASE_FILENAME);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Больница v1.0");
        initRootLayout();
        initializeData();
        showSickOverview();
    }

    private void showSickOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SickOverview.fxml"));
            AnchorPane sickOverview = loader.load();
            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(sickOverview);
            // Даём контроллеру доступ к главному приложению.
            SickOverview controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ch.makery.address.MainApp.class.getResource("view/RootLayout.fxml"));
            this.rootLayout = loader.load();
            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
