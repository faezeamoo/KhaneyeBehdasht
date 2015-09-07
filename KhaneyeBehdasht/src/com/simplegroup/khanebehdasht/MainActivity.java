package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.MainDatabaseOpenHelper;
import com.simplegroup.khanebehdasht.models.PersonItem;
import com.simplegroup.khanebehdasht.models.PersonListAdapter;
import com.simplegroup.khanebehdasht.models.PersonOperation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends Activity {

	/*
	 * #########################################################################
	 * ############################ basic methods ##############################
	 * #########################################################################
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initilizeAttributes();
	}

	@Override
	protected void onDestroy() {
		MainDatabaseOpenHelper.close();
		super.onDestroy();
	}

	private void initilizeAttributes() {
		// create database.
		MainDatabaseOpenHelper.Start(getApplicationContext());
		// MainDatabaseOpenHelper.upgrade();

		nameAndFamily = (EditText) findViewById(id.editText_NameAndFamily);
		nameAndFamily.addTextChangedListener(refrashTextWatcher);

		nationalCode = (EditText) findViewById(id.editText_NationalCode);
		nationalCode.addTextChangedListener(refrashTextWatcher);

		person = new PersonOperation();

		myArrayList = person.getLike(nameAndFamily.getText().toString(),
				nationalCode.getText().toString());

		myArrayAdapter = new PersonListAdapter(this, R.layout.my_list_item_2,
				myArrayList);

		listView = (ListView) findViewById(id.listView_Person);
		// listView.setOnItemClickListener(listView_onItemClickListener);
		listView.setAdapter(myArrayAdapter);

		registerForContextMenu(listView);
	}

	@Override
	protected void onStart() {
		super.onStart();
		refrashListView();
	}

	/*
	 * #########################################################################
	 * ####################### basic menu functions. ###########################
	 * #########################################################################
	 */

	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v,
			android.view.ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.person, menu);
		// set listView
		AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuInfo;
		this.item = (PersonItem) listView.getItemAtPosition(acmi.position);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_edit) {
			Intent intent = new Intent(this, PersonActivity.class);

			intent.putExtra("NameAndFamily", this.item.getNameAndFamily());
			intent.putExtra("NationalCode", this.item.getNationalCode());
			intent.putExtra("FatherCode", this.item.getFatherCode());
			intent.putExtra("MotherCode", this.item.getMotherCode());
			intent.putExtra("FamilyCode", this.item.getFamilyCode());
			intent.putExtra("BimeCode", this.item.getBimeCode());
			intent.putExtra("PolicyCode", this.item.getPolicyCode());
			intent.putExtra("IsAlive", this.item.getIsAlive());
			intent.putExtra("Editable", true);

			startActivity(intent);

		} else if (itemId == R.id.action_morajee) {
			morajeeList();
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_khane_behdasht) {
			khaneBehdashtAction();
			return true;
		} else if (id == R.id.action_bime) {
			bimeAction();
			return true;
		} /*
		  else if (id == R.id.action_drug) { drugAction(); }
		 *//*else if (id == R.id.action_expert) {
			expertAction();
		} */else if (id == R.id.action_malady) {
			maladyAction();
		} else if (id == R.id.action_new_person) {
			addPerson();
		} else if (id == R.id.action_family) {
			familyAction();
		}
		return super.onOptionsItemSelected(item);
	}

	private void familyAction() {
		Intent intent = new Intent(this, FamilyListActivity.class);

		try {
			startActivity(intent);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	private void khaneBehdashtAction() {
		Intent intent = new Intent(this, KhaneBehdashtActivity.class);

		try {
			startActivity(intent);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	private void bimeAction() {
		Intent intent = new Intent(this, BimeActivity.class);

		try {
			startActivity(intent);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	/*
	 * private void drugAction() { Intent intent = new Intent(this,
	 * DrugActivity.class);
	 * 
	 * try { startActivity(intent); } catch (Exception e) { Log.d("MAKH",
	 * e.getMessage()); } }
	 */
	/*
	 * private void expertAction() { Intent intent = new Intent(this,
	 * ExpertActivity.class);
	 * 
	 * try { startActivity(intent); } catch (Exception e) { Log.d("MAKH",
	 * e.getMessage()); } }
	 */

	private void maladyAction() {
		Intent intent = new Intent(this, MaladyActivity.class);

		try {
			startActivity(intent);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	private void addPerson() {
		Intent intent = new Intent(this, PersonActivity.class);

		try {
			startActivity(intent);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	private void morajeeList() {
		Intent intent = new Intent(this, MorajeeListActivity.class);
		intent.putExtra("PersonCode", item.getNationalCode());

		try {
			startActivity(intent);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	/*
	 * #########################################################################
	 * ############################## Events ###################################
	 * #########################################################################
	 */
	private TextWatcher refrashTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			refrashListView();

		}
	};

	/*
	 * private OnItemClickListener listView_onItemClickListener = new
	 * OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { PersonItem item = (PersonItem)
	 * parent.getItemAtPosition(position); Intent intent = new Intent();
	 * intent.putExtra("ReturnedData", item.getNationalCode());
	 * setResult(RESULT_OK, intent); finish(); } };
	 */

	private void refrashListView() {
		myArrayList.clear();
		myArrayList.addAll(person.getLike(nameAndFamily.getText().toString(),
				nationalCode.getText().toString()));
		myArrayAdapter.notifyDataSetChanged();
	}

	/*
	 * #########################################################################
	 * ############################ attributes #################################
	 * #########################################################################
	 */
	private PersonItem item;
	private EditText nameAndFamily;
	private EditText nationalCode;
	private ArrayList<PersonItem> myArrayList;
	private PersonListAdapter myArrayAdapter;
	private ListView listView;
	private PersonOperation person;

}
