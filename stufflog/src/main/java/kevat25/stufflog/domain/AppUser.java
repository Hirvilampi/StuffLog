package kevat25.stufflog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "appuser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long userIdLong;

    // username, that has to be unique and no null
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "firstname")
    @Size(min = 3, max = 50, message = "must be between 3-50 characters")
    private String firstname;

    @Column(name = "surname")
    @Size(min = 3, max = 50, message = "must be between 3-50 characters")
    private String surname;

    @Column(name = "email")
    @Size(min = 7, max = 100, message = "must be between 7-100 characters")
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    public AppUser() {
    }

    public AppUser(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }



    public AppUser(Long userIdLong, String username, String passwordHash,
            @Size(min = 3, max = 50, message = "must be between 3-50 characters") String firstname,
            @Size(min = 3, max = 50, message = "must be between 3-50 characters") String surname,
            @Size(min = 7, max = 100, message = "must be between 7-100 characters") String email, String role) {
        this.userIdLong = userIdLong;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserIdLong() {
        return userIdLong;
    }

    public void setUserIdLong(Long userIdLong) {
        this.userIdLong = userIdLong;
    }


    
}
