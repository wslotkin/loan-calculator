package calculator;

import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

import static java.lang.String.format;

public class CalculatorMain {

    private static final String AMOUNT_SAVED_COMPARED_TO_MAKING_MINIMUM_PAYMENTS_FROM_START = "Amount saved compared to making minimum payments from start: \t$%s!";
    private static final String AMOUNT_SAVED_COMPARED_TO_ALWAYS_MAKING_MINIMUM_PAYMENTS = "Amount saved compared to always making minimum payments: \t$%s!";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
    private static final int DEFAULT_PANEL_WIDTH = 1350;
    private static final int DEFAULT_COMPONENT_WIDTH = 100;
    private static final int DEFAULT_PANEL_HEIGHT = 30;
    private static final int DEFAULT_HEIGHT = 20;

    private static JTextField startDateField;
    private static JTextField endDateField;
    private static JTextField startingPrincipalField;
    private static JTextField interestRateField;
    private static JTextField additionalPaymentField;
    private static JCheckBox useFixedPaymentsBox;
    private static JLabel outputLabel1;
    private static JLabel outputLabel2;
    private static JLabel outputLabel3;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        frame.setTitle("Loan Calculator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1400, 200));

        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));

        startDateField = addInputField(inputPanel, "Start Date", new DateTime(2014, 12, 17, 0, 0).toLocalDate().toString());
        endDateField = addInputField(inputPanel, "End Date", new DateTime(2033, 12, 17, 0, 0).toLocalDate().toString());
        startingPrincipalField = addInputField(inputPanel, "Starting Principal", String.valueOf(75987.14));
        interestRateField = addInputField(inputPanel, "Annual Interest Rate", String.valueOf(0.08));
        additionalPaymentField = addInputField(inputPanel, "Additional Payment Per Month", String.valueOf(0.0));

        JLabel useFixedPaymentsLabel = new JLabel("Use Fixed Monthly Payments");
        useFixedPaymentsBox = new JCheckBox();
        inputPanel.add(useFixedPaymentsLabel);
        inputPanel.add(useFixedPaymentsBox);


        JPanel buttonPanel = new JPanel();
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(DEFAULT_COMPONENT_WIDTH, DEFAULT_HEIGHT));
        calculateButton.addActionListener(e -> calculate());
        buttonPanel.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));
        buttonPanel.add(calculateButton);

        JPanel outputPanel1 = new JPanel();
        outputPanel1.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));
        outputLabel1 = new JLabel("");
        outputPanel1.add(outputLabel1);

        JPanel outputPanel2 = new JPanel();
        outputPanel2.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));
        outputLabel2 = new JLabel(format(AMOUNT_SAVED_COMPARED_TO_MAKING_MINIMUM_PAYMENTS_FROM_START, 0.0));
        outputPanel2.add(outputLabel2);

        JPanel outputPanel3 = new JPanel();
        outputPanel3.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));
        outputLabel3 = new JLabel(format(AMOUNT_SAVED_COMPARED_TO_ALWAYS_MAKING_MINIMUM_PAYMENTS, 0.0));
        outputPanel3.add(outputLabel3);

        JPanel overallPanel = new JPanel();
        overallPanel.setPreferredSize(new Dimension(1400, 150));
        overallPanel.add(inputPanel);
        overallPanel.add(buttonPanel);
        overallPanel.add(outputPanel1);
        overallPanel.add(outputPanel2);
        overallPanel.add(outputPanel3);
        frame.add(overallPanel);

        frame.pack();
        frame.setVisible(true);

        calculate();
    }

    private static void calculate() {
        DateTime startTime = DateTime.parse(startDateField.getText());
        DateTime endTime = DateTime.parse(endDateField.getText());
        double startingPrincipal = Double.parseDouble(startingPrincipalField.getText());
        double annualInterestRate = Double.parseDouble(interestRateField.getText());

        CalculatorResult result = new LoanCalculator().calculate(startTime,
                endTime,
                startingPrincipal,
                annualInterestRate,
                Double.parseDouble(additionalPaymentField.getText()),
                useFixedPaymentsBox.isSelected());

        CalculatorResult minimumPayments = calculateForMinimumPayments(startTime, endTime, startingPrincipal, annualInterestRate);
        CalculatorResult alwaysMinimumPayments = calculateForAlwaysMinimumPayments(endTime, annualInterestRate);

        outputLabel1.setText(result.toString());
        outputLabel2.setText(format(AMOUNT_SAVED_COMPARED_TO_MAKING_MINIMUM_PAYMENTS_FROM_START, DECIMAL_FORMAT.format(minimumPayments.getTotalPayments() - result.getTotalPayments())));
        outputLabel3.setText(format(AMOUNT_SAVED_COMPARED_TO_ALWAYS_MAKING_MINIMUM_PAYMENTS, DECIMAL_FORMAT.format(alwaysMinimumPayments.getTotalPayments() - result.getTotalPayments())));
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

    private static JTextField addInputField(JPanel inputPanel, String fieldName, String defaultValue) {
        JLabel fieldLabel = new JLabel(fieldName + ":");
        inputPanel.add(fieldLabel);

        JTextField inputField = new JTextField(defaultValue);
        inputField.setPreferredSize(new Dimension(DEFAULT_COMPONENT_WIDTH, DEFAULT_HEIGHT));
        inputPanel.add(inputField);

        return inputField;
    }
}
