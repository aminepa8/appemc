package com.example.myapplication;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
    // La chaîne de caractères par défaut
    private final String defaut = "Cliquez sur le bouton « Calculer l'IMC » pour le résultat.";
    // La chaîne de caractères de la megafonction
    private final String megaString = "Vous faites un poids parfait !";
    Button envoyer = null;
    Button raz = null;
    EditText poids = null;
    EditText taille = null;
    RadioGroup group = null;
    TextView result = null;
    CheckBox mega = null;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // On récupère toutes les vues dont on a besoin
        envoyer = (Button) findViewById(R.id.calcul);
        raz = (Button) findViewById(R.id.raz);
        taille = (EditText) findViewById(R.id.taille);
        poids = (EditText) findViewById(R.id.poids);
        mega = (CheckBox) findViewById(R.id.mega);
        group = (RadioGroup) findViewById(R.id.group);
        result = (TextView) findViewById(R.id.result);
        // On attribue un listener adapté aux vues qui en ont besoin
        envoyer.setOnClickListener(envoyerListener);
        raz.setOnClickListener(razListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);
        // Solution avec des onKey
        //taille.setOnKeyListener(modificationListener);
        //poids.setOnKeyListener(modificationListener);
        mega.setOnClickListener(checkedListener);
    }
    /*  // Se lance à chaque fois qu'on appuie sur une touche en étant sur un EditText  private OnKeyListener modificationListener = new OnKeyListener() {    @Override    public boolean onKey(View v, int keyCode, KeyEvent event) {      // On remet le texte à sa valeur par défaut pour ne pas avoir de résultat incohérent      result.setText(defaut);      return false;    }  };*/
    private TextWatcher textWatcher = new TextWatcher() {
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(defaut);
        }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    };
    // Uniquement pour le bouton "envoyer"
    private OnClickListener envoyerListener = new OnClickListener() {
        @Override public void onClick(View v) {
            if(taille.getText().toString().matches("") ||  poids.getText().toString().matches("")){
                Toast.makeText(MainActivity.this, "both 'Poids' and 'Taille' fields are required ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!mega.isChecked()) {
                // Si la megafonction n'est pas activéematches()
                // On récupère la taille
                String t = taille.getText().toString();
                // On récupère le poids
                String p = poids.getText().toString();
                float tValue = Float.valueOf(t);
                // Puis on vérifie que la taille est cohérente
                if (tValue == 0) Toast.makeText(MainActivity.this, "Hého, tu es un Minipouce ou quoi ?", Toast.LENGTH_SHORT).show();
                else {
                    float pValue = Float.valueOf(p);
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide deson identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio2) tValue = tValue / 100;
                    tValue = (float) Math.pow(tValue, 2);
                    float imc = pValue / tValue;
                    result.setText("Votre IMC est " + String.valueOf(imc));
                }
            } else result.setText(megaString);
        }
    };
    // Listener du bouton de remise à zéro
    private OnClickListener razListener = new OnClickListener() {
        @Override public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(defaut);
        }
    };
    // Listener du bouton de la megafonction.
    private OnClickListener checkedListener = new OnClickListener() {
        @Override public void onClick(View v) {
            // On remet le texte par défaut si c'était le texte de la megafonction quiétait écrit
            if (!((CheckBox) v).isChecked() && result.getText().equals(megaString))
                result.setText(defaut);
        }
    };
}