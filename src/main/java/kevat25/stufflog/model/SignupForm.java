package kevat25.stufflog.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class SignupForm {
    @NotEmpty
    @Size (min = 4, max=30)
    private String username="";

    @NotEmpty
    @Size (min = 7, max =30)
    private String password="";

    @NotEmpty
@Size (min = 7, max =30)
private String passwordCheck="";


    @NotEmpty
    @Size(min = 3, max = 50, message = "must be between 3-50 characters")
    private String firstname;

    @NotEmpty
    @Size(min = 3, max = 50, message = "must be between 3-50 characters")
    private String surname;

    @NotEmpty
    @Size(min = 7, max = 100, message = "must be between 7-100 characters")
    private String email;


@NotEmpty
private String role = "USER";

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

public String getPasswordCheck() {
    return passwordCheck;
}

public void setPasswordCheck(String passwordCheck) {
    this.passwordCheck = passwordCheck;
}

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
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



}
