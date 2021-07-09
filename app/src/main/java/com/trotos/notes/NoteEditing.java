package com.trotos.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trotos.notes.models.LogIn;
import com.trotos.notes.models.Note;
import com.trotos.notes.models.responseModels.ResponseLogin;
import com.trotos.notes.models.responseModels.ResponseSaveNote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoteEditing extends AppCompatActivity {

    FloatingActionButton saveNoteButton;
    Note note;
    TextView noteDate;
    EditText noteTitle;
    EditText noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editing);
        configureUI();
    }

    private void configureUI() {
        if(getIntent().getSerializableExtra("note") != null) {
            note = (Note) getIntent().getSerializableExtra("note");
        } else {
            note = new Note();
        }

        noteDate = findViewById(R.id.noteDateText);
        noteTitle = findViewById(R.id.noteTitleEditText);
        noteContent = findViewById(R.id.noteContentEditText);
        saveNoteButton = findViewById(R.id.saveNote);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checktextStatus()) {
                    saveNote();
                } else {

                }
            }
        });

        if(note != null) {
            noteDate.setText(note.getUpdatedAt());
            noteTitle.setText(note.getTitle());
            noteContent.setText(note.getContent());
        }
    }

    private boolean checktextStatus() {
        String title = noteTitle.getText().toString();
        String content = noteContent.getText().toString();
        if(!title.isEmpty()) {
            note.setTitle(title);
            note.setContent(content);
            return true;
        } else return false;
    }

    private void saveNote() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUtils as = retrofit.create(APIUtils.class);

        Call<ResponseSaveNote> call = as.saveNote(note, "Bearer "+ token);

        call.enqueue(new Callback<ResponseSaveNote>() {
            @Override
            public void onResponse(Call<ResponseSaveNote> call, Response<ResponseSaveNote> response) {
                if (response.isSuccessful()) {
                    ResponseSaveNote responseSaveNote = response.body();
                    note = responseSaveNote.getNote();
                    //TODO: go back with the new note
                } else {
                    // TODO: check other errors
                }
            }

            @Override
            public void onFailure(Call<ResponseSaveNote> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast1.show();
                System.out.println(t.getMessage());
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}