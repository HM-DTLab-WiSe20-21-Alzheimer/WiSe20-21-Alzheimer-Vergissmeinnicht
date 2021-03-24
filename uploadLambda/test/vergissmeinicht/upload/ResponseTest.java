package vergissmeinicht.upload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {
    @Test
    void CreateResponseTest(){
        Response test = new Response("test");
        String want = "test";
        String have = test.getMessage();
        assertEquals(want, have);
    }
}
