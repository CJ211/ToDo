package com.example.jay.todo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private DatabaseAccessToDo databaseAccessToDo;
    private List<ToDo> toDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        databaseAccessToDo = DatabaseAccessToDo.getInstance(this);


        listView = (ListView) findViewById(R.id.listView_for_todo);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDo toDo = toDos.get(position);
                TextView txtDescription = (TextView) view.findViewById(R.id.tv_todo_description);
                if (toDo.isFullDisplayed()) {
                    txtDescription.setText(toDo.getShortDescription());
                    toDo.setFullDisplayed(false);
                } else {
                    txtDescription.setText(toDo.getDescription());
                    toDo.setFullDisplayed(true);
                }
            }
        });
    }
    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(ToDoActivity.this, ToDoActivity.class);  //your class
        startActivity(i);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addtodo: {
                Intent intentToDo = new Intent(this, ToDoEditActivity.class);
                Log.e("TDA", "ToDoEdit");
                startActivity(intentToDo);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        databaseAccessToDo.open();
        this.toDos = databaseAccessToDo.getAllToDos();
        databaseAccessToDo.close();
        ToDoAdapter adapter = new ToDoAdapter(this, toDos);
        this.listView.setAdapter(adapter);
    }

    public void onAddTodo(ToDo toDo) {
        DatabaseAccessToDo databaseAccessToDo = DatabaseAccessToDo.getInstance(this);
        databaseAccessToDo.open();
        databaseAccessToDo.saveToDo(toDo);
        databaseAccessToDo.close();
        //this.finish();
        onRestart();
        Log.e("TDA : ","ToDo Saved");

    }

    public void onDeleteToDoClicked(ToDo toDo) {
        databaseAccessToDo.open();
        databaseAccessToDo.deleteToDo(toDo);
        databaseAccessToDo.close();

        ArrayAdapter<ToDo> adapter = (ArrayAdapter<ToDo>) listView.getAdapter();
        adapter.remove(toDo);
        adapter.notifyDataSetChanged();
    }

    public void onEditToDoClicked(ToDo toDo) {
        Intent intent = new Intent(this, ToDoEditActivity.class);
        intent.putExtra("TODO", toDo);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }


    private class ToDoAdapter extends ArrayAdapter<ToDo> {


        public ToDoAdapter(Context context, List<ToDo> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_todo_list_item, parent, false);
            }

            ImageView btnEdit = (ImageView) convertView.findViewById(R.id.todo_btnEdit);
            ImageView btnDelete = (ImageView) convertView.findViewById(R.id.todo_btnDelete);
            TextView textViewHeading = (TextView) convertView.findViewById(R.id.tv_todo_heading);
            TextView textViewTitle = (TextView) convertView.findViewById(R.id.tv_todo_title);
            TextView textViewDescription = (TextView) convertView.findViewById(R.id.tv_todo_description);
            TextView textViewDueDate = (TextView) convertView.findViewById(R.id.tv_todo_duedate);

            final ToDo toDo = toDos.get(position);
            toDo.setFullDisplayed(false);
            textViewHeading.setText(toDo.getDate());
            textViewTitle.setText(toDo.getTitle());
            textViewDescription.setText(toDo.getDescription());
            textViewDueDate.setText(toDo.getToDoDate());
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditToDoClicked(toDo);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteToDoClicked(toDo);
                }
            });
            return convertView;
        }
    }
}

