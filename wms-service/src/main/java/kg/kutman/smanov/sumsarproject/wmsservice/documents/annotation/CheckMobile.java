package kg.kutman.smanov.sumsarproject.wmsservice.documents.annotation;

import cn.hutool.core.lang.Validator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * china mobile  verification
 *
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 *
 * @author GuoGuang
 * @公众号 码道人生
 * @gitHub https://github.com/GuoGuang
 * @website https://madaoo.com
 * @created 2021-09-02
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckMobile.ValidatorValue.class)
public @interface CheckMobile {

    int value() default 0;

    String message() default "invalid mobile";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link CheckMobile} annotations on the same element
     */
    @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CheckMobile[] value();
    }

    class ValidatorValue implements ConstraintValidator<CheckMobile, String> {

        @Override
        public void initialize(CheckMobile constraintAnnotation) {
        }

        @Override
        public boolean isValid(String content, ConstraintValidatorContext constraintValidatorContext) {
            if (content == null) {
                return false;
            }
            return Validator.isMobile(content);
        }
    }

}
