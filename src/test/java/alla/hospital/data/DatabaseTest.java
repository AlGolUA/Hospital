package alla.hospital.data;

import alla.hospital.model.Bulk;
import alla.hospital.model.Doctor;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void readFromFile() {
    }

    @Test
    public void saveToFile() throws IOException {
        Database database = Database.getInstance();
        List<Bulk> bulks = database.getBulks();
        List<Doctor> doctors = database.getDoctors();
        bulks.add(new Bulk(1, "����� 1"));
        bulks.add(new Bulk(2, "����� 2"));
        doctors.add(new Doctor(1, "������", "��������", "������", "031", null));
        doctors.add(new Doctor(2, "����", "���������", "�������", "032", null));
        doctors.add(new Doctor(3, "�����", "��������", "������", "033", null));
        database.saveToFile("database.txt");
    }
}
