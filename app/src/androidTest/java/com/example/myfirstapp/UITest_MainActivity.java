package com.example.myfirstapp;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest_MainActivity {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void editCaption() {
        onView(withId(R.id.etCaption)).perform(clearText());
        onView(withId(R.id.etCaption)).perform(typeText("I am editing the caption"), closeSoftKeyboard());
        onView(withId(R.id.btnNext)).perform(click());
        onView(withId(R.id.btnPrev)).perform(click());
        onView(withId(R.id.etCaption)).check(matches(withText("I am editing the caption")));
    }

    @Test
    public void searchForImage() {
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.etFromDateTime)).perform(clearText());
        onView(withId(R.id.etFromDateTime)).perform(typeText("2021-04-20"), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(clearText());
        onView(withId(R.id.etToDateTime)).perform(typeText("2021-04-20"), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(typeText("Cat"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.etCaption)).check(matches(withText("Cat")));
    }

    @Test
    public void checkDateMatchesOnSearch() {
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.etFromDateTime)).perform(clearText());
        onView(withId(R.id.etFromDateTime)).perform(typeText("2021-04-20"), closeSoftKeyboard());
        onView(withId(R.id.etToDateTime)).perform(clearText());
        onView(withId(R.id.etToDateTime)).perform(typeText("2021-04-20"), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(typeText("Cat"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.tvTimestamp)).check(matches(withText("20210420")));
    }
}
