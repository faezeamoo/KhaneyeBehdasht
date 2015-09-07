package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.BimeItem;
import com.simplegroup.khanebehdasht.models.BimeListAdapter;
import com.simplegroup.khanebehdasht.models.BimeOperation;
import com.simplegroup.khanebehdasht.models.FamilyItem;
import com.simplegroup.khanebehdasht.models.FamilyOperation;
import com.simplegroup.khanebehdasht.models.PersonOperation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PersonActivity extends Activity {

	/*
	 * #########################################################################
	 * ########################## basic methods ################################
	 * #########################################################################
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		initializeAttributes();
	}

	private void initializeAttributes() {
		bime = new BimeOperation();

		bimeList = bime.getAvailables();

		bimeAdapter = new BimeListAdapter(this, R.layout.my_list_item_1,
				bimeList);
		if (bimeAdapter.getCount() == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.message_bime_must_be_specified, Toast.LENGTH_LONG)
					.show();
		}

		nameAndFamily = (EditText) findViewById(id.editText_NameAndFamily);

		nationalCode = (EditText) findViewById(id.editText_NationalCode);

		fatherCode = (EditText) findViewById(id.editText_FatherCode);

		fatherSelect = (Button) findViewById(id.button_FatherSelect);
		fatherSelect.setOnClickListener(button_FatherSelect_OnClickListener);

		motherCode = (EditText) findViewById(id.editText_MotherCode);

		motherSelect = (Button) findViewById(id.button_MotherSelect);
		motherSelect.setOnClickListener(button_MotherSelect_OnClickListener);

		familySelect = (Button) findViewById(id.button_FamilyCode);
		familySelect.setOnClickListener(button_FamilySelect_OnClickListener);
		familyCode = -1;

		bimeCode = (Spinner) findViewById(id.spinner_Bime);
		bimeCode.setAdapter(bimeAdapter);

		policyCode = (EditText) findViewById(id.editText_BimeNumber);

		isAlive = (CheckBox) findViewById(id.checkBox_IsAlive);
		isAlive.setChecked(true);

		buttonOk = (Button) findViewById(id.button_Ok);
		buttonOk.setOnClickListener(button_Ok_OnClickListener);

		person = new PersonOperation();

		family = new FamilyOperation();

		isEdit = getIntent().getBooleanExtra("Editable", false);
		if (isEdit) {
			nameAndFamily.setText(getIntent().getStringExtra("NameAndFamily"));
			nationalCode.setText(getIntent().getStringExtra("NationalCode"));
			nationalCode.setEnabled(false);
			motherCode.setText(getIntent().getStringExtra("MotherCode"));
			fatherCode.setText(getIntent().getStringExtra("FatherCode"));
			bimeCode.setSelection(bimeFind(getIntent().getIntExtra("BimeCode",
					0)));
			familyCode = getIntent().getIntExtra("FamilyCode", -1);
			policyCode.setText(getIntent().getStringExtra("PolicyCode"));
			isAlive.setChecked(getIntent().getBooleanExtra("IsAlive", true));
		}
	}

	private int bimeFind(int code) {
		for (int i = 0; i < bimeAdapter.getCount(); i++) {
			if (((BimeItem) bimeAdapter.getItem(i)).getCode() == code) {
				return i;
			}
		}
		return -1;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == this.FATHER_REQUEST_CODE && resultCode == RESULT_OK) {
			fatherCode.setText(data.getStringExtra("ReturnedData"));
		} else if (requestCode == this.MOTHER_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			motherCode.setText(data.getStringExtra("ReturnedData"));
		} else if (requestCode == this.FAMILY_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			FamilyItem t = family.getFamilyByFather(data
					.getStringExtra("ReturnedData"));
			if (t != null) {
				familyCode = t.getCode();
			} else {
				Toast.makeText(getApplicationContext(),
						R.string.message_family_not_found, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	/*
	 * #########################################################################
	 * ################################ events #################################
	 * #########################################################################
	 */

	private OnClickListener button_Ok_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isEdit) {
				editPerson();
				finish();
			} else {
				addPerson();
			}
		}
	};

	private OnClickListener button_FatherSelect_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			searchActivity(FATHER_REQUEST_CODE);
		}
	};

	private OnClickListener button_MotherSelect_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			searchActivity(MOTHER_REQUEST_CODE);

		}
	};

	private OnClickListener button_FamilySelect_OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			searchActivity(FAMILY_REQUEST_CODE);
		}
	};

	public void searchActivity(int requestCode) {
		Intent intent = new Intent(this, SearchPersonActivity.class);

		try {
			startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			Log.d("MAKH", e.getMessage());
		}
	}

	/*
	 * #########################################################################
	 * ########################## require add methods ##########################
	 * #########################################################################
	 */

	private void addPerson() {
		if (checkFields()) {
			String m = person.insert(this.nameAndFamily.getText().toString(),
					nationalCode.getText().toString(), fatherCode.getText()
							.toString(), motherCode.getText().toString(),
					familyCode, ((BimeItem) bimeCode.getSelectedItem())
							.getCode(), policyCode.getText().toString(),
					isAlive.isChecked());
			if (m.length() > 0) {
				Toast.makeText(getApplicationContext(),
						R.string.message_national_isnt_unique,
						Toast.LENGTH_SHORT).show();
				return;
			}
			clearFields();
		}
	}

	private void editPerson() {
		if (checkFields()) {
			person.saveChanges(this.nameAndFamily.getText().toString(),
					nationalCode.getText().toString(), fatherCode.getText()
							.toString(), motherCode.getText().toString(),
					familyCode, ((BimeItem) bimeCode.getSelectedItem())
							.getCode(), policyCode.getText().toString(),
					isAlive.isChecked());
		}
	}

	private boolean checkFields() {
		int length;
		length = nameAndFamily.getText().toString().length();
		if (length == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.message_name_and_family_is_required,
					Toast.LENGTH_SHORT).show();
			return false;
		}

		length = nationalCode.getText().toString().length();
		if (length == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.message_national_code_is_required,
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (length < 0) {
			Toast.makeText(getApplicationContext(),
					R.string.message_national_length_is_short,
					Toast.LENGTH_SHORT).show();
			return false;
		}

		length = fatherCode.getText().toString().length();
		if (length != 0 && length < 10) {
			Toast.makeText(getApplicationContext(),
					R.string.message_father_length_is_short, Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		length = motherCode.getText().toString().length();
		if (length != 0 && length < 10) {
			Toast.makeText(getApplicationContext(),
					R.string.message_mother_length_is_short, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private void clearFields() {
		nameAndFamily.setText("");
		nationalCode.setText("");
		fatherCode.setText("");
		motherCode.setText("");
		familyCode = -1;
		policyCode.setText("");
	}

	/*
	 * #########################################################################
	 * ############################ attributes #################################
	 * #########################################################################
	 */
	private boolean isEdit;
	private EditText nameAndFamily;
	private EditText nationalCode;
	private EditText fatherCode;
	private Button fatherSelect;
	private EditText motherCode;
	private Button motherSelect;
	private Button familySelect;
	private int familyCode;
	private Spinner bimeCode;
	private EditText policyCode;
	private CheckBox isAlive;
	private Button buttonOk;
	private BimeListAdapter bimeAdapter;
	private ArrayList<BimeItem> bimeList;
	private BimeOperation bime;
	private PersonOperation person;
	private FamilyOperation family;

	private final int MOTHER_REQUEST_CODE = 11;
	private final int FATHER_REQUEST_CODE = 12;
	private final int FAMILY_REQUEST_CODE = 14;
}
