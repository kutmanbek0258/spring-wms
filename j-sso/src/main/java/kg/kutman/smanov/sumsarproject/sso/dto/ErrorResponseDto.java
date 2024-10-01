package kg.kutman.smanov.sumsarproject.sso.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kg.kutman.smanov.sumsarproject.sso.type.ErrorLevel;
import kg.kutman.smanov.sumsarproject.sso.utils.StackElementSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponseDto {

    private boolean informative;

    private ErrorLevel level;
    private String message;

    @JsonSerialize(using = StackElementSerializer.class)
    private StackTraceElement[] stacktrace;

    public static ErrorResponseDtoBuilder builder(String message, Throwable cause) {
        return new ErrorResponseDtoBuilder()
            .message(message)
            .stacktrace(cause.getStackTrace())
            .informative(false);
    }

    public static ErrorResponseDtoBuilder builder(String message) {
        return new ErrorResponseDtoBuilder()
            .message(message)
            .informative(false);
    }

    public static ErrorResponseDtoBuilder builder() {
        return new ErrorResponseDtoBuilder();
    }
}
