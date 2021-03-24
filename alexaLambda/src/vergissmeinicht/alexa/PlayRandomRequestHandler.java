package vergissmeinicht.alexa;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import vergissmeinicht.helper.VergissmeinichtDynamoDB;
import vergissmeinicht.helper.VergissmeinichtHandlerInput;
import vergissmeinicht.helper.VergissmeinichtS3;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class PlayRandomRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("PlayRandom"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String bucketName = "vergissmeinnichtbucket145332-dev";
        String userId = VergissmeinichtHandlerInput.getSub(handlerInput);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(String.valueOf(Region.EU_Frankfurt))
                .build();
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
        String identityId = VergissmeinichtDynamoDB.getIdentityId(dynamoDB, userId);
        String fileName = VergissmeinichtDynamoDB.getFilenameForRandomMemory(userId, dynamoDB);
        String fileKey = "private/"+identityId+"/"+fileName;

        if (fileName != null) {
            String streamUrl = VergissmeinichtS3.getFileUrl(bucketName, fileKey, s3Client).toString();
            return handlerInput.getResponseBuilder()
                    .withSpeech("Hier ist eine zufällige Erinnerung.")
                    .addAudioPlayerPlayDirective(PlayBehavior.REPLACE_ALL, 0L, null, "bla", streamUrl)
                    .build();
        } else return handlerInput.getResponseBuilder()
                .withSpeech("Bisher sind keine Erinnerungen hinterlegt.")
                .build();
    }
}
