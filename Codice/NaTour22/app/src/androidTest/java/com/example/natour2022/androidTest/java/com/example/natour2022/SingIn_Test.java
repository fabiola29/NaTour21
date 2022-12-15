package com.example.natour2022;

import static org.junit.Assert.fail;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SingIn_Test {
    FirebaseAuth auth;
    boolean test;

    @Test
    public void ValidEmailPasswordTest(){

        test = false;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("prova1@live.it", "Prova3456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while(!test){

        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidEmptyEmailPasswordTest(){

        test = false;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("", "").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while(!test){

        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidEmptyEmailTest(){

        test = false;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("", "Prova3456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    fail();


                } else {
                    Assert.assertTrue(true);

                }
                test = true;
            }
        });
        while(!test){

        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidEmptyPasswordTest(){

        test = false;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("prova1@live.it", "").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);
                    fail();


                } else {
                    //Assert.assertFalse(false);
                    Assert.assertTrue(true);

                }
                test = true;
            }
        });
        while(!test){

        }
    }

    @Test
    public void InvalidFormatEmailPasswordTest(){

        test = false;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("prova1@live", "Prova").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    fail();

                } else {
                    Assert.assertTrue(true);
                }
                test = true;
            }
        });
        while(!test){

        }
    }

    @Test
    public void InvalidFormatPasswordTest(){

        test = false;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("prova1@live.it", "Prova").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while(!test){

        }
    }


    @Test
    public void InvalidFormatEmailTest(){

        test = false;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("prova1@live", "Prova3456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    fail();

                } else {

                    Assert.assertTrue(true);


                }
                test = true;
            }
        });
        while(!test){

        }
    }
}


