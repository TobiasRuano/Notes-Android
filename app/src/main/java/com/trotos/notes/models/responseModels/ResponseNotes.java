package com.trotos.notes.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trotos.notes.models.Note;

import java.util.List;

public class ResponseNotes {

    @SerializedName("data")
    @Expose
    private List<Note> data = null;

    public List<Note> getData() {
        return data;
    }

    public void setData(List<Note> data) {
        this.data = data;
    }

}
