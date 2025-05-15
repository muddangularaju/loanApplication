package org.digitiumit.loan.application.jobworker;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Component
public class LoanDisbursement {

	
	 @JobWorker(type = "loan_disbursement", autoComplete = false)
    public void handleSendAmount(JobClient client, ActivatedJob job) {

        Map<String, Object> variables = job.getVariablesAsMap();

     
        Number totalAmount = (Number) variables.get("totalAmount");
        Number accountNumber = (Number) variables.get("accountNumber");
        System.out.println("Sending ₹" + totalAmount + " to account number: " + accountNumber);

        Map<String, Object> result = new HashMap<>();
        result.put("transferStatus", "SUCCESS");
        result.put("transferredAmount", totalAmount);

        client.newCompleteCommand(job.getKey())
              .variables(result)
              .send()
              .join();
    }
}
