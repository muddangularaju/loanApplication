package org.digitiumit.loan.application.jobworker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;

@Component
public class CreditScoreWorker {

	@JobWorker(type = "credit_score_check")
    public Map<String, Object> fetchCreditScore(@Variable String applicantId) {
        try {
            String endpoint = "https://loanapplication.free.beeceptor.com/credit-score/" + applicantId;

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed to fetch credit score");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
            in.close();

            // Use Jackson to parse JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(content.toString());

            int creditScore = json.get("creditScore").asInt();
            String scoreBand = json.get("scoreBand").asText();

            // Return variables directly
            Map<String, Object> variables = new HashMap<>();
            variables.put("creditScore", creditScore);
            variables.put("scoreBand", scoreBand);

            return variables;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching credit score: " + e.getMessage(), e);
        }
    }
}

