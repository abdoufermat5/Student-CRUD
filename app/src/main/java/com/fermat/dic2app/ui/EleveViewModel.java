package com.fermat.dic2app.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fermat.dic2app.models.Eleve;
import com.fermat.dic2app.repository.EleveRepository;

import java.util.List;

public class EleveViewModel extends AndroidViewModel {
    //variable pour l'entrepot
    private final EleveRepository repository;
    //variable observable pour la liste des eleves
    private final LiveData<List<Eleve>> allEleves;

    // constructeur
    public EleveViewModel(@NonNull Application application) {
        super(application);
        repository = new EleveRepository(application);
        allEleves = repository.getAllEleves();
    }


    // insertion
    public void insert(Eleve model) {
        repository.insert(model);
    }

    // m-a-j
    public void update(Eleve model) {
        repository.update(model);
    }

    // suppression
    public void delete(Eleve model) {
        repository.delete(model);
    }

    // suppression totale
    public void deleteAllCourses() {
        repository.deleteAllEleves();
    }

    // recuperation
    public LiveData<List<Eleve>> getAllEleves() {
        return allEleves;
    }
}
