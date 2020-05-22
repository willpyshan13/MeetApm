package com.meiyou.common.apm.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/2/5
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Proguard {
}
