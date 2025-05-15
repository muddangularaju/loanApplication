package org.digitiumit.loan.application.jobworker;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Component
public class LoanGeneration {

	
	@JobWorker(type = "generate_loan_agreement", autoComplete = false)
    public void handleGenerateLoanAgreement(JobClient client, ActivatedJob job) {

        Map<String, Object> loanMap = job.getVariablesAsMap();

        Number loanAmount = (Number) loanMap.get("loanAmount");
        long principalAmount = loanAmount.longValue();
        final int tenure = 2;
        final float interestRate = 0.8f;

        double totalAmount = principalAmount + (principalAmount * tenure * interestRate);
        loanMap.put("totalAmount", totalAmount);
        client.newCompleteCommand(job.getKey()).variables(loanMap).send().join();
    }
}
