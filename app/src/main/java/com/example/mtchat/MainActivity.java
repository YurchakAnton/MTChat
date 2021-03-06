package com.example.mtchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mtchat.Adapters.MessageAdapter;
import com.example.mtchat.Interfaces.ApiInterface;
import com.example.mtchat.Model.Message;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Message> adapter;
    private EditText inputText;
    private ImageButton sendImageBtn;
    private RecyclerView listView;
    private FloatingActionButton btnSend;
    private DatabaseReference MessagesRef, MessagesImageRef;
    private NestedScrollView scrollView;
    private String checker = "", downloadImageUrl = "";
    private UploadTask uploadTask;
    private static final int GalleryPick = 1;
    private Uri fileUri;
    private ProgressDialog loadingBar;
    private boolean isImageScaled=false;
    private ArrayList<String> existWord = new ArrayList<String>();

    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.send_message_btn);
        inputText = (EditText) findViewById(R.id.input_message);
        listView = findViewById(R.id.list_of_messages);
        scrollView = findViewById(R.id.scroll_view);
        sendImageBtn = findViewById(R.id.select_send_image_btn);
        loadingBar = new ProgressDialog(this);

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        apiInterface.getMessages().enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.isSuccessful()) {
                    List<Message> messageList = response.body();
                    System.out.println("RESPONSE:"+messageList.get(0).getData());
                    MessageAdapter adapter = new MessageAdapter(MainActivity.this, messageList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    listView.setLayoutManager(linearLayoutManager);
                    listView.setAdapter(adapter);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(scrollView.FOCUS_DOWN);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        sendImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "image";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GalleryPick);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputText.getText().toString().isEmpty())
                {
                    Message message = new Message("text", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    apiInterface.saveMessages(message).enqueue(new Callback<List<Message>>() {
                        @Override
                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                        }

                        @Override
                        public void onFailure(Call<List<Message>> call, Throwable t) {

                        }
                    });
//                    checker = "text";
//                    long key = new Date().getTime();
//                    String[] words = {"????????????", "????????????????", "????????", "??", "??????????", "????????????"};
//                    if(inputText.getText().toString().contains("?")) {
////                        existWord = containsWords(inputText.getText().toString(), words);
////                        Toast.makeText(MainActivity.this, existWord.toString(), Toast.LENGTH_LONG).show();
//                    }
////                    FirebaseDatabase.getInstance().getReference().child("Messages").child(String.valueOf(key)).setValue(
////                            new Message(inputText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), key, checker));
//                    inputText.setText("");
                }
            }
        });



//        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
//            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
//        } else {
//            Toast.makeText(this,
//                    "??????????????, " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
//                    Toast.LENGTH_LONG).show();
//            displayChatMessages();
//        }
    }

