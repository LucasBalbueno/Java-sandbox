package javaSandbox.TestesUnitarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import javaSandbox.TestesUnitarios.Controller.ProductController;
import javaSandbox.TestesUnitarios.DTO.ProductDTO;
import javaSandbox.TestesUnitarios.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("MacBook Air Chip M1");
        productDTO.setDescription("O melhor computador que você pode ter!");
        productDTO.setPrice(4.999f);
        productDTO.setQuantity(3);

        ProductDTO dto = new ProductDTO(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getQuantity());

        when(productService.createProduct(dto)).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("MacBook Air Chip M1"));
    }

    @Test
    public void testGetProductById() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("MacBook Air Chip M1");
        productDTO.setDescription("O melhor computador que você pode ter!");
        productDTO.setPrice(4.999f);
        productDTO.setQuantity(3);

        when(productService.getProductById(productId)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("MacBook Air Chip M1"));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("MacBook Air Chip M1");
        productDTO.setDescription("O melhor computador que você pode ter!");
        productDTO.setPrice(4.999f);
        productDTO.setQuantity(3);

        when(productService.getAllProducts()).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("MacBook Air Chip M1"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName("MacBook Air Chip M1");
        productDTO.setDescription("O melhor computador que você pode ter!");
        productDTO.setPrice(4.999f);
        productDTO.setQuantity(3);

        ProductDTO updatedProductDTO = new ProductDTO("MacBook Air Chip M2", "Atualizado para o chip M2", 5.999f, 5);
        updatedProductDTO.setId(productId);

        when(productService.updateProduct(productId, updatedProductDTO)).thenReturn(updatedProductDTO);

        mockMvc.perform(put("/api/products/{id}", productId)
                        .content(objectMapper.writeValueAsString(updatedProductDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("MacBook Air Chip M2"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());
    }
}