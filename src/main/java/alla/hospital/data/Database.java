package alla.hospital.data;

import alla.hospital.model.Bulk;
import alla.hospital.model.Doctor;
import alla.hospital.model.Sick;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final Database instance = new Database();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.YYYY");

    private List<Sick> sicks;
    private List<Bulk> bulks;
    private List<Doctor> doctors;

    public static Database getInstance() {
        return instance;
    }

    public void readFromFile(String fileName) {

    };

    public List<Sick> getSicks() {
        if (sicks==null) sicks = new ArrayList<>();
        return sicks;
    }

    public List<Bulk> getBulks() {
        if (bulks==null) bulks = new ArrayList<>();
        return bulks;
    }

    public List<Doctor> getDoctors() {
        if (doctors==null) doctors = new ArrayList<>();
        return doctors;
    }

    public void saveToFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write("Version;1\n");
        fileWriter.write("Bulks;" + bulks.size()+'\n');
        for (int i = 0; i < bulks.size(); i++) {
            Bulk tmpBulk = bulks.get(i);
            String s = String.format("%d;%s", tmpBulk.getId(), tmpBulk.getName());
            fileWriter.write(s+'\n');
        }
        fileWriter.write("Doctors;" + doctors.size()+'\n');
        for (int i = 0; i < doctors.size(); i++) {
            Doctor tmpDoctor = doctors.get(i);
            String s = String.format("%d;%s;%s;%s;%s", tmpDoctor.getId(), tmpDoctor.getFirstName(), tmpDoctor.getMiddleName(), tmpDoctor.getLastName(), tmpDoctor.getPhone(), tmpDoctor.getAddress());
            fileWriter.write(s+'\n');
        }
        fileWriter.write("Sicks;" + sicks.size()+'\n');
        for (int i = 0; i < sicks.size(); i++) {
            Sick tmpSick = sicks.get(i);
            String s = String.format("%d;%s;%s;%s;%s;%s;%s;%s;%d;%d",
                    tmpSick.getId(), tmpSick.getFirstName(), tmpSick.getMiddleName(), tmpSick.getLastName(), tmpSick.getPhone(), tmpSick.getAddress(),
                    simpleDateFormat.format(tmpSick.getBirthday()), tmpSick.getDiagnosis(),tmpSick.getDoctorId(), tmpSick.getBulkId());
            fileWriter.write(s+'\n');
        }
        fileWriter.flush();
    }
}
