/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.org.icsfoodapp.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.loader.HttpAsyncTaskLoader;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.icsfoodapp.MyApp;
import com.org.icsfoodapp.OrderDetailedActivity;
import com.org.icsfoodapp.R;
import com.org.icsfoodapp.model.OrderListResponse;
import com.org.icsfoodapp.model.OrderListResponse.OrderListData;
import com.org.icsfoodapp.model.Response;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.support.v4.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Contacts.People;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Demonstration of the use of a CursorLoader to load and display contacts
 * data in a fragment.
 */
@SuppressWarnings("all")

public class OrderListFragment extends Fragment {

    @Override
	public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
    	BitmapManager.init(getActivity());
        ObjectXListView listView = new ObjectXListView(getActivity());
        final String url = getArguments().getString(String.class.getName(), MyApp.ApiUrl + "Orders/orderList");
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshWhitoutViewEnable(true);
		listView.setPullLoadWhitoutViewEnable(true);
        listView.setAdapter(new ObjectXAdapter.PagesAdapter<OrderListData>() {

			@Override
			public View bindView(OrderListData data, int position, View convertView) {
				View view = inflater.inflate(R.layout.order_list_item, null, false);
				BitmapManager.bindView((ImageView)view.findViewById(R.id.order_list_image), data.getImage());
				((TextView)view.findViewById(R.id.order_list_title)).setText(data.getOrder_title());
				((TextView)view.findViewById(R.id.order_list_number)).setText(data.getNum());
				((TextView)view.findViewById(R.id.order_list_total)).setText(data.getTotal());
				((TextView)view.findViewById(R.id.order_list_status)).setText(
						data.getUse()==1?getString(R.string.available):
							data.getUse()==2?getString(R.string.unavailable):
								data.getUse()==3?getString(R.string.not_pay):"");
				return view;
			}

			@Override
			public String getUrl(int page) {
				return url+"?p="+page+"&lang=" + MyApp.getLang();
			}

			@Override
			public List<OrderListData> instanceNewList(String json) throws Exception {
				String dataString = json;
				Gson gson = new Gson();
				OrderListResponse response = gson.fromJson(json, new TypeToken<OrderListResponse>(){}.getType());
				if (response.isOk()) {
					return response.getData();
				}
				return null;
			}

			@Override
			public void onItemClick(OrderListData data, View view, int position, long id) {
				super.onItemClick(data, view, position, id);
				OrderDetailedActivity.start(getActivity(), data);
			}
		});
        return listView;
    }
}


