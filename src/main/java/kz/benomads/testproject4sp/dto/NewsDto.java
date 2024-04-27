package kz.benomads.testproject4sp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private Long id;
    private String title;
    private String content;
    private Long productId;

}
