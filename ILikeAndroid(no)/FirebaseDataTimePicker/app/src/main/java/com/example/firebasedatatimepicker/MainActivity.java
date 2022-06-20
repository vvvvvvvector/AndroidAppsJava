package com.example.firebasedatatimepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button addNoteToDatabase;

    private DatePickerDialog datePickerDialog;

    private ListView listView;

    private Button signOut;

    private ArrayAdapter<String> adapter;
    private final ArrayList<String> list = new ArrayList<>();

    private final ArrayList<Note> notes = new ArrayList<>();
    private final ArrayList<String> ids = new ArrayList<>();

    private int hour, minute;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore
                .getInstance()
                .collection("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> note = doc.getData();

                                String date = (String) note.get("date");
                                Long hour = (Long) note.get("hour");
                                Long minute = (Long) note.get("minute");
                                String text = (String) note.get("text");

                                notes.add(new Note(text, hour, minute, date));
                                ids.add(doc.getId());

                                String str = "text: " + text + "\ntime: " + hour + ":";

                                if (minute < 10)
                                    str += "0" + minute + "\n";
                                else
                                    str += minute + "\n";

                                str += "date: " + date;

                                list.add(str);

                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        addNoteToDatabase = findViewById(R.id.add_button_id);
        listView = findViewById(R.id.list_view_id);
        signOut = findViewById(R.id.signOut_button_id);

        adapter = new ArrayAdapter<>(this, R.layout.listview_row, list);
        listView.setAdapter(adapter);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Sign out successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
            }
        });

        addNoteToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.dialog_note_add);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                EditText note_text = dialog.findViewById(R.id.edittext_your_text);
                Button select_time = dialog.findViewById(R.id.time_picker);
                Button date_picker = dialog.findViewById(R.id.date_picker);
                Button add_note = dialog.findViewById(R.id.add_note);

                date_picker.setText(getTodayDate());

                select_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                hour = selectedHour;
                                minute = selectedMinute;
                                select_time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                            }
                        };

                        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, onTimeSetListener, hour, minute, true);

                        timePickerDialog.setTitle("Select Time");
                        timePickerDialog.show();
                    }
                });

                date_picker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date = getMonthFormat(month) + " " + day + " " + year;
                                date_picker.setText(date);
                            }
                        };

                        Calendar calendar = Calendar.getInstance();

                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);

                        datePickerDialog.show();
                    }
                });

                dialog.show();

                add_note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = note_text.getText().toString();
                        Long selected_hour = Long.valueOf(hour);
                        Long selected_minute = Long.valueOf(minute);
                        String selected_date = date;

                        Note note = new Note(text, selected_hour, selected_minute, selected_date);

                        DocumentReference ref = FirebaseFirestore.getInstance()
                                .collection("users/"
                                        + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                        + "/notes").document();

                        ids.add(ref.getId());
                        ref.set(note);
                        notes.add(note);

                        String str = "text: " + text + "\ntime: " + selected_hour + ":";

                        if (selected_minute < 10)
                            str += "0" + selected_minute + "\n";
                        else
                            str += selected_minute + "\n";

                        str += "date: " + selected_date;

                        list.add(str);

                        adapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "Note was successfully added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.delete_update_dialog);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                Button delete_button = dialog.findViewById(R.id.delete_button);
                Button update_button = dialog.findViewById(R.id.update_button);

                delete_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore.getInstance()
                                .collection("users/"
                                        + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                        + "/notes")
                                .document(ids.get(index))
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Note was successfully deleted!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Note wasn't deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        list.remove(index);
                        notes.remove(index);
                        ids.remove(index);

                        adapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                update_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog update_text_dialog = new Dialog(MainActivity.this);

                        update_text_dialog.setContentView(R.layout.update_text_dialog);

                        int width = WindowManager.LayoutParams.MATCH_PARENT;
                        int height = WindowManager.LayoutParams.WRAP_CONTENT;
                        update_text_dialog.getWindow().setLayout(width, height);

                        EditText edit_text = update_text_dialog.findViewById(R.id.edit_text_note_text);
                        Button update = update_text_dialog.findViewById(R.id.update_text_note_text);

                        edit_text.setText(notes.get(index).getText());

                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                notes.get(index).setText(edit_text.getText().toString());

                                String str = "text: " + edit_text.getText().toString() + "\ntime: " + notes.get(index).getHour() + ":";

                                if (notes.get(index).getMinute() < 10)
                                    str += "0" + notes.get(index).getMinute() + "\n";
                                else
                                    str += notes.get(index).getMinute() + "\n";

                                str += "date: " + notes.get(index).getDate();

                                Note updated_note = new Note(edit_text.getText().toString(), notes.get(index).getHour(), notes.get(index).getMinute(), notes.get(index).getDate());

                                notes.set(index, updated_note);
                                list.set(index, str);

                                adapter.notifyDataSetChanged();

                                FirebaseFirestore
                                        .getInstance()
                                        .collection("users/"
                                                + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                                + "/notes")
                                        .document(ids.get(index))
                                        .set(updated_note)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this, "Note text was successfully updated!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "Note text wasn't updated!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                update_text_dialog.dismiss();
                                dialog.dismiss();
                            }
                        });

                        update_text_dialog.show();
                    }
                });

                dialog.show();
            }
        });
    }

    private String getMonthFormat(int month) {
        month++;

        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }

        return "JAN";
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return getMonthFormat(month) + " " + day + " " + year;
    }
}