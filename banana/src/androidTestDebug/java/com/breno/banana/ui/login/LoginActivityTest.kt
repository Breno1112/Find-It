package com.breno.banana.ui.login


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.os.Environment
import android.view.inputmethod.InputMethodManager
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.UiDevice
import com.breno.banana.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import java.io.*


class LoginActivityTest {

    fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
            Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    fun isToastShownWithText(text: String, activity: Activity){
        val decorView = activity.window.decorView
        onView(withText(text))
            .inRoot(withDecorView(not(decorView)))// Here we use decorView
            .check(matches(isDisplayed()))
    }



    @get:Rule
    val activityRule = ActivityTestRule(LoginActivity::class.java)

    @get:Rule
    val mGrantPermissionRule: GrantPermissionRule? = GrantPermissionRule.grant(
        READ_EXTERNAL_STORAGE,
        WRITE_EXTERNAL_STORAGE
    )


    @org.junit.Before
    fun setUp() {
        captureScreenshot("Before")
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
    fun loginSimulatorWrongPassword(){
        onView(withId(R.id.username)).perform(click())
        check(isKeyboardShown())
        onView(withId(R.id.username)).perform(typeText("este teste instrumental funciona perfeitamente em um modulo"), closeSoftKeyboard())
        onView(withId(R.id.username)).check(matches(withText("este teste instrumental funciona perfeitamente em um modulo")))
//        check(!isKeyboardShown())
        onView(withId(R.id.password)).perform(click())
//        check(isKeyboardShown())
        onView(withId(R.id.password)).perform(typeText("senha"), closeSoftKeyboard())
//        check(!isKeyboardShown())
        onView(withId(R.id.password)).check(matches(withText("senha")))
        onView(withId(R.id.login)).perform(click())
//        check(isKeyboardShown())
    }

    @Test
    fun loginSimulatorRightPassword(){

        val activity = activityRule.activity
        val welcome = activityRule.activity.getString(R.string.welcome)
//        val displayName = activity.

        onView(withId(R.id.username)).perform(click())
        check(isKeyboardShown())
        captureScreenshot("username_keyboard_open")
        onView(withId(R.id.username)).perform(typeText("este teste instrumental funciona perfeitamente em um modulo"), closeSoftKeyboard())
        onView(withId(R.id.username)).check(matches(withText("este teste instrumental funciona perfeitamente em um modulo")))
        captureScreenshot("username_text_accurate")
//        check(!isKeyboardShown())
        onView(withId(R.id.password)).perform(click())
        captureScreenshot("password_keyboard_open")
//        check(isKeyboardShown())
        onView(withId(R.id.password)).perform(typeText("senha12345"), closeSoftKeyboard())
//        check(!isKeyboardShown())
        onView(withId(R.id.password)).check(matches(withText("senha12345")))
        captureScreenshot("password_text_accurate")
        onView(withId(R.id.login)).perform(click())
        isToastShownWithText(welcome,activity)
        captureScreenshot("welcome_toast")
    }

    fun captureScreenshot(name:String){
        val path = File(
            Environment.getExternalStorageDirectory().getAbsolutePath()
                .toString() + "/test-screenshots/" + getTargetContext().packageName
        )
        if (!path.exists()) {
            path.mkdirs()
        }

        val device =
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val filename = "$name.png"
        device.takeScreenshot(File(path, filename))
    }
}