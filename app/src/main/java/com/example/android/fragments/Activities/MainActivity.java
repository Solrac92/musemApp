/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.example.android.fragments.Fragments.ArtListFragment;
import com.example.android.fragments.Fragments.HeadlinesFragment;
import com.example.android.fragments.Fragments.InstructionsFragment;
import com.example.android.fragments.Others.DialogHelper;
import com.example.android.fragments.R;
import com.example.android.fragments.data.MuseumDbHelper;

import java.util.Random;

public class MainActivity extends ActionBarActivity implements HeadlinesFragment.OnHeadlineSelectedListener {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    boolean tablet = true;
    boolean mobile = false;  //Know it's redundant, but easy to code


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

        if (findViewById(R.id.fragment_container) != null) {

            tablet = false;
            mobile = true;

            if (savedInstanceState != null) { return; } //If restored, do nothing.

            HeadlinesFragment firstFragment = new HeadlinesFragment();

            firstFragment.setArguments(getIntent().getExtras()); //In intent case

            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.fragment_container, firstFragment).commit();

        }
    }

    public void onArticleSelected(int position) {

        if (position == 0) {

            //scanQR(this.getWindow().getDecorView().findViewById(android.R.id.content));
            addRandomArtJob();

        }

        else if (position == 1) { articlopedia(); }

        else if (position == 2) {

            if (tablet) {

               // Intent intent = new Intent(this, instructionsActivity.class);
               // startActivity(intent);

                InstructionsFragment newFragment = new InstructionsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.article_fragment, newFragment);
                transaction.addToBackStack(null); //Can navigate back

                transaction.commit();

            }

            else {

                InstructionsFragment newFragment = new InstructionsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null); //Can navigate back

                transaction.commit();

            }

        }

    }

    public void scanQR(View v) {

        try {

            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException anfe) {

            DialogHelper.showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();

        }

    }

    public void articlopedia() {

        if(tablet) {

            finish();
            startActivity(getIntent());

        } else { //Mobile

            ArtListFragment newFragment = new ArtListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null); //Can navigate back

            transaction.commit();

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                MuseumDbHelper MDBH = new MuseumDbHelper(getApplicationContext());
                String added = MDBH.insertFromQR(contents);

                if (added != null) {

                    if (mobile) {

                        showCatchedDialog(this, getString(R.string.catched_title), getString(R.string.catched_subtitle), getString(R.string.positive), getString(R.string.negative)).show();

                    }
                    else {

                        showTabletCatchedDialog(this, getString(R.string.catched_title), "Greetings!", "OK").show();

                    }
                }

                else {

                    DialogHelper.showNoCatchedDialog(this, getString(R.string.already_catched_title), getString(R.string.already_catched_subtitle), "OK").show();

                }

            }
        }
    }

    //Complementary functions

    private AlertDialog showCatchedDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {

                articlopedia();

            }

        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {}

        });

        return downloadDialog.show();
    }

    private AlertDialog showTabletCatchedDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonOk) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setNegativeButton(buttonOk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                articlopedia();
            }
        });
        return downloadDialog.show();
    }

    public void addRandomArtJob() {

        Random r = new Random();

        String contents = ";00; " + r.nextInt() + " ;0;\n" +
                ";01; Leonardo Da Vinci ;1;\n" +
                ";02; La Giokkarda ;2;\n" +
                ";03; 1992 ;3;\n" +
                ";04; English: \n" +
                "The Mona Lisa (Monna Lisa or La Gioconda in Italian; La Joconde in French) is a half-length portrait of a woman by the Italian artist Leonardo da Vinci, which has been acclaimed as \"the best known, the most visited, the most written about, the most sung about, the most parodied work of art in the world\".\n" +
                "\n" +
                "The painting, thought to be a portrait of Lisa Gherardini, the wife of Francesco del Giocondo, is in oil on a white Lombardy poplar panel, and is believed to have been painted between 1503 and 1506, although Leonardo may have continued working on it as late as 1517. It was acquired by King Francis I of France and is now the property of the French Republic, on permanent display at the Louvre Museum in Paris since 1797.\n" +
                "\n" +
                "Español:\n" +
                "La Gioconda (La Joconde en francés), también conocida como La Mona Lisa, es una obra pictórica del pintor renacentista italiano Leonardo da Vinci. Fue adquirida por el rey Francisco I de Francia a principios del siglo XVI y desde entonces es propiedad del Estado Francés, actualmente se exhibe en el Museo del Louvre de París.\n" +
                "\n" +
                "Su nombre, La Gioconda (la alegre, en castellano), deriva de la tesis más aceptada acerca de la identidad de la modelo: la esposa de Francesco Bartolomeo de Giocondo, que realmente se llamaba Lisa Gherardini, de donde viene su otro nombre: Mona (señora, del italiano antiguo) Lisa.\n" +
                "\n" +
                "Es un óleo sobre tabla de álamo de 77 x 53 cm, pintado entre 1503 y 1519,1 y retocado varias veces por el autor. Se considera el ejemplo más logrado de sfumato, técnica muy característica de Leonardo, si bien actualmente su colorido original es menos perceptible por el oscurecimiento de los barnices. El cuadro está protegido por múltiples sistemas de seguridad y ambientado a temperatura estable para su preservación óptima.2 Es revisado constantemente para verificar y prevenir su deterioro. ;4;";

        MuseumDbHelper MDBH = new MuseumDbHelper(getApplicationContext());

        String added = MDBH.insertFromQR(contents);

        if (added != null) {

            if (mobile) {
                showCatchedDialog(this, getString(R.string.catched_title), getString(R.string.catched_subtitle), getString(R.string.positive), getString(R.string.negative)).show();
            }
            else {

                showTabletCatchedDialog(this, getString(R.string.catched_title), "Greetings!", "OK").show();

            }
        }

        else {

            DialogHelper.showNoCatchedDialog(this, getString(R.string.already_catched_title), getString(R.string.already_catched_subtitle), "OK").show();

        }
    }

}