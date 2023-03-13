package com.monster.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 * <p>
 * Create on 2023/3/13 10:48
 *
 * @author monster gan
 */
public class MonsterConversionService extends ApplicationConversionService {

    @Nullable
    private static volatile MonsterConversionService SHARED_INSTANCE;

    public MonsterConversionService() {
        this(null);
    }

    public MonsterConversionService(@Nullable StringValueResolver embeddedValueResolver) {
        super(embeddedValueResolver);
        super.addConverter(new EnumToStringConverter());
        super.addConverter(new StringToEnumConverter());
    }

    /**
     * Return a shared default application {@code ConversionService} instance, lazily
     * building it once needed.
     * <p>
     * Note: This method actually returns an {@link MonsterConversionService}
     * instance. However, the {@code ConversionService} signature has been preserved for
     * binary compatibility.
     *
     * @return the shared {@code KteConversionService} instance (never{@code null})
     */
    public static GenericConversionService getInstance() {
        MonsterConversionService sharedInstance = MonsterConversionService.SHARED_INSTANCE;
        if (sharedInstance == null) {
            synchronized (MonsterConversionService.class) {
                sharedInstance = MonsterConversionService.SHARED_INSTANCE;
                if (sharedInstance == null) {
                    sharedInstance = new MonsterConversionService();
                    MonsterConversionService.SHARED_INSTANCE = sharedInstance;
                }
            }
        }
        return sharedInstance;
    }

}
