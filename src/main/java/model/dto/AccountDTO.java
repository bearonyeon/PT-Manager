package model.dto;

public class AccountDTO {
    private String userId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String role;
    private double height;
    
    public AccountDTO() {
        super();
    }

    public AccountDTO(String userId, String password, String name, String email, String phone, String role, double height) {
        super();
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.height = height;
    }

    public AccountDTO(String userId, String name, String email, String phone, String role, double height) {
        super();
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.height = height;
    }

    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public boolean matchPassword(String password) {
    	if (password == null) {
    		return false;
    	}
    	return this.password.equals(password);
    }
    
}
