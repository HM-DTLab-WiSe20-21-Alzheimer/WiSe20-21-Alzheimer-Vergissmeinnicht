import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import org.junit.jupiter.api.Test;
import vergissmeinicht.alexa.ResumePlaybackRequestHandler;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResumePlaybackRequestHandlerTest {
    @Test
    void CanHandleTest(){
        ResumePlaybackRequestHandler handler = new ResumePlaybackRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("AMAZON.ResumeIntent");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertTrue(handler.canHandle(hi));
    }

    @Test
    void CantHandleTest(){
        ResumePlaybackRequestHandler handler = new ResumePlaybackRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("wrong");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertFalse(handler.canHandle(hi));
    }

    @Test
    void handleTest(){
        ResumePlaybackRequestHandler handler = new ResumePlaybackRequestHandler();
        Intent intent = Intent.builder().withName("AMAZON.ResumeIntent").build();
        IntentRequest intentRequest = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope requestEnvelope = RequestEnvelope.builder().withRequest(intentRequest).build();

        HandlerInput handlerInput = HandlerInput.builder().withRequestEnvelope(requestEnvelope).build();
        Optional<Response> response = handler.handle(handlerInput);
        String responseString ="class SsmlOutputSpeech {\n" +
                "    class OutputSpeech {\n" +
                "        type: SSML\n" +
                "        playBehavior: null\n" +
                "    }\n" +
                "    ssml: <speak>Das Fortsetzen pausierter Streams ist zum jetzigen Zeitpunkt leider noch nicht möglich.</speak>\n" +
                "}";

        assertEquals(responseString,response.get().getOutputSpeech().toString());
    }
}
