package kevat25.stufflog.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userId;

    @Column(name = "username")
    @Size(min = 3, max = 50, message = "must be between 3-500 characters")
    private String username;

    @Column(name = "password")
    @Size(min = 3, max = 50, message = "must be between 3-500 characters")
    private String password;

    @Column(name = "firstname")
    @Size(min = 3, max = 50, message = "must be between 3-50 characters")
    private String firstname;

    @Column(name = "surname")
    @Size(min = 3, max = 50, message = "must be between 3-50 characters")
    private String surname;

    @Column(name = "email")
    @Size(min = 7, max = 100, message = "must be between 7-100 characters")
    private String email;

    @JsonIgnoreProperties("userAccount")
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<Item> items;

    public UserAccount() {

    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public UserAccount(
            @Size(min = 3, max = 50, message = "must be between 3-500 characters") String username,
            @Size(min = 3, max = 50, message = "must be between 3-500 characters") String password,
            @Size(min = 3, max = 50, message = "must be between 3-50 characters") String firstname,
            @Size(min = 3, max = 50, message = "must be between 3-50 characters") String surname,
            @Size(min = 7, max = 100, message = "must be between 7-100 characters") String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
    }

    public UserAccount(List<Item> iditems) {
        items = iditems;
    }



    // public UserAccount(List<Item> items) {
    // this.items = items; }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    
    @Override
    public String toString() {
        return "UserAccount [userId=" + userId + ", username=" + username + ", password=" + password + ", firstname="
                + firstname + ", surname=" + surname + ", items=" + items + "]";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
