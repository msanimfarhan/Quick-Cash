package com.example.quickcash;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class jUnitTest {

    private Register register;

    @Before
    public void setUp() {
        register = new Register();
    }

    @Test
    public void isValidEmailAddress_ValidEmail_ReturnsTrue() {
        Assert.assertTrue(register.isValidEmailAddress("test@example.com"));
    }

    @Test
    public void isValidEmailAddress_InvalidEmail_ReturnsFalse() {
        Assert.assertFalse(register.isValidEmailAddress("invalid-email"));
    }

    @Test
    public void isValidEmailAddress_NullEmail_ReturnsFalse() {
        Assert.assertFalse(register.isValidEmailAddress(null));
    }

    @Test
    public void isValidPassword_ValidPassword_ReturnsTrue() {
        Assert.assertTrue(register.isValidPassword("strongPassword123"));
    }

    @Test
    public void isValidPassword_ShortPassword_ReturnsFalse() {
        Assert.assertFalse(register.isValidPassword("short"));
    }

    @Test
    public void isValidPassword_NullPassword_ReturnsFalse() {
        Assert.assertFalse(register.isValidPassword(null));
    }

    @Test
    public void ifBothPasswordMatches_MatchingPasswords_ReturnsTrue() {
        Assert.assertTrue(register.ifBothPasswordMatches("password", "password"));
    }

    @Test
    public void ifBothPasswordMatches_NonMatchingPasswords_ReturnsFalse() {
        Assert.assertFalse(register.ifBothPasswordMatches("password1", "password2"));
    }

    @Test
    public void ifBothPasswordMatches_NullPasswords_ReturnsFalse() {
        Assert.assertFalse(register.ifBothPasswordMatches(null, null));
    }
}
