package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExpertOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// Table Name
	public static final String EXPERT_TABLE = "Expert";
	// Table Columns
	public static final String EXPERT_CODE = "Code";
	public static final String EXPERT_NAME = "Name";
	public static final String EXPERT_ISAVAILABLE = "IsAvailable";

	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public ExpertOperation() {
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
		values.put(EXPERT_NAME, Name);
		values.put(EXPERT_ISAVAILABLE, String.valueOf(IsAvailable));

		database.insert(EXPERT_TABLE, null, values);
	}
	
	public void insert(ExpertItem newRow)
	{
		this.insert(newRow.getName(), newRow.getIsAvailable());
	}
	
	public void changeToNotAvailable(ExpertItem row) {
		String[] args = new String[1];
		args[0] = String.valueOf(row.getCode());
		
		ContentValues values = new ContentValues();
		values.put(EXPERT_ISAVAILABLE, String.valueOf(false));
		
		database.update(EXPERT_TABLE, values, EXPERT_CODE + "=?", args);
	}

	// Instead of send parameter update in its own scope
	public void saveChanges(ExpertItem targetItem) {
		ContentValues values = new ContentValues();
		values.put(EXPERT_CODE, targetItem.getCode());
		values.put(EXPERT_NAME, targetItem.getName());
		values.put(EXPERT_ISAVAILABLE,
				targetItem.getIsAvailableAsString());
		Log.d(this.getClass().getName(), targetItem.getIsAvailableAsString());
		
		database.update(EXPERT_TABLE, values, EXPERT_CODE + "=?",
				new String[] { String.valueOf(targetItem.getCode()) });
	}

	public ArrayList<ExpertItem> getAll() {
		ArrayList<ExpertItem> result = new ArrayList<ExpertItem>();
		Cursor resultIterator;

		resultIterator = database.query(EXPERT_TABLE, null, null, null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int CodeIndex = resultIterator.getColumnIndex(EXPERT_CODE);
			int NameIndex = resultIterator.getColumnIndex(EXPERT_NAME);
			int AvailabilityIndex = resultIterator
					.getColumnIndex(EXPERT_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(CodeIndex);
				String newItemName = resultIterator.getString(NameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(AvailabilityIndex));

				ExpertItem newItem = new ExpertItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}
	
	public ArrayList<ExpertItem> getAvailables()
	{
		ArrayList<ExpertItem> result = new ArrayList<ExpertItem>();
		Cursor resultIterator;

		resultIterator = database.query(EXPERT_TABLE, null, EXPERT_ISAVAILABLE +  " = '" + String.valueOf(true) + "'", null, null,
				null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int CodeIndex = resultIterator.getColumnIndex(EXPERT_CODE);
			int NameIndex = resultIterator.getColumnIndex(EXPERT_NAME);
			int AvailabilityIndex = resultIterator
					.getColumnIndex(EXPERT_ISAVAILABLE);

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(CodeIndex);
				String newItemName = resultIterator.getString(NameIndex);
				boolean newItemAvailability = Boolean.valueOf(resultIterator.getString(AvailabilityIndex));

				ExpertItem newItem = new ExpertItem();
				newItem.setCode(newItemCode);
				newItem.setName(newItemName);
				newItem.setIsAvailable(newItemAvailability);

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}

}
