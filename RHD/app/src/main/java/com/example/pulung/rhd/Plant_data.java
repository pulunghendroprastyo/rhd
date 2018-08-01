package com.example.pulung.rhd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Plant_data extends AppCompatActivity {


    TextView txtMakassar_Name,txtIndonesiaName,txtLatinName,txtCategory,txtSeed,txtCollection,txtYear,txtConservation,txtQuantity,txtArea,txtDescription,txtStory,txtBenefit,txtDescriptor;
    String Makassar_Name,IndonesiaName,LatinName,Category,Seed,Collection,Year,Conservation,Quantity,Area,Description,Story,Benefit,Descriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //Intent
        Makassar_Name   = getIntent().getStringExtra("makassar_name");
        IndonesiaName   = getIntent().getStringExtra("indonesia_name");
        LatinName       = getIntent().getStringExtra("latin_name");
        Category        = getIntent().getStringExtra("category");
        Seed            = getIntent().getStringExtra("seed");
        Collection      = getIntent().getStringExtra("collection_code");
        Year            = getIntent().getStringExtra("year");
        Conservation    = getIntent().getStringExtra("conservation");
        Quantity        = getIntent().getStringExtra("quantity");
        Area            = getIntent().getStringExtra("area");
        Description     = getIntent().getStringExtra("description");
        Story           = getIntent().getStringExtra("story");
        Benefit         = getIntent().getStringExtra("benefit");
        Descriptor      = getIntent().getStringExtra("descriptor");


        txtMakassar_Name   = findViewById(R.id.txtNamaMakassar);
        txtMakassar_Name.setText(Makassar_Name);

        txtIndonesiaName   = findViewById(R.id.txtNamaIndonesia);
        txtIndonesiaName.setText(IndonesiaName);

        txtLatinName       = findViewById(R.id.txtNamaLatin);
        txtLatinName.setText(LatinName);

        txtCategory        = findViewById(R.id.txtKategori);
        txtCategory.setText(Category);

        txtSeed            = findViewById(R.id.txtAsalBenih);
        txtSeed.setText(Seed);

        txtCollection      = findViewById(R.id.txtKodeKoleksi);
        txtCollection.setText(Collection);

        txtYear            = findViewById(R.id.txtTahunTanaman);
        txtYear.setText(Year);

        txtConservation    = findViewById(R.id.txtKonservasi);
        txtConservation.setText(Conservation);

        txtQuantity        = findViewById(R.id.txtJumlah);
        txtQuantity.setText(Quantity+" Batang");

        txtArea            = findViewById(R.id.txtArea);
        txtArea.setText(Area);

        txtDescription     = findViewById(R.id.txtDeskripsi);
        txtDescription.setText(Description);

        txtStory           = findViewById(R.id.txtKisah);
        txtStory.setText(Story);

        txtBenefit         = findViewById(R.id.txtManfaat);
        txtBenefit.setText(Benefit);

        txtDescriptor       = findViewById(R.id.txtPendeskripsi);
        txtDescriptor.setText(Descriptor);





    }
}
