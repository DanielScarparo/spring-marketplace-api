package dio.marketplace.registration.domain;

import org.springframework.util.Assert;

public class Customer {
    private CustomerId id;
    private String name;
    private String email;


    public Customer (CustomerId id, String name, String email) {
        Assert.notNull(name, "name must not be null");
        Assert.notNull(email, "email must not be null");

        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Customer (String name, String email) {
        this(new CustomerId(), name, email);
    }
}
