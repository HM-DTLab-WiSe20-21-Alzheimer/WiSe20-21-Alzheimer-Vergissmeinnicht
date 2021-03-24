package vergissmeinicht.upload;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.json.*;
import vergissmeinicht.helper.VergissmeinichtDynamoDB;
import java.util.HashMap;
import java.util.Map;


public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        Map<String, String> headers = validateInput(input);
        int statusCode = Integer.parseInt(headers.get("Code"));
        if (statusCode == 200) {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            DynamoDB dynamoDB = new DynamoDB(client);
            final String body = input.get("body").toString();
            JSONObject json = new JSONObject(body);
            VergissmeinichtDynamoDB.storeUserIds(dynamoDB, json.getJSONObject("user").getString("userID"), json.getJSONObject("user").getString("identityID"));
            VergissmeinichtDynamoDB.storeMemory(dynamoDB, json);
        }
        headers.remove("Code");
        return ApiGatewayResponse.builder()
                .setStatusCode(statusCode)
                .setHeaders(headers)
                .build();
    }

    /**
     * Validates a given request, built to be extended easily.
     * @param input input map to check
     * @return map of response headers to send
     */
    Map<String, String> validateInput(Map<String, Object> input) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Code", "200");
        if(!input.get("httpMethod").equals("POST")) {
            headers.put("Code", "400");
            headers.put("Allow", "POST");
        }
        return headers;
    }
}