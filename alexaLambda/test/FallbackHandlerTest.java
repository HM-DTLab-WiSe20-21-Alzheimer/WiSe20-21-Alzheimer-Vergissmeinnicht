import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import org.junit.jupiter.api.Test;
import vergissmeinicht.alexa.FallbackHandler;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FallbackHandlerTest {
    @Test
    void CanHandleTest(){
        FallbackHandler handler = new FallbackHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("AMAZON.FallbackIntent");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertTrue(handler.canHandle(hi));
    }

    @Test
    void CantHandleTest(){
        FallbackHandler handler = new FallbackHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("wrong");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertFalse(handler.canHandle(hi));
    }

    @Test
    void handleTest(){
        FallbackHandler handler = new FallbackHandler();
        Intent intent = Intent.builder().withName("AMAZON.FallbackIntent").build();
        IntentRequest intentRequest = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope requestEnvelope = RequestEnvelope.builder().withRequest(intentRequest).build();

        HandlerInput handlerInput = HandlerInput.builder().withRequestEnvelope(requestEnvelope).build();
        Optional<Response> response = handler.handle(handlerInput);
        String responseString ="class SsmlOutputSpeech {\n" +
                "    class OutputSpeech {\n" +
                "        type: SSML\n" +
                "        playBehavior: null\n" +
                "    }\n" +
                "    ssml: <speak>Ich habe dich leider nicht verstanden. Bitte versuche es noch einmal.</speak>\n" +
                "}";

        assertEquals(responseString,response.get().getOutputSpeech().toString());
    }
}
