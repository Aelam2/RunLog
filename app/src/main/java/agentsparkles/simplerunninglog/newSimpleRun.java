package agentsparkles.simplerunninglog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.valueOf;

public class newSimpleRun extends AppCompatActivity {

    //Set Date Picker Variables
    Button dateButton;
    DatePickerDialog datePickerDialog;
    Calendar currentDate;
    int day, month, year;

    Button timeButton;
    int hour, minute;
    int hourFinal, minuteFinal;

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
    int finalDistance;
    String finalTime;
    String finalTitle;
    String avgPace;

    //Save Button;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_simple_run);

        final runDatabase db = Room.databaseBuilder(getApplicationContext(), runDatabase.class, "Recent_Runs")
                .allowMainThreadQueries()
                .build();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = df.format(c);

        dateButton = findViewById(R.id.buttonDatePicker);
        dateButton.setText(formattedDate);
        //Calls the method that retrieves the calendar date
        getDate();

        Date t = Calendar.getInstance().getTime();
        SimpleDateFormat adf = new SimpleDateFormat("hh:mm aa");
        String formattedTime = adf.format(t);
        timeButton = findViewById(R.id.timeDatePicker);
        timeButton.setText(formattedTime);
        //Calls time retrieval
        getTime();

        //Set distanceButton
        Button distanceButton = findViewById(R.id.distanceButton);
        //on button click open distance Picker
        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates dialog and allows distance selection
                showDistancePicker();
            }});

        Button elapsedTimeButton = findViewById(R.id.elapsedTimeButton);
        elapsedTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showElapsedTimePicker();
            }});

        editTitle = findViewById(R.id.titleEdit);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveData(finalTitle, finalDate, finalRunType, finalSurfaceType, finalDistance, finalTime, db);
                }
                catch(Exception ParseException){
                    Log.e("Fail", "onClick: ", ParseException);
                }
            }
        });

