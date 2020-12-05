package alla.hospital.model;

import java.util.Date;

public class Sick {
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String address;
    private Date birthday;
    private String diagnosis;
    private Integer doctorId;
    private Integer bulkId;

    public Sick(Integer id, String firstName, String lastName, String middleName, String phone, String address, Date birthday, String diagnosis, Integer doctorId, Integer bulkId)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.diagnosis = diagnosis;
        this.doctorId = doctorId;
        this.bulkId = bulkId;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getDiagnosis()
    {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    public Integer getDoctorId()
    {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId)
    {
        this.doctorId = doctorId;
    }

    public Integer getBulkId()
    {
        return bulkId;
    }

    public void setBulkId(Integer bulkId)
    {
        this.bulkId = bulkId;
    }
}
