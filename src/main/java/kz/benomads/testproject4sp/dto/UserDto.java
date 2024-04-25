package kz.benomads.testproject4sp.dto;

import kz.benomads.testproject4sp.model.Category;
import kz.benomads.testproject4sp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String fullName;
    private String username;
    private String avatarUrl;
    private String email;
    private String phoneNumber;
    private Role role;
    private List<Long> orderIds;
    private List<Long> productIds;


    public <R> UserDto(Long id,
                       String fullName,
                       String username,
                       String avatarUrl,
                       String email,
                       String phoneNumber,
                       Role role,
                       List<Long> orderIds,
                       List<Long> productIds) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.orderIds = (List<Long>) orderIds;
        this.productIds = (List<Long>) productIds;
    }


}
