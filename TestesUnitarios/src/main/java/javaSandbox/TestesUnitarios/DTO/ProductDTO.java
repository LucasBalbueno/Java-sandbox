package javaSandbox.TestesUnitarios.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private float price;
    private Integer quantity;

    public ProductDTO() {}

    public ProductDTO(String name, String description, float price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}