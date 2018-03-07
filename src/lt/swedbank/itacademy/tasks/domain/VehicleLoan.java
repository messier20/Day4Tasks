package lt.swedbank.itacademy.tasks.domain;

import java.util.Date;

public class VehicleLoan extends Loan {
    private Date manufactured;
    private String model;
    private int age;
    private int maximumAge;

    public Date getManufactured() {
        return manufactured;
    }

    public String getModel() {
        return model;
    }

    public int getAge() {
        return age;
    }

    public int getMaximumAge() {
        return maximumAge;
    }

    public void setManufactured(Date manufactured) {
        this.manufactured = manufactured;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMaximumAge(int maximumAge) {
        this.maximumAge = maximumAge;
    }
}
