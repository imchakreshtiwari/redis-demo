package com.redisexample.redisdemo.repo;

import com.redisexample.redisdemo.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudnetRepo extends CrudRepository<Student, Long> {

}
