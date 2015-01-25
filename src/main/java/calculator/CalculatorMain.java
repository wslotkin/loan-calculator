package calculator;

import org.joda.time.DateTime;

public class CalculatorMain {

    public static void main(String[] args) {
        LoanCalculator loanCalculator = new LoanCalculator();

        DateTime startTime = new DateTime(2014, 12, 17, 0, 0);
        DateTime endTime = new DateTime(2033, 12, 17, 0, 0);
        double startingPrincipal = 75987.14;
        double annualInterestRate = 0.08;
        double additionalPaymentPerMonth = 0.0;
        boolean useFixedMonthlyPayments = false;

        CalculatorResult result = loanCalculator.calculate(startTime,
                endTime,
                startingPrincipal,
                annualInterestRate,
                additionalPaymentPerMonth,
                useFixedMonthlyPayments);

        System.out.println(result);

        CalculatorResult minimumPayments = calculateForMinimumPayments(startTime, endTime, startingPrincipal, annualInterestRate);
        CalculatorResult alwaysMinimumPayments = calculateForAlwaysMinimumPayments(endTime, annualInterestRate);

        System.out.println(String.format("Amount saved compared to making minimum payments from start: $%.2f!", minimumPayments.getTotalPayments() - result.getTotalPayments()));
        System.out.println(String.format("Amount saved compared to always making minimum payments:     $%.2f!", alwaysMinimumPayments.getTotalPayments() - result.getTotalPayments()));
    }

    private static CalculatorResult calculateForMinimumPayments(DateTime startTime, DateTime endTime, double startingPrincipal, double annualInterestRate) {
        return new LoanCalculator().calculate(startTime,
                endTime,
                startingPrincipal,
                annualInterestRate,
                0.0,
                false);
    }

    private static CalculatorResult calculateForAlwaysMinimumPayments(DateTime endTime, double annualInterestRate) {
        return new LoanCalculator().calculate(new DateTime(2013, 12, 17, 0, 0),
                endTime,
                77841.10,
                annualInterestRate,
                0.0,
                false);
    }
}
