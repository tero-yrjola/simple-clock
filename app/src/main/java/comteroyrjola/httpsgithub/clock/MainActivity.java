package comteroyrjola.httpsgithub.clock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Boolean SimpleMode = false;
    Boolean ShowDescriptions = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView date = findViewById(R.id.dateView);
        final TextView time = findViewById(R.id.clockView);
        final TextView dayOrNight = findViewById(R.id.dayOrNight);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String[] data = GetTime();
                                time.setText(data[0]);
                                date.setText(data[1]);
                                dayOrNight.setText(data[2]);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    private String[] GetTime() {
        Calendar calendar = Calendar.getInstance();
        String[] data = new String[3];

        Date rightNow = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        if (SimpleMode) dateFormat = new SimpleDateFormat("HH:mm");
        else dateFormat = new SimpleDateFormat("HH:mm:ss");
        if (ShowDescriptions) data[0] = "KELLO: " + dateFormat.format(rightNow);
        else data[0] = dateFormat.format(rightNow);

        if (SimpleMode) {
            String day = "";
            dateFormat = new SimpleDateFormat("dd");
            if (ShowDescriptions) day = "PVM: " + dateFormat.format(rightNow) + ". ";
            else day = dateFormat.format(rightNow) + ". ";
            data[1] = day;
            int monthIndex = rightNow.getMonth();

            switch(monthIndex){
                case 0: data[1] += "tammikuuta ";
                case 1: data[1] += "helmikuuta ";
                case 2: data[1] += "maaliskuuta ";
                case 3: data[1] += "huhtikuuta ";
                case 4: data[1] += "toukokuuta ";
                case 5: data[1] += "kesäkuuta ";
                case 6: data[1] += "heinäkuuta ";
                case 7: data[1] += "elokuuta ";
                case 8: data[1] += "syyskuuta ";
                case 9: data[1] += "lokakuuta ";
                case 10: data[1] += "marraskuuta ";
                case 11: data[1] += "joulukuuta ";
            }

            data[1] += rightNow.getYear() + 1900;
        }
        else{
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            if (ShowDescriptions) data[1] = "PVM: " + dateFormat.format(rightNow);
            else data[1] = dateFormat.format(rightNow);
            }

        String nightOrDay = "";
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        if (hours < 8) nightOrDay = "YÖ \uD83C\uDF19";
        else if(hours < 11) nightOrDay = "AAMU ⛅";
        else if (hours < 16) nightOrDay = "PÄIVÄ \uD83C\uDF1E";
        else if( hours < 21) nightOrDay = "ILTA ⛅";
        else nightOrDay = "YÖ";

        data[2] = nightOrDay;

        return data;
    }

    public void SimpleModeOnClick(View v){
        CheckBox checkbox = findViewById(R.id.SimpleModeBox);

        SimpleMode = checkbox.isChecked();
    }

    public void  ExplanationBoxOnClick(View v){
        CheckBox checkbox = findViewById(R.id.ExplanationBox);

        ShowDescriptions = checkbox.isChecked();
    }
}
