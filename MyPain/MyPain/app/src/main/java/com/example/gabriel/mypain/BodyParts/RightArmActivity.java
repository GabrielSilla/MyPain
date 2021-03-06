
package com.example.gabriel.mypain.BodyParts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.gabriel.mypain.ColorTool;
import com.example.gabriel.mypain.PainRating;
import com.example.gabriel.mypain.R;

import java.util.ArrayList;

public class RightArmActivity extends Activity implements View.OnTouchListener {
    String bodyOrientation = null;
    private ArrayList<String> selectedInjuries;
    private String localLastInjurie;

    private String pacientName;
    private String pacientCpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_arm);

        Intent thisView = getIntent();
        bodyOrientation = thisView.getStringExtra("bodyOrientation");
        selectedInjuries = thisView.getStringArrayListExtra("selectedInjuries");
        localLastInjurie = thisView.getStringExtra("localLastInjurie");
        pacientName = thisView.getStringExtra("pacientName");
        pacientCpf = thisView.getStringExtra("pacientCpf");

        final ImageView iv = (ImageView) findViewById(R.id.image_not_mask_right_arm);
        final ImageView iv_mask = (ImageView) findViewById(R.id.image_areas_right_arm);
        if (iv != null){
            iv.setOnTouchListener(this);
        }

        if(bodyOrientation.equals("FRONT")){
            iv.setImageResource(R.drawable.right_arm_front);
            iv_mask.setImageResource(R.drawable.right_arm_front_mask);
        }
        else if(bodyOrientation.equals("BACK")){
            iv.setImageResource(R.drawable.right_arm_back);
            iv_mask.setImageResource(R.drawable.right_arm_back_mask);
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;

        int image = (bodyOrientation == "FRONT") ? R.drawable.right_arm_front : R.drawable.right_arm_back;

        ImageView imageView = (ImageView) findViewById(R.id.image_not_mask_right_arm);
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
                int touchColor = getHotspotColor(R.id.image_areas_right_arm, evX, evY);
                ColorTool ct = new ColorTool();
                int tolerance = 25;

                if (ct.closeMatch(Color.RED, touchColor, tolerance)) selectedArea = "Ombro direito";
                else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) selectedArea = "Antebraço direito";
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance)) selectedArea = "Braço direito";
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance)) selectedArea = "Mão direita";
                else if (ct.closeMatch(Color.rgb(0,255,0), touchColor, tolerance)) selectedArea = "Cotovelo direito";
                else if (ct.closeMatch(Color.rgb(128,0,0), touchColor, tolerance)) selectedArea = "Pulso direito";
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
                intentLocal.putExtra("pacientName", pacientName);
                intentLocal.putExtra("pacientCpf", pacientCpf);
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
