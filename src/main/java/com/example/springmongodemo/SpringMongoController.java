package com.example.springmongodemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="/api/students")
public class SpringMongoController {
    @Autowired
    StudentRepository Sturepo;
    @GetMapping("/")
    public List<Student> getAllStudents(){
        return Sturepo.findAll();
    }

    @GetMapping("/{id}")
    private Object getById(@PathVariable String id){
        List<Student> details = new ArrayList<>();
        details = Sturepo.findAll();
        for(Student i:details){
            if(i.id.equals(id)){
                return i;
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id not Found !!");
    }

    @PostMapping("/")
    public ResponseEntity<String> addNewStudent(@RequestBody Student newStu) {
        if(newStu.id!=null && newStu.name!=null && newStu.roll!=null){
            String stuRoll = newStu.roll;
            int firstChar = stuRoll.indexOf("C");
            if(firstChar==0 && stuRoll.length()==4){
                List<Student> details = new ArrayList<>();
                details = Sturepo.findAll();
                List<String> dbIds = new ArrayList<>();
                List<String> dbRolls = new ArrayList<>();
                for (Student i : details) {
                    dbIds.add(i.id);
                    dbRolls.add(i.roll);
                }
                if (dbIds.contains(newStu.id) || dbRolls.contains(newStu.roll)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Id or Rollno is Already Exsists !!!");
                } else {
                    Sturepo.save(new Student(newStu.id, newStu.name, newStu.roll));
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("New Student has been Added");
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("ROLL NO is Not in Required Format");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Some Details are missing");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletebyId(@PathVariable String id){
        List<String> dbIds = new ArrayList<>();
        List<Student> details = new ArrayList<>();
        details = Sturepo.findAll();
        for(Student i:details){
            dbIds.add(i.id);
        }
        if(dbIds.contains(id)){
            Sturepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Student has been Deleted !!!");
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student Id not found");
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateStudents(@RequestBody Student newStu){
        if(newStu.id!=null && newStu.name!=null && newStu.roll!=null) {
            String stuRoll = newStu.roll;
            int firstChar = stuRoll.indexOf("C");
            if(firstChar==0 && stuRoll.length()==4) {
                List<String> dbIds = new ArrayList<>();
                List<Student> details = new ArrayList<>();
                details = Sturepo.findAll();
                for (Student i : details) {
                    dbIds.add(i.id);
                }
                if (dbIds.contains(newStu.id)) {
                    Optional<Student> updatedStu = Sturepo.findById(newStu.id);
                    updatedStu.get().name = newStu.name;
                    updatedStu.get().roll = newStu.roll;
                    Sturepo.save(updatedStu.get());
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("The User has been Updated");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entered Id not found");
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("ROLL NO is Not in Required Format");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Some Details are missing");
        }
    }
}
