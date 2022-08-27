package com.accenture.springbootapp.controller;

import com.accenture.springbootapp.bean.StudentAndDeptTemplate;
import com.accenture.springbootapp.bean.StudentBean;
import com.accenture.springbootapp.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <br/>
 * CLASS DESCRIPTION: <br/>
 * A Controller class for receiving and handling all Student related requests <br/>
 */

@RestController
@RequestMapping(value = "/studentService")
public class StudentController {

    private final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentServiceImpl;

    /**
     * METHOD DESCRIPTION: <br/>
     * This method denoted the Student service is working fine <br/>
     *
     * @return
     */
    @GetMapping(value = "/")
    public ResponseEntity<String> displayMessage(){
        return new ResponseEntity<String>("Student Service is up and running!", HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method return the list of all student documents available
     * in the Student collection <br/>
     *
     * @return list of all students
     * @throws Exception
     */
    @GetMapping(path = "/getAllStudentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentBean>> displayAllStudents() throws Exception {
        List<StudentBean> studentList = studentServiceImpl.getAllStudents();
        log.info("Student List fetched");
        return new ResponseEntity<List<StudentBean>>(studentList, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method fetches the StudentEntity from the Request Body
     * and inserts it into the Student Collection <br/>
     *
     * @param studentBean
     * @return the inserted student bean
     * @throws Exception
     */
    @PostMapping(value = "/addStudent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentBean> addStudent(@RequestBody StudentBean studentBean) throws Exception {
        StudentBean bean = studentServiceImpl.addStudent(studentBean);
        log.info("Inserted 1 Document into Student collection. Student ID: " + bean.getStudentId());
        return new ResponseEntity<StudentBean>(bean, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method fetches the list of StudentEntity
     * from the Request Body
     * and inserts it into the Student Collection <br/>
     *
     * @param studentBeans
     * @return the inserted student bean
     * @throws Exception
     */
    @PostMapping(value = "/addStudentList", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentBean>> addStudentList(@RequestBody StudentBean[] studentBeans) throws Exception {
        List<StudentBean> beanList = studentServiceImpl.addStudentList(studentBeans);
        log.info("Inserted " + beanList.size() + " documents into Student collection");
        log.info("Inserted Student IDs:");
        for(StudentBean bean:beanList){
            log.info(bean.getStudentId() + "\n");
        }
        return new ResponseEntity<List<StudentBean>>(beanList, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method fetches the Student Bean from the Student collection
     * using the studentId and the corresponding Department Bean from
     * the Department Collection <br/>
     *
     * @param id
     * @return the student and department bean corresponding to the given student id
     * @throws Exception
     */
    @GetMapping(value = "/getStudentById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentAndDeptTemplate> getStudentById(@PathVariable("id") long id) throws Exception {
        StudentAndDeptTemplate bean = studentServiceImpl.getStudentById(id);
        log.info("Fetched Student document of id " + id);
        return new ResponseEntity<StudentAndDeptTemplate>(bean, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method returns the list of student beans present in the given department id <br/>
     *
     * @param id
     * @return the list of student beans in the given department
     * @throws Exception
     */
    @GetMapping(value = "/getStudentByDeptId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentBean>> getStudentByDeptId(@PathVariable("id") int id) throws Exception {
        List<StudentBean> studentList = studentServiceImpl.getStudentByDeptId(id);
        log.info("Fetched Students by Department ID.");
        return new ResponseEntity<List<StudentBean>>(studentList, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method finds the student using student id and
     * deletes the student document from the Student Collection <br/>
     *
     * @param id
     * @return the deleted student bean
     * @throws Exception
     */
    @GetMapping(value = "/deleteStudent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentBean> deleteStudent(@PathVariable("id") long id) throws Exception {
        StudentBean studentBean = studentServiceImpl.deleteStudent(id);
        log.info("Deleted 1 document from Student collection: " + studentBean.getStudentId());
        return new ResponseEntity<StudentBean>(studentBean, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method updates the student document alredy present in the
     * Student collection with the new values given. <br/>
     *
     * @param studentBean
     * @return the updated student bean
     * @throws Exception
     */
    @PostMapping(value = "/updateStudentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentBean> updateStudentDetails(@RequestBody StudentBean studentBean) throws Exception {
        StudentBean bean = studentServiceImpl.updateStudentDetails(studentBean);
        log.info("Updated 1 student info: " + bean.getStudentId());
        return new ResponseEntity<StudentBean>(bean, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method handles all the exceptions, logs it in the logger
     * and prints an appropriate message <br/>
     *
     * @param request
     * @param ex
     * @return the exception message
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public String handleExceptions(HttpServletRequest request, Exception ex){
        log.error("Requested URL: " + request.getRequestURI());
        log.error("Raised Exception: " + ex.toString());
        String message = "An Error occured!" + "\n" + "Exception Caused:" + "\n" + ex.getLocalizedMessage();
        return message;
    }
}
