package calculator;


import org.joda.time.LocalDate;

public class CalculatorResult {

    private final LocalDate finalPaymentDate;
    private final double totalPayments;
    private final double interestPaid;
    private final int paymentCount;
    private final double maxPayment;
    private final double minPayment;
    private final double meanPayment;

    public CalculatorResult(LocalDate finalPaymentDate,
                            double totalPayments,
                            double interestPaid,
                            int paymentCount,
                            double maxPayment,
                            double minPayment,
                            double meanPayment) {
        this.finalPaymentDate = finalPaymentDate;
        this.totalPayments = totalPayments;
        this.interestPaid = interestPaid;
        this.paymentCount = paymentCount;
        this.maxPayment = maxPayment;
        this.minPayment = minPayment;
        this.meanPayment = meanPayment;
    }

    public LocalDate getFinalPaymentDate() {
        return finalPaymentDate;
    }

    public double getTotalPayments() {
        return totalPayments;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public int getPaymentCount() {
        return paymentCount;
    }

    public double getMaxPayment() {
        return maxPayment;
    }

    public double getMinPayment() {
        return minPayment;
    }

    public double getMeanPayment() {
        return meanPayment;
    }

    @Override
    public String toString() {
        return "CalculatorResult{"
                + "finalPaymentDate: " + finalPaymentDate + ", "
                + "totalPayments: " + doubleToString(totalPayments) + ", "
                + "interestPaid: " + doubleToString(interestPaid) + ", "
                + "paymentCount: " + paymentCount + ", "
                + "maxPayment: " + doubleToString(maxPayment) + ", "
                + "minPayment: " + doubleToString(minPayment) + ", "
                + "meanPayment: " + doubleToString(meanPayment)
                + "}";
    }

    private static String doubleToString(double value) {
        return String.format("%.2f", value);
    }
}
