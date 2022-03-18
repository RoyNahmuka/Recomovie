package com.example.recomovie.model.users;
import com.example.recomovie.model.common.Listener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class UsersModel {
    public final static UsersModel instance = new UsersModel();
    UsersModelFireBase usersModelFireBase = UsersModelFireBase.instance;

    private UsersModel() {}
    public void getUser(String id, final Listener<User> listener) {
        usersModelFireBase.getUser(id, listener);
    }

    public void registerUser(User user, String password, final Listener<Task<AuthResult>> listener) {
        usersModelFireBase.register(user, password, listener);
    }

    public void login(String email, String password, final Listener<Boolean> listener) {
        usersModelFireBase.login(email, password, listener);
    }

    public void logout() {
        usersModelFireBase.logout();
    }

    public User getCurrentUser() {
        return usersModelFireBase.getCurrentUser();
    }

    public void onUserChange(Listener<FirebaseUser> firebaseUserListener) {
        usersModelFireBase.onUserChange(firebaseUserListener);
    }
}
