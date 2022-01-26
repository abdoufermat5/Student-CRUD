package com.fermat.dic2app.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "eleve_table")
public class Eleve {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name = "prenom")
    @NonNull
    private String prenom;

    @ColumnInfo(name = "nom")
    @NonNull
    private String nom;

    @ColumnInfo(name = "classe")
    @NonNull
    private String classe;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name="avatar")
    private byte[] avatar;



    //below line we are creating constructor class.
    //inside constructor class we are not passing our id because it is incrementing automatically
    public Eleve(@NonNull String prenom, @NonNull String nom, @NonNull String classe, byte[] avatar) {
        this.prenom = prenom;
        this.nom = nom;
        this.classe = classe;
        this.avatar = avatar;
    }

    //on below line we are creating getter and setter methods.
    @NonNull
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @NonNull
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @NonNull
    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
