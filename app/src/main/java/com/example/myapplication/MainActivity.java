// MainActivity.java - Updated with sorting logic
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText messageInput;
    private ImageButton sendButton, logoutButton;
    private RecyclerView messageRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUsername = getIntent().getStringExtra("username");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        logoutButton = findViewById(R.id.logoutButton);
        messageRecyclerView = findViewById(R.id.recyclerView);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, currentUsername);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(v -> {
            String messageContent = messageInput.getText().toString();
            if (!messageContent.isEmpty()) {
                Message message = new Message(messageContent, currentUsername, 0);
                DatabaseReference newMessageRef = mDatabase.push();
                message.setKey(newMessageRef.getKey());
                newMessageRef.setValue(message);
                messageInput.setText("");
            }
        });

        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

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
                sortMessages(); // Appliquer le tri avant d’afficher
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer les erreurs possibles
            }
        });
    }

    private void sortMessages() {
        long currentTime = System.currentTimeMillis();
        for (Message message : messageList) {
            long messageAgeInDays = (currentTime - message.getTimestamp()) / (1000 * 60 * 60 * 24);
            double omega = 30 - messageAgeInDays * message.getLikes();
            message.setSortingScore(omega); // Nouvelle méthode pour définir le score
        }

        Collections.sort(messageList, Comparator.comparingDouble(Message::getSortingScore).reversed());
    }
}
