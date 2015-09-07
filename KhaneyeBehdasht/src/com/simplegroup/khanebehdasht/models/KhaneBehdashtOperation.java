package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class KhaneBehdashtOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// table name
	public static final String KHANE_BEHDASHT_TABLE = "KhaneBehdasht";

	// table columns name
	public static final String KHANE_BEHDASHT_CODE = "Code";
	public static final String KHANE_BEHDASHT_NAME = "Name";
	public static final String KHANE_BEHDASHT_IS_AVAILABLE = "IsAvailable";

	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public KhaneBehdashtOperation() {
		database = MainDatabaseOpenHelper.getDatabase();
	}

	/*
	 * #########################################################################
	 * ########################## database operations ##########################
	 * #########################################################################
	 */

	public void insert(String name, Boolean isAvailable) {
		ContentValues values = new ContentValues();
		values.put(KHANE_BEHDASHT_NAME, name);
		values.put(KHANE_BEHDASHT_IS_AVAILABLE, String.valueOf(isAvailable));
		
		database.insert(KHANE_BEHDASHT_TABLE, null, values);
		Log.d(this.getClass().getName(), "New row inserted in the database.");
	}

	public void insert(KhaneBehdashtItem newRow) {
		this.insert(newRow.getName(), newRow.getIsAvailable());
	}

	public void changeToNotAvailable(KhaneBehdashtItem row) {
		String[] args = new String[1];
		args[0] = String.valueOf(row.getCode());
		
		ContentValues values = new ContentValues();
		values.put(KHANE_BEHDASHT_IS_AVAILABLE, String.valueOf(false));
		
		database.update(KHANE_BEHDASHT_TABLE, values, KHANE_BEHDASHT_CODE + "=?", args);
	}
	
	public ArrayList<KhaneBehdashtItem> getAll() {
		ArrayList<KhaneBehdashtItem> output = new ArrayList<KhaneBehdashtItem>();
		Cursor result;

		result = database.query(KHANE_BEHDASHT_TABLE, null, null, null, null,
				null, KHANE_BEHDASHT_NAME);

		Log.d(this.getClass().getName(), String.valueOf(result.getCount()));

		if (result.moveToFirst()) {

			int codeIndex = result.getColumnIndex(KHANE_BEHDASHT_CODE);
			int nameIndex = result.getColumnIndex(KHANE_BEHDASHT_NAME);
			int isAvailableIndex = result
					.getColumnIndex(KHANE_BEHDASHT_IS_AVAILABLE);

			do {
				KhaneBehdashtItem row = new KhaneBehdashtItem();

				row.setCode(result.getInt(codeIndex));
				row.setName(result.getString(nameIndex));
				row.setIsAvailable(Boolean.valueOf(result
						.getString(isAvailableIndex)));

				output.add(row);
			} while (result.moveToNext());
		}

		return output;
	}
	
	public ArrayList<KhaneBehdashtItem> getAvailables() {
		ArrayList<KhaneBehdashtItem> output = new ArrayList<KhaneBehdashtItem>();
		Cursor result;

		result = database.query(KHANE_BEHDASHT_TABLE, null, KHANE_BEHDASHT_IS_AVAILABLE + " = '" + String.valueOf(true) + "'", null, null,
				null, KHANE_BEHDASHT_NAME);

		Log.d(this.getClass().getName(), String.valueOf(result.getCount()));

		if (result.moveToFirst()) {

			int codeIndex = result.getColumnIndex(KHANE_BEHDASHT_CODE);
			int nameIndex = result.getColumnIndex(KHANE_BEHDASHT_NAME);
			int isAvailableIndex = result
					.getColumnIndex(KHANE_BEHDASHT_IS_AVAILABLE);

			do {
				KhaneBehdashtItem row = new KhaneBehdashtItem();

				row.setCode(result.getInt(codeIndex));
				row.setName(result.getString(nameIndex));
				row.setIsAvailable(Boolean.valueOf(result
						.getString(isAvailableIndex)));

				output.add(row);
			} while (result.moveToNext());
		}

		return output;
	}
	
	public void saveChanges(KhaneBehdashtItem targetItem) {
		ContentValues values = new ContentValues();
		values.put(KHANE_BEHDASHT_CODE, targetItem.getCode());
		values.put(KHANE_BEHDASHT_NAME, targetItem.getName());
		values.put(KHANE_BEHDASHT_IS_AVAILABLE, targetItem.getIsAvailableAsString());
		
		database.update(KHANE_BEHDASHT_TABLE, values, KHANE_BEHDASHT_CODE + "=?",
				new String[] { String.valueOf(targetItem.getCode()) });
	}
}
