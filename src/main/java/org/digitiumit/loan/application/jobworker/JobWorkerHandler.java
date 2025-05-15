//package org.digitiumit.loan.application.jobworker;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import io.camunda.zeebe.client.api.response.ActivatedJob;
//import io.camunda.zeebe.client.api.worker.JobClient;
//import io.camunda.zeebe.spring.client.annotation.JobWorker;
//
//@Component
//public class JobWorkerHandler {
//
//	
//	@JobWorker(type="credit_score_check" , autoComplete = false)
//	public void handleJob(JobClient client,  ActivatedJob job) {
//
//		Map<String, Object> variables = new HashMap<>();
//
//		variables = job.getVariablesAsMap();
//
//		String name = (String) variables.get("name");
//		String empId = (String) variables.get("empId");
//		//String email = (String) variables.get("email");
//		String phoneNo = (String) variables.get("phoneNo");
//		
//		if (name!=null || empId!=null ||  phoneNo!=null) {
//			
//			variables.put("creditScore", true);
//		}
//		else
//		{
//			variables.put("creditScore", false);
//		}
//		
//		client.newCompleteCommand(job.getKey()).variables(variables).send().join();
//	
//	}
//	
//}
