package kz.benomads.testproject4sp.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String fullName;

    @Column(unique = true)
    private String username;
    private String password;
    private String matchingPassword;
    private String avatarUrl;
    private String email;
    private String phoneNumber;
    private List<Long> orderIds;
    private List<Long> productIds;

}
