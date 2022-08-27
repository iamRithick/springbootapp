package com.accenture.springbootapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class DESCRIPTION: <br/>
 * This entity class is used to store the store
 * the sequence value of Student Document. <br/>
 */
@Document(collection = "student_sequence")
public class StudentSequence {

    @Id
    private String id;
    private long seq;

    public StudentSequence() {
    }

    public StudentSequence(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "StudentSequence{" +
                "id='" + id + '\'' +
                ", seq=" + seq +
                '}';
    }
}
