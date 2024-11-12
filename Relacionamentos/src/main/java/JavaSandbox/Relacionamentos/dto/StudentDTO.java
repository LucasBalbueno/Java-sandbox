package JavaSandbox.Relacionamentos.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private List<CourseDTO> courses;}
