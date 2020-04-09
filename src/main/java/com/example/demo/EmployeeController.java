package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class EmployeeController {
    private static Map<String, Employee> employeeDB = new HashMap<>();
    static {
        Employee e1 = new Employee();
        e1.setId("1");
        e1.setName("Kaila");
        e1.setAge(24);
        employeeDB.put(e1.getId(), e1);

        Employee e2 = new Employee();
        e2.setId("2");
        e2.setName("Nimal");
        e2.setAge(28);
        employeeDB.put(e2.getId(), e2);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") String id) {
        if(employeeDB.containsKey(id)) {
            employeeDB.remove(id);
            return new ResponseEntity<>("Employee is deleted successsfully", HttpStatus.OK);
        } else return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {

        if(employee.getName()== null || employee.getAge()==0){
            return new ResponseEntity<>("Employee data is missing", HttpStatus.NOT_ACCEPTABLE);
        }
        if (employeeDB.containsKey(id)) {
            employeeDB.remove(id);
            employee.setId(id);
            employeeDB.put(id, employee);
            return new ResponseEntity<>("Employee is updated successsfully", HttpStatus.OK);
        } else return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/employees")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        if(employee.getName()== null || employee.getId() == null || employee.getAge()==0){
            return new ResponseEntity<>("Employee data is missing", HttpStatus.NOT_ACCEPTABLE);
        } else
        employeeDB.put(employee.getId(), employee);
        return new ResponseEntity<>("Employee is added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<Object> getAllEmployee() {
        return new ResponseEntity<>(employeeDB.values(), HttpStatus.OK);
    }

    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable("id") String id) {
        // employeeDB.get(id);
        if (employeeDB.containsKey(id)) {
            return new ResponseEntity<>(employeeDB.get(id), HttpStatus.OK);
        } else return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }
}

