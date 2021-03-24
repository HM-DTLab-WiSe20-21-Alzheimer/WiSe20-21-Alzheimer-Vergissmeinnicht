import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import com.amazon.ask.model.interfaces.audioplayer.StopDirective;
import org.junit.jupiter.api.Test;
import vergissmeinicht.alexa.PausePlaybackRequestHandler;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PausePlaybackRequestHandlerTest {
    @Test
    void CanHandleTest(){
        PausePlaybackRequestHandler handler = new PausePlaybackRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("AMAZON.PauseIntent");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertTrue(handler.canHandle(hi));
    }

    @Test
    void CantHandleTest(){
        PausePlaybackRequestHandler handler = new PausePlaybackRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("wrong");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertFalse(handler.canHandle(hi));
    }

    @Test
    void handleTest(){
        PausePlaybackRequestHandler handler = new PausePlaybackRequestHandler();
        Intent intent = Intent.builder().withName("AMAZON.PauseIntent").build();
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

        Directive dir =  response.get().getDirectives().get(0);
        assertEquals(dir.getClass(), StopDirective.class);
    }
}
