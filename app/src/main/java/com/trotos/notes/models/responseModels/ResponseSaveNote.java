package com.trotos.notes.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trotos.notes.models.Note;

public class ResponseSaveNote {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Note")
    @Expose
    private Note note;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

}
