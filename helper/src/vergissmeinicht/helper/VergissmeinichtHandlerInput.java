package vergissmeinicht.helper;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import org.apache.commons.codec.binary.Base64;

public final class VergissmeinichtHandlerInput {

    private VergissmeinichtHandlerInput() {}

    public static String getSlot(HandlerInput handlerInput, String slotName){
        IntentRequest intent = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
        return intent.getIntent().getSlots().get(slotName).getValue();
    }

    public static String getSub(HandlerInput handlerInput){
        String accessToken = handlerInput.getRequestEnvelope().getSession().getUser().getAccessToken();

        String[] splitString = accessToken.split("\\.");
        String base64EncodedBody = splitString[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));

        String[] first = body.replace("{","").split(",");
        String[] sub = first[0].split(":");
        return sub[1].replace("\"", "");
    }
}
