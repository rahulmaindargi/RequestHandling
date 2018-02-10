package rahul.api.userdetails.controllers.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String loginId;
    private String password;
    private String confirm;

    private String emailId;

    /*public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String email) {
        this.emailId = email;
    }*/
}
