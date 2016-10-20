package com.example.hp.eduapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.eduapp.R;
import com.example.hp.eduapp.adapters.PicturesAdapter;
import com.example.hp.eduapp.listeners.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesFragment extends Fragment {

    public FilesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_files, container, false);
        setHasOptionsMenu(true);

        //get the files list view and pics recycler view
        ListView filesListView = (ListView) rootView.findViewById(R.id.files_listview);
        RecyclerView picsRecyclerView = (RecyclerView) rootView.findViewById(R.id.pictures_recyclerview);

        //layout manager for pics
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        picsRecyclerView.setLayoutManager(manager);

        //fake data for files list
        String[] fakeFiles = {
                "File 1",
                "File 2",
                "File 3",
                "File 4",
                "File 5",
                "File 6",
                "File 7",
                "File 8",
                "File 9",
                "File 10"
        };

        List<String> files = new ArrayList<>(Arrays.asList(fakeFiles));

        //make adapters for the views
        PicturesAdapter picturesAdapter = new PicturesAdapter(getContext());
        ArrayAdapter<String> filesAdapter = new ArrayAdapter<>(getContext(), R.layout.item_files, R.id
                .files_name_textView, files);

        //set adapters to views
        picsRecyclerView.setAdapter(picturesAdapter);
        filesListView.setAdapter(filesAdapter);

        picsRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(
                getContext(),
                picsRecyclerView,
                new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Toast.makeText(getContext(), "Opening Picture\nat position " + position, Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {}
                }
        ));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_files, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_camera){
            Toast.makeText(getActivity(), "Take a picture\nusing the Camera app", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
