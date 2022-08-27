package com.accenture.springbootapp.bean;

public class StudentBean {

    private String objId;
    private long studentId;
    private String studentName;
    private int deptId;
    private long phone;
    private String email;

    public StudentBean() {

    }

    public StudentBean(long studentId, String studentName, int deptId, long phone, String email) {
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
        return "StudentBean{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", deptId=" + deptId +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }
}
