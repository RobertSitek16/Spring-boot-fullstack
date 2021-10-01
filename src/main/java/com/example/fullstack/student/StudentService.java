package com.example.fullstack.student;

import com.example.fullstack.student.exception.BadRequestException;
import com.example.fullstack.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        String email = student.getEmail();
        Boolean existsEmail = studentRepository.selectExistsEmail(email);
        if (existsEmail) {
            throw new BadRequestException("Email " + email + " is already taken!");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Student with id " + studentId + " doesn't exists!");
        }
        studentRepository.deleteById(studentId);
    }
}
