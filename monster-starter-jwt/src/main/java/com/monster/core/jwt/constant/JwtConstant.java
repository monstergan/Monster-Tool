package com.monster.core.jwt.constant;

/**
 * JWT常量
 *
 * @author monster gan
 */
public interface JwtConstant {

    /**
     * 默认key
     */
    String DEFAULT_SECRET_KEY = "ktexisapowerfulmicroservicearchitectureupgradedandoptimizedfromacommercialproject";

    /**
     * <a href="https://tools.ietf.org/html/rfc7518#section-3.2">key安全长度，具体见</a>
     */
    int SECRET_KEY_LENGTH = 32;

}
