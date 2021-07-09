package com.trotos.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.trotos.notes.APIUtils;
import com.trotos.notes.R;
import com.trotos.notes.adapters.AdapterNotes;
import com.trotos.notes.databinding.FragmentHomeBinding;
import com.trotos.notes.models.Note;
import com.trotos.notes.models.responseModels.ResponseNotes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentHomeBinding binding;
    SwipeRefreshLayout mSwipeRefreshLayout;
    AdapterNotes adapter;
    private final List<Note> notes = new ArrayList<Note>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getNotes();
        configureUI();

        return root;
    }

    private void configureUI() {
        RecyclerView notesReciclerView = binding.notesReciclerView;
        notesReciclerView.setHasFixedSize(true);
        notesReciclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterNotes(getContext(), notes, new AdapterNotes.OnItemClickListener() {
            @Override
            public void onItemClick(Note item) {
                toNoteEditActivity(item);
            }
        });
        notesReciclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) binding.swipeContainer;
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void toNoteEditActivity(Note item) {
        Intent intent = new Intent(getActivity(), NoteEditing.class);
        intent.putExtra("note", item);
        startActivity(intent);
    }

    private void getNotes() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUtils as = retrofit.create(APIUtils.class);
        Call<ResponseNotes> call = as.getNotes("Bearer "+ token);

        call.enqueue(new Callback<ResponseNotes>() {
            @Override
            public void onResponse(Call<ResponseNotes> call, Response<ResponseNotes> response) {
                if(response.isSuccessful()) {
                    ResponseNotes responseNotes = response.body();
                    if (responseNotes != null) {
                        notes.clear();
                        notes.addAll(responseNotes.getData());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast toast2 = Toast.makeText(getContext(), "Error al obtener las Notas: ", Toast.LENGTH_LONG);
                    toast2.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNotes> call, Throwable t) {
                Toast toast2 = Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast2.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRefresh() {
        getNotes();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}