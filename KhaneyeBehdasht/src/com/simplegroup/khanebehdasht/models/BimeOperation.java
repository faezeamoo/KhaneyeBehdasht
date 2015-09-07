package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BimeOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// Table Name
	public static final String BIME_TABLE = "bime";
	// Table Columns
	public static final String BIME_CODE = "Code";
	public static final String BIME_NAME = "Name";
	public static final String BIME_ISAVAILABLE = "IsAvailable";

	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public BimeOperation() {
		database = MainDatabaseOpenHelper.getDatabase();
	}

	/*
	 * #########################################################################
	 * ########################## database operations ##########################
	 * #########################################################################
	 */
	
	public void insert(String bimeName,
			boolean bimeIsAvailable) {
		ContentValues values = new ContentValues();
		values.put(BIME_NAME, bimeName);
		values.put(BIME_ISAVAILABLE, String.valueOf(bimeIsAvailable));

		database.insert(BIME_TABLE, null, values);
	}
	
	public void insert(BimeItem newRow)
	{
		this.insert(newRow.getName(), newRow.getIsAvailable());
	}
	
	public void changeToNotAvailable(BimeItem row) {
		String[] args = new String[1];
		args[0] = String.valueOf(row.getCode());
		
		ContentValues values = new ContentValues();
		values.put(BIME_ISAVAILABLE, String.valueOf(false));
		
		database.update(BIME_TABLE, values, BIME_CODE + "=?", args);
	}

	// Instead of send parameter update in its own scope
	public void saveChanges(BimeItem targetItem) {
		ContentValues values = new ContentValues();
		values.put(BIME_CODE, targetItem.getCode());
		values.put(BIME_NAME, targetItem.getName());
		values.put(BIME_ISAVAILABLE,
				targetItem.getIsAvailableAsString());
		Log.d(this.getClass().getName(), targetItem.getIsAvailableAsString());
		
		database.update(BIME_TABLE, values, BIME_CODE + "=?",
				new String[] { String.valueOf(targetItem.getCode()) });
	}

	public ArrayList<BimeItem> getAll() {
		ArrayList<BimeItem> result = new ArrayList<BimeItem>();
		Cursor resultIterator;

		resultIterator = database.query(BIME_TABLE, null, null, null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int bimeCodeIndex = resultIterator.getColumnIndex(BIME_CODE);
			int bimeNameIndex = resultIterator.getColumnIndex(BIME_NAME);
			int bimeAvailabilityIndex = resultIterator
					.getColumnIndex(BIME_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(bimeCodeIndex);
				String newItemName = resultIterator.getString(bimeNameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(bimeAvailabilityIndex));

				BimeItem newItem = new BimeItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}
	
	public ArrayList<BimeItem> getAvailables()
	{
		ArrayList<BimeItem> result = new ArrayList<BimeItem>();
		Cursor resultIterator;

		resultIterator = database.query(BIME_TABLE, null, BIME_ISAVAILABLE +  " = '" + String.valueOf(true) + "'", null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int bimeCodeIndex = resultIterator.getColumnIndex(BIME_CODE);
			int bimeNameIndex = resultIterator.getColumnIndex(BIME_NAME);
			int bimeAvailabilityIndex = resultIterator
					.getColumnIndex(BIME_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(bimeCodeIndex);
				String newItemName = resultIterator.getString(bimeNameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(bimeAvailabilityIndex));

				BimeItem newItem = new BimeItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}
}
