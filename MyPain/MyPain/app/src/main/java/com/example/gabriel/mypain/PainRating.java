package com.example.gabriel.mypain;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PainRating extends Activity implements View.OnTouchListener {

    String painLocation = null;
    private ArrayList<String> selectedInjuries;
    private String localLastInjurie;
    private AlertDialog alerta;

    private String pacientName;
    private String pacientCpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_rating);

        Intent thisView = getIntent();
        painLocation = thisView.getStringExtra("injurieLocal");
        selectedInjuries = thisView.getStringArrayListExtra("selectedInjuries");
        localLastInjurie = thisView.getStringExtra("localLastInjurie");
        pacientName = thisView.getStringExtra("pacientName");
        pacientCpf = thisView.getStringExtra("pacientCpf");

        final ImageView iv = (ImageView) findViewById(R.id.image_not_mask_pain_rating);
        if (iv != null){
            iv.setOnTouchListener(this);
        }

        TextView injurieLocation = (TextView) findViewById(R.id.injurie_local);
        injurieLocation.setText(painLocation);
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;

        int image = R.drawable.pain_rating;

        ImageView imageView = (ImageView) findViewById(R.id.image_not_mask_pain_rating);
        if (imageView == null) return false;

        Integer tagNum = (Integer) imageView.getTag();

        int currentResource = (tagNum == null) ? image : tagNum.intValue();

        String painLevel = null;



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
                int touchColor = getHotspotColor(R.id.image_areas_pain_rating, evX, evY);
                ColorTool ct = new ColorTool();
                int tolerance = 25;

                if (ct.closeMatch(Color.GRAY, touchColor, tolerance)) painLevel = "1";
                else if (ct.closeMatch(Color.rgb(192,192,192), touchColor, tolerance)) painLevel = "2";
                else if (ct.closeMatch(Color.RED, touchColor, tolerance)) painLevel = "3";
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance)) painLevel = "4";
                else if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) painLevel = "5";
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance)) painLevel = "6";
                else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) painLevel = "7";
                else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance)) painLevel = "8";
                else if (ct.closeMatch(Color.rgb(128,0,0), touchColor, tolerance)) painLevel = "9";
                else if (ct.closeMatch(Color.rgb(128,0,128), touchColor, tolerance)) painLevel = "10";
                final String finalPainLevel = painLevel;

                if(painLevel != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirmar Sintoma?");
                    builder.setMessage("\n Sintoma: " + localLastInjurie + " \n Local: " + painLocation + " \n Intensidade: " + finalPainLevel);
                    builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alerta.cancel();
                        }
                    });
                    builder.setPositiveButton("Adicionar outro sintoma", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), DiagFinal.class);
                            selectedInjuries.add(localLastInjurie + " no(a) " + painLocation + " com intensidade: " + finalPainLevel);
                            intent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                            intent.putExtra("pacientName", pacientName);
                            intent.putExtra("pacientCpf", pacientCpf);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Finalizar e exibir relatorio", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), InjurieLocationList.class);
                            selectedInjuries.add(localLastInjurie + " no(a) " + painLocation + " com intensidade: " + finalPainLevel);
                            intent.putStringArrayListExtra("selectedInjuries", selectedInjuries);
                            intent.putExtra("pacientName", pacientName);
                            intent.putExtra("pacientCpf", pacientCpf);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });

                    alerta = builder.create();
                    alerta.show();
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