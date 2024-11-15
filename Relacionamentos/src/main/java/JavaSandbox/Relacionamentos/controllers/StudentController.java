package JavaSandbox.Relacionamentos.controllers;

import JavaSandbox.Relacionamentos.dto.CourseDTO;
import JavaSandbox.Relacionamentos.dto.StudentDTO;
import JavaSandbox.Relacionamentos.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/cursos/{courseId}")
    public ResponseEntity<StudentDTO> enrollStudentToCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        return studentService.enrollStudentToCourse(studentId, courseId);
    }

    @DeleteMapping("/{studentId}/cursos/{courseId}")
    public ResponseEntity<StudentDTO> unenrollStudentFromCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        return studentService.unenrollStudentFromCourse(studentId, courseId);
    }

    @GetMapping("/{studentId}/cursos")
    public ResponseEntity<List<CourseDTO>> getCoursesByStudent(@PathVariable Long studentId){
        return studentService.getCoursesByStudent(studentId);
    }
}