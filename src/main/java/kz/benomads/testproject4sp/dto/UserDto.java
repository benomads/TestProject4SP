package kz.benomads.testproject4sp.dto;

import kz.benomads.testproject4sp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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





}
