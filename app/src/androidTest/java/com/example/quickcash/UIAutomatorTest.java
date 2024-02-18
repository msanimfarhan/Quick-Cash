package com.example.quickcash;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest {
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.quickcash";
    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(appIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void move2LandingRegister() throws UiObjectNotFoundException {
        UiObject register_button = device.findObject(new UiSelector().text("Register"));
        register_button.clickAndWaitForNewWindow();
        UiObject signupName = device.findObject(new UiSelector().textContains("Enter your name"));
        signupName.setText("WASD");
        UiObject signupEmail = device.findObject(new UiSelector().textContains("Enter your email"));
        signupEmail.setText("wasd123@gmail.com");
        UiObject signupRoleSelection = device.findObject(new UiSelector().textContains("Select Role"));
        signupRoleSelection.click();
        List<UiObject2> roles = device.findObjects(By.res("android:id/text1"));
        roles.get(1).click();
        UiObject signupPass = device.findObject(new UiSelector().textContains("Enter Password"));
        signupPass.setText("wasdqwerty");
        UiObject signupConfirmPass = device.findObject(new UiSelector().textContains("Confirm Password"));
        signupConfirmPass.setText("wasdqwerty");
        UiObject signupBtn = device.findObject(new UiSelector().text("Register"));
        signupBtn.clickAndWaitForNewWindow();
        UiObject employer_landing = device.findObject(new UiSelector().textContains("Employer Landing"));
        assertTrue(employer_landing.exists());
    }

    @Test
    public void move2LandingSignIn() throws UiObjectNotFoundException {
        UiObject username = device.findObject(new UiSelector().textContains("Email"));
        username.setText("wasd123@gmail.com");
        UiObject password = device.findObject(new UiSelector().textContains("Password"));
        password.setText("wasdqwerty");
        UiObject login_button = device.findObject(new UiSelector().text("Login"));
        login_button.clickAndWaitForNewWindow();
        UiObject employer_landing = device.findObject(new UiSelector().textContains("Employer Landing"));
        assertTrue(employer_landing.exists());
    }
}

