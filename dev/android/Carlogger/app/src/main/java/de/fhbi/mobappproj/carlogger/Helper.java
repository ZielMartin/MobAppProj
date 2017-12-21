package de.fhbi.mobappproj.carlogger;

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

import de.fhbi.mobappproj.carlogger.activities.MainActivity;

/**
 * Created by Johannes on 21.12.2017.
 */

public class Helper {


    /**
     * change color of button to grey onClick
     * @param button
     */
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xdddddddd, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}
