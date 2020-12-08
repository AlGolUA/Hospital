package alla.hospital.view;

import alla.hospital.MainApp;
import alla.hospital.data.Database;
import alla.hospital.model.Sick;
import alla.hospital.model.SickView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SickOverview {
    public TableView sickTable;
    public TableColumn firstNameColumn;
    public TableColumn lastNameColumn;
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label streetLabel;
    public Label cityLabel;
    public Label postalCodeLabel;
    public Label birthdayLabel;


    // ������ �� ������� ����������.
    private MainApp mainApp;
    private ObservableList<SickView> sickData = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * �����������.
     * ����������� ���������� ������ ������ initialize().
     */
    public SickOverview() {
    }

    /**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
        sickData.clear();
        Database database = Database.getInstance();
        for (Sick sick : database.getSicks()) {
            sickData.add(new SickView(database, sick));
        }
        // ������������� ������� ��������� � ����� ���������.
        //firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        //lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        // ������� ��������� ������, � ��� ��������� ����������
        // �������������� ���������� �� ��������.
        //personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
    }


    public void handleNewsick(ActionEvent actionEvent) {

    }

    public void handleDeletesick(ActionEvent actionEvent) {

    }

    public void handleEditsick(ActionEvent actionEvent) {

    }
}
