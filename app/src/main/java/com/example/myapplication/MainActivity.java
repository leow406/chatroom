package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText messageInput;
    private ImageButton sendButton;
    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Redirige l'utilisateur non authentifié vers LoginActivity
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // Initialisation de Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");

        // Initialisation des vues
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        messageRecyclerView = findViewById(R.id.recyclerView);

        // Configuration du RecyclerView
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(messageAdapter);

        // Gestion du clic sur le bouton d'envoi
        sendButton.setOnClickListener(v -> sendMessage());

        // Chargement des messages depuis Firebase
        loadMessages();
    }

    private void sendMessage() {
        String messageContent = messageInput.getText().toString().trim();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (TextUtils.isEmpty(messageContent)) {
            Toast.makeText(this, "Le message ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentUser != null) {
            String username = currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Utilisateur";
            Message message = new Message(messageContent, username, (int) System.currentTimeMillis());
            DatabaseReference newMessageRef = mDatabase.push();
            message.setKey(newMessageRef.getKey());

            // Enregistrement du message dans la base de données
            newMessageRef.setValue(message).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    messageInput.setText(""); // Efface le champ de saisie
                    Toast.makeText(MainActivity.this, "Message envoyé", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Échec de l'envoi", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadMessages() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null) {
                        message.setKey(snapshot.getKey());
                        messageList.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Erreur de chargement des messages", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Vérification de l'utilisateur à chaque lancement de l'activité
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}
