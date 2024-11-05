package courier;

public class CourierLogin {
    private String password;
    private String login;

    public CourierLogin withLogin(String login) {
        this.login = login;
        return this;
    }
    public CourierLogin withPassword(String password) {
        this.password = password;
        return this;
    }

}
