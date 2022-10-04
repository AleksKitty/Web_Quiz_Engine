package engine.businesslayer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "email")
    private String email;
    @NotBlank
    @Column(name = "password")
    private String password;
    @NotBlank
    @Column(name = "role")
    private String role; // should be prefixed with ROLE_

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
