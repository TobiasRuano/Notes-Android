package com.trotos.notes;

import com.trotos.notes.models.LogIn;
import com.trotos.notes.models.Note;
import com.trotos.notes.models.User;
import com.trotos.notes.models.responseModels.ResponseLogin;
import com.trotos.notes.models.responseModels.ResponseNotes;
import com.trotos.notes.models.responseModels.ResponseSaveNote;
import com.trotos.notes.models.responseModels.ResponseSignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Header;

public interface APIUtils {

    @HTTP(method = "POST", path = "login/", hasBody = true)
    Call<ResponseLogin> logIn(@Body LogIn logIn);

    @HTTP(method = "POST", path = "user/sign-up", hasBody = true)
    Call<ResponseSignUp> signUp(@Body User user);

    @HTTP(method = "POST", path = "user/getnotes", hasBody = true)
    Call<ResponseNotes> getNotes(@Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/", hasBody = true)
    Call<ResponseSaveNote> saveNote(@Body Note note, @Header("Authorization") String auth);

    @HTTP(method = "DELETE", path = "user/", hasBody = true)
    Call<String> deleteNote(@Body Note note, @Header("Authorization") String auth);

}
