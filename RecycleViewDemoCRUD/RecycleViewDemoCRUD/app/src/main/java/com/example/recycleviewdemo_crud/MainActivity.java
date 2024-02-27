package com.example.recycleviewdemo_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recycleviewdemo_crud.model.Tour;
import com.example.recycleviewdemo_crud.model.TourAdapter;
import com.example.recycleviewdemo_crud.model.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TourAdapter.CatItemListener,
              SearchView.OnQueryTextListener {
    private Spinner sp;
    private RecyclerView recyclerView;
    private TourAdapter adapter;
    private EditText eName, eDesc;
    private Button btAdd, btUpdate, btIgnore;
    private SearchView searchView;
    private int pcurr;
    private int[] imgs = {R.drawable.plane, R.drawable.motorbike, R.drawable.car};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        adapter = new TourAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        searchView.setOnQueryTextListener(this);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = sp.getSelectedItem().toString();
                String name = eName.getText().toString();
                String desc = eDesc.getText().toString();
                int img;
                try {
                    img = imgs[Integer.parseInt(i)];
                    Tour tour = new Tour(img, name, desc);

                    if (!name.isEmpty() && !desc.isEmpty()) {
                        if (!adapter.checkExist(tour)) {
                            adapter.add(tour);
                        } else {
                            Toast.makeText(getApplicationContext(), "Tour already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tour updatedTour = new Tour();
                String i = sp.getSelectedItem().toString();
                String name = eName.getText().toString();
                String desc = eDesc.getText().toString();
                int img;
                img = imgs[Integer.parseInt(i)];
                updatedTour.setImg(img);
                updatedTour.setName(name);
                updatedTour.setDescribe(desc);

                if (!adapter.checkExist(updatedTour)) {
                    adapter.update(pcurr, updatedTour);
                    btAdd.setEnabled(true);
                    btUpdate.setEnabled(false);
                } else {
                    Toast.makeText(getApplicationContext(), "Updated tour already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.setSelection(0);
                eName.setText("");
                eDesc.setText("");

                btAdd.setEnabled(true);
                btUpdate.setEnabled(false);
            }
        });
    }

    private void initView() {
        sp = findViewById(R.id.img);
        SpinnerAdapter adapter = new SpinnerAdapter(this, imgs);
        sp.setAdapter(adapter);
        recyclerView = findViewById(R.id.recycleView);
        eName = findViewById(R.id.name);
        eDesc = findViewById(R.id.describe);
        btAdd = findViewById(R.id.btAdd);
        btUpdate = findViewById(R.id.btUpdate);
        btIgnore = findViewById(R.id.btClear);
        btUpdate.setEnabled(false);
        searchView = findViewById(R.id.search);
    }

    @Override
    public void onItemClick(View view, int position) {
        btAdd.setEnabled(false);
        btUpdate.setEnabled(true);
        pcurr = position;
        Tour tour = adapter.getItem(position);
        int img = tour.getImg();
        int p = 0;
        for (int i = 0; i < imgs.length; i++) {
            if (img == imgs[i]) {
                p = i;
                break;
            }
        }
        sp.setSelection(p);
        eName.setText(tour.getName());
        eDesc.setText(tour.getDescribe());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String s) {
        List<Tour> filterlist = new ArrayList<>();
        for (Tour i : adapter.getBackup()) {
            if (i.getName().toLowerCase().contains(s.toLowerCase())) {
                filterlist.add(i);
            }
        }
        if (filterlist.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        adapter.filterList(filterlist);
    }
}