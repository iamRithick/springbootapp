package com.accenture.springbootapp.DAO;

import com.accenture.springbootapp.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentDAO extends MongoRepository<StudentEntity, Long> {
    StudentEntity findByStudentId(long studentId);

    List<StudentEntity> findAllByDeptId(int deptId);
}
