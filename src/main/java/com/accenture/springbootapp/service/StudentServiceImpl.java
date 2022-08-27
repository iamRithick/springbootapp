package com.accenture.springbootapp.service;

import com.accenture.springbootapp.DAO.StudentDAO;
import com.accenture.springbootapp.bean.DepartmentBean;
import com.accenture.springbootapp.bean.StudentAndDeptTemplate;
import com.accenture.springbootapp.bean.StudentBean;
import com.accenture.springbootapp.client.DepartmentConsumer;
import com.accenture.springbootapp.entity.StudentEntity;
import com.accenture.springbootapp.entity.StudentSequence;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentDAO studentDAO;
    private DepartmentConsumer departmentConsumer;
    private MongoOperations mongoOperations;

    /**
     * Paramaterized Constructor
     *
     * @param studentDAO
     * @param departmentConsumer
     * @param mongoOperations
     */
    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, DepartmentConsumer departmentConsumer, MongoOperations mongoOperations) {
        this.studentDAO = studentDAO;
        this.departmentConsumer = departmentConsumer;
        this.mongoOperations = mongoOperations;
    }

    /**
     *METHOD DESCRIPTION: <br/>
     *This method fetches the list of all Student entity,
     *convert them to bean and return the bean list <br/>
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentBean> getAllStudents() throws Exception{
        List<StudentBean> beans = null;
        try {
            List<StudentEntity> entities = studentDAO.findAll();
            beans = new ArrayList<StudentBean>();
            for (StudentEntity entity : entities) {
                beans.add(convertEntityToBean(entity));
            }
        }
        catch (Exception e){
            throw e;
        }
        return beans;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method inserts a Student entity to the Student collection <br/>
     *
     * @param studentBean
     * @return The inserted Student bean
     * @throws Exception
     */
    @Override
    public StudentBean addStudent(StudentBean studentBean) throws Exception{
        StudentEntity studentEntity = null;
        try {
            if(studentBean.getStudentId()==0){
                studentBean.setStudentId(generateSequence(StudentEntity.SEQUENCE_NAME));
            }
            studentEntity = convertBeanToEntity(studentBean);
            studentDAO.insert(studentEntity);
        }
        catch (DuplicateKeyException e){
            String message = " is already present";
            String error = e.getMessage();
            if(error.contains("studentId")){
                message = "Student ID" + message;
            }
            if(error.contains("phone")){
                message = "Phone number" + message;
            }
            if(error.contains("email")){
                message = "Email ID " + message;
            }
            throw new DuplicateKeyException(message);
        }
        catch (Exception e){
            mongoOperations.findAndModify(new Query().addCriteria(Criteria.where("_id").is(StudentEntity.SEQUENCE_NAME)),
                    new Update().inc("seq",-1), StudentSequence.class);
            throw e;
        }
        return convertEntityToBean(studentEntity);
    }

    /**
     * METHOD DESCRIPTION:<br/>
     * This method gets a list of Student beans and converts
     * them into entity and inserts the new Student entities
     * Omits the entities already present in the collection.<br/>
     *
     * @param studentBeanList
     * @return List of inserted Student Beans
     * @throws Exception
     */
    @Override
    public List<StudentBean> addStudentList(StudentBean[] studentBeanList) throws Exception {
        List<StudentBean> studentBeans = null;
        try{
            studentBeans = new ArrayList<StudentBean>();
            for(StudentBean bean:studentBeanList){
                try{
                    bean = addStudent(bean);
                    studentBeans.add(bean);
                }
                catch (Exception e){
                    continue;
                }
            }
        }
        catch (Exception e){
            throw e;
        }
        return studentBeans;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method fetches the Student entity based on id,
     * converts it into Student bean and returns it <br/>
     *
     * @param studentId
     * @return Student bean having the respective Student id
     * @throws Exception
     */
    @Override
    public StudentAndDeptTemplate getStudentById(long studentId) throws Exception{
        StudentAndDeptTemplate studentAndDeptTemplate = null;
        try {
            StudentEntity studentEntity = studentDAO.findByStudentId(studentId);
            StudentBean studentBean = convertEntityToBean(studentEntity);
            int deptId = studentBean.getDeptId();
            DepartmentBean deptBean = departmentConsumer.getDeptById(deptId);
            studentAndDeptTemplate = new StudentAndDeptTemplate(studentBean, deptBean);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Student ID is not present");
        }
        catch (Exception e){
            throw e;
        }
        return studentAndDeptTemplate;
    }

    /**
     * METHOD DESCRIPTION:<br/>
     * This method returns the list of Student entities
     * that has the given Department Id. <br/>
     *
     * @param deptId
     * @return List of Student entities
     * @throws Exception
     */
    @Override
    public List<StudentBean> getStudentByDeptId(int deptId) throws Exception{
        List<StudentBean> studentBeanList = null;
        try {
            List<StudentEntity> studentEntityList = studentDAO.findAllByDeptId(deptId);
            studentBeanList = new ArrayList<StudentBean>();
            for (StudentEntity entity : studentEntityList) {
                studentBeanList.add(convertEntityToBean(entity));
            }
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Dept Id is not present!");
        }
        catch (Exception e){
            throw e;
        }
        return studentBeanList;
    }


    /**
     * METHOD DESCRIPTION:<br/>
     * This method finds the student Entity with the given
     * Student id and deletes it.
     *
     * @param studentId
     * @return The deleted student entity
     * @throws Exception
     */
    @Override
    public StudentBean deleteStudent(long studentId) throws Exception{
        StudentBean bean = null;
        try{
            StudentEntity entity = studentDAO.findByStudentId(studentId);
            studentDAO.delete(entity);
            mongoOperations.findAndModify(new Query().addCriteria(Criteria.where("_id").is(StudentEntity.SEQUENCE_NAME)),
                    new Update().inc("seq",-1), StudentSequence.class);
            bean = convertEntityToBean(entity);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Student is not present!");
        }
        catch (Exception e){
            throw e;
        }
        return bean;
    }

    /**
     * METHOD DESCRIPTION:<br/>
     * This method updates the existing Student entity
     * with the new values passed in the Student bean <br/>
     *
     * @param studentBean
     * @return
     * @throws Exception
     */
    @Override
    public StudentBean updateStudentDetails(StudentBean studentBean) throws Exception{
        StudentBean bean = null;
        try {
            long studentId = studentBean.getStudentId();
            String objId = studentDAO.findByStudentId(studentId).getObjId();
            studentBean.setObjId(objId);
            StudentEntity entity = convertBeanToEntity(studentBean);
            studentDAO.save(entity);
            bean = convertEntityToBean(entity);
        }
        catch (Exception e){
            throw e;
        }
        return bean;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method is used to generate the Student ID
     * using the student_sequence collection <br/>
     *
     * @param sequenceName
     * @return Student Id
     */
    @Override
    public long generateSequence(String sequenceName) throws Exception {
        StudentSequence counter = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(sequenceName));
            counter = mongoOperations.findOne(query, StudentSequence.class);
            if (counter == null) {
                mongoOperations.insert(new StudentSequence("student_sequence", 173001));
            }
            counter = mongoOperations.findAndModify(query, new Update().inc("seq", 1), StudentSequence.class);
        }
        catch (Exception e){
            throw e;
        }
        return counter.getSeq();
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method is used to convert the Student Bean to Student Entity <br/>
     *
     * @param bean
     * @return Student Entity
     */
    public StudentEntity convertBeanToEntity(StudentBean bean){
        StudentEntity entity = new StudentEntity();
        BeanUtils.copyProperties(bean, entity);
        return entity;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method is used to convert the Student Entity to Student Bean <br/>
     *
     * @param entity
     * @return Student Entity
     */
    public StudentBean convertEntityToBean(StudentEntity entity){
        StudentBean bean = new StudentBean();
        BeanUtils.copyProperties(entity, bean);
        return bean;
    }

}
