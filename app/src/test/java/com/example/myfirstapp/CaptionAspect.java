package com.example.myfirstapp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


@Aspect
public class CaptionAspect {

    @Pointcut("call(String MainActivity.checkCaption())")
    public void methodCheckCaption() {
        System.out.println("TEST");
        throw new RuntimeException("TEST");
    }

}