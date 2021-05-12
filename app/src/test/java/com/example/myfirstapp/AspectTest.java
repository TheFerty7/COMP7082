package com.example.myfirstapp;

import com.example.myfirstapp.DataStorage.LocalStorage;
import com.example.myfirstapp.View.MainActivity;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import com.example.myfirstapp.CaptionAspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AspectTest {
    private MainActivity mainActivity;

    @Test
    public void checkCaption() {
        assertTrue(mainActivity.checkCaption());
    }
}
