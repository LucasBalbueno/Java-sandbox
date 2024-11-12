package JavaSandbox.Relacionamentos.services;

import JavaSandbox.Relacionamentos.dto.CourseDTO;
import JavaSandbox.Relacionamentos.dto.StudentDTO;
import JavaSandbox.Relacionamentos.entities.CourseEntity;
import JavaSandbox.Relacionamentos.entities.StudentEntity;
import JavaSandbox.Relacionamentos.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseName(courseDTO.getCourseName());
        courseEntity.setCourseDescription(courseDTO.getCourseDescription());

        courseRepository.save(courseEntity);

        return convertToDTO(courseEntity);
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(id);

        if (courseEntityOptional.isPresent()) {
            CourseEntity courseEntity = courseEntityOptional.get();
            courseEntity.setCourseName(courseDTO.getCourseName());
            courseEntity.setCourseDescription(courseDTO.getCourseDescription());

            courseRepository.save(courseEntity);

            return convertToDTO(courseEntity);
        }

        return null;
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public ResponseEntity<List<StudentDTO>> getAllAlunosByCurso(Long id){
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(id);
        if(courseEntityOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<StudentDTO> studentDTOs = courseEntityOptional.get().getStudents().stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(studentDTOs);
    }

    private StudentDTO convertToStudentDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentEntity.getStudentId());
        studentDTO.setStudentName(studentEntity.getStudentName());
        studentDTO.setStudentEmail(studentEntity.getStudentEmail());
        return studentDTO;
    }

    private CourseDTO convertToDTO(CourseEntity courseEntity) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(courseEntity.getCourseId());
        courseDTO.setCourseName(courseEntity.getCourseName());
        courseDTO.setCourseDescription(courseEntity.getCourseDescription());

        return courseDTO;
    }
}