//End of OnCreate
    }

    private void saveData(String finalTitle, String date, String finalRunType, String finalSurfaceType, double finalDistance, String time, runDatabase db) throws ParseException {

        //Get Title of Run
        finalTitle = editTitle.getText().toString();
        //Gets Run Type from Spinner
        finalRunType = getRunType();
        //Gets Surface Type from spinner
        finalSurfaceType = getSurfaceType();


        //Gets local device time
        //Eventually add option to select time of run
        String chosenTime = timeButton.getText().toString();


        //Gets text from Date button and creates a readable string
        date = String.valueOf(dateButton.getText());
        String year = date.substring(date.lastIndexOf("-") + 1);
        String day = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-") );
        String month = date.substring(0, date.indexOf("-") );
        String convertDate = String.valueOf(year) +"-"+ String.valueOf(month) +"-"+ String.valueOf(day);

        //Adds Local time and date of run
        String currentTotalTime = chosenTime + " "+ convertDate;
        //Converts date and time to milliseconds for database reading
        SimpleDateFormat df = new SimpleDateFormat("HH:mm aa yyyy-MM-dd");
        Date longDate = df.parse(currentTotalTime);
        long millis = longDate.getTime();
        Log.i("time", String.valueOf(millis));

        //Converts and seperates distanceButton text into a double for distance run and string for units
        distanceButton = findViewById(R.id.distanceButton);
        String distanceText = distanceButton.getText().toString();
        String distanceUnit = distanceText.substring(distanceText.lastIndexOf(" "));
        finalDistance = Double.parseDouble(distanceText.substring(0,distanceText.indexOf(" ")));
        Log.i("distance", String.valueOf(finalDistance));
        Log.i("unit", distanceUnit);

        elapsedTimeButton = findViewById(R.id.elapsedTimeButton);
        time = elapsedTimeButton.getText().toString();
        String seconds = time.substring(time.lastIndexOf(":") + 1);
        String minutes = time.substring(time.indexOf(":") + 1, time.lastIndexOf(":"));
        String hours = time.substring(0, time.indexOf(":"));
        int totalTimeInSeconds = Integer.parseInt(seconds) + Integer.parseInt(minutes ) * 60 + Integer.parseInt(hours ) * 3600;
        long totalTimeinMilli = totalTimeInSeconds * 1000;
//        //00:52:32
//        int finalTime = seconds + (minutes * 60) + (hours * 60 * 60);
        Log.i("totalSecondsRun", String.valueOf(totalTimeInSeconds));
        avgPace = getAvgPace( totalTimeinMilli, finalDistance);

        if (!finalTitle.equals("") ) {
            if (finalDistance != 0.00) {
                if (totalTimeInSeconds != 0) {
                    String toastNot = finalTitle + date + finalRunType + finalSurfaceType + String.valueOf(finalDistance + totalTimeInSeconds);
                    Toast.makeText(this, toastNot, Toast.LENGTH_LONG).show();
                    db.runEntryDAO().insertAll(new runEntry(millis, finalTitle, finalRunType, finalSurfaceType, distanceUnit, finalDistance, totalTimeInSeconds, avgPace));
                    startActivity(new Intent(newSimpleRun.this, MainActivity.class));
                }
                else {String missingFields = "Total Distance is blank";
                Toast.makeText(this, missingFields, Toast.LENGTH_LONG).show();}
            }
            else {
                String missingFields = "Total Distance is blank";
                Toast.makeText(this, missingFields, Toast.LENGTH_LONG).show();}
        }
        else {
            String missingFields = "Run Title is blank";
            Toast.makeText(this, missingFields, Toast.LENGTH_LONG).show();}

        }
        //newEntry(String finalTitle, String finalDate, String finalRunType, String finalSurfaceType, String finalDistance, String finalTime);

    private String getAvgPace(long totalTimeInMilli, double totalDistance) {

            double avgPace = totalTimeInMilli / totalDistance;
            long millAvgPace = (long)avgPace;
            Date date = new Date(millAvgPace);
            SimpleDateFormat fast = new SimpleDateFormat("m:ss");
            SimpleDateFormat slow = new SimpleDateFormat("mm:ss");

        Log.i("date", fast.format(date));


            if (600000 > avgPace) {
                return fast.format(date);
            }else{
                return slow.format(date);
            }
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
                datePickerDialog = new DatePickerDialog(newSimpleRun.this, R.style.TimePickerTheme, new     DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        @SuppressLint("DefaultLocale") String newDate = String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth) + "-" + year;
                        dateButton.setText(newDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void getTime(){
        //DatePicker views
        timeButton = findViewById(R.id.timeDatePicker);
        currentDate = Calendar.getInstance();
        hour = currentDate.get(Calendar.HOUR_OF_DAY);
        minute = currentDate.get(Calendar.MINUTE);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(newSimpleRun.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String am_pm = "";

                       Calendar c = Calendar.getInstance();
                       c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                       c.set(Calendar.MINUTE, minute);

                        if (c.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (c.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";
                        String strHrsToShow = (c.get(Calendar.HOUR) == 0) ?"12":c.get(Calendar.HOUR)+"";
                        timeButton.setText(strHrsToShow+":"+String.format("%02d",c.get(Calendar.MINUTE))+" "+am_pm);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }

    private String getRunType() {
        //Get the runType selected by user
        Spinner runTypeSpinner = findViewById(R.id.runTypeSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.runTypes, R.layout.newentryspinner );
//        runTypeSpinner.setAdapter(adapter);
        String runTypeSelected = runTypeSpinner.getSelectedItem().toString();
        return runTypeSelected;
    }
    private String getSurfaceType() {
        //Get the surfaceType selected by user
        Spinner surfaceTypeSpinner = findViewById(R.id.surfaceTypeSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.surfaceTypes, R.layout.newentryspinner );
//        surfaceTypeSpinner.setAdapter(adapter);
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
        distanceNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }});

        final NumberPicker decimalDistanceNumberPicker = d.findViewById(R.id.decimalDistanceNumberPicker);
        decimalDistanceNumberPicker.setMaxValue(99);
        decimalDistanceNumberPicker.setMinValue(0);
        decimalDistanceNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }});

        final NumberPicker miOrKmDistanceNumberPicker = d.findViewById(R.id.miOrKmDistanceNumberPicker);
        miOrKmDistanceNumberPicker.setMaxValue(1);
        miOrKmDistanceNumberPicker.setMinValue(0);
        miOrKmDistanceNumberPicker.setDisplayedValues(new String[] {"mi", "mi"});
        final String[] chooseUnits = new String[]{"mi", "km"};

        distanceNumberPicker.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int distancePicked = distanceNumberPicker.getValue();
                int decimalPicked = decimalDistanceNumberPicker.getValue();
                int unitPicked = miOrKmDistanceNumberPicker.getValue();
                @SuppressLint("DefaultLocale") String totalDistance = valueOf(distancePicked) + "." +
                        String.format("%02d",decimalPicked) + " " + chooseUnits[unitPicked];
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
                    @SuppressLint("DefaultLocale")
                    @Override
                    public String format(int value) {
                        return String.format("%02d", value);
            }
        });


        final NumberPicker secondNumberPicker = d.findViewById(R.id.miOrKmDistanceNumberPicker);
        secondNumberPicker.setMaxValue(59);
        secondNumberPicker.setMinValue(0);
        secondNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
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
                @SuppressLint("DefaultLocale") String totalTime = String.format("%02d", hoursPicked) + ":" +
                        String.format("%02d", minutesPicked) + ":" +
                        String.format("%02d", secondsPicked);
                elapsedTimeButton.setText(totalTime);
                d.dismiss();
            }
        });
        d.show();
    }}

