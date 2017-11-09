package com.example.gabriel.mypain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gabriel.mypain.BodyParts.HeadActivity;
import com.example.gabriel.mypain.BodyParts.LeftArmActivity;
import com.example.gabriel.mypain.BodyParts.LeftLegActivity;
import com.example.gabriel.mypain.BodyParts.RightArmActivity;
import com.example.gabriel.mypain.BodyParts.RightLegActivity;
import com.example.gabriel.mypain.BodyParts.TorsoActivity;

import java.util.ArrayList;

public class ImageAreas extends Activity implements View.OnTouchListener {

    String bodyOrientation = "FRONT";

    private ArrayList<String> selectedInjuries;
    private String localLastInjurie;
    private String pacientName;
    private String pacientCpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_areas);

        Intent lastIntent = getIntent();
        selectedInjuries = lastIntent.getStringArrayListExtra("selectedInjuries");
        localLastInjurie = lastIntent.getStringExtra("localLastInjurie");
        pacientName = lastIntent.getStringExtra("pacientName");
        pacientCpf = lastIntent.getStringExtra("pacientCpf");


        final ImageView iv = (ImageView) findViewById(R.id.image_not_mask);
        final ImageView iv_mask = (ImageView) findViewById(R.id.image_areas);
        if (iv != null){
            iv.setOnTouchListener(this);
        }

        FloatingActionButton changeRotation = (FloatingActionButton) findViewById(R.id.change_rotation);
        changeRotation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(bodyOrientation.equals("FRONT")){
                    iv.setImageResource(R.drawable.body_back);
                    iv_mask.setImageResource(R.drawable.body_mask_back);
                    bodyOrientation = "BACK";
                }
                else if(bodyOrientation.equals("BACK")){
                    iv.setImageResource(R.drawable.body);
                    iv_mask.setImageResource(R.drawable.body_mask);
                    bodyOrientation = "FRONT";
                }
            }
        });
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;

        int image = (bodyOrientation == "FRONT") ? R.drawable.body : R.drawable.body_back;

        ImageView imageView = (ImageView) findViewById(R.id.image_not_mask);
        if (imageView == null) return false;

        Integer tagNum = (Integer) imageView.getTag();

        int currentResource = (tagNum == null) ? image : tagNum.intValue();

        Intent intent = null;

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
                int touchColor = getHotspotColor(R.id.image_areas, evX, evY);
                ColorTool ct = new ColorTool();
                int tolerance = 25;
                nextImage = image;

                String text = null;

                if (ct.closeMatch(Color.RED, touchColor, tolerance)){
                    Intent headIntent = new Intent(getBaseContext(), HeadActivity.class);
                    headIntent.putExtra("bodyOrientation", bodyOrientation);
                    headIntent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                    headIntent.putExtra("localLastInjurie", localLastInjurie);
                    headIntent.putExtra("pacientName", pacientName);
                    headIntent.putExtra("pacientCpf", pacientCpf);
                    startActivity(headIntent);
                }
                else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
                    Intent torsoIntent = new Intent(getBaseContext(), TorsoActivity.class);
                    torsoIntent.putExtra("bodyOrientation", bodyOrientation);
                    torsoIntent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                    torsoIntent.putExtra("localLastInjurie", localLastInjurie);
                    torsoIntent.putExtra("pacientName", pacientName);
                    torsoIntent.putExtra("pacientCpf", pacientCpf);
                    startActivity(torsoIntent);
                }
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance)){
                    Intent rightlegIntent = new Intent(getBaseContext(), RightLegActivity.class);
                    rightlegIntent.putExtra("bodyOrientation", bodyOrientation);
                    rightlegIntent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                    rightlegIntent.putExtra("localLastInjurie", localLastInjurie);
                    rightlegIntent.putExtra("pacientName", pacientName);
                    rightlegIntent.putExtra("pacientCpf", pacientCpf);
                    startActivity(rightlegIntent);
                }
                else if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) {
                    Intent rightarmIntent = new Intent(getBaseContext(), RightArmActivity.class);
                    rightarmIntent.putExtra("bodyOrientation", bodyOrientation);
                    rightarmIntent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                    rightarmIntent.putExtra("localLastInjurie", localLastInjurie);
                    rightarmIntent.putExtra("pacientName", pacientName);
                    rightarmIntent.putExtra("pacientCpf", pacientCpf);
                    startActivity(rightarmIntent);
                }
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance)) {
                    Intent leftarmIntent = new Intent(getBaseContext(), LeftArmActivity.class);
                    leftarmIntent.putExtra("bodyOrientation", bodyOrientation);
                    leftarmIntent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                    leftarmIntent.putExtra("localLastInjurie", localLastInjurie);
                    leftarmIntent.putExtra("pacientName", pacientName);
                    leftarmIntent.putExtra("pacientCpf", pacientCpf);
                    startActivity(leftarmIntent);
                }
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance)){
                    Intent leftlegIntent = new Intent(getBaseContext(), LeftLegActivity.class);
                    leftlegIntent.putExtra("bodyOrientation", bodyOrientation);
                    leftlegIntent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                    leftlegIntent.putExtra("localLastInjurie", localLastInjurie);
                    leftlegIntent.putExtra("pacientName", pacientName);
                    leftlegIntent.putExtra("pacientCpf", pacientCpf);
                    startActivity(leftlegIntent);
                }
                handledHere = true;
                break;


            default:
                handledHere = false;
        } // end switch
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
