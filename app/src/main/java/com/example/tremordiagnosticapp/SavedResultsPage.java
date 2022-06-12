package com.example.tremordiagnosticapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.tremordiagnosticapp.entity.CustomResultListItem;
import com.example.tremordiagnosticapp.entity.SavedData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedResultsPage extends AppCompatActivity {

    public ChangePage changePage = new ChangePage();
    private RecyclerView recyclerView;
    public CustomViewAdapter adapter; //the bridge between json data and custom view data in recycle view
    public CustomViewAdapter.CustomViewHolder customViewHolder;
    private RecyclerView.LayoutManager layoutManager;
    private List<SavedData> data = new ArrayList<>();
    public ArrayList<CustomResultListItem> customResultListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedresult);
        init();
    }

    public void init(){
        listInit();
        variablesInit();
    }

    public void variablesInit(){
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void removeItem(int position) {
        data.remove(position);
        new SaveViaGson().save(data, "User", "Results");
        customResultListItems.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void removeSelectedItem(){
        adapter.setOnItemClickListener(new CustomViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public void listInit(){
        data.addAll(Arrays.asList(new ReceivingDataFromJson().receivingData()));
        customResultListItems = new ArrayList<>();
        setDataToList(customResultListItems);
        adapter = new CustomViewAdapter(customResultListItems);
        removeSelectedItem();
    }

    public void setDataToList(ArrayList<CustomResultListItem> customResultListItems){
        for(int i = 0; i < data.size(); i++){
            customResultListItems.add(new CustomResultListItem(
                    String.valueOf(i+1),
                    data.get(i).getName(),
                    data.get(i).getDate(),
                    data.get(i).getResult()));
        }
    }

    public void onBackToMain(View view){
        changePage.moveFromTo(this, MainPage.class, "", "");
    }
}