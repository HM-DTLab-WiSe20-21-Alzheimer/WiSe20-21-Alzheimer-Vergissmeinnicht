import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import org.junit.jupiter.api.Test;
import vergissmeinicht.alexa.StopRequestHandler;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StopRequestHandlerTest {
    @Test
    void CanHandleTest(){
        StopRequestHandler handler = new StopRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("AMAZON.StopIntent");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertTrue(handler.canHandle(hi));
    }

    @Test
    void CantHandleTest(){
        StopRequestHandler handler = new StopRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("wrong");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertFalse(handler.canHandle(hi));
    }

    @Test
    void handleTest(){
        StopRequestHandler handler = new StopRequestHandler();
        Intent intent = Intent.builder().withName("AMAZON.StopIntent").build();
        IntentRequest intentRequest = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope requestEnvelope = RequestEnvelope.builder().withRequest(intentRequest).build();

        HandlerInput handlerInput = HandlerInput.builder().withRequestEnvelope(requestEnvelope).build();
        Optional<Response> response = handler.handle(handlerInput);
        String responseString ="class SsmlOutputSpeech {\n" +
                "    class OutputSpeech {\n" +
                "        type: SSML\n" +
                "        playBehavior: null\n" +
                "    }\n" +
                "    ssml: <speak>Auf Wiedersehen.</speak>\n" +
                "}";

        assertEquals(responseString,response.get().getOutputSpeech().toString());
    }
}
