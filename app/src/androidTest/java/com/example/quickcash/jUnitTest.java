package com.example.quickcash;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

    @Test
    public void isValidRole_invalidRole_ReturnTrue() {
        Assert.assertTrue(register.validRole("Employer"));
        Assert.assertTrue(register.validRole("Employee"));
    }
    @Test
    public void testLocationDataExists() throws InterruptedException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users").child("a@dal,ca");

        final CountDownLatch latch = new CountDownLatch(1);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    fail("User does not exist in the database");
                } else {
                    String location = dataSnapshot.child("location").getValue(String.class);
                    assertNotNull("Location data exists", location);
                    assertTrue("Location contains Latitude", location.contains("Latitude"));
                    assertTrue("Location contains Longitude", location.contains("Longitude"));
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fail("Database error: " + databaseError.getMessage());
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Firebase response timed out.");
        }
    }
}
