package vergissmeinicht.upload;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

class ApiGatewayResponseTest {
    @Test
    void builderTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("TestHeader", "TestValue");
        ApiGatewayResponse sut = new ApiGatewayResponse.Builder()
                .setStatusCode(200)
                .setHeaders(headers)
                .setRawBody("Test")
                .setBase64Encoded(false)
                .build();
        Assertions.assertEquals("Test", sut.getBody());
        Assertions.assertEquals(200, sut.getStatusCode());
        Assertions.assertEquals(headers, sut.getHeaders());
        Assertions.assertFalse(sut.isIsBase64Encoded());
    }

    @Test
    void builderWithBinaryBodyTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("TestHeader", "TestValue");
        byte[] byteBody = "Hallo Welt".getBytes();
        ApiGatewayResponse sut = new ApiGatewayResponse.Builder()
                .setStatusCode(200)
                .setHeaders(headers)
                .setBinaryBody(byteBody)
                .build();
        Assertions.assertEquals(200, sut.getStatusCode());
        Assertions.assertEquals(headers, sut.getHeaders());
        Assertions.assertTrue(sut.isIsBase64Encoded());
    }

    @Test
    void builderWithObjectBodyTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("TestHeader", "TestValue");
        Object body = "Hallo Welt";
        ApiGatewayResponse sut = new ApiGatewayResponse.Builder()
                .setStatusCode(200)
                .setHeaders(headers)
                .setObjectBody(body)
                .build();
        Assertions.assertEquals(200, sut.getStatusCode());
        Assertions.assertEquals(headers, sut.getHeaders());
    }
}
