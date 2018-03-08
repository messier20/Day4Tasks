package lt.swedbank.itacademy.service;

import lt.swedbank.itacademy.domain.*;
import lt.swedbank.itacademy.domain.*;
import lt.swedbank.itacademy.util.DateUtil;

import java.math.BigDecimal;
import java.util.*;


public class LoanService {
    private Loan[] loans;
    //Naming!!! "HighRiskLoans" -> "highRiskLoans"
    //"ArrayList<Loan> HighRiskLoans" too specific type. Use either "List<Loan> ..." or "Collection<Loan> ..."
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

    //Too specific return type!
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

    //Too specific return type!
    public ArrayList<VehicleLoan> getVehicleLoans() {
        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "VehicleLoans" -> "vehicleLoans"
        ArrayList<VehicleLoan> VehicleLoans = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan instanceof VehicleLoan) {
                VehicleLoans.add((VehicleLoan) loan);
            }
        }
        return VehicleLoans;
    }

    //Too specific return type!
    public ArrayList<VehicleLoan> getNormalRiskVehicleLoans() {

        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "NormalRiskVehicleLoans" -> "normalRiskVehicleLoans"
        ArrayList<VehicleLoan> NormalRiskVehicleLoans = new ArrayList<>();
        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "VehicleLoans" -> "vehicleLoans"
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

    //Too specific return type!
    public ArrayList<RealEstateLoan> getRealEstateLoan() {

        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "RealEstateLoans" -> "realEstateLoans"
        ArrayList<RealEstateLoan> RealEstateLoans = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan instanceof RealEstateLoan) {
                RealEstateLoans.add((RealEstateLoan) loan);
            }
        }
        return RealEstateLoans;
    }

    //Too specific return type!
    public ArrayList<RealEstateLoan> getPersonalRealEstateLoans() {

        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "RealEstateLoans" -> "realEstateLoans"
        ArrayList<RealEstateLoan> RealEstateLoans = getRealEstateLoan();
        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "PersonalRealEstateLoans" -> "personalRealEstateLoans"
        ArrayList<RealEstateLoan> PersonalRealEstateLoans = new ArrayList<>();

        for (RealEstateLoan realEstateLoan : RealEstateLoans) {
            if (realEstateLoan.getPurpose() == RealEstatePurpose.PERSONAL) {
                PersonalRealEstateLoans.add(realEstateLoan);
            }
        }
        return PersonalRealEstateLoans;
    }

    //Too specific return type!
    public ArrayList<VehicleLoan> getExpiredHighRiskVehicleLoansOfHighestDuration() {

        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "VehicleLoans" -> "vehicleLoans"
        ArrayList<VehicleLoan> VehicleLoans = getVehicleLoans();
        //Too specific type. Use either "List<...>" or "Collection<...>"!
        //Naming!!! "ExpiredVehicleLoansOfHighestDuration" -> "expiredVehicleLoansOfHighestDuration"
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

    //GOOD RETURN TYPE!!! FINALLY!!! :)
    //Solution is correct (y). Well done!
    public Collection<String> findVehicleModels() {
        //Naming!!! "VehicleLoans" -> "vehicleLoans"
        List<VehicleLoan> VehicleLoans = getVehicleLoans();
        //Naming!!! "VehicleModels" -> "vehicleModels"
        Collection<String> VehicleModels = new HashSet<>();

        for (VehicleLoan vehicleLoan : VehicleLoans) {
            VehicleModels.add(vehicleLoan.getModel());
        }
      return VehicleModels;
    }

    //Solution is correct (y). Well done!
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
