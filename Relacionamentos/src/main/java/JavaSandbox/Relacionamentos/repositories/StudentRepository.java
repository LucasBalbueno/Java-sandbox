package JavaSandbox.Relacionamentos.repositories;

import JavaSandbox.Relacionamentos.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

}
