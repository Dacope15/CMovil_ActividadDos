package co.edu.uniminuto.segundaactividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText etTaskN;
    private Button btnUdp;
    private Button btnDlt;

    private int taskPosition;
    private String taskname;

    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_edit_task);

        initObjects();

        this.btnUdp.setOnClickListener(this::actualizarTask);
        this.btnDlt.setOnClickListener(this::eliminarTask);


    }

    private void actualizarTask (View view){
        String actualizarTask = etTaskN.getText().toString().trim();
        if (!actualizarTask.isEmpty()){
            Intent intent = new Intent();
            intent.putExtra("ActualizarTask",actualizarTask);
            intent.putExtra("position",taskPosition);
            setResult(RESULT_OK,intent);
            finish();
        }else {
            Toast.makeText(this, "El nombre no puede estar vacio",Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarTask(View view){
        Intent intent = new Intent();
        intent.putExtra("EliminarTask",true);
        intent.putExtra("position",taskPosition);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void initObjects(){
        etTaskN = findViewById(R.id.etTaskN);
        btnUdp =findViewById(R.id.btnUdp);
        btnDlt =findViewById(R.id.btnDlt);

        Intent intent =  getIntent();
        taskname = intent.getStringExtra("task");
        taskPosition =intent.getIntExtra("position",-1);

        etTaskN.setText(taskname);
    }

}
