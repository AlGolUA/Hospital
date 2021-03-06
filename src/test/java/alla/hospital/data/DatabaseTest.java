package alla.hospital.data;

import alla.hospital.model.Bulk;
import alla.hospital.model.Doctor;
import alla.hospital.model.Sick;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void readFromFile() {
        Database database = Database.getInstance();
        database.readFromFile("database.txt");
        System.out.println(database.getBulks().size());
        System.out.println(database.getDoctors().size());
        System.out.println(database.getSicks().size());
    }

    private static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    @Test
    public void saveToFile() throws IOException {
        Database database = Database.getInstance();
        List<Bulk> bulks = database.getBulks();
        List<Doctor> doctors = database.getDoctors();
        bulks.add(new Bulk(1, "�����|1"));
        bulks.add(new Bulk(2, "�����#2"));
        doctors.add(new Doctor(1, "������", "��������", "������", "031", "null|address"));
        doctors.add(new Doctor(2, "����", "���������", "�������", "032", null));
        doctors.add(new Doctor(3, "�����", "��������", "������", "033", null));
        doctors.add(new Doctor(4, "����", null, null, null, null));
        List<Sick> sicks = database.getSicks();
        sicks.add(new Sick(1, "A", "L", "M", "phone", "address", getDate(2020,0,1), "diagnosis", 1, 1));
        sicks.add(new Sick(2, "firstName", "LastName", "Mname", "phone2", "address2", null, "COVID", null, null));
        database.saveToFile("database.txt");
    }
}
