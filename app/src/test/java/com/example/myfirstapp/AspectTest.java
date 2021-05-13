package com.example.myfirstapp;

import com.example.myfirstapp.View.MainActivity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AspectTest {
    private MainActivity mainActivity;

    @Test
    public void checkCaption() {
        mainActivity = new MainActivity();
        assertTrue(mainActivity.checkCaption());
    }
}
