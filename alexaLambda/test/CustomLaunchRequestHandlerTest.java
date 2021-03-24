import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import org.junit.jupiter.api.Test;
import vergissmeinicht.alexa.CustomLaunchRequestHandler;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomLaunchRequestHandlerTest {
    @Test
    void CustomLaunchRequestHandlerTestCanHandle() {
        CustomLaunchRequestHandler handler = new CustomLaunchRequestHandler();
        LaunchRequest lr = mock(LaunchRequest.class);
        RequestEnvelope requestEnvelope = RequestEnvelope.builder().withRequest(lr).build();
        HandlerInput handlerInput = HandlerInput.builder().withRequestEnvelope(requestEnvelope).build();
        assertTrue(handler.canHandle(handlerInput,lr));
    }

    @Test
    void CustomLaunchRequestHandlerTestHandle(){
        CustomLaunchRequestHandler handler = new CustomLaunchRequestHandler();
        LaunchRequest lr = mock(LaunchRequest.class);
        RequestEnvelope requestEnvelope = RequestEnvelope.builder().withRequest(lr).build();
        HandlerInput handlerInput = HandlerInput.builder().withRequestEnvelope(requestEnvelope).build();
        Optional<Response> response = handler.handle(handlerInput, lr);
        String responseString = "class SsmlOutputSpeech {\n" +
                "    class OutputSpeech {\n" +
                "        type: SSML\n" +
                "        playBehavior: null\n" +
                "    }\n" +
                "    ssml: <speak>Willkomen zu Vergissmeinicht, frag mich nach Erinnerungen! Sag zum Beispiel: Erzähl mir was.</speak>\n" +
                "}";
        assertEquals(responseString,response.get().getOutputSpeech().toString());
    }
}