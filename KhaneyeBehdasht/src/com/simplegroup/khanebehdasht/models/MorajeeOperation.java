package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MorajeeOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// Table Name
	public static final String MORAJEE_TABLE = "Morajee";
	// Table Columns
	public static final String MORAJEE_PERSON_CODE = "PersonCode";
	public static final String MORAJEE_DATE = "MDate";
	public static final String MORAJEE_MALADY_CODE = "MaladyCode";
	public static final String MORAJEE_SEEING = "Seeing";
	public static final String MORAJEE_TAJVIZ = "Tajviz";

	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public MorajeeOperation() {
		database = MainDatabaseOpenHelper.getDatabase();
	}

	/*
	 * #########################################################################
	 * ########################## database operations ##########################
	 * #########################################################################
	 */

	public void insert(String personCode, String mDate, int maladyCode,
			String seeing, String tajviz) {
		ContentValues values = new ContentValues();
		values.put(MORAJEE_PERSON_CODE, personCode);
		values.put(MORAJEE_DATE, mDate);
		values.put(MORAJEE_MALADY_CODE, maladyCode);
		values.put(MORAJEE_SEEING, seeing);
		values.put(MORAJEE_TAJVIZ, tajviz);

		database.insert(MORAJEE_TABLE, null, values);
	}

	public void insert(MorajeeItem newRow) {
		this.insert(newRow.getPersonCode(), newRow.getDate(),
				newRow.getMalady(), newRow.getSeeing(), newRow.getTajviz());
	}

	// Instead of send parameter update in its own scope
	public void saveChanges(String personCode, String oldDate, String mDate,
			int maladyCode, String seeing, String tajviz) {
		ContentValues values = new ContentValues();
		values.put(MORAJEE_PERSON_CODE, personCode);
		values.put(MORAJEE_DATE, mDate);
		values.put(MORAJEE_MALADY_CODE, maladyCode);
		values.put(MORAJEE_SEEING, seeing);
		values.put(MORAJEE_TAJVIZ, tajviz);
		
		
		database.update(MORAJEE_TABLE, values, MORAJEE_PERSON_CODE + "=? AND "
				+ MORAJEE_DATE + "=?", new String[] { personCode, oldDate });
	}

	public void saveChanges(MorajeeItem newRow) {
		this.saveChanges(newRow.getPersonCode(), newRow.getDate(),
				newRow.getDate(), newRow.getMalady(), newRow.getSeeing(),
				newRow.getTajviz());
	}

	public ArrayList<MorajeeItem> getAllForPersons(String personCode) {
		ArrayList<MorajeeItem> result = new ArrayList<MorajeeItem>();
		Cursor resultIterator;

		resultIterator = database.query(MORAJEE_TABLE + ", "
				+ MaladyOperation.MALADY_TABLE, null, MORAJEE_PERSON_CODE
				+ " = ? AND " + MaladyOperation.MALADY_CODE + " = "
				+ MORAJEE_MALADY_CODE, new String[] { personCode }, null, null,
				null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int PersonCodeIndex = resultIterator
					.getColumnIndex(MORAJEE_PERSON_CODE);
			int DateIndex = resultIterator.getColumnIndex(MORAJEE_DATE);
			int MaladyCodeIndex = resultIterator
					.getColumnIndex(MORAJEE_MALADY_CODE);
			int MaladyName = resultIterator
					.getColumnIndex(MaladyOperation.MALADY_NAME);
			int SeeingIndex = resultIterator.getColumnIndex(MORAJEE_SEEING);
			int TajvizIndex = resultIterator.getColumnIndex(MORAJEE_TAJVIZ);

			do {
				// Create Items
				MorajeeItem row = new MorajeeItem();
				row.setPersonCode(resultIterator.getString(PersonCodeIndex));
				row.setDate(resultIterator.getString(DateIndex));
				row.setMalady(resultIterator.getInt(MaladyCodeIndex));
				row.setMaladyName(resultIterator.getString(MaladyName));
				row.setSeeing(resultIterator.getString(SeeingIndex));
				row.setTajviz(resultIterator.getString(TajvizIndex));

				result.add(row);

			} while (resultIterator.moveToNext());

		}

		return result;
	}
}
