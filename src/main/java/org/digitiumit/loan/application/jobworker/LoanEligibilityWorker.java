package org.digitiumit.loan.application.jobworker;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoanEligibilityWorker {

    @JobWorker(type = "loan_eligibility_check")
    public Map<String, Object> evaluateEligibility(
            @Variable double income,
            @Variable boolean employmentStatus,
            @Variable(name = "debtToIncomeRatio") double debtToIncomeRatio) {

        Map<String, Object> result = new HashMap<>();

        if (employmentStatus && income > 25000) {
            result.put("incomeStatus", "eligible");

            // Example logic to calculate loan amount based on income and debt ratio
            double baseAmount = income * 10; // simple multiplier
            double riskFactor = (debtToIncomeRatio > 0.4) ? 0.75 : 1.0;

            double qualifiedLoanAmount = baseAmount * riskFactor;

            result.put("qualifiedLoanAmount", qualifiedLoanAmount);
            result.put("loanApprovalStatus", "Pre-Approved");

        } else {
            result.put("incomeStatus", "Not Eligible");
            result.put("qualifiedLoanAmount", 0.0);
            result.put("loanApprovalStatus", "Denied");
        }

        return result;
    }
}

