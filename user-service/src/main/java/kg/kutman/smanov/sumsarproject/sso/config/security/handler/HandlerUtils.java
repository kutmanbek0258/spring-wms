package kg.kutman.smanov.sumsarproject.sso.config.security.handler;

import lombok.experimental.UtilityClass;
import org.springframework.security.web.savedrequest.SavedRequest;

@UtilityClass
public class HandlerUtils {

    /**
     * Получение clientId из сохранённого запроса.
     */
    public String getClientId(SavedRequest savedRequest) {
        if (savedRequest != null) {
            if (savedRequest.getParameterMap().containsKey("client_id")) {
                String[] clientIdValues = savedRequest.getParameterValues("client_id");
                if (clientIdValues.length > 0) {
                    return clientIdValues[0];
                }
            }
        }
        return null;
    }
}
