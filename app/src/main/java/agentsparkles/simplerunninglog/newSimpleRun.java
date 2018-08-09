package agentsparkles.simplerunninglog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.String.valueOf;

public class newSimpleRun extends AppCompatActivity {

    //Set Date Picker Variables
    Button dateButton;
    DatePickerDialog datePickerDialog;
    Calendar currentDate;
    int day, month, year;

    //Set Number Picker Variables
    Button distanceButton;
    NumberPicker distanceNumberPicker;
    NumberPicker decimalDistanceNumberPicker;
    NumberPicker miOrKmDistanceNumberPicker;

    //Set Number Picker Variables
    Button elapsedTimeButton;
    NumberPicker hourNumberPicker;
    NumberPicker minuteNumberPicker;
    NumberPicker secondNumberPicker;

    EditText editTitle;

    //Final Results for Database
    String finalDate;
    String finalRunType;
    String finalSurfaceType;
    String finalDistance;
    String finalTime;
    String finalTitle;

    //Save Button;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_simple_run);

        //Calls the method that retrieves the calendar date
        getDate();

        //Set distanceButton
        Button distanceButton = findViewById(R.id.distanceButton);
        //on button click open distance Picker
        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates dialog and allows distance selection
                    showDistancePicker();
                    }

                });

        Button elapsedTimeButton = findViewById(R.id.elapsedTimeButton);
        elapsedTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showElapsedTimePicker();
            }
        });


    editTitle = findViewById(R.id.titleEdit);
    saveButton = findViewById(R.id.saveButton);
    saveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveData(finalTitle, finalDate, finalRunType, finalSurfaceType, finalDistance, finalTime);
        }
    });


}

    private void saveData(String finalTitle, String finalDate, String finalRunType, String finalSurfaceType, String finalDistance, String finalTime) {
        finalTitle = editTitle.getText().toString();
        finalDate = String.valueOf(dateButton.getText());
        finalRunType = getRunType();
        finalSurfaceType = getSurfaceType();

        //figure out value type
        //differentiate distance and the unit
        //finalDistance = valueOf(distanceButton.getText());
        //finalTime = elapsedTimeButton.getText().toString();

        String toastNot = finalTitle + finalDate + finalRunType + finalSurfaceType + String.valueOf(finalDistance + finalTime);
        Toast.makeText(this, toastNot, Toast.LENGTH_LONG).show();
//        Toast.makeText(this, finalRunType, Toast.LENGTH_LONG).show();
//        Toast.makeText(this, finalSurfaceType, Toast.LENGTH_LONG).show();
//        Toast.makeText(this, String.valueOf(finalDistance), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, finalTime, Toast.LENGTH_LONG).show();

    }

    private void getDate() {
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
                datePickerDialog = new DatePickerDialog(newSimpleRun.this, R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
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

    private String getRunType() {
        //Get the runType selected by user
        Spinner runTypeSpinner = findViewById(R.id.runTypeSpinner);
        String runTypeSelected = runTypeSpinner.getSelectedItem().toString();
        return runTypeSelected;
    }
    private String getSurfaceType() {
        //Get the surfaceType selected by user
        Spinner surfaceTypeSpinner = findViewById(R.id.surfaceTypeSpinner);
        String surfaceTypeSelected = surfaceTypeSpinner.getSelectedItem().toString();
        return surfaceTypeSelected;
    }

    private void showDistancePicker() {
        //https://stackoverflow.com/questions/27525915/i-want-to-show-two-number-picker-using-dialog

        final Dialog d = new Dialog(newSimpleRun.this);
        d.setTitle("Total Distance");
        d.setContentView(R.layout.dialog);
        Button b1 = d.findViewById(R.id.dialogOk);
        final Button distanceButton = findViewById(R.id.distanceButton);

        final NumberPicker distanceNumberPicker = d.findViewById(R.id.distanceNumberPicker);
        distanceNumberPicker.setMaxValue(99);
        distanceNumberPicker.setMinValue(0);

        final NumberPicker decimalDistanceNumberPicker = d.findViewById(R.id.decimalDistanceNumberPicker);
        decimalDistanceNumberPicker.setMaxValue(99);
        decimalDistanceNumberPicker.setMinValue(0);

        final NumberPicker miOrKmDistanceNumberPicker = d.findViewById(R.id.miOrKmDistanceNumberPicker);
        miOrKmDistanceNumberPicker.setMaxValue(1);
        miOrKmDistanceNumberPicker.setMinValue(0);
        miOrKmDistanceNumberPicker.setDisplayedValues(new String[] {"MI", "KM"});
        final String[] chooseUnits = new String[]{"MI", "KM"};

        distanceNumberPicker.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int distancePicked = distanceNumberPicker.getValue();
                int decimalPicked = decimalDistanceNumberPicker.getValue();
                int unitPicked = miOrKmDistanceNumberPicker.getValue();
                String totalDistance = valueOf(distancePicked) + "." +
                        valueOf(decimalPicked) + " " + chooseUnits[unitPicked];
                distanceButton.setText(totalDistance);
                d.dismiss();
            }
        });
        d.show();

    }

    private void showElapsedTimePicker() {

        //https://stackoverflow.com/questions/27525915/i-want-to-show-two-number-picker-using-dialog

        final Dialog d = new Dialog(newSimpleRun.this);
        d.setTitle("Total Time");
        d.setContentView(R.layout.dialog);
        Button b1 = d.findViewById(R.id.dialogOk);
        final Button elapsedTimeButton = findViewById(R.id.elapsedTimeButton);

        final NumberPicker hourNumberPicker = d.findViewById(R.id.distanceNumberPicker);
        hourNumberPicker.setMaxValue(99);
        hourNumberPicker.setMinValue(0);

        final NumberPicker minuteNumberPicker = d.findViewById(R.id.decimalDistanceNumberPicker);
        minuteNumberPicker.setMaxValue(59);
        minuteNumberPicker.setMinValue(0);
        minuteNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });


        final NumberPicker secondNumberPicker = d.findViewById(R.id.miOrKmDistanceNumberPicker);
        secondNumberPicker.setMaxValue(59);
        secondNumberPicker.setMinValue(0);
        secondNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });


        minuteNumberPicker.setWrapSelectorWheel(true);
        hourNumberPicker.setWrapSelectorWheel(true);
        secondNumberPicker.setWrapSelectorWheel(true);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int hoursPicked = hourNumberPicker.getValue();
                int minutesPicked = minuteNumberPicker.getValue();
                int secondsPicked = secondNumberPicker.getValue();
                String totalTime = valueOf(hoursPicked) + ":" +
                        String.format("%02d", minutesPicked) + ":" +
                        String.format("%02d", secondsPicked);
                elapsedTimeButton.setText(totalTime);
                d.dismiss();
            }
        });
        d.show();
    }}

