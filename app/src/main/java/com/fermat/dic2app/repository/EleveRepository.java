package com.fermat.dic2app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fermat.dic2app.dao.Dao;
import com.fermat.dic2app.db.EleveDatabase;
import com.fermat.dic2app.models.Eleve;

import java.util.List;

public class EleveRepository {
    //variable dao
    private final Dao dao;

    // liste des eleves
    // Pour pouvoir observer les changements en temps réels on utilise LiveData...
    private final LiveData<List<Eleve>> allEleves;

    // constructeur de l'entrepot.
    public EleveRepository(Application application) {
        EleveDatabase database = EleveDatabase.getInstance(application);
        dao = database.Dao();
        allEleves = dao.getAllEleves();
    }

    // methode pour l'insertion d'un eleve dans la bd.
    public void insert(Eleve model) {
        new InsertEleveAsyncTask(dao).execute(model);
    }

    // methode pour l'update d'un eleve dans la bd.
    public void update(Eleve model) {
        new UpdateElevesAsyncTask(dao).execute(model);
    }

    // methode pour supprimer un eleve de la bd.
    public void delete(Eleve model) {
        new DeleteEleveAsyncTask(dao).execute(model);
    }

    // methode pour un suppression totale des eleves de la bd.
    public void deleteAllEleves() {
        new DeleteAllElevesAsyncTask(dao).execute();
    }

    // methode pour recuperer les eleves de la bd.
    public LiveData<List<Eleve>> getAllEleves() {
        return allEleves;
    }

    // methodes asynchrones: Insert - Delete - Update - DeleteAll
    // Cela permet de ne pas retarder le thread principal
    // On créer des thread paralleles pour excuter ces taches

    private static class InsertEleveAsyncTask extends AsyncTask<Eleve, Void, Void> {
        private final Dao dao;

        private InsertEleveAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Eleve... model) {

            dao.insert(model[0]);
            return null;
        }
    }


    private static class UpdateElevesAsyncTask extends AsyncTask<Eleve, Void, Void> {
        private final Dao dao;

        private UpdateElevesAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Eleve... models) {

            dao.update(models[0]);
            return null;
        }
    }


    private static class DeleteEleveAsyncTask extends AsyncTask<Eleve, Void, Void> {
        private final Dao dao;

        private DeleteEleveAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Eleve... models) {

            dao.delete(models[0]);
            return null;
        }
    }


    private static class DeleteAllElevesAsyncTask extends AsyncTask<Void, Void, Void> {
        private final Dao dao;

        private DeleteAllElevesAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.deleteAllEleves();
            return null;
        }
    }
}
