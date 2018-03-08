package com.example.hui.template.ioc.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description： 用于是否显示加载布局的注解  --> 做一下标记
 *
 * Created by 曾辉 on 2016/10/14.
 * QQ：240336124
 * Email: 240336124@qq.com
 * Version：1.0
 */
@Target(ElementType.METHOD)// FIELD 属性  METHOD方法  TYPE 放在类上
@Retention(RetentionPolicy.RUNTIME)// SOURCE 编译时检测  RUNTIME运行时检测
public @interface ShowLoadView {

}
