package JavaSandbox.Relacionamentos.services;

import JavaSandbox.Relacionamentos.dto.CourseDTO;
import JavaSandbox.Relacionamentos.dto.StudentDTO;
import JavaSandbox.Relacionamentos.entities.CourseEntity;
import JavaSandbox.Relacionamentos.entities.StudentEntity;
import JavaSandbox.Relacionamentos.repositories.CourseRepository;
import JavaSandbox.Relacionamentos.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentName(studentDTO.getStudentName());
        studentEntity.setStudentEmail(studentDTO.getStudentEmail());

        studentRepository.save(studentEntity);

        return convertToDTO(studentEntity);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(id);

        if (studentEntityOptional.isPresent()) {
            StudentEntity studentEntity = studentEntityOptional.get();
            studentEntity.setStudentName(studentDTO.getStudentName());
            studentEntity.setStudentEmail(studentDTO.getStudentEmail());

            studentRepository.save(studentEntity);

            return convertToDTO(studentEntity);
        }

        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public ResponseEntity<StudentDTO> enrollStudentToCourse(Long studentId, Long courseId){
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(studentId);
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if(studentEntityOptional.isEmpty() || courseEntityOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        StudentEntity studentEntity = studentEntityOptional.get();
        CourseEntity courseEntity = courseEntityOptional.get();

        studentEntity.getCourses().add(courseEntity);
        studentRepository.save(studentEntity);

        return ResponseEntity.ok(convertToDTO(studentEntity));
    }

    public ResponseEntity<StudentDTO> unenrollStudentFromCourse(Long studentId, Long courseId){
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(studentId);
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if(studentEntityOptional.isEmpty() || courseEntityOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        StudentEntity studentEntity = studentEntityOptional.get();
        CourseEntity courseEntity = courseEntityOptional.get();

        studentEntity.getCourses().remove(courseEntity);
        studentRepository.save(studentEntity);

        return ResponseEntity.ok(convertToDTO(studentEntity));
    }

    public ResponseEntity<List<CourseDTO>> getCoursesByStudent(Long studentId){
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(studentId);

        if(studentEntityOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<CourseDTO> courseDTOs = studentEntityOptional.get().getCourses().stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(courseDTOs);
    }

    private StudentDTO convertToDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentEntity.getStudentId());
        studentDTO.setStudentName(studentEntity.getStudentName());
        studentDTO.setStudentEmail(studentEntity.getStudentEmail());
        studentDTO.setCourses(studentEntity.getCourses().stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList()));

        return studentDTO;
    }

    private CourseDTO convertToCourseDTO(CourseEntity courseEntity) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(courseEntity.getCourseId());
        courseDTO.setCourseName(courseEntity.getCourseName());
        courseDTO.setCourseDescription(courseEntity.getCourseDescription());
        return courseDTO;
    }
}