package com.fermat.dic2app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fermat.dic2app.R;
import com.fermat.dic2app.models.Eleve;
import com.fermat.dic2app.ui.adapter.EleveAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    //creating a variables for our recycler view.
    private RecyclerView rv;
    private static final int ADD_ELEVE_REQUEST = 1;
    private static final int EDIT_EDIT_REQUEST = 2;
    private EleveViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing our variable for our recycler view and fab.
        rv = findViewById(R.id.idRecyler);
        FloatingActionButton fab = findViewById(R.id.idFab);

        //adding on click listner for floating action button.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting a new activity for adding a new course and passing a constant value in it.
                Intent intent = new Intent(MainActivity.this, NewEleveActivity.class);
                startActivityForResult(intent, ADD_ELEVE_REQUEST);
            }
        });

        // layout manager pour l'adaptation
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        // recylclerview
        final EleveAdapter adapter = new EleveAdapter();

        // adaptateur
        rv.setAdapter(adapter);

        // on passe la donnée au viewmodel
        viewModel = ViewModelProviders.of(this).get(EleveViewModel.class);

        // recuperation liste des eleves enregistrés dans la bd
        // et observer ces données (Observer pattern)
        viewModel.getAllEleves().observe(this, adapter::submitList);
        // ajout d'une suppression par glissement (a gauche ou a droite)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //a l'action glisser on supprime
                viewModel.delete(adapter.getEleve(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Eleve supprimé avec succés", Toast.LENGTH_SHORT).show();
            }
        }).
                //on lie la fonctionnalité aux recycleur
                        attachToRecyclerView(rv);
        //click listener
        adapter.setOnItemClickListener(eleve -> {


            Intent intent = new Intent(MainActivity.this, NewEleveActivity.class);
            intent.putExtra(NewEleveActivity.EXTRA_ID, eleve.getId());
            intent.putExtra(NewEleveActivity.EXTRA_ELEVE_NAME, eleve.getPrenom());
            intent.putExtra(NewEleveActivity.EXTRA_ELEVE_LASTNAME, eleve.getNom());
            intent.putExtra(NewEleveActivity.EXTRA_CLASSE, eleve.getClasse());
            intent.putExtra(NewEleveActivity.EXTRA_AVATAR, eleve.getAvatar());

            //on demarre l'activité d'édition d'un nouveau eleve
            startActivityForResult(intent, EDIT_EDIT_REQUEST);

        });

    }

    // après EDITION ou CREATION on récupère le résultat
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if (requestCode == ADD_ELEVE_REQUEST && resultCode == RESULT_OK) {

                String elevePrenom = data.getStringExtra(NewEleveActivity.EXTRA_ELEVE_NAME);
                String eleveNom = data.getStringExtra(NewEleveActivity.EXTRA_ELEVE_LASTNAME);
                String eleveClasse = data.getStringExtra(NewEleveActivity.EXTRA_CLASSE);
                byte[] eleveAvatar = data.getByteArrayExtra(NewEleveActivity.EXTRA_AVATAR);
                Eleve newEleve = new Eleve(elevePrenom, eleveNom, eleveClasse, eleveAvatar);
                viewModel.insert(newEleve);
                showMessage("Eleve enregistré!");
            } else if (requestCode == EDIT_EDIT_REQUEST && resultCode == RESULT_OK) {
                int id = data.getIntExtra(NewEleveActivity.EXTRA_ID, -1);
                if (id == -1) {
                    showMessage("Impossible d'effectuer cette action");
                    return;
                }
                String elevePrenom = data.getStringExtra(NewEleveActivity.EXTRA_ELEVE_NAME);
                String courseDesc = data.getStringExtra(NewEleveActivity.EXTRA_ELEVE_LASTNAME);
                String eleveClasse = data.getStringExtra(NewEleveActivity.EXTRA_CLASSE);
                byte[] eleveAvatar = data.getByteArrayExtra(NewEleveActivity.EXTRA_AVATAR);
                Eleve model = new Eleve(elevePrenom, courseDesc, eleveClasse, eleveAvatar);
                model.setId(id);
                viewModel.update(model);
                showMessage("Mis à jour effectée!");
            }else{
                showMessage("Impossible d'effectuer cette action");
            }
        } else {
            Toast.makeText(this, "Impossible d'effectuer cette action", Toast.LENGTH_SHORT).show();
        }



    }
    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}