package cz.muni.fi.pa165.dto;

/**
 * @author Adam Bananka
 */
public class UserRegisterDTO {
    private UserDTO user;
    private String password;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
