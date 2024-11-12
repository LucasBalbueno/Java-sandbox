package JavaSandbox.Relacionamentos.services;

import JavaSandbox.Relacionamentos.dto.StudentDTO;
import JavaSandbox.Relacionamentos.entities.StudentEntity;
import JavaSandbox.Relacionamentos.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::convetToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentName(studentDTO.getStudentName());
        studentEntity.setStudentEmail(studentDTO.getStudentEmail());

        studentRepository.save(studentEntity);

        return convetToDTO(studentEntity);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(id);

        if (studentEntityOptional.isPresent()) {
            StudentEntity studentEntity = studentEntityOptional.get();
            studentEntity.setStudentName(studentDTO.getStudentName());
            studentEntity.setStudentEmail(studentDTO.getStudentEmail());

            studentRepository.save(studentEntity);

            return convetToDTO(studentEntity);
        }

        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO convetToDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(studentEntity.getStudentId());
        studentDTO.setStudentName(studentEntity.getStudentName());
        studentDTO.setStudentEmail(studentEntity.getStudentEmail());

        return studentDTO;
    }
}