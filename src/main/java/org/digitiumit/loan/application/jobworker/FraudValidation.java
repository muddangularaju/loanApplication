package org.digitiumit.loan.application.jobworker;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Component
public class FraudValidation {
	 @JobWorker(type = "fraud_verification", autoComplete = false)
	    public void handleGenerateLoanAggrement(JobClient client, ActivatedJob job) {

	        Map<String, Object> variables = job.getVariablesAsMap();

	        String panNumber = (String) variables.get("panNumber");
	        Long aadharNumber = (Long) variables.get("aadharNumber");
	        String aadhar = (aadharNumber != null) ? String.valueOf(aadharNumber) : null;

	        boolean validatestatus = panNumber != null && panNumber.length() == 10 &&
	                                 aadhar != null && aadhar.length() == 12;

	        Map<String, Object> updatedVariables = new HashMap<>(variables);
	        updatedVariables.put("validatestatus", validatestatus);

	        client.newCompleteCommand(job.getKey())
	              .variables(updatedVariables)
	              .send()
	              .join();
	    }
}
