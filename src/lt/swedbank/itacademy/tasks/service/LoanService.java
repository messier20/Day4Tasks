package lt.swedbank.itacademy.tasks.service;

import lt.swedbank.itacademy.tasks.domain.Loan;
import lt.swedbank.itacademy.tasks.domain.LoanRiskType;
import lt.swedbank.itacademy.tasks.domain.VehicleLoan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;


public class LoanService {
    private Loan[] loans;
    private ArrayList<Loan> HighRiskLoans = new ArrayList<>();
    private BigDecimal averageLoanCost = BigDecimal.ZERO;
    private BigDecimal maximumPriceOfNonExpiredLoans = new BigDecimal(0);

    public LoanService(Loan[] loans) {
        this.loans = loans;
    }

    public Loan[] getLoans() {
        return loans;
    }

    public BigDecimal getAverageLoanCost() {

        int numberOfLoans = 0;

        for (Loan loan : loans) {
            averageLoanCost = averageLoanCost.add(loan.calculateTotalLoanCost());
            numberOfLoans++;
        }

        averageLoanCost = averageLoanCost.divide(new BigDecimal(numberOfLoans), 2, BigDecimal.ROUND_UP);

        return averageLoanCost;
    }

    public ArrayList<Loan> getHighRiskLoans() {

        for (Loan loan : loans) {
            if (loan.getRiskType() == LoanRiskType.HIGH_RISK) {
                HighRiskLoans.add(loan);
            }
        }

        return HighRiskLoans;
    }

    public BigDecimal getAverageLoanCostOfHighRiskLoans() {

        return getAverageLoanCost(LoanRiskType.HIGH_RISK);

    }

    public BigDecimal getMaximumPriceOfNonExpiredLoans() {

        for (Loan loan : loans) {
            if (loan.isValid()) {
                if (loan.getPrice().compareTo(maximumPriceOfNonExpiredLoans) > 0)
                    maximumPriceOfNonExpiredLoans = loan.getPrice();
            }
        }

        return maximumPriceOfNonExpiredLoans;
    }

    public BigDecimal getAverageLoanCost(LoanRiskType riskType) {

        BigDecimal averageLoanCost = BigDecimal.ZERO;

        int NumberOfRiskLoans = 0;

        for (Loan loan : loans) {
            if (loan.getRiskType() == riskType) {
                averageLoanCost = averageLoanCost.add(loan.calculateTotalLoanCost());
                NumberOfRiskLoans++;
            }
        }

        return averageLoanCost.divide(new BigDecimal(NumberOfRiskLoans), 2, BigDecimal.ROUND_UP);

    }


}
