package com.redisexample.redisdemo.service;

import com.redisexample.redisdemo.model.Student;
import com.redisexample.redisdemo.repo.StudnetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class StudentService {

    @Autowired
    private StudnetRepo studnetRepo;

    public Student getStudentById(long id) {
        System.out.println("Getting from db");
        return studnetRepo.findById(id).get();
    }

    public void deleteStudent(long id) {
        studnetRepo.deleteById(id);
    }

    public Iterable<Student> getAllStudent(){
        return studnetRepo.findAll();
    }

    public void updateStudent(Student student) {
        Student st = studnetRepo.findById(student.getId()).get();
        if (st == null) {
            //throw NotFoundException
        }
        //update values to st object and save to db
        studnetRepo.save(st);
    }

    public void saveStudent(@RequestBody Student student){
        studnetRepo.save(student);
    }

}
