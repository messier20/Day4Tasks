package lt.swedbank.itacademy.tasks.domain;

import java.math.BigDecimal;
import java.util.Date;

import static lt.swedbank.itacademy.tasks.util.DateUtil.addYears;

public class Loan {

    private Date creationDate;
    private int termInYears;
    private String name;
    private BigDecimal interestRate;
    private BigDecimal price;
    private LoanRiskType riskType;


    public Date getCreationDate() {
        return creationDate;
    }

    public int getTermInYears() {
        return termInYears;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LoanRiskType getRiskType() {
        return riskType;
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setTermInYears(int termInYears) {
        this.termInYears = termInYears;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRiskType(LoanRiskType riskType) {
        this.riskType = riskType;
    }


    public BigDecimal calculateTotalLoanCost() {
        return price.add(calculateInterest());
    }

    public BigDecimal calculateInterest() {
        return price.multiply(interestRate.divide(new BigDecimal(100)));
    }

    public boolean isValid() {
        Date terminationDate = addYears(creationDate, termInYears);
        return terminationDate.after(new Date());
    }

}
