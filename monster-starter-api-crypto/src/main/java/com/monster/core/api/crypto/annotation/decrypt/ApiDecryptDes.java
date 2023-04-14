package com.monster.core.api.crypto.annotation.decrypt;

import com.monster.core.api.crypto.enums.CryptoType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * DES 解密
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiDecrypt(CryptoType.DES)
public @interface ApiDecryptDes {

	/**
	 * Alias for {@link ApiDecrypt#secretKey()}.
	 *
	 * @return {String}
	 */
	@AliasFor(annotation = ApiDecrypt.class)
	String secretKey() default "";

}
