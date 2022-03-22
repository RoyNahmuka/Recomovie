package com.example.recomovie.model.users;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.recomovie.model.EmptyListener;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.common.Listener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Map;

public class UsersModelFireBase {
    private static final String USERS_COLLECTION = "users";
    public static final UsersModelFireBase instance = new UsersModelFireBase();
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private UsersModelFireBase() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void onUserChange(Listener<FirebaseUser> listener) {
        auth.addAuthStateListener(firebaseAuth -> listener.onComplete(firebaseAuth.getCurrentUser()));
    }

    public void getUser(String id, Listener<User> listener) {
        db.collection(USERS_COLLECTION)
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    User user = null;
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null) {
                            user = doc.toObject(User.class);
                        }
                        listener.onComplete(user);
                    } else {
                    }
                });

    }

    public User getCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        return firebaseUser == null ? null :
                new User(
                        firebaseUser.getDisplayName(),
                        firebaseUser.getEmail(),
                        firebaseUser.getPhoneNumber(),
                        firebaseUser.getUid());
    }

    public void register(final User user, String password, final Listener<Task<AuthResult>> listener) {
        auth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userUid=task.getResult().getUser().getUid();
                            user.setId(userUid);
                            //TODO - async
                            db.collection(USERS_COLLECTION).document(userUid).set(user);
                            updateUserProfile(user, new Listener<Boolean>() {
                                @Override
                                public void onComplete(Boolean data) {
                                    if (data) {
                                        listener.onComplete(task);
                                    }
                                }
                            });
                        } else {
                            if (listener != null) {
                                listener.onComplete(task);
                            }
                        }
                    }
                });
    }

    public void login(String email, String password, final Listener<Boolean> listener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (listener != null) {
                            listener.onComplete(true);
                        }
                    } else {
                        if (listener != null) {
                            listener.onComplete(false);
                        }
                    }
                });
    }

    public void logout() {
        auth.signOut();
    }

    public void updateUserProfile(User user, final Listener<Boolean> listener) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(user.getName()).build();

        firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public void updateUser(User user, EmptyListener listener) {
        db.collection("users").document(user.getId()).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener != null) listener.onComplete();
            }
        });
    }
}
