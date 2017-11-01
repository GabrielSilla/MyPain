
package com.example.gabriel.mypain.BodyParts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.gabriel.mypain.ColorTool;
import com.example.gabriel.mypain.PainRating;
import com.example.gabriel.mypain.R;

import java.util.ArrayList;

public class HeadActivity extends Activity implements View.OnTouchListener {
    String bodyOrientation = null;
    private ArrayList<String> selectedInjuries;
    private String localLastInjurie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);

        Intent thisView = getIntent();
        bodyOrientation = thisView.getStringExtra("bodyOrientation");
        selectedInjuries = thisView.getStringArrayListExtra("selectedInjuries");
        localLastInjurie = thisView.getStringExtra("localLastInjurie");

        final ImageView iv = (ImageView) findViewById(R.id.image_not_mask_head);
        final ImageView iv_mask = (ImageView) findViewById(R.id.image_areas_head);
        if (iv != null){
            iv.setOnTouchListener(this);
        }

        if(bodyOrientation.equals("FRONT")){
            iv.setImageResource(R.drawable.head_front);
            iv_mask.setImageResource(R.drawable.head_front_mask);
        }
        else if(bodyOrientation.equals("BACK")){
            iv.setImageResource(R.drawable.head_back);
            iv_mask.setImageResource(R.drawable.head_back_mask);
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;

        int image = (bodyOrientation == "FRONT") ? R.drawable.head_front : R.drawable.head_back;

        ImageView imageView = (ImageView) findViewById(R.id.image_not_mask_head);
        if (imageView == null) return false;

        Integer tagNum = (Integer) imageView.getTag();

        int currentResource = (tagNum == null) ? image : tagNum.intValue();

        String selectedArea = null;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (currentResource == image) {
                    //Toast.makeText(v.getContext(), "Clique no corpo", Toast.LENGTH_SHORT).show();
                    handledHere = true;
                } else {
                    handledHere = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                int touchColor = getHotspotColor(R.id.image_areas_head, evX, evY);
                ColorTool ct = new ColorTool();
                int tolerance = 25;

                if (ct.closeMatch(Color.RED, touchColor, tolerance)) selectedArea = "Olho esquerdo";
                else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) selectedArea = "Olho direito";
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance)) selectedArea = "Orelha esquerda";
                else if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) selectedArea = "Boca";
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance)) selectedArea = "Nariz";
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance)) selectedArea = "Testa";
                else if (ct.closeMatch(Color.rgb(192,192,192), touchColor, tolerance)) selectedArea = "Pescoço";
                else if (ct.closeMatch(Color.rgb(128,0,0), touchColor, tolerance)) selectedArea = "Orelha direita";
                else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance)) selectedArea = "Nuca";

                handledHere = true;
                break;

            default:
                handledHere = false;
        } // end switch

        if (handledHere) {
            if(selectedArea != null){
                Intent intentLocal = new Intent(getBaseContext(), PainRating.class);
                intentLocal.putExtra("injurieLocal", selectedArea);
                intentLocal.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                intentLocal.putExtra("localLastInjurie", localLastInjurie);
                startActivity(intentLocal);
                finish();
            }
        }
        return handledHere;
    }

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById (hotspotId);
        if (img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }
}
