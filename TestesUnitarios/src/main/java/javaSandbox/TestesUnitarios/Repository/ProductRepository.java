package javaSandbox.TestesUnitarios.Repository;

import javaSandbox.TestesUnitarios.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
