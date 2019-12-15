package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecretCodesActivity extends AppCompatActivity {

    Button addAdmin, clearAdmin;
    EditText pass;
    TextView out;
    String outputTextS = "", passwordTextS = "";

    DBPassword dbPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_code);



        pass = (EditText)findViewById(R.id.input);
        out = (TextView)findViewById(R.id.out);

        dbPassword = new DBPassword(this);

        String table = "adminT";
        output (table);
        onClick();
    }

    public void onClick() {
        addAdmin = (Button)findViewById(R.id.add);
        clearAdmin = (Button)findViewById(R.id.clear);



        addAdmin.setOnClickListener(
                new View.OnClickListener() {
                    public void  onClick (View v){
                        passwordTextS = "";
                        passwordTextS = pass.getText().toString();
                        insert(passwordTextS);
                        String table = "adminT";
                        output (table);
                        outputTextS = "";
                        pass.setText(outputTextS);
                    }
                }
        );

        clearAdmin.setOnClickListener(
                new View.OnClickListener() {
                    public void  onClick (View v){
                        SQLiteDatabase dbPasswordD = dbPassword.getWritableDatabase();
                        dbPasswordD.delete(DBPassword.TABLE_ADMIN, null, null);
                        outputTextS = "Data not found\n";
                        out.setText(outputTextS);
                        dbPassword.close();
                    }
                }
        );
    }

    private void output (String table) {

        SQLiteDatabase databaseAdmin = dbPassword.getWritableDatabase();

        Cursor cur = databaseAdmin.query(table, null, null, null, null, null, null);

        if (cur.moveToFirst()){
            String idS = DBPassword.KEY_ID;
            String dayNS = DBPassword.KEY_PASSWORD;
            int idIndex = cur.getColumnIndex(idS);
            int dayIndex = cur.getColumnIndex(dayNS);
            outputTextS = "";
            do {
                String id = cur.getString(idIndex);
                String dayN = cur.getString(dayIndex);

                outputTextS = outputTextS  + id + "  " + dayN + "\n";
            } while (cur.moveToNext());
        } else
            outputTextS = "Data not found\n";
        out.setText(outputTextS);
        cur.close();
    }

    private void insert (String pass){
        SQLiteDatabase database = dbPassword.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBPassword.KEY_PASSWORD, pass);
        database.insert(DBPassword.TABLE_ADMIN,null, contentValues);
        dbPassword.close();
    }

}
