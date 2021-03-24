package vergissmeinicht.upload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * Test for the class handling upload requests.
 */
class HandlerTest {
    /** An object of our subject under test. */
    private Handler handler;

    /** Still empty input map. */
    private Map<String, Object> input;

    @BeforeEach
    void init() {
        //initiate members
        handler = new Handler();
        input = new HashMap<>();
    }

    @Test
    void validateInputValidTest() {
        input.put("httpMethod", "POST");
        Map<String, String> result = handler.validateInput(input);
        Assertions.assertTrue(result.containsKey("Code"));
        Assertions.assertFalse(result.containsKey("Allow"));
        Assertions.assertEquals("200", result.get("Code"));
    }

    @Test
    void validateInputInvalidTest() {
        input.put("httpMethod", "GET");
        Map<String, String> result = handler.validateInput(input);
        Assertions.assertTrue(result.containsKey("Code"));
        Assertions.assertTrue(result.containsKey("Allow"));
        Assertions.assertEquals("400", result.get("Code"));
        Assertions.assertEquals("POST", result.get("Allow"));
    }

    @Test
    void handleRequestInvalidTest() {
        input.put("httpMethod", "GET");
        ApiGatewayResponse response = handler.handleRequest(input, null);
        Assertions.assertEquals(400, response.getStatusCode());
        Assertions.assertTrue(response.getHeaders().containsKey("Allow"));
        Assertions.assertEquals("POST", response.getHeaders().get("Allow"));
    }
}
