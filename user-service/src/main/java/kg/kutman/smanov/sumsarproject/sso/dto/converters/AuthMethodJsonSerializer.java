package kg.kutman.smanov.sumsarproject.sso.dto.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public class AuthMethodJsonSerializer extends JsonSerializer<ClientAuthenticationMethod> {

    @Override
    public void serialize(ClientAuthenticationMethod value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
        if (value == null) {
            return;
        }
        gen.writeString(value.getValue());
    }
}
