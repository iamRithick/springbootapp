package com.accenture.springbootapp.entity;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Student")
public class StudentEntity {

    public static final String SEQUENCE_NAME = "student_sequence";

    @Id
    private String objId;
    @Indexed(unique = true)
    private long studentId;
    @NonNull
    private String studentName;
    @NonNull
    private int deptId;
    @Indexed(unique = true)
    private long phone;
    @Indexed(unique = true)
    private String email;

    public StudentEntity() {
    }

    public StudentEntity(long studentId, String studentName, int deptId, long phone, String email) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.deptId = deptId;
        this.phone = phone;
        this.email = email;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", deptId=" + deptId +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }
}
