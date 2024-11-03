package Order;

import Courier.CreateCourier;

public class CreateOrder {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;


    public CreateOrder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CreateOrder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CreateOrder withAddress(String address) {
        this.address = address;
        return this;
    }

    public CreateOrder withMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public CreateOrder withPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public CreateOrder withRentTime(Integer rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public CreateOrder withDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public CreateOrder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public CreateOrder withColor(String[] color) {
        this.color = color;
        return this;
    }
}
