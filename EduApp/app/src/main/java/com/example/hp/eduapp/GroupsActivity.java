package com.example.hp.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.eduapp.adapters.ChatMessageAdapter;
import com.example.hp.eduapp.data_models.ChatMessage;
import com.example.hp.eduapp.data_models.Group;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupsActivity extends AppCompatActivity {
    private ListView mListView;
    private ImageButton mButtonSend;
    private TextView debugTextView;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;
    private ArrayList<ChatMessage> chatHistory;
    private Button createBtn;
    private Button joinBtn;
    private Firebase mFirebaseRef;
    private Firebase mGFirebaseRef;
    private String groupId;
    private Group group;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFirebaseRef = new Firebase("https://educapplication-95d23.firebaseio.com/");
        mGFirebaseRef = new Firebase("https://educapplication-95d23.firebaseio.com/messages/");

        debugTextView = (TextView) findViewById(R.id.debugTextView);
        createBtn = (Button) findViewById(R.id.create_group_btn);
        joinBtn = (Button) findViewById(R.id.join_group_btn);
//        if(!group.getGroupName().equals("")){
//            setTitle(group.getGroupName());
//        }

//        final FrameLayout fabLayout = (FrameLayout) findViewById(R.id.groups_screen_fab_layout);
//        fabLayout.getBackground().setAlpha(0);
//        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.group_screen_fab_menu);
//        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
//            @Override
//            public void onMenuExpanded() {
//                fabLayout.getBackground().setAlpha(240);
//                fabLayout.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        fabMenu.collapse();
//                        return true;
//                    }
//                });
//                com.getbase.floatingactionbutton.FloatingActionButton newGroupFab = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.new_group_fab);
//                com.getbase.floatingactionbutton.FloatingActionButton joinGroupFab = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.join_group_fab);
//
//                newGroupFab.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(), CreateGroupActivity.class);
//                        startActivity(intent);
//                        fabMenu.collapse();
//                    }
//                });
//                joinGroupFab.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(), "SearchGroupActivity", Toast.LENGTH_SHORT).show();
//                        fabMenu.collapse();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onMenuCollapsed() {
//                fabLayout.getBackground().setAlpha(0);
//                fabLayout.setOnTouchListener(null);
//            }
//        });
        initControls();
        readGroupData();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupsActivity.this, CreateGroupActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings_groups){
            startActivity(new Intent(GroupsActivity.this, GroupSettingsActivity.class));
        }
        return true;
    }

    private void initControls() {
            mListView = (ListView) findViewById(R.id.msgListView);
            mEditTextMessage = (EditText) findViewById(R.id.messageEditText);
            mButtonSend = (ImageButton) findViewById(R.id.sendMessageButton);

//            TextView meLabel = (TextView) findViewById(R.id.meLbl);
//            TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
//            RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
//            companionLabel.setText("My Buddy");// Hard Coded
            loadDummyHistory();

            mButtonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = mEditTextMessage.getText().toString();
                    if (TextUtils.isEmpty(messageText)) {
                        return;
                    }

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId(122);//Specifies the user who sent it
                    chatMessage.setMessage(messageText);
                    chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                    chatMessage.setMe(true);
                    chatMessage.setAuthor("Ben"); // Get the username from the constants class
                    //Sending the chat message to the Group Node
                    group.setChatMsg(chatMessage);
                    mGFirebaseRef.push().setValue(chatMessage);
                    mEditTextMessage.setText("");
                    displayMessage(chatMessage);
                }
            });
        }

        public void displayMessage(ChatMessage message) {
            mAdapter.add(message);
            mAdapter.notifyDataSetChanged();
            scroll();
        }

        private void scroll() {
            mListView.setSelection(mListView.getCount() - 1);
        }

        private void loadDummyHistory(){

            chatHistory = new ArrayList<ChatMessage>();

            ChatMessage msg = new ChatMessage();
            msg.setId(1);
            msg.setMe(false);
            msg.setMessage("Hi");
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg);
            ChatMessage msg1 = new ChatMessage();
            msg1.setId(2);
            msg1.setMe(false);
            msg1.setMessage("How r u doing???");
            msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg1);

            mAdapter = new ChatMessageAdapter(GroupsActivity.this, new ArrayList<ChatMessage>());
            mListView.setAdapter(mAdapter);

            for(int i=0; i<chatHistory.size(); i++) {
                ChatMessage message = chatHistory.get(i);
                displayMessage(message);
            }
        }
        public void readGroupData(){
//            mFirebaseRef.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    if (!dataSnapshot.exists() ){
//                        return;
//                    }
//
//                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
//                        //Getting the data from snapshot
//                        group = postSnapShot.getValue(Group.class);
//                        groupId = postSnapShot.getKey();
//                        group.setId(groupId);
//                        String adminName = group.getAdminName();
//                        group.setAdminName(adminName);
//                        String groupName = group.getGroupName();
//                        group.setGroupName(groupName);
//                        group.setChatMsg(null);
//                        // ChatMessage msg = group.getChatMsg();
//
//                        debugTextView.setText("GROUP INFO \n" + adminName + "\n"+
//                                groupName + "\n" + groupId);
//                    }
//
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });
            mFirebaseRef.child("groups").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists() ){
                        return;
                    }

                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                        //Getting the data from snapshot
                        group = postSnapShot.getValue(Group.class);
                        groupId = postSnapShot.getKey();
                        group.setId(groupId);
                        String adminName = group.getAdminName();
                        group.setAdminName(adminName);
                        String groupName = group.getGroupName();
                        group.setGroupName(groupName);
                        group.setChatMsg(null);
                       // ChatMessage msg = group.getChatMsg();

                        debugTextView.setText("GROUP INFO \n" + adminName + "\n"+
                                groupName + "\n" + groupId);

                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

    }
