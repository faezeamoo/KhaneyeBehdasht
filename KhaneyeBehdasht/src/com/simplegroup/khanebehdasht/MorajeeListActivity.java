package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.MorajeeItem;
import com.simplegroup.khanebehdasht.models.MorajeeListAdapter;
import com.simplegroup.khanebehdasht.models.MorajeeOperation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MorajeeListActivity extends Activity {

	/*
	 * #########################################################################
	 * ######################### basic methods #################################
	 * #########################################################################
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_list);
		initializeAttributes();
	}

	private void initializeAttributes() {
		morajee = new MorajeeOperation();
		personCode = getIntent().getStringExtra("PersonCode");

		arraylist = morajee.getAllForPersons(personCode);

		adapter = new MorajeeListAdapter(this, R.layout.my_list_item_1,
				arraylist);
		listView = (ListView) findViewById(id.listView_name);
		listView.setAdapter(adapter);

		add = (Button) findViewById(id.button_Add);
		add.setOnClickListener(add_Event);
		
		registerForContextMenu(listView);
	}

	public void refreshListView() {
		arraylist.clear();
		arraylist.addAll(morajee.getAllForPersons(personCode));
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onStart() {
		super.onStart();
		refreshListView();
	}

	/*
	 * #########################################################################
	 * ############################ Events #####################################
	 * #########################################################################
	 */

	private OnClickListener add_Event = new OnClickListener() {

		@Override
		public void onClick(View v) {
			morajee();
		}
	};

	private void morajee() {
		Intent intent = new Intent(this, MorajeeActivity.class);
		intent.putExtra("PersonCode", personCode);

		try {
			startActivity(intent);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	/*
	 * #########################################################################
	 * ############################# Menu ######################################
	 * #########################################################################
	 */

	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v,
			android.view.ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.edit, menu);
		// set listView
		AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuInfo;
		this.item = (MorajeeItem) listView.getItemAtPosition(acmi.position);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_edit) {
			Intent intent = new Intent(this, MorajeeActivity.class);
			
			intent.putExtra("PersonCode", personCode);
			intent.putExtra("MDate", this.item.getDate());
			intent.putExtra("MaladyCode", this.item.getMalady());
			intent.putExtra("MaladyName", this.item.getMaladyName());
			intent.putExtra("Seeing", this.item.getSeeing());
			intent.putExtra("Tajviz", this.item.getTajviz());
			intent.putExtra("Editable", true);
			
			startActivity(intent);
		}
		return super.onContextItemSelected(item);
	}

	/*
	 * #########################################################################
	 * ########################### Attributes ##################################
	 * #########################################################################
	 */
	private MorajeeItem item;
	private String personCode;
	private ArrayList<MorajeeItem> arraylist;
	private MorajeeListAdapter adapter;
	private MorajeeOperation morajee;
	private ListView listView;
	private Button add;
}
