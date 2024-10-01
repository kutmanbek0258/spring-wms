package kg.kutman.smanov.sumsarproject.sso.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReferenceDto<T> {

    private T value;
    private String title;
}
