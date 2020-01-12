package com.breno.banana.ui.login

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.breno.banana.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test


class LoginActivityTest {

    fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
            Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    fun sendKeys(keys: String){
        val keySender = InstrumentationRegistry.getInstrumentation()
        keySender.sendStringSync(keys)
    }

    @get:Rule
    val activityRule = ActivityTestRule(LoginActivity::class.java)

    @org.junit.Before
    fun setUp() {
    }

    @org.junit.After
    fun tearDown() {
        activityRule.finishActivity()

    }

    @Test
    fun layoutTest(){
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.password)).check(matches(isDisplayed()))
        onView(withId(R.id.login)).check(matches(isDisplayed()))
        onView(withId(R.id.loading)).check(matches(not(isDisplayed())))
    }

    @Test
    fun loginSimulator(){
        onView(withId(R.id.username)).perform(click())
        check(isKeyboardShown())
        sendKeys("este teste instrumental funciona perfeitamente em um modulo")
        onView(withId(R.id.username)).check(matches(withText("este teste instrumental funciona perfeitamente em um modulo")))
        onView(withId(R.id.password)).perform(click())
        sendKeys("senha")
        onView(withId(R.id.password)).check(matches(withText("senha")))
        onView(withId(R.id.login)).perform(click())
        check(isKeyboardShown())
    }
}