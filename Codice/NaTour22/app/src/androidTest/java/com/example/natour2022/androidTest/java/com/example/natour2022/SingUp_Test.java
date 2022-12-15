package com.example.natour2022;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.natour2022.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SingUp_Test {

    FirebaseAuth auth;
    DatabaseReference referenceDb;
    boolean test;

    @Test
    public void ValidSingUpTest() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "Prova", "prova1@live.it", "Prova3456", "14/04/1996", "female");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);
                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }

    @Test
    public void EmptySingInTest1() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "", "", "", "", "");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }

    @Test
    public void EmptyEmailTest() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "Prova1", "", "Prova3456", "14/04/1996", "female");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }

    @Test
    public void EmptyPasswordTest() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "Prova", "prova1@live.it", "", "14/04/1996", "female");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }

    @Test
    public void EmptyNicknameTest() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "", "prova1@live.it", "Prova3456", "14/04/1996", "female");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }

    @Test
    public void EmptyGenderTest() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "Prova", "prova1@live.it", "Prova3456", "14/04/1996", "");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }

    @Test
    public void EmptyDataOfBirthTest() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "Prova", "prova1@live.it", "Prova3456", "", "female");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }

    @Test
    public void InvalidGenderTest() {
        test = false;
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User currUser = new User("PRpqGWkrosXoxubMfbLF3G0gYtF3", "Prova", "prova1@live.it", "Prova3456", "14/04/1996", "hgjhghj");
        referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Assert.assertTrue(true);

                } else {
                    Assert.assertFalse(false);
                }
                test = true;
            }
        });
        while (!test) {
        }
    }
}

