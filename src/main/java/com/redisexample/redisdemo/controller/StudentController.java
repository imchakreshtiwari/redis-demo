package com.redisexample.redisdemo.controller;

import com.redisexample.redisdemo.service.StudentService;
import com.redisexample.redisdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@EnableCaching
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/test")
    public String testEP(){
        return "Hello Test";
    }


    @PostMapping
    public void saveStudent(@RequestBody Student student){
        studentService.saveStudent(student);
    }

    @CachePut(value = "student", key = "#student.id")
    @PutMapping
    public void updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
    }

    @GetMapping
    public Iterable<Student> getAllStudent(){
        return studentService.getAllStudent();
    }

    //whose age greater than 22 dont cache them
    @Cacheable(value = "student", key = "#id", unless = "#result.age > 22")
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") long id) {
        return studentService.getStudentById(id);
    }

    @CacheEvict(value = "student", allEntries = true)
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
    }

}
