package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);  // Assurez-vous que le bon layout est utilisé

        mAuth = FirebaseAuth.getInstance();  // Initialisation de FirebaseAuth
        emailInput = findViewById(R.id.etEmailRegister);
        passwordInput = findViewById(R.id.etPasswordRegister);
        registerButton = findViewById(R.id.btnRegister);

        // Enregistrement d'un utilisateur
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Vérifiez si les champs sont remplis
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer un utilisateur dans Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // L'utilisateur est inscrit avec succès
                        Toast.makeText(RegisterActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        // Vous pouvez rediriger vers LoginActivity ou MainActivity après l'inscription
                        // Exemple : startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    } else {
                        // En cas d'erreur, affichez le message d'erreur
                        Toast.makeText(RegisterActivity.this, "Erreur : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
