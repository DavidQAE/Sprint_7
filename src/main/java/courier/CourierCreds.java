package courier;

public class CourierCreds {


    private String password;
    private String login;

    public CourierCreds(String login, String password) {
        this.password = password;
        this.login = login;
    }
   public static CourierCreds credsFromCourier(CreateCourier createCourier){

        return new CourierCreds(createCourier.getLogin(), createCourier.getPassword());
    }
}
