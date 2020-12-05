package alla.hospital.data;

import alla.hospital.model.Bulk;
import alla.hospital.model.Doctor;
import alla.hospital.model.Sick;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Database {
    private static final Database instance = new Database();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private List<Sick> sicks;
    private List<Bulk> bulks;
    private List<Doctor> doctors;

    public static Database getInstance() {
        return instance;
    }

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

    private String escape(String s) {
        return s==null ? "" : s.replaceAll("\\|", "&777;");
    }

    private String escape(Date d) {
        return d==null ? "" : simpleDateFormat.format(d);
    }

    private String escape(Integer i) {
        return i==null ? "" : String.valueOf(i);
    }

    private String unescapeString(String s) {
        return s==null || s.isEmpty() ? null : s.replaceAll("&777;", "|");
    }

    private Date unescapeDate(String s) throws ParseException {
        return s==null || s.isEmpty() ? null : simpleDateFormat.parse(s);
    }

    private Integer unescapeInteger(String s) throws ParseException {
        return s==null || s.isEmpty() ? null : Integer.parseInt(s);
    }


    public void saveToFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write("Version|1\n");
        fileWriter.write("Bulks|" + bulks.size()+'\n');
        for (Bulk tmpBulk : bulks)
        {
            String s = String.format("%s|%s",
                    escape(tmpBulk.getId()),
                    escape(tmpBulk.getName()));
            fileWriter.write(s + '\n');
        }
        fileWriter.write("Doctors|" + doctors.size()+'\n');
        for (Doctor tmpDoctor : doctors)
        {
            String s = String.format("%s|%s|%s|%s|%s|%s",
                    escape(tmpDoctor.getId()),
                    escape(tmpDoctor.getFirstName()),
                    escape(tmpDoctor.getMiddleName()),
                    escape(tmpDoctor.getLastName()),
                    escape(tmpDoctor.getPhone()),
                    escape(tmpDoctor.getAddress()));
            fileWriter.write(s + '\n');
        }
        fileWriter.write("Sicks|" + sicks.size()+'\n');
        for (Sick tmpSick : sicks)
        {
            String s = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
                    escape(tmpSick.getId()),
                    escape(tmpSick.getFirstName()),
                    escape(tmpSick.getMiddleName()),
                    escape(tmpSick.getLastName()),
                    escape(tmpSick.getPhone()),
                    escape(tmpSick.getAddress()),
                    escape(tmpSick.getBirthday()),
                    escape(tmpSick.getDiagnosis()),
                    escape(tmpSick.getDoctorId()),
                    escape(tmpSick.getBulkId()));
            fileWriter.write(s + '\n');
        }
        fileWriter.flush();
    }

    public void readFromFile(String fileName) {
        List<Sick> tmpSicks = new ArrayList<>();
        List<Bulk> tmpBulks = new ArrayList<>();
        List<Doctor> tmpDoctors = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner scanner = new Scanner(fileReader);
            assert scanner.hasNextLine();
            String s = scanner.nextLine();
            String[] splited = s.split("\\|",2);
            assert "Version".equals(splited[0]) && "1".equals(splited[1]);
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                splited = s.split("\\|",2);
                String model = splited[0];
                int count = Integer.parseInt(splited[1]);
                if ("Doctors".equals(model)) {
                    for (int i = 0; i < count; i++) {
                        assert scanner.hasNextLine();
                        s = scanner.nextLine();
                        splited = s.split("\\|",6);
                        Doctor doctor = new Doctor(
                                unescapeInteger(splited[0]),
                                unescapeString(splited[1]),
                                unescapeString(splited[2]),
                                unescapeString(splited[3]),
                                unescapeString(splited[4]),
                                unescapeString(splited[5]));
                        tmpDoctors.add(doctor);
                    }
                } else if ("Bulks".equals(model)) {
                    for (int i = 0; i < count; i++) {
                        assert scanner.hasNextLine();
                        s = scanner.nextLine();
                        splited = s.split("\\|",2);
                        Bulk bulk = new Bulk(
                                unescapeInteger(splited[0]),
                                unescapeString(splited[1]));
                        tmpBulks.add(bulk);
                    }
                } else if ("Sicks".equals(model)) {
                    for (int i = 0; i < count; i++) {
                        assert scanner.hasNextLine();
                        s = scanner.nextLine();
                        splited = s.split("\\|",10);
                        assert splited.length == 10;
                        Sick sick = new Sick(
                                unescapeInteger(splited[0]),
                                unescapeString(splited[1]),
                                unescapeString(splited[2]),
                                unescapeString(splited[3]),
                                unescapeString(splited[4]),
                                unescapeString(splited[5]),
                                unescapeDate(splited[6]),
                                unescapeString(splited[7]),
                                unescapeInteger(splited[8]),
                                unescapeInteger(splited[9]));
                        tmpSicks.add(sick);
                    }
                }
            }
            this.sicks = tmpSicks;
            this.doctors = tmpDoctors;
            this.bulks = tmpBulks;
        } catch (AssertionError | Exception e) {
            throw new RuntimeException("Error reading of database from file " + fileName, e);
        }
    };
}
