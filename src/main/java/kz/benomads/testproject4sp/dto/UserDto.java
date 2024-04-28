package kz.benomads.testproject4sp.dto;

import kz.benomads.testproject4sp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private List<Role> role;
    private List<Long> orderIds = new ArrayList<>();
    private List<Long> productIds = new ArrayList<>();


    public UserDto(Long id,
                   String fullName,
                   String username,
                   String avatarUrl,
                   String email,
                   String phoneNumber,
                   List<Role> roles
                   ) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = roles;
    }
}
