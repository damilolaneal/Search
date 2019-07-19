package com.damilola.searchapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText confirm_password;
    EditText e_username;
    EditText full_name;

    DbHelper dbHelper;

    Boolean check_confirm_password = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        email = findViewById(R.id.username);
        e_username = findViewById(R.id.e_username);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        full_name = findViewById(R.id.full_name);

        email.getText().clear();
        password.getText().clear();

        e_username.setVisibility(View.GONE);
        confirm_password.setVisibility(View.GONE);
        full_name.setVisibility(View.GONE);

        e_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String word = e_username.getText().toString().trim();
                String[] words = (e_username.getText().toString().trim()).split("");

                int x = dbHelper.check_username(word);
                //fill kinikan
                if (words.length != 1 && x > 0){
                    e_username.setError("Username exist");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String word = email.getText().toString().trim();
                String[] words = (email.getText().toString().trim()).split("");

                String[] wordsa = (email.getText().toString().trim()).split("@");

                int x = dbHelper.check_email(word);
                //fill kinikan
                if (words.length != 1 && x > 0){
                    email.setError("Email exist");
                }else if (isConfirmPasswordShown() && wordsa.length == 1 ){
                    email.setError("Please enter a valid e-mail address");
                }else if (isConfirmPasswordShown() && wordsa.length == 0){
                    email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("BG Direct");
        alertDialog.setMessage("Click exit to close this application");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Exit",
                (dialog, which) -> {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                    dialog.cancel();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> {
                    onResume();
                    dialog.dismiss();
                });

        alertDialog.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        sign_in_phase();
    }


    public void SignIn(View view) {
        if (isConfirmPasswordShown()){
            sign_in_phase();
        }else if(!isConfirmPasswordShown()){
            if ((email.getText().toString().trim()).matches("")){
                email.setError(getResources().getString(R.string.error));
            }else if ((password.getText().toString().trim()).equalsIgnoreCase("")){
                password.setError(getResources().getString(R.string.error));
            }else{
                String answer = dbHelper.confirmDetails(email.getText().toString().trim());
                if (!answer.matches(password.getText().toString().trim())){
                    Snackbar.make(view, "Invalid username or password", Snackbar.LENGTH_LONG).show();
                }else{
                    String full_name = dbHelper.selectFullName(email.getText().toString().trim());
                    Intent intent = new Intent(this, Main2Activity.class);
                    intent.putExtra("full_name",full_name);
                    startActivity(intent);
                }
            }

        }

    }

    public void SignUp(View view) {

        String mail = email.getText().toString().trim();
        String user = e_username.getText().toString().trim();

        int x = dbHelper.check_email(mail);
        int y = dbHelper.check_username(user);

        String[] words = (email.getText().toString().trim()).split("@");

        if (!isConfirmPasswordShown()){
            sign_up_phase();
        }else if (isConfirmPasswordShown()){
            if ((email.getText().toString().trim()).matches("")){
                email.setError(getResources().getString(R.string.error));
            }
            else if ((e_username.getText().toString().trim()).equalsIgnoreCase("")){
                e_username.setError(getResources().getString(R.string.error));
            }
            else if ((password.getText().toString().trim()).equalsIgnoreCase("")){
                password.setError(getResources().getString(R.string.error));
            }
            else if ((confirm_password.getText().toString().trim()).equalsIgnoreCase("")){
                confirm_password.setError(getResources().getString(R.string.error));
            }
            else if ((full_name.getText().toString().trim()).equalsIgnoreCase("")){
                confirm_password.setError(getResources().getString(R.string.error));
            }
            else{
                if (!(password.getText().toString().trim()).equalsIgnoreCase((confirm_password.getText().toString().trim()))){
//                    Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_LONG).show();
                }else if(words.length <= 1){
//                    Toast.makeText(MainActivity.this, "Email not valid", Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "Email not valid", Snackbar.LENGTH_LONG).show();
                }else if (x > 0){
                    email.setError("Email exist");
                }else if (y > 0){
                    email.setError("Username exist");
                }else{
                    //save to database and move to next activity

                    if (saved_to_database()){
//                        Toast.makeText(MainActivity.this, "Details saved, please sign in", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Details saved, please sign in.", Snackbar.LENGTH_LONG).show();
                        sign_in_phase();
                    }else{
//                        Toast.makeText(MainActivity.this, "Details not saved", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Details not saved", Snackbar.LENGTH_LONG).show();
                        sign_up_phase();
                    }
                }
            }
        }

    }

    public Boolean isConfirmPasswordShown(){
        if (confirm_password.getVisibility() == View.VISIBLE){
            check_confirm_password = true;
        }else if (confirm_password.getVisibility() != View.VISIBLE){
            check_confirm_password = false;
        }
        return check_confirm_password;
    }

    public Boolean saved_to_database(){
        dbHelper.save_new_member(email.getText().toString().trim(),e_username.getText().toString().trim(),
                password.getText().toString().trim(),full_name.getText().toString().trim());
        return true;
    }

    public void sign_in_phase(){
        confirm_password.getText().clear();
        e_username.getText().clear();
        confirm_password.setVisibility(View.GONE);
        e_username.setVisibility(View.GONE);
        full_name.setVisibility(View.GONE);
        email.getText().clear();
        password.getText().clear();
        full_name.getText().clear();
        email.setHint(R.string.username_placeholder);
        email.setError(null);
    }

    public void sign_up_phase(){
        e_username.setVisibility(View.VISIBLE);
        e_username.setHint(R.string.e_username_placeholder);
        email.setHint(R.string.email_placeholder);
        confirm_password.setVisibility(View.VISIBLE);
        full_name.setVisibility(View.VISIBLE);
        email.getText().clear();
        password.getText().clear();
    }

}
