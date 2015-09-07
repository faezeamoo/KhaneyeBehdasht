package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.BimeItem;
import com.simplegroup.khanebehdasht.models.BimeOperation;
import com.simplegroup.khanebehdasht.models.BimeListAdapter;

public class BimeActivity extends Activity {
	/*
	 * #########################################################################
	 * ############################# basic methods #############################
	 * #########################################################################
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_list);

		initilizeAttributes();
	}

	private void initilizeAttributes() {
		dialog_InputName = new Dialog(this);
		dialog_InputName.setCancelable(true);
		dialog_InputName.setCanceledOnTouchOutside(true);
		dialog_InputName.setContentView(R.layout.dialog_input_name);
		((Button) dialog_InputName.findViewById(id.button_Ok))
				.setOnClickListener(dialog_InputName_Button_Ok_OnClickListener);

		button_Add = (Button) findViewById(id.button_Add);
		button_Add.setOnClickListener(button_Add_OnClickListener);

		bime = new BimeOperation();

		myArrayList = bime.getAvailables();

		myArrayAdapter = new BimeListAdapter(this,
				R.layout.my_list_item_1, myArrayList);

		listView_Name = (ListView) findViewById(id.listView_name);
		listView_Name.setAdapter(myArrayAdapter);
		registerForContextMenu(listView_Name);

		Log.d("MAKH", "initialize complete");
	}

	public void refreshListView() {
		myArrayList.clear();
		myArrayList.addAll(bime.getAvailables());
		myArrayAdapter.notifyDataSetChanged();
	}

	/*
	 * #########################################################################
	 * ############################### events ##################################
	 * #########################################################################
	 */

	private OnClickListener button_Add_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			((EditText) dialog_InputName.findViewById(R.id.editText_Name))
			.setText("");
			dialog_InputName.show();
		}
	};

	private OnClickListener dialog_InputName_Button_Ok_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isEditAction == false) {
				BimeItem newRow = new BimeItem();
				newRow.setName(((EditText) dialog_InputName
						.findViewById(id.editText_Name)).getText().toString());
				newRow.setIsAvailable(true);
				bime.insert(newRow);
				refreshListView();
				dialog_InputName.cancel();
			} else {
				isEditAction = false;
				item.setName(((EditText) dialog_InputName
						.findViewById(id.editText_Name)).getText().toString());
				
				bime.saveChanges(item);
				refreshListView();
				dialog_InputName.cancel();
			}
		}
	};

	/*
	 * #########################################################################
	 * ######################### basic menu functions ##########################
	 * #########################################################################
	 */

	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v,
			android.view.ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.modify, menu);
		// set listView
		AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuInfo;
		this.item = (BimeItem) listView_Name
				.getItemAtPosition(acmi.position);
		Log.d("MAKH", "onCreateContextMenu:" + item.getIsAvailableAsString());
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_delete) {
			showDeleteAlertDialog();
		} else if (itemId == R.id.action_edit) {
			((EditText) dialog_InputName.findViewById(R.id.editText_Name))
					.setText(this.item.getName());
			this.isEditAction = true;
			dialog_InputName.show();
		}
		return super.onContextItemSelected(item);
	}

	public void showDeleteAlertDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.app_name).setMessage(R.string.message_delete)
				.setCancelable(false);

		builder.setNegativeButton(R.string.button_no,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// do nothing.
					}
				});

		builder.setPositiveButton(R.string.button_yes,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						bime.changeToNotAvailable(item);
						refreshListView();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	private BimeItem item;
	private Dialog dialog_InputName;
	private Button button_Add;
	private BimeOperation bime;
	private ListView listView_Name;
	private BimeListAdapter myArrayAdapter;
	private ArrayList<BimeItem> myArrayList;
	private boolean isEditAction = false;

}
