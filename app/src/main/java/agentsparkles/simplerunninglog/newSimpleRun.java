package agentsparkles.simplerunninglog;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.util.Calendar;

public class newSimpleRun extends AppCompatActivity {

    //Set Date Picker Variables
    Button dateButton;
    DatePickerDialog datePickerDialog;
    Calendar currentDate;
    int day, month, year;

    //Set Number Picker Variables
    Button distanceButton;
    NumberPicker distanceNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_simple_run);

        //DatePicker views
        dateButton = findViewById(R.id.buttonDatePicker);
        currentDate = Calendar.getInstance();
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH);
        year = currentDate.get(Calendar.YEAR);

        //On Button Click, Date Picker
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(newSimpleRun.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String newDate = month + "/" + dayOfMonth + "/" + year;
                        dateButton.setText(newDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }
}
