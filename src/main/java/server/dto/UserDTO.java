package server.dto;

public class UserDTO {
    private int userId;
    private String name;
    private String role;
    private String username;
    private boolean accountLocked;


    public UserDTO() {
    }

    public UserDTO(int userId, String name, String role, String username, boolean accountLocked) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.username = username;
        this.accountLocked = accountLocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", accountLocked=" + accountLocked +
                '}';
    }
}