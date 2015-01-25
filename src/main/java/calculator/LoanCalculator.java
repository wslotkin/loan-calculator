package calculator;

import org.joda.time.DateTime;

import static java.lang.Math.min;
import static java.lang.Math.pow;
import static org.joda.time.Days.daysBetween;
import static org.joda.time.Months.monthsBetween;

public class LoanCalculator {

    private static final double MONTHS_PER_YEAR = 12.0;

    public CalculatorResult calculate(DateTime startTime,
                                      DateTime endTime,
                                      double principal,
                                      double annualInterestRate,
                                      double additionalPayment,
                                      boolean useFixedMonthlyPayment) {
        DateTime currentPaymentTime = startTime;
        double currentPrincipal = principal;

        double initialMonthlyPayment = estimateMonthlyPayment(currentPaymentTime, endTime, currentPrincipal, annualInterestRate);

        CalculatorResultBuilder resultBuilder = new CalculatorResultBuilder();
        while (currentPrincipal > 0.0) {
            double dailyInterestRate = calculateDailyInterestRate(currentPaymentTime, annualInterestRate);
            DateTime nextPaymentTime = currentPaymentTime.plusMonths(1);
            int daysInPaymentMonth = daysBetween(currentPaymentTime, nextPaymentTime).getDays();

            double monthlyPayment = !useFixedMonthlyPayment
                    ? estimateMonthlyPayment(currentPaymentTime, endTime, currentPrincipal, annualInterestRate)
                    : initialMonthlyPayment;

            double accruedInterest = daysInPaymentMonth * dailyInterestRate * currentPrincipal;
            double principalAfterInterest = currentPrincipal + accruedInterest;
            double totalPayment = min(monthlyPayment + additionalPayment, principalAfterInterest);

            resultBuilder.addInterest(min(totalPayment, accruedInterest));
            resultBuilder.addPayment(totalPayment);

            currentPrincipal = principalAfterInterest - totalPayment;
            currentPaymentTime = nextPaymentTime;
        }

        return resultBuilder.build(currentPaymentTime.toLocalDate());
    }

    private static double estimateMonthlyPayment(DateTime currentPaymentTime,
                                                 DateTime endDateTime,
                                                 double currentPrincipal,
                                                 double annualInterestRate) {
        int monthsLeftInLoan = monthsBetween(currentPaymentTime, endDateTime).getMonths();
        double monthlyInterestRate = annualInterestRate / MONTHS_PER_YEAR;

        double compoundedInterest = pow(1 + monthlyInterestRate, monthsLeftInLoan);

        return (currentPrincipal * monthlyInterestRate * compoundedInterest) / (compoundedInterest - 1);
    }

    private static double calculateDailyInterestRate(DateTime currentPaymentTime, double annualInterestRate) {
        DateTime firstDayOfYear = currentPaymentTime.withMonthOfYear(1).withDayOfMonth(1);
        DateTime firstDayOfNextYear = firstDayOfYear.plusYears(1);
        int daysInYear = daysBetween(firstDayOfYear, firstDayOfNextYear).getDays();
        return annualInterestRate / daysInYear;
    }
}
