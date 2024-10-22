package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText messageInput;
    private ImageButton sendButton;
    private RecyclerView messageRecyclerView;  // Déclaration du RecyclerView
    private MessageAdapter messageAdapter;       // Déclaration de l'adaptateur
    private List<Message> messageList;
    @SuppressLint("MissingInflatedId")
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialiser Firebase
    mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");

    // Initialiser les vues
    messageInput = findViewById(R.id.messageInput);
    sendButton = findViewById(R.id.sendButton);
    messageRecyclerView = findViewById(R.id.recyclerView);  // Initialiser le RecyclerView

    // Initialiser la liste des messages
    messageList = new ArrayList<>();

    // Configurer l'adaptateur et le RecyclerView
    messageAdapter = new MessageAdapter(messageList);
    messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    messageRecyclerView.setAdapter(messageAdapter);

    // Configurer l'action de clic sur le bouton d'envoi
    sendButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendMessage();  // Appeler la méthode pour envoyer le message
        }
    });

    // Récupérer les messages depuis Firebase
    retrieveMessages();
}

// Méthode pour envoyer le message à Firebase
private void sendMessage() {
    String messageContent = messageInput.getText().toString().trim();

    if (!messageContent.isEmpty()) {
        Message message = new Message(messageContent, "UsernamePlaceholder", 0);
        mDatabase.push().setValue(message);
        messageInput.setText("");
    }
}

// Méthode pour récupérer les messages de Firebase
private void retrieveMessages() {
    mDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            messageList.clear(); // Effacer la liste avant d'ajouter de nouveaux messages
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Message message = snapshot.getValue(Message.class);
                messageList.add(message); // Ajouter le message à la liste
            }
            messageAdapter.notifyDataSetChanged(); // Informer l'adaptateur des changements
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // Gérer les erreurs ici
        }
    });
}
}