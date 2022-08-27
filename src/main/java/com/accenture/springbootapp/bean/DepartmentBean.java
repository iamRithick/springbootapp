package com.accenture.springbootapp.bean;

public class DepartmentBean {
    private int deptId;
    private String deptName;

    public DepartmentBean() {

    }

    public DepartmentBean(int deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "DepartmentBean{" +
                "deptId=" + deptId  +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
