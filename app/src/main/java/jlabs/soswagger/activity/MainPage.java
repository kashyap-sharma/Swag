package jlabs.soswagger.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jlabs.soswagger.R;
import jlabs.soswagger.Rounded.RoundedImageView;
import jlabs.soswagger.customCompo.Static_Catelog;

public class MainPage extends AppCompatActivity {
    RoundedImageView photo;
    TextView name, occu;
    ImageView nav;
    public static String email;
    public static String firstname;
    public static String lastname;
    public static String occupation;
    public static String pics;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.activity_main_page);
        context=this;
       // photo=(RoundedImageView)findViewById(R.id.photo);
        name=(TextView)findViewById(R.id.user);
        occu=(TextView)findViewById(R.id.occu);
        nav=(ImageView)findViewById(R.id.nav);

        Intent intent = getIntent();
        email = Static_Catelog.getStringProperty(context,"ema");
        firstname = Static_Catelog.getStringProperty(context, "checkin");
        lastname = Static_Catelog.getStringProperty(context, "las");
        pics = Static_Catelog.getStringProperty(context, "pica");
       // occupation = intent.getStringExtra("posi");
        photo=(RoundedImageView)findViewById(R.id.photo);


        Picasso.with(context)
                .load(pics)
                .resize(80, 80)
                .centerCrop()
                .into(photo);

        occu.setText("Swagger");
        name.setText(firstname+" "+lastname);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.nav_drawer);
                TextView userme=(TextView)dialog.findViewById(R.id.userme);
                userme.setText( Static_Catelog.getStringProperty(context, "checkin")+" "+ Static_Catelog.getStringProperty(context, "las"));
                LinearLayout all_cat=(LinearLayout)dialog.findViewById(R.id.all_cat);

                LinearLayout my_brands=(LinearLayout)dialog.findViewById(R.id.my_brands);

                all_cat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =new Intent(context,AllCategories.class);
                        startActivity(i);
                        finish();
                    }
                });
                my_brands.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =new Intent(context,DealPage.class);
                        startActivity(i);
                        finish();
                    }
                });
                Window window = dialog.getWindow();
                window.setGravity(Gravity.LEFT|Gravity.TOP);
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP|Gravity.LEFT;
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
