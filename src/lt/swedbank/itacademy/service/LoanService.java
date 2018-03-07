package lt.swedbank.itacademy.service;

import lt.swedbank.itacademy.domain.*;
import lt.swedbank.itacademy.domain.*;
import lt.swedbank.itacademy.util.DateUtil;

import java.math.BigDecimal;
import java.util.*;


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

    public ArrayList<VehicleLoan> getVehicleLoans() {

        ArrayList<VehicleLoan> VehicleLoans = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan instanceof VehicleLoan) {
                VehicleLoans.add((VehicleLoan) loan);
            }
        }
        return VehicleLoans;
    }

    public ArrayList<VehicleLoan> getNormalRiskVehicleLoans() {

        ArrayList<VehicleLoan> NormalRiskVehicleLoans = new ArrayList<>();
        ArrayList<VehicleLoan> VehicleLoans = getVehicleLoans();

        for (VehicleLoan vehicleLoan : VehicleLoans) {
            if (vehicleLoan.getRiskType() == LoanRiskType.NORMAL_RISK) {
                NormalRiskVehicleLoans.add(vehicleLoan);
            }
        }

        return NormalRiskVehicleLoans;
    }

    public int getMaximumAgeOfLowRiskLoanedVehicles() {

        ArrayList<VehicleLoan> VehicleLoans = getVehicleLoans();
        int temporaryMaxAge = 0;
        for (VehicleLoan vehicleLoan : VehicleLoans) {
            if (vehicleLoan.getRiskType() == LoanRiskType.LOW_RISK) {
                long ageInDays = DateUtil.differenceInDays(new Date(), vehicleLoan.getManufactured());
                int ageInYears = (int) (ageInDays / 365);

                if (temporaryMaxAge < ageInYears) {
                    temporaryMaxAge = ageInYears;
                }
            }

        }
        return temporaryMaxAge;
    }

    public ArrayList<RealEstateLoan> getRealEstateLoan() {

        ArrayList<RealEstateLoan> RealEstateLoans = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan instanceof RealEstateLoan) {
                RealEstateLoans.add((RealEstateLoan) loan);
            }
        }
        return RealEstateLoans;
    }

    public ArrayList<RealEstateLoan> getPersonalRealEstateLoans() {

        ArrayList<RealEstateLoan> RealEstateLoans = getRealEstateLoan();
        ArrayList<RealEstateLoan> PersonalRealEstateLoans = new ArrayList<>();

        for (RealEstateLoan realEstateLoan : RealEstateLoans) {
            if (realEstateLoan.getPurpose() == RealEstatePurpose.PERSONAL) {
                PersonalRealEstateLoans.add(realEstateLoan);
            }
        }
        return PersonalRealEstateLoans;
    }

    public ArrayList<VehicleLoan> getExpiredHighRiskVehicleLoansOfHighestDuration() {

        ArrayList<VehicleLoan> VehicleLoans = getVehicleLoans();
        ArrayList<VehicleLoan> ExpiredVehicleLoansOfHighestDuration = new ArrayList<>();

        int temporaryHighestDuration = 0;

        for (VehicleLoan vehicleLoan : VehicleLoans) {
            if (!vehicleLoan.isValid() && vehicleLoan.getRiskType() == LoanRiskType.HIGH_RISK) {
                if (vehicleLoan.getTermInYears() > temporaryHighestDuration) {
                    ExpiredVehicleLoansOfHighestDuration.add(vehicleLoan);
                }
            }
        }

        return ExpiredVehicleLoansOfHighestDuration;

    }

    public Collection<String> findVehicleModels() {
        List<VehicleLoan> VehicleLoans = getVehicleLoans();
        Collection<String> VehicleModels = new HashSet<>();

        for (VehicleLoan vehicleLoan : VehicleLoans) {
            VehicleModels.add(vehicleLoan.getModel());
        }
      return VehicleModels;
    }

    public Map<LoanRiskType, Collection<Loan>> groupLoansByRiskType() {

        Map<LoanRiskType, Collection<Loan>>  groupedLoansByRiskType = new HashMap<>();


        for (Loan loan : loans){
            LoanRiskType loanRiskType = loan.getRiskType();
            if(groupedLoansByRiskType.containsKey(loanRiskType)) {
                groupedLoansByRiskType.get(loanRiskType).add(loan);
            }
            else {
                Collection<Loan> loans = new ArrayList<>();
                loans.add(loan);
                groupedLoansByRiskType.put(loanRiskType, loans);
            }


        }
        return  groupedLoansByRiskType;
    }

}
