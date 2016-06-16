package jlabs.soswagger.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import jlabs.soswagger.R;
import jlabs.soswagger.Rounded.RoundedImageView;
import jlabs.soswagger.customCompo.Static_Catelog;

public class AllCat_Child extends AppCompatActivity {
    Context context;
    ImageView nav;
    String pics;
    RoundedImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_all_cat__child);
        nav=(ImageView)findViewById(R.id.nav);
        pics = Static_Catelog.getStringProperty(context, "pica");
        // occupation = intent.getStringExtra("posi");
        photo=(RoundedImageView)findViewById(R.id.photo);


        Picasso.with(context)
                .load(pics)
                .resize(80, 80)
                .centerCrop()
                .into(photo);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.nav_drawer);
                LinearLayout all_cat = (LinearLayout) dialog.findViewById(R.id.all_cat);
                LinearLayout my_brands = (LinearLayout) dialog.findViewById(R.id.my_brands);
                all_cat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, AllCategories.class);
                        startActivity(i);
                    }
                });
                my_brands.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, DealPage.class);
                        startActivity(i);
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
