package com.example.nymafica_bitacora1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    //private Button bt1, bt2; //Para hacer onClickListener luego

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.etDate);
        et2 = findViewById(R.id.etNotepad);

    }

    public boolean exists(String[] archivos, String archFecha) {
        for (int f=0; f<archivos.length; f++) {
            if (archFecha.equals(archivos[f])) {
                return true;
            }
        }
        return false;
    }

    public void grabar(View view) {
        try {
            String fechaNombre = et1.getText().toString();
            fechaNombre = fechaNombre.replace("/", "-");
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(fechaNombre, Context.MODE_PRIVATE));
            archivo.write(et2.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) { e.printStackTrace(); }
        Toast.makeText(this, "Datos guardados", Toast.LENGTH_LONG).show();
    }

    public void recuperar(View view) {
        String[] archivos = fileList();
        String fechaBuscar = et1.getText().toString();
        fechaBuscar = fechaBuscar.replace("/", "-");
        try {
            if(exists(archivos, fechaBuscar)) {
                InputStreamReader archivo = new InputStreamReader(openFileInput(fechaBuscar));
                BufferedReader br = new BufferedReader(archivo);
                String todo = "";
                String linea = br.readLine();
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                et2.setText(todo);
            } else {
                et2.setText("");
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

}