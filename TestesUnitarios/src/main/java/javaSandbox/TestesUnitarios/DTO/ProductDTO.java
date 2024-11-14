package javaSandbox.TestesUnitarios.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private float price;
    private Integer quantity;
}