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
package com.example.android.fragments.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.fragments.Activities.ArtJobView;
import com.example.android.fragments.Loaders.ArtCiclopediaLoader;
import com.example.android.fragments.R;

import java.util.List;

public class ArtListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<String>> {

    int mCurrentPosition = -1;
    private static final int THE_LOADER = 0x01;
    private View myFragmentView;
    private static View view;
    ArrayAdapter<String> adapter;
    public final static String EXTRA_MESSAGE ="com.example.android.fragments.MESSAGE";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {




        getLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.artjobs_view, container, false);

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.


        /*------------------

        ArJobFragment firstFragment = new ArJobFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getActivity().getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, firstFragment).commit();

        */

        //TextView newtext = (TextView) getActivity().findViewById(R.id.detail_stetic);

       // newtext.setText(String.valueOf("aaaaaaaa"));


        return myFragmentView;


    }

    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Toast toast = Toast.makeText(getActivity(), "CLICADO", Toast.LENGTH_LONG);
        toast.show();

    /*    String clickedDetail = (String)l.getItemAtPosition(position);

        if(isSinglePane == true){
    /*
     * The second fragment not yet loaded.
     * Load MyDetailFragment by FragmentTransaction, and pass
     * data from current fragment to second fragment via bundle.
     */
      /*      MyDetailFragment myDetailFragment = new MyDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("KEY_DETAIL", clickedDetail);
            myDetailFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction =
                    getActivity().getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.phone_container, myDetailFragment);
            fragmentTransaction.commit();

        }else{
    /*
     * Activity have two fragments. Pass data between fragments
     * via reference to fragment
     */

      /*      //get reference to MyDetailFragment
            MyDetailFragment myDetailFragment =
                    (MyDetailFragment)getFragmentManager().findFragmentById(R.id.detail_fragment);
            myDetailFragment.updateDetail(clickedDetail);

        }
    }*/

/*}*/

  //  @Override
   /* public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }
    }*/

    public void updateArticleView(String Content) {
        //TextView article = (TextView) getActivity().findViewById(R.id.article);
        //article.setText(Ipsusdm.Articles[position]);
       // mCurrentPosition = position;
        //getLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();
       // if (adapter!= null) adapter.add(Content);

                // no need to call if fragment's onDestroyView()
                //has since been called.

        //getActivity().

    }



    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        ArtCiclopediaLoader loader = new ArtCiclopediaLoader(getActivity());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> list) {

        final ListView listview = (ListView) myFragmentView.findViewById(R.id.listview2);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String content = parent.getItemAtPosition(position).toString();
                if (!content.contains("Catch some")) {
                    int final_pos = content.indexOf(":");
                    int art_num = Integer.valueOf(content.substring(0, final_pos));

                    Intent intent = new Intent(getActivity(), ArtJobView.class);
                    intent.putExtra(EXTRA_MESSAGE, String.valueOf(art_num));
                    startActivity(intent);

    /*
                    String content = parent.getItemAtPosition(position).toString();
                    int final_pos = content.indexOf(":");
                    int art_num = Integer.valueOf(content.substring(0, final_pos));

                    ArJobFragment newFragment = new ArJobFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", art_num);
                    newFragment.setArguments(args);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, newFragment);
                    transaction.addToBackStack(null);

    // Commit the transaction
                    transaction.commit();*/

                }
            }
        });
    }


    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
        final ListView listview = (ListView) myFragmentView.findViewById(R.id.listview2);
        listview.setAdapter(null);
    }
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

}