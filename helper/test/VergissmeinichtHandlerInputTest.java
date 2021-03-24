import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Session;
import com.amazon.ask.model.User;
import org.junit.jupiter.api.Test;
import vergissmeinicht.helper.VergissmeinichtHandlerInput;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VergissmeinichtHandlerInputTest {

    /*
     * getSlot hat keine Logik und ist nur dazu da den Code zu vereinfachen, da hier außerdem
     * ein AlexaRequest gebraucht wird, ist hier ein Integrationstest sinnvoll. Man würde einen
     * Request abschicken und prüfen ob getSlot den richtigen Slot zurückgibt
     */

    @Test
    void getSubTest() {
        HandlerInput hi = mock(HandlerInput.class);
        RequestEnvelope re = mock(RequestEnvelope.class);
        Session s = mock(Session.class);
        User u = mock(User.class);
        when(hi.getRequestEnvelope()).thenReturn(re);
        when(re.getSession()).thenReturn(s);
        when(s.getUser()).thenReturn(u);
        when(u.getAccessToken()).thenReturn("eyJraWQiOiJuY1FhaW5Dd3FQeDBsUTI2ZXVKM2RjZHVvTFcrUlBhTFora2h1MzF4ZHVBPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJlODY3Nzg5Mi1mNjFjLTRiYzMtODk3MC1kODc1MTlhMmFjMGMiLCJldmVudF9pZCI6IjgxZGNkMTdhLTdmZTAtNDAxOC1iZDIxLTI3Y2I4ZmY1NzliMiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoib3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNjA5NTkwOTA5LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtY2VudHJhbC0xLmFtYXpvbmF3cy5jb21cL2V1LWNlbnRyYWwtMV9UcFN5b2w1bHUiLCJleHAiOjE2MDk4NjUyMTQsImlhdCI6MTYwOTg2MTYxNCwidmVyc2lvbiI6MiwianRpIjoiMWNlMGI5MWItYzQwMS00ODBjLThiMjQtNWVjZjMzNmE2Mzk1IiwiY2xpZW50X2lkIjoiMThpcjFobTBzYzZhbDRwaWRqNTk1cThwcTIiLCJ1c2VybmFtZSI6ImU4Njc3ODkyLWY2MWMtNGJjMy04OTcwLWQ4NzUxOWEyYWMwYyJ9.Xw8hP0-Q91gh8pBJ0bfNu5u3ZQ5rymWoZe3O86JKkG3terrlPUmNM-HGKQ73Hgu53b_SIghfxqMTKP9Y8sCnwXXotpMMe947plsLhSbwd9qou-IzLAhILPnS9gT5ZF4C9BREAje7UFrB9RoHfpudinVyaUMR65pb2pabVp4o3oXq1O11htNOoxlN4XoBhSXI2bcBDTIMqw-P-9SBZmFA3YxGGBpbl-LOL22_GkL03B1jKCpI11FhayB1aNgA9Q1_TnVt5UPrpSG3L-x3PRloKOh8_cxoxjziXAxWSWnHHdP99PUn77MCLLfq1Qi3CvvAYrcVItT9Ij-3ICSBkF_5jg");
        String want = "e8677892-f61c-4bc3-8970-d87519a2ac0c";
        String have = VergissmeinichtHandlerInput.getSub(hi);
        assertEquals(want, have);
    }
}
