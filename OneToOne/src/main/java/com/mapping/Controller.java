package com.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping
public class Controller {
    @Autowired
    private StuRepo stuRepo;
    @Autowired
    private TeacherRepo teacherRepo;

    @PostMapping("/student")
    public ResponseEntity<Student> save(@RequestBody Student stu){
        Student st=stuRepo.save(stu);
        return new ResponseEntity<>(st,HttpStatus.OK);
    }

   @GetMapping("/student")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = stuRepo.findAllWithTeacher();
        System.out.println("hello jii");
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/teacher")
    public ResponseEntity<Teacher> save(@RequestBody Teacher stu){
        Teacher st=teacherRepo.save(stu);
        return new ResponseEntity<>(st,HttpStatus.OK);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<Teacher>> getAll(){
        return new ResponseEntity<>(teacherRepo.findAll(),HttpStatus.OK);
    }

}

interface TeacherRepo extends JpaRepository<Teacher,Long> {
    
}

interface StuRepo extends JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student s JOIN FETCH s.teacher")
    public List<Student> findAllWithTeacher();
}
