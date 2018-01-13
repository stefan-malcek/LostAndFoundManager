package cz.muni.fi.pa165.dto;

/**
 * @author Adam Bananka
 */
public class UserChangePwDTO {
    private UserAuthenticateDTO user;
    private String newPassword;

    public UserAuthenticateDTO getUser() {
        return user;
    }

    public void setUser(UserAuthenticateDTO user) {
        this.user = user;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
