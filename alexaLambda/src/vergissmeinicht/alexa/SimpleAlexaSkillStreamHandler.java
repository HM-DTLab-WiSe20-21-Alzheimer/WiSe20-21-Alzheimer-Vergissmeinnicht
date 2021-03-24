package vergissmeinicht.alexa;

import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class SimpleAlexaSkillStreamHandler extends SkillStreamHandler {
    public SimpleAlexaSkillStreamHandler() {
        super(Skills.standard()
                .addRequestHandler(new CustomLaunchRequestHandler())
                .addRequestHandler(new PlayMemoryByDateRequestHandler())
                .addRequestHandler(new PlayRandomRequestHandler())
                .addRequestHandler(new PausePlaybackRequestHandler())
                .addRequestHandler(new ResumePlaybackRequestHandler())
                .addRequestHandler(new FallbackHandler())
                .addRequestHandler(new StopRequestHandler())
                .build());
    }
}
