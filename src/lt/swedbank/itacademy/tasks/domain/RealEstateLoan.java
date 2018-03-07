package lt.swedbank.itacademy.tasks.domain;

public class RealEstateLoan extends Loan {
    private String district;
    private float area;
    private RealEstatePurpose purpose;

    public String getDistrict() {
        return district;
    }

    public float getArea() {
        return area;
    }

    public RealEstatePurpose getPurpose() {
        return purpose;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public void setPurpose(RealEstatePurpose purpose) {
        this.purpose = purpose;
    }
}
