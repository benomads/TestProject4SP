package kz.benomads.testproject4sp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;

    @OneToMany
    private List<Order> orders;
    @OneToMany
    private List<Product> products;


}