//    private static ArrayList<String> containsWords(String str, String[] words) {
//        ArrayList<String> existWord = new ArrayList<String>();
//        for(String word : words) {
//            if(str.toLowerCase().contains(word.toLowerCase())) {
//                existWord.add(word);
//            }
//        }
//        return existWord;
//    }
//
//    private void displayChatBotMessage() {
//
//    }
//
//    private void displayChatMessages() {
//        FirebaseRecyclerOptions<Message> options =
//                new FirebaseRecyclerOptions.Builder<Message>()
//                        .setQuery(MessagesRef, Message.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<Message, MessageViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i, @NonNull Message message) {
//                        if(message.getTypeOfMessage().equals("text"))
//                        {
//                            messageViewHolder.messageText.setText(message.getMessageText());
//                            messageViewHolder.messageImage.setVisibility(View.GONE);
//                            messageViewHolder.messageText.setVisibility(View.VISIBLE);
//                        }
//                        else
//                        {
//                            messageViewHolder.messageImage.setVisibility(View.VISIBLE);
//                            messageViewHolder.messageText.setVisibility(View.GONE);
//                            Picasso.get().load(message.getImage()).fit().into(messageViewHolder.messageImage);
//                        }
//                        messageViewHolder.messageUser.setText(message.getMessageUser());
//                        messageViewHolder.messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm)", message.getMessageTime()));
//
//                        messageViewHolder.messageImage.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if(!isImageScaled)
//                                    messageViewHolder.messageImage.animate().scaleX(2f).scaleY(2f).setDuration(500);
//                                if(isImageScaled)
//                                    messageViewHolder.messageImage.animate().scaleX(1f).scaleY(1f).setDuration(500);
//                                isImageScaled = !isImageScaled;
//                            }
//                        });
//
//                        messageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                CharSequence options[] = new CharSequence[]
//                                        {
//                                                "????????????????"
//                                        };
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                builder.setTitle("?????? ?????? ?????????????? ??????????????????:");
//
//                                builder.setItems(options, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        if (which == 0) {
//                                            AlertDialog.Builder builderanswer = new AlertDialog.Builder(MainActivity.this);
//                                            builderanswer.setMessage("???? ????????????????, ???? ?????????????? ???????????????? ?????????????????????????");
//                                            builderanswer.setCancelable(true);
//                                            builderanswer.setPositiveButton("????????????????", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Messages");
//                                                    if (message.getUserEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
//                                                        cartListRef.child(String.valueOf(message.getMessageTime())).removeValue();
//                                                    } else {
//                                                        Toast.makeText(MainActivity.this, "?????????????? ???????? ????????????????????????", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//
//                                            builderanswer.setNegativeButton("????????????????", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.cancel();
//                                                }
//                                            });
//                                            AlertDialog alert = builderanswer.create();
//                                            alert.show();
//                                        }
//                                    }
//                                });
//                                builder.show();
//                            }
//                        });
//                    }
//
//
//                    @NonNull
//                    @Override
//                    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
//                        MessageViewHolder holder = new MessageViewHolder(view);
//                        scrollView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                scrollView.fullScroll(scrollView.FOCUS_DOWN);
//                            }
//                        });
//                        return holder;
//                    }
//
//                };
//        listView.setAdapter(adapter);
//        adapter.startListening();
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SIGN_IN_REQUEST_CODE)
//        {
//            if (resultCode == RESULT_OK)
//            {
//                displayChatMessages();
//            }
//        }
//
//        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null && data.getData() != null)
//        {
//
//            fileUri = data.getData();
//            if (checker.equals("image"))
//            {
//                loadingBar.setMessage("??????????????????...");
//                loadingBar.setCanceledOnTouchOutside(false);
//                loadingBar.show();
//                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Message Images");
//                long key = new Date().getTime();
//                StorageReference filePath = storageReference.child(key + ".jpg");
//                uploadTask = filePath.putFile(fileUri);
//
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e)
//                    {
//                        loadingBar.dismiss();
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                if (!task.isSuccessful()) {
//                                    throw task.getException();
//                                }
//                                downloadImageUrl = filePath.getDownloadUrl().toString();
//                                return filePath.getDownloadUrl();
//                            }
//                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                if (task.isSuccessful()) {
//                                    downloadImageUrl = task.getResult().toString();
//                                    SaveImageMessageInfoToDatabase();
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        }
//    }
//
//    private void SaveImageMessageInfoToDatabase()
//    {
//        MessagesImageRef = FirebaseDatabase.getInstance().getReference();
//        long key = new Date().getTime();
//        MessagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                HashMap<String, Object> messagesMap = new HashMap<>();
//                messagesMap.put("messageText", "");
//                messagesMap.put("messageTime", key);
//                messagesMap.put("messageUser", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
//                messagesMap.put("typeOfMessage", "image");
//                messagesMap.put("image", downloadImageUrl);
//                messagesMap.put("userEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
//
//                MessagesImageRef.child("Messages").child(String.valueOf(key)).updateChildren(messagesMap)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task)
//                            {
//                                if(task.isSuccessful())
//                                {
//                                    loadingBar.dismiss();
//                                    displayChatMessages();
//                                }
//                                else
//                                {
//                                    loadingBar.dismiss();
//                                    Toast.makeText(MainActivity.this,"??????????????! ?????????????????? ?????????? ?????????? ???????????????? ????????????...",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_signout) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
                        }
                    });
        }
        return true;
    }

}