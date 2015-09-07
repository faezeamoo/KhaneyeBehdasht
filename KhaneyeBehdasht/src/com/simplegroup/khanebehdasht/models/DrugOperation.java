package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DrugOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// Table Name
	public static final String DRUG_TABLE = "Drug";
	// Table Columns
	public static final String DRUG_CODE = "Code";
	public static final String DRUG_NAME = "Name";
	public static final String DRUG_ISAVAILABLE = "IsAvailable";

	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public DrugOperation() {
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
		values.put(DRUG_NAME, Name);
		values.put(DRUG_ISAVAILABLE, String.valueOf(IsAvailable));

		database.insert(DRUG_TABLE, null, values);
	}
	
	public void insert(DrugItem newRow)
	{
		this.insert(newRow.getName(), newRow.getIsAvailable());
	}
	
	public void changeToNotAvailable(DrugItem row) {
		String[] args = new String[1];
		args[0] = String.valueOf(row.getCode());
		
		ContentValues values = new ContentValues();
		values.put(DRUG_ISAVAILABLE, String.valueOf(false));
		
		database.update(DRUG_TABLE, values, DRUG_CODE + "=?", args);
	}

	// Instead of send parameter update in its own scope
	public void saveChanges(DrugItem targetItem) {
		ContentValues values = new ContentValues();
		values.put(DRUG_CODE, targetItem.getCode());
		values.put(DRUG_NAME, targetItem.getName());
		values.put(DRUG_ISAVAILABLE,
				targetItem.getIsAvailableAsString());
		Log.d(this.getClass().getName(), targetItem.getIsAvailableAsString());
		
		database.update(DRUG_TABLE, values, DRUG_CODE + "=?",
				new String[] { String.valueOf(targetItem.getCode()) });
	}

	public ArrayList<DrugItem> getAll() {
		ArrayList<DrugItem> result = new ArrayList<DrugItem>();
		Cursor resultIterator;

		resultIterator = database.query(DRUG_TABLE, null, null, null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int CodeIndex = resultIterator.getColumnIndex(DRUG_CODE);
			int NameIndex = resultIterator.getColumnIndex(DRUG_NAME);
			int AvailabilityIndex = resultIterator
					.getColumnIndex(DRUG_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(CodeIndex);
				String newItemName = resultIterator.getString(NameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(AvailabilityIndex));

				DrugItem newItem = new DrugItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}
	
	public ArrayList<DrugItem> getAvailables()
	{
		ArrayList<DrugItem> result = new ArrayList<DrugItem>();
		Cursor resultIterator;

		resultIterator = database.query(DRUG_TABLE, null, DRUG_ISAVAILABLE +  " = '" + String.valueOf(true) + "'", null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int CodeIndex = resultIterator.getColumnIndex(DRUG_CODE);
			int NameIndex = resultIterator.getColumnIndex(DRUG_NAME);
			int AvailabilityIndex = resultIterator
					.getColumnIndex(DRUG_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(CodeIndex);
				String newItemName = resultIterator.getString(NameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(AvailabilityIndex));

				DrugItem newItem = new DrugItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}

}
