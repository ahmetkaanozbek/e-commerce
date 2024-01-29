package com.aozbek.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User that = (User) obj;
        return (Objects.equals(username, that.username)) && (Objects.equals(password, that.password))
                && (Objects.equals(roles, that.roles));
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, roles);
    }
}
