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

import com.example.android.fragments.Fragments.ArtListFragment;
import com.example.android.fragments.Fragments.HeadlinesFragment;
import com.example.android.fragments.R;
import com.example.android.fragments.data.MuseumDbHelper;

public class MainActivity extends ActionBarActivity
        implements HeadlinesFragment.OnHeadlineSelectedListener {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    boolean tablet = true;
    String MainC;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);


        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            tablet = false;

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();




       /* MuseumDbHelper MDBH = new MuseumDbHelper(getApplicationContext());

        MDBH.insert("1","CARLOS","PICASA","10/10/10","Descripcion");
        MDBH.insert("2","eCARLOS","ePICASA","310/10/10","Descripcion2");
        Cursor c = MDBH.read("1");
        Cursor c2 = MDBH.read("2");

        c.moveToFirst();
        c2.moveToFirst();

        Toast toast = Toast.makeText(getApplicationContext(), c.getString(MuseumContract.MuseumInfo.DESCRIPTION), Toast.LENGTH_SHORT);
        toast.show();
        Toast toast2 = Toast.makeText(getApplicationContext(), c2.getString(MuseumContract.MuseumInfo.DESCRIPTION), Toast.LENGTH_LONG);
        toast2.show();*/

        }
    }

    public void articlopedia() {
        // The user selected the headline of an article from the HeadlinesFragment


    // Capture the article fragment from the activity layout
    ArtListFragment articleFrag = (ArtListFragment)
            getSupportFragmentManager().findFragmentById(R.id.article_fragment);

    if(articleFrag!=null) {

        ///articleFrag.updateArticleView(MainC);

        finish();
        startActivity(getIntent());

    }else

    { // MOVRIS
        // If the frag is not available, we're in the one-pane layout and must swap frags...

        // Create fragment and give it an argument for the selected article
        ArtListFragment newFragment = new ArtListFragment();
        //Bundle args = new Bundle();
        //args.putInt(ArtListFragment.ARG_POSITION, 1);
        //newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);


        transaction.commit();

    }

}


    public void onArticleSelected(int position) {

        if (position == 0) {

            scanQR(this.getWindow().getDecorView().findViewById(android.R.id.content));

            /*String contents = ";00; 15 ;0;\n" +
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

                if (!tablet) {
                    showCatchedDialog(this, getString(R.string.catched_title), getString(R.string.catched_subtitle), getString(R.string.positive), getString(R.string.negative), added).show();
                }
                else {

                    showTabletCatchedDialog(this, getString(R.string.catched_title), "Greetings!", "OK").show();


                }
            }

            else {

                showNoCatchedDialog(this, getString(R.string.already_catched_title), getString(R.string.already_catched_subtitle), "OK").show();

            }/*

/*

            //MuseumDbHelper d = new MuseumDbHelper(getApplicationContext());
            //d.addArtJob(1, "carlos", "mona lista", "10/10/10", "desc");*/
        }

        else if (position == 1) {

            articlopedia();

            }

           // MuseumDbHelper d = new MuseumDbHelper(getApplicationContext());
            //d.findArtJob(1);


            /*Intent intent = new Intent(this, ArtListCiclopedia.class);
            startActivity(intent);*/

        else if (position == 2) {


            Intent intent = new Intent(this, instructionsActivity.class);
            startActivity(intent);

        }
        else {

        }
    }


    //QR!


    public void scanBar(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    private AlertDialog showCatchedDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo, final String artNum) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                articlopedia();
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    private AlertDialog showTabletCatchedDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

                articlopedia();
            }
        });
        return downloadDialog.show();
    }

    private static AlertDialog showNoCatchedDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public String getThreeMains (String text) {

        int iniPos;
        int lastPost;

        //Get ArtJobNumber
        iniPos = text.indexOf(";00; ");
        lastPost = text.indexOf(" ;0;");
        int art_num = Integer.valueOf(text.substring(iniPos + 5, lastPost));


        //Get Author Name

        iniPos = text.indexOf(";01; ");
        lastPost = text.indexOf(" ;1;");
        String aut_name = text.substring(iniPos + 5, lastPost);


        //Get Art Job Name
        iniPos = text.indexOf(";02; ");
        lastPost = text.indexOf(" ;2;");
        String art_name = text.substring(iniPos + 5, lastPost);

        return art_num +": " + aut_name + " (" + art_name +")";

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                MainC = getThreeMains(contents);
                MuseumDbHelper MDBH = new MuseumDbHelper(getApplicationContext());

                String added = MDBH.insertFromQR(contents);

                if (added != null) {

                    if (!tablet) {
                        showCatchedDialog(this, getString(R.string.catched_title), getString(R.string.catched_subtitle), getString(R.string.positive), getString(R.string.negative), added).show();
                    }
                    else {

                        showTabletCatchedDialog(this, getString(R.string.catched_title), "Greetings!", "OK").show();


                    }
                }

                else {

                    showNoCatchedDialog(this, getString(R.string.already_catched_title), getString(R.string.already_catched_subtitle), "OK").show();

                }

            }
        }
    }


}