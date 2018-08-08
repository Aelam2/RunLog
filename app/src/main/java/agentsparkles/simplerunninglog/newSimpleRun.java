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
    NumberPicker decimalDistanceNumberPicker;
    NumberPicker miOrKmDistanceNumberPicker;
    
    //Set Spinner Variables
    Spinner runTypeSpinner;
    Spinner surfaceTypeSpinner;

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
        
        //https://stackoverflow.com/questions/27525915/i-want-to-show-two-number-picker-using-dialog
        RelativeLayout relative = new RelativeLayout(mContext);
        final NumberPicker distanceNumberPicker = new NumberPicker(mContext);
        distanceNumberPicker.setMaxValue(99);
        distanceNumberPicker.setMinValue(1);
        
        final NumberPicker decimalDistanceNumberPicker = new NumberPicker(mContext);
        decimalDistanceNumberPicker.setMaxValue(99);
        decimalDistanceNumberPicker.setMinValue(0);
        
        final NumberPicker miOrKmDistanceNumberPicker = new NumberPicker(mContext);
        miOrKmDistanceNumberPicker.setMaxValue(99);
        miOrKmDistanceNumberPicker.setMinValue(0);
        miOrKmDistanceNumberPicker.setDisplayedValues(new String[] {"MI", "KM"});
        
        //Start of tutorial
        //https://stackoverflow.com/questions/17944061/how-to-use-number-picker-with-dialog
        //Set distanceButton
        Button distanceButton = findViewById(R.id.distanceButton)
        //on button click open distance Picker
        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDistancePicker()
                    }
                }
                
        public void showDistancePicker(){

         final Dialog d = new Dialog(MainActivity.this);
         d.setTitle("NumberPicker");
         d.setContentView(R.layout.dialog);
         Button b1 = (Button) d.findViewById(R.id.button1);
         Button b2 = (Button) d.findViewById(R.id.button2);
         final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
         np.setMaxValue(100); // max value 100
         np.setMinValue(0);   // min value 0
         np.setWrapSelectorWheel(false);
         np.setOnValueChangedListener(this);
         b1.setOnClickListener(new OnClickListener()
         {
          @Override
          public void onClick(View v) {
              tv.setText(String.valueOf(np.getValue())); //set the value to textview
              d.dismiss();
           }    
          });
         b2.setOnClickListener(new OnClickListener()
         {
          @Override
          public void onClick(View v) {
              d.dismiss(); // dismiss the dialog
           }    
          });
       d.show();
        //End of tutorial

    }
}
                
        
        //Get the runType selected by user
        Spinner runTypeSpinner = findViewById(R.id.runTypeSpinner);
        String runTypeSelected = runTypeSpinner.getSelectedItem().toString();
        
        //Get the surfaceType selected by user
        Spinner surfaceTypeSpinner = findViewById(R.id.surfaceTypeSpinner);
        String surfaceTypeSelected = surfaceTypeSpinner.getSelectedItem().toString();
    }
}
