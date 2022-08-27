package com.accenture.springbootapp.service;

import com.accenture.springbootapp.bean.StudentAndDeptTemplate;
import com.accenture.springbootapp.bean.StudentBean;

import java.util.List;

public interface StudentService {

    List<StudentBean> getAllStudents() throws Exception;

    StudentBean addStudent(StudentBean studentBean) throws Exception;

    List<StudentBean> addStudentList(StudentBean[] studentBeanList) throws Exception;

    StudentAndDeptTemplate getStudentById(long studentId) throws Exception;

    List<StudentBean> getStudentByDeptId(int deptId) throws Exception;

    StudentBean deleteStudent(long studentId) throws Exception;

    StudentBean updateStudentDetails(StudentBean studentBean) throws Exception;

    long generateSequence(String sequenceName) throws Exception;
}
