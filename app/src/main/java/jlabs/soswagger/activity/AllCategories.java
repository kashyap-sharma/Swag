package jlabs.soswagger.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jlabs.soswagger.R;
import jlabs.soswagger.Rounded.RoundedImageView;
import jlabs.soswagger.customCompo.Static_Catelog;

public class AllCategories extends AppCompatActivity {
    RelativeLayout bt1_pan,bt1_pan1,bt1_pan2;
    Context context;
    ImageView nav;
    String pics;
    RoundedImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_all_categories);
        bt1_pan=(RelativeLayout)findViewById(R.id.bt1_pan);
        bt1_pan2=(RelativeLayout)findViewById(R.id.bt1_pan2);
        bt1_pan1=(RelativeLayout)findViewById(R.id.bt1_pan1);
        pics = Static_Catelog.getStringProperty(context, "pica");
        // occupation = intent.getStringExtra("posi");
        photo=(RoundedImageView)findViewById(R.id.photo);


        Picasso.with(context)
                .load(pics)
                .resize(80, 80)
                .centerCrop()
                .into(photo);
        bt1_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context, AllCat_Child.class);
                startActivity(i);
                finish();
            }
        });

        nav=(ImageView)findViewById(R.id.nav);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.nav_drawer);
                TextView userme=(TextView)dialog.findViewById(R.id.userme);
                userme.setText(Static_Catelog.getStringProperty(context, "checkin") + " " + Static_Catelog.getStringProperty(context, "las"));
                LinearLayout all_cat = (LinearLayout) dialog.findViewById(R.id.all_cat);
                LinearLayout my_brands = (LinearLayout) dialog.findViewById(R.id.my_brands);
                all_cat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, AllCategories.class);
                        startActivity(i);
                        finish();
                    }
                });
                my_brands.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, DealPage.class);
                        startActivity(i);
                        finish();
                    }
                });
                Window window = dialog.getWindow();
                window.setGravity(Gravity.LEFT | Gravity.TOP);
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP | Gravity.LEFT;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                // set the custom dialog components - text, image and button
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialog.show();

            }
        });
    }
}
