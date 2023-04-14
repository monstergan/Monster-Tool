package com.monster.core.api.crypto.annotation.decrypt;

import com.monster.core.api.crypto.enums.CryptoType;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiDecrypt(CryptoType.RSA)
public @interface ApiDecryptRsa {
}
