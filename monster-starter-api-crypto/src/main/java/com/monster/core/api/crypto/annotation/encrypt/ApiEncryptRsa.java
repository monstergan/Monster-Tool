package com.monster.core.api.crypto.annotation.encrypt;

import com.monster.core.api.crypto.enums.CryptoType;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiEncrypt(CryptoType.RSA)
public @interface ApiEncryptRsa {
}
