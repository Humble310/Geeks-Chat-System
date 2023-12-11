package com.example.myapp123.activies;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.myapp123.R;
import com.example.myapp123.adapters.UsersAdapter;
import com.example.myapp123.databinding.ActivityUsersBinding;
import com.example.myapp123.listeners.UserListener;
import com.example.myapp123.models.User;
import com.example.myapp123.utilities.Constants;
import com.example.myapp123.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class    UsersActivity extends BaseActivity implements UserListener {
    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;
    EditText search_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager=new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
        //SearchUser();

    }
    /*private void SearchUser(){search_users.findViewById(R.id.searchUsers);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchUsers(charSequence.toString);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void searchUsers(String S){

    }*/
    private void setListeners(){

        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }


    private void getUsers(){
        loading(true);
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection((Constants.KEY_COLLECTION_USERS)).get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId=preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() !=null){
                        List<User>users=new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentUserId.equals(queryDocumentSnapshot.getId())){
                                    continue;
                            }
                            User user=new User();
                            user.name=queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email=queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image=queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token=queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id=queryDocumentSnapshot.getId();

                            users.add(user);
                        }
                        if (users.size() > 0){
                            UsersAdapter usersAdapter=new UsersAdapter(users,this);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        }

                        else {
                            showErrorMessage();
                        }
                    }else {
                        showErrorMessage();
                    }
                });
    }
    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s","No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }
    private void loading(Boolean isLoading){
        if(isLoading){
           binding.progressBar.setVisibility(View.VISIBLE);

        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user){
        Intent intent= new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER,user);
        startActivity(intent);
        finish();
    }


}