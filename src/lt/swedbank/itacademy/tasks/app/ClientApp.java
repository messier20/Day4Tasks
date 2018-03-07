package lt.swedbank.itacademy.tasks.app;

import lt.swedbank.itacademy.tasks.domain.Loan;
import lt.swedbank.itacademy.tasks.domain.LoanRiskType;
import lt.swedbank.itacademy.tasks.domain.VehicleLoan;
import lt.swedbank.itacademy.tasks.service.LoanService;

public class ClientApp {

    public static void main(String[] args) {

        Loan[] loans = getInitializer().initializeLoans();

        LoanService service = new LoanService(loans);


        System.out.println(service.getAverageLoanCost());
        System.out.println("There are " + service.getHighRiskLoans().size());
        System.out.println(LoanRiskType.NORMAL_RISK + ": " + service.getAverageLoanCost(LoanRiskType.NORMAL_RISK));
        System.out.println(LoanRiskType.HIGH_RISK + ": " + service.getAverageLoanCost(LoanRiskType.HIGH_RISK));
        System.out.println(LoanRiskType.LOW_RISK + ": " + service.getAverageLoanCost(LoanRiskType.LOW_RISK));
        System.out.println(service.getAverageLoanCostOfHighRiskLoans());
        System.out.println(service.getMaximumPriceOfNonExpiredLoans());

    }


    public static DomainInitializer getInitializer() {
        return new Task1DomainInitializer();
    }

}
