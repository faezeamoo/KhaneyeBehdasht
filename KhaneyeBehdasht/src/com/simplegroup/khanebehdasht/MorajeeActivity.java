package com.simplegroup.khanebehdasht;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;
import com.simplegroup.khanebehdasht.models.MaladyItem;
import com.simplegroup.khanebehdasht.models.MaladyListAdapter;
import com.simplegroup.khanebehdasht.models.MaladyOperation;
import com.simplegroup.khanebehdasht.models.MorajeeOperation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MorajeeActivity extends Activity {

	/*
	 * #########################################################################
	 * ########################## basic methods ################################
	 * #########################################################################
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_morajee);
		initializeAttributes();
	}

	private void initializeAttributes() {
		personCode = getIntent().getStringExtra("PersonCode");

		myDate = (EditText) findViewById(id.editText_mydate);

		malady = new MaladyOperation();

		list = malady.getAvailables();

		adapter = new MaladyListAdapter(this, R.layout.my_list_item_1, list);

		maladyList = (Spinner) findViewById(id.spinner_malady);
		maladyList.setAdapter(adapter);

		morajee = new MorajeeOperation();

		seeing = (EditText) findViewById(id.editText_seeing);

		tajviz = (EditText) findViewById(id.editText_tajviz);

		ok = (Button) findViewById(id.button_Ok);
		ok.setOnClickListener(ok_OnClick);

		isEdit = getIntent().getBooleanExtra("Editable", false);
		if (isEdit) {
			myDate.setText(getIntent().getStringExtra("MDate"));
			oldDate = myDate.getText().toString();

			MaladyItem m = new MaladyItem();
			m.setCode(getIntent().getIntExtra("MaladyCode", 0));
			m.setIsAvailable(true);
			m.setName(getIntent().getStringExtra("MaladyName"));
			maladyList.setSelection(adapter.getPosition(m));

			seeing.setText(getIntent().getStringExtra("Seeing"));

			tajviz.setText(getIntent().getStringExtra("Tajviz"));
		}
	}

	/*
	 * #########################################################################
	 * ############################ Events #####################################
	 * #########################################################################
	 */

	private OnClickListener ok_OnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (check()) {
				if (isEdit == false) {
					morajee.insert(personCode, myDate.getText().toString(),
							((MaladyItem) maladyList.getSelectedItem())
									.getCode(), seeing.getText().toString(),
							tajviz.getText().toString());
				} else {
					morajee.saveChanges(personCode, oldDate, myDate.getText()
							.toString(), ((MaladyItem) maladyList
							.getSelectedItem()).getCode(), seeing.getText()
							.toString(), tajviz.getText().toString());
					Log.d("MAKH", oldDate);
					Log.d("MAKH", myDate.getText().toString());
				}
				finish();
			}
		}
	};

	private boolean check() {
		String dateEx = "[0-9][0-9][0-9][0-9][/][0-9][0-9][/][0-9][0-9]";
		String text = myDate.getText().toString();

		// date
		if (text.length() < 10) {
			Toast.makeText(getApplicationContext(), R.string.message_date_test,
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			if (!text.matches(dateEx)) {
				Toast.makeText(getApplicationContext(),
						R.string.message_date_test, Toast.LENGTH_SHORT).show();
				return false;
			}
		}

		return true;
	}

	/*
	 * #########################################################################
	 * ############################ attributes #################################
	 * #########################################################################
	 */

	private String oldDate;
	private boolean isEdit;
	private String personCode;
	private EditText myDate;
	private MaladyOperation malady;
	private ArrayList<MaladyItem> list;
	private MaladyListAdapter adapter;
	private MorajeeOperation morajee;
	private Spinner maladyList;
	private EditText seeing;
	private EditText tajviz;
	private Button ok;
}
