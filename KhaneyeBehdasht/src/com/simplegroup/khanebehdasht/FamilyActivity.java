package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.FamilyItem;
import com.simplegroup.khanebehdasht.models.FamilyOperation;
import com.simplegroup.khanebehdasht.models.KhaneBehdashtItem;
import com.simplegroup.khanebehdasht.models.KhaneBehdashtListAdapter;
import com.simplegroup.khanebehdasht.models.KhaneBehdashtOperation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FamilyActivity extends Activity {

	/*
	 * #########################################################################
	 * ############################# basic methods #############################
	 * #########################################################################
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_family);
		initilizeAttributes();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == this.SELECT_FATHER_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			fatherCode.setText(data.getStringExtra("ReturnedData"));
		}

	}

	public void initilizeAttributes() {
		fatherCode = (TextView) findViewById(id.textView_FatherNameAndFamily);

		selectFather = (Button) findViewById(id.button_FatherSelect);
		selectFather.setOnClickListener(selectFather_OnClickListener);

		khaneBehdasht = new KhaneBehdashtOperation();

		list = khaneBehdasht.getAvailables();

		adapter = new KhaneBehdashtListAdapter(this, R.layout.my_list_item_1,
				list);
		if (adapter.getCount() == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.message_khane_behdasht_must_be_specified,
					Toast.LENGTH_LONG).show();
		}

		selectKhaneBehdasht = (Spinner) findViewById(id.spinner_KhaneBehdasht);
		selectKhaneBehdasht.setAdapter(adapter);

		ok = (Button) findViewById(id.button_Ok);
		ok.setOnClickListener(ok_onClickListener);

		family = new FamilyOperation();

		isEdit = getIntent().getBooleanExtra("EditAction", false);
		if (isEdit) {
			editedItem = new FamilyItem();
			// now this activity run for edit a created item.
			// set code for know what item need to edit.
			editedItem.setCode(getIntent().getIntExtra("Code", 0));

			// set father code.
			fatherCode.setText(getIntent().getStringExtra("FatherCode"));
			editedItem.setFatherCode(getIntent().getStringExtra("FatherCode"));

			// set khane behdasht code and name
			editedItem.setKhaneBehdashtCode(getIntent().getIntExtra(
					"KhaneBehdashtCode", 0));
			KhaneBehdashtItem kbi = new KhaneBehdashtItem();
			kbi.setCode(getIntent().getIntExtra("KhaneBehdashtCode", 0));
			kbi.setName(getIntent().getStringExtra("KhaneBehdashtName")
					.toString());

			// set isAvailable for khane behdahst and edited item.
			kbi.setIsAvailable(true);
			editedItem.setIsAvailable(true);

			// select correct item in spinner.
			selectKhaneBehdasht.setSelection(adapter.getPosition(kbi));
		}
	}

	public void addFamily() {
		family.insert(fatherCode.getText().toString(),
				((KhaneBehdashtItem) selectKhaneBehdasht.getSelectedItem())
						.getCode());
	}

	public void editFamily() {
		editedItem.setFatherCode(fatherCode.getText().toString());
		editedItem
				.setKhaneBehdashtCode(((KhaneBehdashtItem) selectKhaneBehdasht
						.getSelectedItem()).getCode());
		family.saveChanges(editedItem);
	}

	/*
	 * #########################################################################
	 * ############################## Events ###################################
	 * #########################################################################
	 */

	private OnClickListener ok_onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!isEdit) {
				addFamily();
			} else {
				editFamily();
			}

			Intent intent = new Intent();
			intent.putExtra("ReturnedData", fatherCode.getText().toString());

			setResult(RESULT_OK, intent);
			finish();
		}
	};

	private OnClickListener selectFather_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			searchFather();
		}
	};

	private void searchFather() {
		Intent intent = new Intent(this, SearchPersonActivity.class);

		try {
			startActivityForResult(intent, SELECT_FATHER_REQUEST_CODE);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	/*
	 * #########################################################################
	 * ############################ Attributes #################################
	 * #########################################################################
	 */

	private TextView fatherCode;
	private Button selectFather;
	private Button ok;
	private ArrayList<KhaneBehdashtItem> list;
	private KhaneBehdashtListAdapter adapter;
	private Spinner selectKhaneBehdasht;
	private KhaneBehdashtOperation khaneBehdasht;
	private FamilyOperation family;
	private boolean isEdit;
	private FamilyItem editedItem;
	private final int SELECT_FATHER_REQUEST_CODE = 14;
}
