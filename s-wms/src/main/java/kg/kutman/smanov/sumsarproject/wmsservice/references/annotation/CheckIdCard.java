package kg.kutman.smanov.sumsarproject.wmsservice.references.annotation;

import cn.hutool.core.util.IdcardUtil;
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
 * china idCard verification
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
@Constraint(validatedBy = CheckIdCard.ValidatorValue.class)
public @interface CheckIdCard {

    int value() default 0;

    String message() default "illegal idCard";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link CheckIdCard} annotations on the same element
     */
    @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CheckIdCard[] value();
    }

    class ValidatorValue implements ConstraintValidator<CheckIdCard, String> {

        @Override
        public void initialize(CheckIdCard constraintAnnotation) {
        }

        @Override
        public boolean isValid(String content, ConstraintValidatorContext constraintValidatorContext) {
            if (content == null) {
                return false;
            }
            return IdcardUtil.isValidCard(content);
        }
    }

}
