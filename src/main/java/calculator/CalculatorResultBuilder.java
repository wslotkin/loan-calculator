package calculator;

import org.joda.time.LocalDate;

import static java.lang.Double.isNaN;

public class CalculatorResultBuilder {

    private double totalPayments;
    private double interestPaid;
    private int paymentCount;
    private double maxPayment = Double.NaN;
    private double minPayment = Double.NaN;

    public CalculatorResultBuilder addPayment(double payment) {
        this.totalPayments += payment;
        paymentCount++;
        if (isNaN(maxPayment) || payment > maxPayment) maxPayment = payment;
        if (isNaN(minPayment) || payment < minPayment) minPayment = payment;
        return this;
    }

    public CalculatorResultBuilder addInterest(double interestPaid) {
        this.interestPaid += interestPaid;
        return this;
    }

    public CalculatorResult build(LocalDate finalPaymentEndDate) {
        return new CalculatorResult(finalPaymentEndDate,
                totalPayments,
                interestPaid,
                paymentCount,
                maxPayment,
                minPayment,
                totalPayments / paymentCount);
    }
}
