package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MaladyOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// Table Name
	public static final String MALADY_TABLE = "Malady";
	// Table Columns
	public static final String MALADY_CODE = "Code";
	public static final String MALADY_NAME = "Name";
	public static final String MALADY_ISAVAILABLE = "IsAvailable";

	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public MaladyOperation() {
		database = MainDatabaseOpenHelper.getDatabase();
	}

	/*
	 * #########################################################################
	 * ########################## database operations ##########################
	 * #########################################################################
	 */
	
	public void insert(String Name,
			boolean IsAvailable) {
		ContentValues values = new ContentValues();
		values.put(MALADY_NAME, Name);
		values.put(MALADY_ISAVAILABLE, String.valueOf(IsAvailable));

		database.insert(MALADY_TABLE, null, values);
	}
	
	public void insert(MaladyItem newRow)
	{
		this.insert(newRow.getName(), newRow.getIsAvailable());
	}
	
	public void changeToNotAvailable(MaladyItem row) {
		String[] args = new String[1];
		args[0] = String.valueOf(row.getCode());
		
		ContentValues values = new ContentValues();
		values.put(MALADY_ISAVAILABLE, String.valueOf(false));
		
		database.update(MALADY_TABLE, values, MALADY_CODE + "=?", args);
	}

	// Instead of send parameter update in its own scope
	public void saveChanges(MaladyItem targetItem) {
		ContentValues values = new ContentValues();
		values.put(MALADY_CODE, targetItem.getCode());
		values.put(MALADY_NAME, targetItem.getName());
		values.put(MALADY_ISAVAILABLE,
				targetItem.getIsAvailableAsString());
		Log.d(this.getClass().getName(), targetItem.getIsAvailableAsString());
		
		database.update(MALADY_TABLE, values, MALADY_CODE + "=?",
				new String[] { String.valueOf(targetItem.getCode()) });
	}

	public ArrayList<MaladyItem> getAll() {
		ArrayList<MaladyItem> result = new ArrayList<MaladyItem>();
		Cursor resultIterator;

		resultIterator = database.query(MALADY_TABLE, null, null, null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int CodeIndex = resultIterator.getColumnIndex(MALADY_CODE);
			int NameIndex = resultIterator.getColumnIndex(MALADY_NAME);
			int AvailabilityIndex = resultIterator
					.getColumnIndex(MALADY_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(CodeIndex);
				String newItemName = resultIterator.getString(NameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(AvailabilityIndex));

				MaladyItem newItem = new MaladyItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}
	
	public ArrayList<MaladyItem> getAvailables()
	{
		ArrayList<MaladyItem> result = new ArrayList<MaladyItem>();
		Cursor resultIterator;

		resultIterator = database.query(MALADY_TABLE, null, MALADY_ISAVAILABLE +  " = '" + String.valueOf(true) + "'", null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int CodeIndex = resultIterator.getColumnIndex(MALADY_CODE);
			int NameIndex = resultIterator.getColumnIndex(MALADY_NAME);
			int AvailabilityIndex = resultIterator
					.getColumnIndex(MALADY_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(CodeIndex);
				String newItemName = resultIterator.getString(NameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(AvailabilityIndex));

				MaladyItem newItem = new MaladyItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}
}
