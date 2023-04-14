package com.monster.core.api.crypto.annotation.crypto;

import com.monster.core.api.crypto.annotation.decrypt.ApiDecrypt;
import com.monster.core.api.crypto.annotation.encrypt.ApiEncrypt;
import com.monster.core.api.crypto.enums.CryptoType;

import java.lang.annotation.*;

/**
 * AES加密解密含有{@link org.springframework.web.bind.annotation.RequestBody}注解的参数请求数据
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiEncrypt(CryptoType.RSA)
@ApiDecrypt(CryptoType.RSA)
public @interface ApiCryptoRsa {
}
