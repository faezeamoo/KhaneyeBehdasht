package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.PersonItem;
import com.simplegroup.khanebehdasht.models.PersonListAdapter;
import com.simplegroup.khanebehdasht.models.PersonOperation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class SearchPersonActivity extends Activity {

	/*
	 * #########################################################################
	 * ############################ basic methods ##############################
	 * #########################################################################
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initializeAttributes();
	}

	private void initializeAttributes() {
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
		listView.setOnItemClickListener(listView_onItemClickListener);
		listView.setAdapter(myArrayAdapter);
	}
	
	/*
	 * #########################################################################
	 * ############################## Events ###################################
	 * #########################################################################
	 */
	private TextWatcher refrashTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
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
	
	private OnItemClickListener listView_onItemClickListener = new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			PersonItem item = (PersonItem) parent.getItemAtPosition(position);
			Intent intent = new Intent();
			intent.putExtra("ReturnedData", item.getNationalCode());
			setResult(RESULT_OK, intent);
			finish();
		}
	};
	
	
	
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
	private EditText nameAndFamily;
	private EditText nationalCode;
	private ArrayList<PersonItem> myArrayList;
	private PersonListAdapter myArrayAdapter;
	private ListView listView;
	private PersonOperation person;
}
