package kz.benomads.testproject4sp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long userId;
    private Long productId;
    private Integer quantity;
    private Double totalAmount;
    private String address;
    private String phoneNumber;
}
