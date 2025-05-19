package kg.kutman.smanov.sumsarproject.sso.dto.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

public class GrantTypeJsonSerializer extends JsonSerializer<AuthorizationGrantType> {

    @Override
    public void serialize(
        AuthorizationGrantType value,
        JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider
    ) throws IOException {
        if (value == null) {
            return;
        }
        jsonGenerator.writeString(value.getValue());
    }
}
