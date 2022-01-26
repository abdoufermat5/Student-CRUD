package com.fermat.dic2app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fermat.dic2app.R;

import java.io.ByteArrayOutputStream;

public class NewEleveActivity extends AppCompatActivity {

    //creating a variables for our button and edittext.
    private EditText elevePrenomEditTxt, eleveNomEditTxt, eleveClasseEditTxt;
    private ImageView avatarPic;
    private TextView description;

    private final int SELECT_PICTURE = 200;
    public static final String EXTRA_ID =
            "com.fermat.dic2.EXTRA_ID";
    public static final String EXTRA_ELEVE_NAME =
            "com.fermat.dic2.EXTRA_ELEVE_NAME";
    public static final String EXTRA_ELEVE_LASTNAME =
            "com.fermat.dic2.EXTRA_LASTNAME";
    public static final String EXTRA_CLASSE =
            "com.fermat.dic2.EXTRA_CLASSE";
    public static final String EXTRA_AVATAR =
            "com.fermat.dic2.EXTRA_AVATAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_eleve);
        //initialisation
        elevePrenomEditTxt = findViewById(R.id.idEdtElevePrenom);
        eleveNomEditTxt = findViewById(R.id.idEdtEleveNom);
        eleveClasseEditTxt = findViewById(R.id.idEdtEleveClasse);
        avatarPic = findViewById(R.id.idAvatar);
        description = findViewById(R.id.idDesc);
        Button saveButton = findViewById(R.id.idBtnSaveEleve);


        // en cliquant sur la photo
        avatarPic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        //below line is to get intent as we are getting data via an intent.
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            //if we get id for our data then we are setting values to our edit text fields.
            elevePrenomEditTxt.setText(intent.getStringExtra(EXTRA_ELEVE_NAME));
            eleveNomEditTxt.setText(intent.getStringExtra(EXTRA_ELEVE_LASTNAME));
            eleveClasseEditTxt.setText(intent.getStringExtra(EXTRA_CLASSE));
            byte[] bytes = intent.getByteArrayExtra(EXTRA_AVATAR);
            Bitmap bm = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
            if (bm != null){
                avatarPic.setImageBitmap(bm);
                description.setVisibility(View.GONE);
            }else{
                avatarPic.setImageResource(R.drawable.logo_icon);
                description.setVisibility(View.GONE);
            }
        }
        //adding on click listner for our save button.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting text value from edittext and validating if the text fields are empty or not.
                String eleveName = elevePrenomEditTxt.getText().toString();
                String eleveDesc = eleveNomEditTxt.getText().toString();
                String eleveDuration = eleveClasseEditTxt.getText().toString();
                Bitmap bitmap = ((BitmapDrawable) avatarPic.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                if (eleveName.isEmpty() || eleveDesc.isEmpty() || eleveDuration.isEmpty()) {
                    showMessage("Veuillez entrer les champs correctement");
                    return;
                }
                //calling a method to save our eleve.
                saveCourse(eleveName, eleveDesc, eleveDuration, imageInByte);
            }
        });
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Choisir une image"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    avatarPic.setImageURI(selectedImageUri);
                    description.setVisibility(View.GONE);
                }
            }
        }
    }

    private void saveCourse(String eleveName, String eleveDescription, String eleveDuration, byte[] imgPic) {
        //inside this method we are passing all the data via an intent.
        Intent data = new Intent(NewEleveActivity.this, MainActivity.class);
        //in below line we are passing all our eleve detail.
        data.putExtra(EXTRA_ELEVE_NAME, eleveName);
        data.putExtra(EXTRA_ELEVE_LASTNAME, eleveDescription);
        data.putExtra(EXTRA_CLASSE, eleveDuration);
        data.putExtra(EXTRA_AVATAR, imgPic);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            //in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }
        //at last we are setting result as data.
        setResult(RESULT_OK, data);
        //displaying a toast message after adding the data
        showMessage("Eleve enregistré avec succès!!");
        startActivity(data);
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}