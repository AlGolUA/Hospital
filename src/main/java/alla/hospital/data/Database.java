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

/**
 * Тип Database.
 * Реализующий Singleton паттерн объект, предоставляющий методы для чтения/записи БД, доступ к данными и уникальные ID для новых сущностей
 */
public class Database {
    private static final Database instance = new Database();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final String REGEX_DIVIDER = "\\|";

    private List<Sick> sicks;
    private List<Bulk> bulks;
    private List<Doctor> doctors;

    private int nextSickId = 1;
    private int nextBulkId = 1;
    private int nextDoctorId = 1;

    private Database() {
    }

    /**
     * Возвращает singleton объект класса Database.
     *
     * @return экземпляр объекта Database
     */
    public static Database getInstance() {
        return instance;
    }

    /**
     * Gets sicks.
     *
     * @return the sicks
     */
    public List<Sick> getSicks() {
        if (sicks==null) sicks = new ArrayList<>();
        return sicks;
    }

    /**
     * Gets bulks.
     *
     * @return the bulks
     */
    public List<Bulk> getBulks() {
        if (bulks==null) bulks = new ArrayList<>();
        return bulks;
    }

    /**
     * Gets doctors.
     *
     * @return the doctors
     */
    public List<Doctor> getDoctors() {
        if (doctors==null) doctors = new ArrayList<>();
        return doctors;
    }

    /**
     * Gets next sick id.
     *
     * @return the next sick id
     */
    public int getNextSickId() {
        return nextSickId;
    }

    /**
     * Gets next bulk id.
     *
     * @return the next bulk id
     */
    public int getNextBulkId() {
        return nextBulkId;
    }

    /**
     * Gets next doctor id.
     *
     * @return the next doctor id
     */
    public int getNextDoctorId() {
        return nextDoctorId;
    }

    private String escape(String s) {
        return s==null ? "" : s.replaceAll(REGEX_DIVIDER, "&777;");
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


    /**
     * Save to file.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
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

    /**
     * Read from file.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public void readFromFile(String fileName) throws IOException {
        List<Sick> tmpSicks = new ArrayList<>();
        List<Bulk> tmpBulks = new ArrayList<>();
        List<Doctor> tmpDoctors = new ArrayList<>();
        int maxSickId = 0;
        int maxBulkId = 0;
        int maxDoctorId = 0;
        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner scanner = new Scanner(fileReader);
            assert scanner.hasNextLine();
            String s = scanner.nextLine();
            String[] splited = s.split(REGEX_DIVIDER,2);
            assert "Version".equals(splited[0]) && "1".equals(splited[1]);
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                splited = s.split(REGEX_DIVIDER,2);
                String model = splited[0];
                int count = Integer.parseInt(splited[1]);
                if ("Doctors".equals(model)) {
                    for (int i = 0; i < count; i++) {
                        assert scanner.hasNextLine();
                        s = scanner.nextLine();
                        splited = s.split(REGEX_DIVIDER,6);
                        Integer id = unescapeInteger(splited[0]);
                        Doctor doctor = new Doctor(
                                id,
                                unescapeString(splited[1]),
                                unescapeString(splited[2]),
                                unescapeString(splited[3]),
                                unescapeString(splited[4]),
                                unescapeString(splited[5]));
                        tmpDoctors.add(doctor);
                        if (id>maxDoctorId) maxDoctorId=id;
                    }
                } else if ("Bulks".equals(model)) {
                    for (int i = 0; i < count; i++) {
                        assert scanner.hasNextLine();
                        s = scanner.nextLine();
                        splited = s.split(REGEX_DIVIDER,2);
                        Integer id = unescapeInteger(splited[0]);
                        Bulk bulk = new Bulk(
                                id,
                                unescapeString(splited[1]));
                        tmpBulks.add(bulk);
                        if (id>maxBulkId) maxBulkId=id;
                    }
                } else if ("Sicks".equals(model)) {
                    for (int i = 0; i < count; i++) {
                        assert scanner.hasNextLine();
                        s = scanner.nextLine();
                        splited = s.split(REGEX_DIVIDER,10);
                        assert splited.length == 10;
                        Integer id = unescapeInteger(splited[0]);
                        Sick sick = new Sick(
                                id,
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
                        if (id>maxSickId) maxSickId=id;
                    }
                }
            }
            this.sicks = tmpSicks;
            this.doctors = tmpDoctors;
            this.bulks = tmpBulks;
            this.nextBulkId = maxBulkId + 1;
            this.nextSickId = maxSickId + 1;
            this.nextDoctorId = maxDoctorId + 1;
        } catch (AssertionError | Exception e) {
            throw new IOException("Error reading of database from file " + fileName, e);
        }
    };
}
