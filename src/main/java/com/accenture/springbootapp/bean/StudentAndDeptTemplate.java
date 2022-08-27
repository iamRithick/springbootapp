package com.accenture.springbootapp.bean;

public class StudentAndDeptTemplate {
    private StudentBean student;
    private DepartmentBean department;

    public StudentAndDeptTemplate() {
    }

    public StudentAndDeptTemplate(StudentBean student, DepartmentBean department) {
        this.student = student;
        this.department = department;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "StudentAndDeptTemplate{" +
                "student=" + student +
                ", department=" + department +
                '}';
    }
}
