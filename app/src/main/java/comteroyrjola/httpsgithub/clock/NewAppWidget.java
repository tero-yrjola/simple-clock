package comteroyrjola.httpsgithub.clock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    static Calendar calendar = Calendar.getInstance();

    public static Bitmap BuildUpdate(String text, int size, Context context){
        Paint paint = new Paint();
        paint.setTextSize(size);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setSubpixelText(true);
        paint.setAntiAlias(true);
        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(text)+0.5f);
        int height = (int) (baseline + paint.descent()+0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Date rightNow = calendar.getTime();
        String currentTime = "KELLO: ";
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        currentTime += dateFormat.format(rightNow);

        String currentDate = "PVM: ";
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        currentDate += dateFormat.format(rightNow);

        String nightOrDay = "";
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        if (hours < 8) nightOrDay = "YÖ \uD83C\uDF19";
        else if(hours < 11) nightOrDay = "AAMU ⛅";
        else if (hours < 16) nightOrDay = "PÄIVÄ \uD83C\uDF1E";
        else if( hours < 21) nightOrDay = "ILTA ⛅";
        else nightOrDay = "YÖ";

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setImageViewBitmap(R.id.imgdate, BuildUpdate(currentDate, 50, context));
        views.setImageViewBitmap(R.id.imgtime, BuildUpdate(currentTime + "\n" + nightOrDay, 100, context));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

