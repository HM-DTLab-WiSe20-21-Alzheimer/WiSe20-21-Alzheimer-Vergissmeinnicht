import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import vergissmeinicht.alexa.PlayMemoryByDateRequestHandler;
import static org.mockito.Mockito.*;

public class PlayMemoryByDateRequestHandlerTest {
    /*
     * Die Hauptfunktionalität kann nicht mit Unittests getestet werden. Da hauptsächlich auf externe System,
     * also auf DynamoDB und S3, zugegriffen wird. Man müsste also mit einem Integrationstest testen. Man könnte für
     * unterschiedliche Test dateien den Response des Handlers prüfen.
     */

    @Test
    void PlayMemoryByDateRequestCanHandle(){
        PlayMemoryByDateRequestHandler handler = new PlayMemoryByDateRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("PlayRandomByDate");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertTrue(handler.canHandle(hi));
    }

    @Test
    void PlayMemoryByDateRequestCantHandle(){
        PlayMemoryByDateRequestHandler handler = new PlayMemoryByDateRequestHandler();
        Intent intent = mock(Intent.class);
        when(intent.getName()).thenReturn("wrong");
        IntentRequest ir = IntentRequest.builder().withIntent(intent).build();
        RequestEnvelope re = RequestEnvelope.builder().withRequest(ir).build();
        HandlerInput hi = HandlerInput.builder().withRequestEnvelope(re).build();
        assertFalse(handler.canHandle(hi));
    }
}
