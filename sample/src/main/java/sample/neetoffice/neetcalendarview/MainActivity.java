package sample.neetoffice.neetcalendarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.neetoffice.library.neetcalendarview.CalendarView;
import com.neetoffice.library.neetcalendarview.SelectedDateListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SelectedDateListener {
    DateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.getCalendarController().setSelectedDateListener(this);
    }

    @Override
    public void onSelectedDate(Date selectedBeginDate) {
        Toast.makeText(this, FORMAT.format(selectedBeginDate), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectedDates(List<Date> selectedDates) {
        for (Date date : selectedDates) {
            Toast.makeText(this, FORMAT.format(date), Toast.LENGTH_SHORT).show();
        }
    }
}
