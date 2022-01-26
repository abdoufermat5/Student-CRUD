package com.fermat.dic2app.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fermat.dic2app.dao.Dao;
import com.fermat.dic2app.models.Eleve;


@Database(entities = {Eleve.class}, version = 1)
public abstract class EleveDatabase extends RoomDatabase {

    private static EleveDatabase instance;

    public abstract Dao Dao();

    public static synchronized EleveDatabase getInstance(Context context) {

        if (instance == null) {

            instance =
                    // un builder pour instancier la bd
                    Room.databaseBuilder(context.getApplicationContext(),
                            EleveDatabase.class, "eleve_database")
                            // en cas d'echecs
                            .fallbackToDestructiveMigration()
                            // ajout d'une callback
                            .addCallback(roomCallback)
                            // build
                            .build();
        }
        //instance crée
        return instance;
    }

    // creation du callback
    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //une fois que la bd est crée executer cette action
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // pour executer en background
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        PopulateDbAsyncTask(EleveDatabase instance) {
            Dao dao = instance.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
