package lt.swedbank.itacademy.tasks.app;

import lt.swedbank.itacademy.tasks.domain.Loan;
import lt.swedbank.itacademy.tasks.domain.VehicleLoan;

public interface DomainInitializer {

    Loan[] initializeLoans();


}