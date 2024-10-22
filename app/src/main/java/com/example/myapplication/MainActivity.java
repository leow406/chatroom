package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText messageInput;
    private ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");

        // Initialiser les vues
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // Configurer l'action de clic sur le bouton d'envoi
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();  // Appeler la méthode pour envoyer le message
            }
        });
    }

    // Méthode pour envoyer le message à Firebase
    private void sendMessage() {
        // Récupérer le contenu du message
        String messageContent = messageInput.getText().toString().trim();

        // Vérifier que le message n'est pas vide
        if (!messageContent.isEmpty()) {
            // Créer un objet Message
            Message message = new Message(messageContent, "UsernamePlaceholder", 0);  // 0 likes au début

            // Ajouter le message à Firebase
            mDatabase.push().setValue(message);

            // Vider le champ de saisie après l'envoi
            messageInput.setText("");
        }
    }
}
