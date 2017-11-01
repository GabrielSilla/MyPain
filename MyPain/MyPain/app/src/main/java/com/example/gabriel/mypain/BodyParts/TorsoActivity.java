package com.example.gabriel.mypain.BodyParts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.gabriel.mypain.ColorTool;
import com.example.gabriel.mypain.PainRating;
import com.example.gabriel.mypain.R;

public class TorsoActivity extends Activity implements View.OnTouchListener {

    String bodyOrientation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torso);


        Intent thisView = getIntent();
        bodyOrientation = thisView.getStringExtra("bodyOrientation");

        final ImageView iv = (ImageView) findViewById(R.id.image_not_mask_torso);
        final ImageView iv_mask = (ImageView) findViewById(R.id.image_areas_torso);
        if (iv != null){
            iv.setOnTouchListener(this);
        }

        if(bodyOrientation.equals("FRONT")){
            iv.setImageResource(R.drawable.torso_front);
            iv_mask.setImageResource(R.drawable.torso_front_mask);
        }
        else if(bodyOrientation.equals("BACK")){
            iv.setImageResource(R.drawable.torso_back);
            iv_mask.setImageResource(R.drawable.torso_back_mask);
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;

        int image = (bodyOrientation == "FRONT") ? R.drawable.torso_front : R.drawable.torso_back;

        ImageView imageView = (ImageView) findViewById(R.id.image_not_mask_torso);
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
                int touchColor = getHotspotColor(R.id.image_areas_torso, evX, evY);
                ColorTool ct = new ColorTool();
                int tolerance = 25;

                if (ct.closeMatch(Color.RED, touchColor, tolerance)) selectedArea = "Peito direito";
                else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) selectedArea = "Peito esquerdo";
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance)) selectedArea = "Barriga";
                else if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) selectedArea = "Costa direita";
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance)) selectedArea = "Costela direita";
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance)) selectedArea = "Abdômen";
                else if (ct.closeMatch(Color.rgb(192,192,192), touchColor, tolerance)) selectedArea = "Glúteo direito";
                else if (ct.closeMatch(Color.rgb(128,0,0), touchColor, tolerance)) selectedArea = "Costela esquerda";
                else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance)) selectedArea = "Genital";
                else if (ct.closeMatch(Color.rgb(0,0,128), touchColor, tolerance)) selectedArea = "Costa esquerda";
                else if (ct.closeMatch(Color.rgb(128,0,128), touchColor, tolerance)) selectedArea = "Coluna";
                else if (ct.closeMatch(Color.rgb(255,128,0), touchColor, tolerance)) selectedArea = "Glúteo esquerdo";
                else if (ct.closeMatch(Color.rgb(128,128,0), touchColor, tolerance)) selectedArea = "Ânus";

                handledHere = true;
                break;

            default:
                handledHere = false;
        } // end switch

        if (handledHere) {
            if(selectedArea != null){
                Intent intentLocal = new Intent(getBaseContext(), PainRating.class);
                intentLocal.putExtra("injurieLocal", selectedArea);
                startActivity(intentLocal);
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

