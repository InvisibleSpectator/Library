package entity;

import java.sql.Date;

public class ReaderEntity {
    private long readerId;
    private String readerSurname;
    private String readerName;
    private String readerPatronymic;
    private Date readerRegistrationDate;
    private Long readerTelephone;

    public ReaderEntity(long reader_id, String reader_surname, String reader_name, String reader_patronymic, Date reader_registration_date, Long reader_telephone) {
        readerId = reader_id;
        readerSurname = reader_surname;
        readerName = reader_name;
        readerPatronymic = reader_patronymic;
        readerRegistrationDate = reader_registration_date;
        readerTelephone = reader_telephone;
    }

    public long getReaderId() {
        return readerId;
    }

    public void setReaderId(long readerId) {
        this.readerId = readerId;
    }

    public String getReaderSurname() {
        return readerSurname;
    }

    public void setReaderSurname(String readerSurname) {
        this.readerSurname = readerSurname;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderPatronymic() {
        return readerPatronymic;
    }

    public void setReaderPatronymic(String readerPatronymic) {
        this.readerPatronymic = readerPatronymic;
    }

    public Date getReaderRegistrationDate() {
        return readerRegistrationDate;
    }

    public void setReaderRegistrationDate(Date readerRegistrationDate) {
        this.readerRegistrationDate = readerRegistrationDate;
    }

    public Long getReaderTelephone() {
        return readerTelephone;
    }

    public void setReaderTelephone(Long readerTelephone) {
        this.readerTelephone = readerTelephone;
    }
}
