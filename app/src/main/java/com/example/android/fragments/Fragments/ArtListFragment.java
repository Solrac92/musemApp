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

    private static final int THE_LOADER = 0x01;
    private View myFragmentView;
    public ArrayAdapter<String> adapter;
    public final static String EXTRA_MESSAGE ="com.example.android.fragments.MESSAGE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        getLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();

        myFragmentView = inflater.inflate(R.layout.artjobs_view, container, false);

        return myFragmentView;

    }

    public void updateAdapter() {

        adapter.notifyDataSetChanged();

    }


    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        ArtCiclopediaLoader loader = new ArtCiclopediaLoader(getActivity());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> list) {

        ListView listview = (ListView) myFragmentView.findViewById(R.id.listview2);
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
                }
            }
        });
    }


    @Override
    public void onLoaderReset(Loader<List<String>> loader) {
        ListView listview = (ListView) myFragmentView.findViewById(R.id.listview2);
        listview.setAdapter(null);
    }

}