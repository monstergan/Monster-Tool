package com.monster.core.api.crypto.annotation.encrypt;

import com.monster.core.api.crypto.enums.CryptoType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiEncrypt(CryptoType.DES)
public @interface ApiEncryptDes {

    /**
     * Alias for {@link ApiEncrypt#secretKey()}.
     *
     * @return {String}
     */
    @AliasFor(annotation = ApiEncrypt.class)
    String secretKey() default "";

}
