package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FamilyOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// Table Name
	public static final String FAMILY_TABLE = "Family";
	// Table Columns
	public static final String FAMILY_CODE = "Code";
	public static final String FAMILY_FATHER_CODE = "FatherCode";
	public static final String FAMILY_KHANE_BEHDASHT_CODE = "KhaneBehdashtCode";
	public static final String FAMILY_ISAVAILABLE = "IsAvailable";

	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public FamilyOperation() {
		database = MainDatabaseOpenHelper.getDatabase();
	}

	/*
	 * #########################################################################
	 * ########################## database operations ##########################
	 * #########################################################################
	 */

	public void insert(String fatherCode, int khaneBehdashtCode) {
		ContentValues values = new ContentValues();
		values.put(FAMILY_FATHER_CODE, fatherCode);
		values.put(FAMILY_KHANE_BEHDASHT_CODE, khaneBehdashtCode);
		values.put(FAMILY_ISAVAILABLE, String.valueOf(true));

		database.insert(FAMILY_TABLE, null, values);
	}

	public void insert(FamilyItem newRow) {
		this.insert(newRow.getFahterCode(), newRow.getKhaneBehdashtCode());
	}

	public void changeToNotAvailable(FamilyItem row) {
		String[] args = new String[1];
		args[0] = String.valueOf(row.getCode());

		ContentValues values = new ContentValues();
		values.put(FAMILY_ISAVAILABLE, String.valueOf(false));

		database.update(FAMILY_TABLE, values, FAMILY_CODE + "=?", args);
	}

	public void saveChanges(FamilyItem targetItem) {
		ContentValues values = new ContentValues();
		values.put(FAMILY_CODE, targetItem.getCode());
		values.put(FAMILY_FATHER_CODE, targetItem.getFahterCode());
		values.put(FAMILY_KHANE_BEHDASHT_CODE,
				targetItem.getKhaneBehdashtCode());
		values.put(FAMILY_ISAVAILABLE, targetItem.getIsAvailableAsString());

		database.update(FAMILY_TABLE, values, FAMILY_CODE + "=?",
				new String[] { String.valueOf(targetItem.getCode()) });
	}

	public ArrayList<FamilyItem> getAll() {
		ArrayList<FamilyItem> result = new ArrayList<FamilyItem>();
		Cursor resultIterator;

		resultIterator = database
				.query(FAMILY_TABLE + ", "
						+ KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE + ", "
						+ PersonOperation.PERSON_TABLE,
						new String[] {
								/* khane behdasht columns */
								KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE
										+ "."
										+ KhaneBehdashtOperation.KHANE_BEHDASHT_CODE
										+ " AS khCode ",
								KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE
										+ "."
										+ KhaneBehdashtOperation.KHANE_BEHDASHT_NAME
										+ " AS khName ",
								/* family columns */
								FAMILY_TABLE + "." + FAMILY_CODE + " AS fCode",
								FAMILY_TABLE + "." + FAMILY_FATHER_CODE
										+ " AS ffCode ",
								FAMILY_TABLE + "." + FAMILY_KHANE_BEHDASHT_CODE,
								FAMILY_TABLE + "." + FAMILY_ISAVAILABLE
										+ " AS fIsA ",
								/* person columns */
								PersonOperation.PERSON_TABLE
										+ "."
										+ PersonOperation.PERSON_NAME_AND_FAMILY,
								PersonOperation.PERSON_TABLE + "."
										+ PersonOperation.PERSON_NATIONAL_CODE
										+ " AS pCode " },
						/* KhaneBehdasht.Code = Family.KhaneBehdashtCode AND */
						" khCode = " + FAMILY_KHANE_BEHDASHT_CODE + " AND "
						/* Person.NationalCode = Family.FatherCode AND */
						+ " pCode = ffCode", null, null, null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int codeIndex = resultIterator.getColumnIndex("fCode");
			int fatherCodeIndex = resultIterator.getColumnIndex("ffCode");
			int fatherNameIndex = resultIterator
					.getColumnIndex(PersonOperation.PERSON_NAME_AND_FAMILY);
			int khaneBehdashtCodeIndex = resultIterator
					.getColumnIndex(FAMILY_KHANE_BEHDASHT_CODE);
			int khaneBehdashtNameIndex = resultIterator
					.getColumnIndex("khName");
			int isAvailable = resultIterator.getColumnIndex("fIsA");

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(codeIndex);
				String newItemFatherCode = resultIterator
						.getString(fatherCodeIndex);
				String newItemFatherName = resultIterator
						.getString(fatherNameIndex);
				int newItemkhaneBehdahstCode = resultIterator
						.getInt(khaneBehdashtCodeIndex);
				String newItemKhaneBehdashtName = resultIterator
						.getString(khaneBehdashtNameIndex);
				String newItemIsAvailable = resultIterator
						.getString(isAvailable);

				FamilyItem newItem = new FamilyItem();
				newItem.setCode(newItemCode);
				newItem.setFatherCode(newItemFatherCode);
				newItem.setFatherNameAndFamily(newItemFatherName);
				newItem.setKhaneBehdashtCode(newItemkhaneBehdahstCode);
				newItem.setKhaneBehdashtName(newItemKhaneBehdashtName);
				newItem.setIsAvailable(Boolean.valueOf(newItemIsAvailable));

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}

	public ArrayList<FamilyItem> getAvailables() {
		ArrayList<FamilyItem> result = new ArrayList<FamilyItem>();
		Cursor resultIterator;

		resultIterator = database
				.query(FAMILY_TABLE + ", "
						+ KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE + ", "
						+ PersonOperation.PERSON_TABLE,
						new String[] {
								/* khane behdasht columns */
								KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE
										+ "."
										+ KhaneBehdashtOperation.KHANE_BEHDASHT_CODE
										+ " AS khCode ",
								KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE
										+ "."
										+ KhaneBehdashtOperation.KHANE_BEHDASHT_NAME
										+ " AS khName ",
								/* family columns */
								FAMILY_TABLE + "." + FAMILY_CODE + " AS fCode",
								FAMILY_TABLE + "." + FAMILY_FATHER_CODE
										+ " AS ffCode ",
								FAMILY_TABLE + "." + FAMILY_KHANE_BEHDASHT_CODE,
								FAMILY_TABLE + "." + FAMILY_ISAVAILABLE
										+ " AS fIsA ",
								/* person columns */
								PersonOperation.PERSON_TABLE
										+ "."
										+ PersonOperation.PERSON_NAME_AND_FAMILY,
								PersonOperation.PERSON_TABLE + "."
										+ PersonOperation.PERSON_NATIONAL_CODE
										+ " AS pCode " },
						/* KhaneBehdasht.Code = Family.KhaneBehdashtCode AND */
						" khCode = " + FAMILY_KHANE_BEHDASHT_CODE + " AND "
						/* Person.NationalCode = Family.FatherCode AND */
						+ " pCode = ffCode AND "
						/* Family.IsAvailable = ? */
						+ " fIsA = ? ", new String[] { String.valueOf(true) },
						null, null, null);

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {
			Log.d("MAKH", "#######################################");
			int codeIndex = resultIterator.getColumnIndex("fCode");
			int fatherCodeIndex = resultIterator.getColumnIndex("ffCode");
			int fatherNameIndex = resultIterator
					.getColumnIndex(PersonOperation.PERSON_NAME_AND_FAMILY);
			int khaneBehdashtCodeIndex = resultIterator
					.getColumnIndex(FAMILY_KHANE_BEHDASHT_CODE);
			int khaneBehdashtNameIndex = resultIterator
					.getColumnIndex("khName");
			int isAvailable = resultIterator.getColumnIndex("fIsA");

			do {
				// Create Items
				int newItemCode = resultIterator.getInt(codeIndex);
				String newItemFatherCode = resultIterator
						.getString(fatherCodeIndex);
				String newItemFatherName = resultIterator
						.getString(fatherNameIndex);
				int newItemkhaneBehdahstCode = resultIterator
						.getInt(khaneBehdashtCodeIndex);
				String newItemKhaneBehdashtName = resultIterator
						.getString(khaneBehdashtNameIndex);
				String newItemIsAvailable = resultIterator
						.getString(isAvailable);

				FamilyItem newItem = new FamilyItem();
				newItem.setCode(newItemCode);
				newItem.setFatherCode(newItemFatherCode);
				newItem.setFatherNameAndFamily(newItemFatherName);
				newItem.setKhaneBehdashtCode(newItemkhaneBehdahstCode);
				newItem.setKhaneBehdashtName(newItemKhaneBehdashtName);
				newItem.setIsAvailable(Boolean.valueOf(newItemIsAvailable));

				result.add(newItem);

			} while (resultIterator.moveToNext());

		}

		return result;
	}

	public FamilyItem getFamilyByFather(String fatherCode) {
		FamilyItem result = null;
		Cursor resultIterator;

		resultIterator = database
				.query(FAMILY_TABLE + ", "
						+ KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE + ", "
						+ PersonOperation.PERSON_TABLE,
						new String[] {
								/* khane behdasht columns */
								KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE
										+ "."
										+ KhaneBehdashtOperation.KHANE_BEHDASHT_CODE
										+ " AS khCode ",
								KhaneBehdashtOperation.KHANE_BEHDASHT_TABLE
										+ "."
										+ KhaneBehdashtOperation.KHANE_BEHDASHT_NAME
										+ " AS khName ",
								/* family columns */
								FAMILY_TABLE + "." + FAMILY_CODE + " AS fCode",
								FAMILY_TABLE + "." + FAMILY_FATHER_CODE
										+ " AS ffCode ",
								FAMILY_TABLE + "." + FAMILY_KHANE_BEHDASHT_CODE,
								FAMILY_TABLE + "." + FAMILY_ISAVAILABLE
										+ " AS fIsA ",
								/* person columns */
								PersonOperation.PERSON_TABLE
										+ "."
										+ PersonOperation.PERSON_NAME_AND_FAMILY,
								PersonOperation.PERSON_TABLE + "."
										+ PersonOperation.PERSON_NATIONAL_CODE
										+ " AS pCode " },
						/* KhaneBehdasht.Code = Family.KhaneBehdashtCode AND */
						" khCode = " + FAMILY_KHANE_BEHDASHT_CODE + " AND "
						/* Person.NationalCode = Family.FatherCode AND */
						+ " pCode = ffCode AND "
						/* Family.IsAvailable = ? */
						+ " fIsA = ? AND "
						/* Family.FatherCode = ? */
						+ " ffCode = ? ", new String[] { String.valueOf(true),
								fatherCode }, null, null, null);

		Log.d(this.getClass().getName(),
				String.valueOf(resultIterator.getCount()));

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {
			result = new FamilyItem();

			int codeIndex = resultIterator.getColumnIndex("fCode");
			int fatherCodeIndex = resultIterator.getColumnIndex("ffCode");
			int fatherNameIndex = resultIterator
					.getColumnIndex(PersonOperation.PERSON_NAME_AND_FAMILY);
			int khaneBehdashtCodeIndex = resultIterator
					.getColumnIndex(FAMILY_KHANE_BEHDASHT_CODE);
			int khaneBehdashtNameIndex = resultIterator
					.getColumnIndex("khName");
			int isAvailable = resultIterator.getColumnIndex("fIsA");

			// Create Items
			result.setCode(resultIterator.getInt(codeIndex));
			result.setFatherCode(resultIterator.getString(fatherCodeIndex));
			result.setFatherNameAndFamily(resultIterator
					.getString(fatherNameIndex));
			result.setKhaneBehdashtCode(resultIterator
					.getInt(khaneBehdashtCodeIndex));
			result.setKhaneBehdashtName(resultIterator
					.getString(khaneBehdashtNameIndex));
			result.setIsAvailable(Boolean.valueOf(resultIterator
					.getString(isAvailable)));
		}

		return result;
	}
}