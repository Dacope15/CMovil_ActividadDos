package co.edu.uniminuto.segundaactividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etTask;
    private Button btnAdd;
    private ListView listTask;
    private ArrayList<String>arrayList;
    private ArrayAdapter<String> adapter;
    private Button btnBsq;
    private Button btnLmp;


    private ActivityResultLauncher<Intent> activityResultLauncher;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        initObject();
        this.btnAdd.setOnClickListener(this::addTask);
        this.btnBsq.setOnClickListener(this::buscarTask);
        this.btnLmp.setOnClickListener(this::limpiarBusqueda);
        setupItemClickLintener();
        setupActivityResultLauncher();
    }

    private void limpiarBusqueda(View view) {
        this.listTask.setAdapter(this.adapter);
        this.etTask.setText("");
        Toast.makeText(this, "Lista restaurada", Toast.LENGTH_LONG).show();
    }


    private void setupActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        handleActivityResult(result.getData());
                    }
                }
        );
    }
    private void handleActivityResult(Intent data) {
        int position = data.getIntExtra("position", -1);
        if (data.hasExtra("ActualizarTask")) {
            String actualizarTask = data.getStringExtra("ActualizarTask");
            if (position != -1) {
                arrayList.set(position, actualizarTask);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Tarea actualizada: " + actualizarTask, Toast.LENGTH_LONG).show();
            }
        } else if (data.hasExtra("EliminarTask")) {
            if (position != -1) {
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void setupItemClickLintener(){
        this.listTask.setOnItemClickListener((parent, view, position, id) -> {
            String seleccionarTask = this.arrayList.get(position);
            Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
            intent.putExtra("task",seleccionarTask);
            intent.putExtra("position", position);
            activityResultLauncher.launch(intent);

        });

    }

    private void buscarTask(View view){ //Metodo para buscar las tareas
        String query = this.etTask.getText().toString().trim().toLowerCase();
        if (!query.isEmpty()){
            ArrayList<String> filteredTasks = new ArrayList<>();
            for (String task: this.arrayList) {
                if (task.toLowerCase().contains(query)){
                    filteredTasks.add(task);
                }
            }
            if (!filteredTasks.isEmpty()){
                ArrayAdapter<String>  tempAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredTasks);
                this.listTask.setAdapter(tempAdapter);
                Toast.makeText(this,"Tarea encontrada: "+filteredTasks.size(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"No hay tarea",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "Ingrese su tarea  a buscar",Toast.LENGTH_LONG).show();
        }
    }
    private void addTask(View view){
        String task = this.etTask.getText().toString().trim();
        if(!task.isEmpty()){
            this.arrayList.add(task);
            this.adapter.notifyDataSetChanged();
            this.etTask.setText("");
        }else {
            Toast.makeText(this, "coloque una tarea", Toast.LENGTH_LONG).show();
        }

    }

    private void initObject() {
        this.etTask = findViewById(R.id.etTask);
        this.btnAdd = findViewById(R.id.btnAdd);
        this.listTask = findViewById(R.id.lisTask);
        this.arrayList = new ArrayList<>();
        this.adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,this.arrayList);
        this.listTask.setAdapter(this.adapter);
        this.btnBsq = findViewById(R.id.btnBsq);
        this.btnLmp= findViewById(R.id.btnLmp);
    }



}


















