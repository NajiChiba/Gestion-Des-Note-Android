package com.example.gestionnotes;

import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declaration des variables
    ListView notesListView;
    protected EditText note_tf;
    protected Button add_btn;
    protected EditText moyenne_tf;
    protected double moyenne_ = 0;
    static double noteSomme = 0;
    protected ArrayList<String> listViewItems;
    static ArrayList<String> noteInserer;
    ArrayAdapter<String> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ajouter les elements du dropdown
        Spinner dropdown = findViewById(R.id.dropdown);
        String[] items = new String[]{"math", "Infos", "Chimie","Reseaux"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // initialiser les components
        notesListView = findViewById(R.id.notes_list);
        note_tf = findViewById(R.id.note_tf);
        note_tf.setInputType(InputType.TYPE_CLASS_NUMBER);
        moyenne_tf = findViewById(R.id.moyenne_tf);
        moyenne_tf.setEnabled(false);
        add_btn = findViewById(R.id.add_btn);

        noteInserer = new ArrayList<>();
        listViewItems = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listViewItems);
        notesListView.setAdapter(notesAdapter);

        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String note = note_tf.getText().toString();
                String module = dropdown.getSelectedItem().toString();

                //verifier si l'utilisateur a insairer une note
                if(note == null){
                    show_toast("Veuillez saisir une note");
                    hideKeyBoard();
                }else{
                    // verifier si la note du moddule est deja inseree
                    if (noteInserer.contains(module)){
                        show_toast("La note du module "+ module +" existe deja");
                        hideKeyBoard();
                    }else{
                        addNote(note, module);
                        noteSomme = noteSomme + Double.parseDouble(note);
                        // update moyenne
                        moyenne_ = noteSomme / listViewItems.size();
                        moyenne_tf.setText(moyenne_+"");

                        hideKeyBoard();
                    }
                }

            }
        });

    }

    // cacher le clavier
    public void hideKeyBoard(){
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // afficher message
    public void show_toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    public void addNote(String note, String module){
        listViewItems.add(n+"          "+module+"         "+note);
        int n = listViewItems.size() + 1;
        noteInserer.add(module);
        note_tf.setText("");
        notesListView.setAdapter(notesAdapter);
    }

}