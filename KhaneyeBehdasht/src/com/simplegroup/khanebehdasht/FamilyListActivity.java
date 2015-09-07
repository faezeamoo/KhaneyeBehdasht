package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.FamilyItem;
import com.simplegroup.khanebehdasht.models.FamilyListAdapter;
import com.simplegroup.khanebehdasht.models.FamilyOperation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

public class FamilyListActivity extends Activity{
	
	/*
	 * #########################################################################
	 * ########################## basic methods ################################
	 * #########################################################################
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_list);
		initializeAttributes();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refrashListView();
	}
	
	private void initializeAttributes() {
		family = new FamilyOperation();
		
		list = family.getAvailables();
		
		adapter = new FamilyListAdapter(this, R.layout.my_list_item_2, list);
		
		listView =  (ListView) findViewById(id.listView_name);
		listView.setAdapter(adapter);
		registerForContextMenu(listView);
		
		add = (Button) findViewById(id.button_Add);
		add.setOnClickListener(add_OnClickListener);
	}
	
	private void addFamily() {
		Intent intent =  new Intent(this, FamilyActivity.class);
		
		startActivity(intent);
	}
	
	private void refrashListView() {
		list.clear();
		list.addAll(family.getAvailables());
		adapter.notifyDataSetChanged();
	}
	
	/*
	 * #########################################################################
	 * ############################## Events ###################################
	 * #########################################################################
	 */
	
	private OnClickListener add_OnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			addFamily();
		}
	};
	
	/*
	 * #########################################################################
	 * ############################ menu methods ###############################
	 * #########################################################################
	 */
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.modify, menu);
		
		AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuInfo;
		this.selectedItem = (FamilyItem) listView.getItemAtPosition(acmi.position);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_edit) {
			editAction();
		} else if (id == R.id.action_delete) {
			deleteAction();
		}
		
		return super.onContextItemSelected(item);
	}
	
	public void editAction() {
		Intent intent = new Intent(this, FamilyActivity.class);
		
		intent.putExtra("EditAction", true);
		intent.putExtra("Code", selectedItem.getCode());
		intent.putExtra("FatherCode", selectedItem.getFahterCode());
		intent.putExtra("KhaneBehdashtCode", selectedItem.getKhaneBehdashtCode());
		intent.putExtra("KhaneBehdashtName", selectedItem.getKhaneBehdashtName());
		
		startActivity(intent);
	}
	
	public void deleteAction() {
		family.changeToNotAvailable(selectedItem);
	}
	
	/*
	 * #########################################################################
	 * ############################ Attributes #################################
	 * #########################################################################
	 */
	private ArrayList<FamilyItem> list;
	private FamilyListAdapter adapter;
	private ListView listView;
	private FamilyItem selectedItem;
	private Button add;
	private FamilyOperation family;
	
}
