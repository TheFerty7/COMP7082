package com.example.myfirstapp.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

public class MainActivityAspect {

    @Before("call(Boolean com.example.myfirstapp.View.MainActivity.checkCaption())")
    public void callFromCheckCaption(){
        System.out.println("checkCaption called");
    }

    @Pointcut("call(Boolean com.example.myfirstapp.View.MainActivity.checkCaption())")
    public void callCheckCaption() {
        System.out.println("TEST Pointcut");
        throw new RuntimeException("TEST");
    }

}
