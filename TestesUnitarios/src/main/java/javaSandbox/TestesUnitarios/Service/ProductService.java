package javaSandbox.TestesUnitarios.Service;

import javaSandbox.TestesUnitarios.DTO.ProductDTO;
import javaSandbox.TestesUnitarios.Entity.ProductEntity;
import javaSandbox.TestesUnitarios.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        return productEntityOptional.map(this::convertToDTO).orElse(null);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setQuantity(productDTO.getQuantity());

        productRepository.save(productEntity);

        return convertToDTO(productEntity);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            productEntity.setName(productDTO.getName());
            productEntity.setDescription(productDTO.getDescription());
            productEntity.setPrice(productDTO.getPrice());
            productEntity.setQuantity(productDTO.getQuantity());

            productRepository.save(productEntity);

            return convertToDTO(productEntity);
        }

        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setQuantity(productEntity.getQuantity());

        return productDTO;
    }
}