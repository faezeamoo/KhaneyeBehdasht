package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PersonOperation {
	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	// Table Name
	public static final String PERSON_TABLE = "Person";
	// Table Columns
	public static final String PERSON_NAME_AND_FAMILY = "NameAndFamily";
	public static final String PERSON_NATIONAL_CODE = "NationalCode";
	public static final String PERSON_FATHER_CODE = "FatherCode";
	public static final String PERSON_MOTHER_CODE = "MotherCode";
	public static final String PERSON_FAMILY_CODE = "FamilyCode";
	public static final String PERSON_BIME_CODE = "BimeCode";
	public static final String PERSON_POLICY_CODE = "PolicyCode";
	public static final String PERSON_IS_ALIVE = "IsAlive";
	// other
	private SQLiteDatabase database;

	/*
	 * #########################################################################
	 * ############################# basic Methods #############################
	 * #########################################################################
	 */
	public PersonOperation() {
		database = MainDatabaseOpenHelper.getDatabase();
	}

	/*
	 * #########################################################################
	 * ########################## database operations ##########################
	 * #########################################################################
	 */

	public String insert(String nameAndFmaily, String nationalCode,
			String fatherCode, String motherCode, int familyCode, int bimeCode,
			String PolicyCode, boolean isAlive) {
		ContentValues values = new ContentValues();
		values.put(PERSON_NAME_AND_FAMILY, nameAndFmaily);
		values.put(PERSON_NATIONAL_CODE, nationalCode);
		values.put(PERSON_FATHER_CODE, fatherCode);
		values.put(PERSON_MOTHER_CODE, motherCode);
		values.put(PERSON_FAMILY_CODE, familyCode);
		values.put(PERSON_BIME_CODE, bimeCode);
		values.put(PERSON_POLICY_CODE, PolicyCode);
		values.put(PERSON_IS_ALIVE, String.valueOf(isAlive));

		try {
			database.insertOrThrow(PERSON_TABLE, null, values);
		} catch (Exception e) {
			Log.e(this.getClass().getName(), e.getMessage());
			return e.getMessage();
		}

		return "";
	}

	public String insert(PersonItem row) {
		return this.insert(row.getNameAndFamily(), row.getNationalCode(),
				row.getFatherCode(), row.getMotherCode(), row.getFamilyCode(),
				row.getBimeCode(), row.getPolicyCode(), row.getIsAlive());

	}
	
	public void saveChanges(String nameAndFmaily, String nationalCode,
			String fatherCode, String motherCode, int familyCode, int bimeCode,
			String PolicyCode, boolean isAlive) {
		
		ContentValues values = new ContentValues();
		values.put(PERSON_NAME_AND_FAMILY, nameAndFmaily);
		values.put(PERSON_NATIONAL_CODE, nationalCode);
		values.put(PERSON_FATHER_CODE, fatherCode);
		values.put(PERSON_MOTHER_CODE, motherCode);
		values.put(PERSON_FAMILY_CODE, familyCode);
		values.put(PERSON_BIME_CODE, bimeCode);
		values.put(PERSON_POLICY_CODE, PolicyCode);
		values.put(PERSON_IS_ALIVE, String.valueOf(isAlive));
		
		database.update(PERSON_TABLE, values, PERSON_NATIONAL_CODE + "=?",
				new String[] { nationalCode });
	}

	public void saveChanges(PersonItem targetItem) {
		ContentValues values = new ContentValues();
		values.put(PERSON_NAME_AND_FAMILY, targetItem.getNameAndFamily());
		values.put(PERSON_NATIONAL_CODE, targetItem.getNationalCode());
		values.put(PERSON_FATHER_CODE, targetItem.getFatherCode());
		values.put(PERSON_MOTHER_CODE, targetItem.getMotherCode());
		values.put(PERSON_FAMILY_CODE, targetItem.getFamilyCode());
		values.put(PERSON_BIME_CODE, targetItem.getBimeCode());
		values.put(PERSON_POLICY_CODE, targetItem.getPolicyCode());
		values.put(PERSON_IS_ALIVE, targetItem.getIsAliveAsString());

		database.update(PERSON_TABLE, values, PERSON_NATIONAL_CODE + "=?",
				new String[] { targetItem.getNationalCode() });
	}

	public ArrayList<PersonItem> getAll() {
		ArrayList<PersonItem> result = new ArrayList<PersonItem>();
		Cursor resultIterator;

		resultIterator = database.query(PERSON_TABLE, null, null, null, null,
				null, null);

		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int nameAndFamilyIndex = resultIterator
					.getColumnIndex(PERSON_NAME_AND_FAMILY);
			int nationalCodeIndex = resultIterator
					.getColumnIndex(PERSON_NATIONAL_CODE);
			int fatherCodeIndex = resultIterator
					.getColumnIndex(PERSON_FATHER_CODE);
			int motherCodeIndex = resultIterator
					.getColumnIndex(PERSON_MOTHER_CODE);
			int familyCodeIndex = resultIterator
					.getColumnIndex(PERSON_FAMILY_CODE);
			int bimeCodeIndex = resultIterator.getColumnIndex(PERSON_BIME_CODE);
			int policyCodeIndex = resultIterator
					.getColumnIndex(PERSON_POLICY_CODE);
			int isAliveIndex = resultIterator.getColumnIndex(PERSON_IS_ALIVE);

			do {
				// Create Items
				String nameAndFmaily = resultIterator
						.getString(nameAndFamilyIndex);
				String nationalCode = resultIterator
						.getString(nationalCodeIndex);
				String fatherCode = resultIterator.getString(fatherCodeIndex);
				String motherCode = resultIterator.getString(motherCodeIndex);
				int familyCode = resultIterator.getInt(familyCodeIndex);
				int bimeCode = resultIterator.getInt(bimeCodeIndex);
				String policyCode = resultIterator.getString(policyCodeIndex);
				boolean isAlive = Boolean.valueOf(resultIterator
						.getString(isAliveIndex));

				PersonItem newItem = new PersonItem();
				newItem.setNameAndFamily(nameAndFmaily);
				newItem.setNationalCode(nationalCode);
				newItem.setFatherCode(fatherCode);
				newItem.setMotherCode(motherCode);
				newItem.setFamilyCode(familyCode);
				newItem.setBimeCode(bimeCode);
				newItem.setPolicyCode(policyCode);
				newItem.setIsAlive(isAlive);

				result.add(newItem);
			} while (resultIterator.moveToNext());

		}

		return result;
	}

	public ArrayList<PersonItem> getLike(String NAF, String NC) {
		ArrayList<PersonItem> result = new ArrayList<PersonItem>();
		Cursor resultIterator;
		String[] args = new String[] { '%' + NAF + '%', '%' + NC + '%'};

		resultIterator = database.query(PERSON_TABLE, null,
				PERSON_NAME_AND_FAMILY + " LIKE ? AND "
						+ PERSON_NATIONAL_CODE + " LIKE ? ", args, null,
				null, null);

		Log.d("MAKH", "naf : " + NAF + ", nc : " + NC + ", count : " + resultIterator.getCount());
		
		// Create Result List if it is exist.
		if (resultIterator.moveToFirst()) {

			int nameAndFamilyIndex = resultIterator
					.getColumnIndex(PERSON_NAME_AND_FAMILY);
			int nationalCodeIndex = resultIterator
					.getColumnIndex(PERSON_NATIONAL_CODE);
			int fatherCodeIndex = resultIterator
					.getColumnIndex(PERSON_FATHER_CODE);
			int motherCodeIndex = resultIterator
					.getColumnIndex(PERSON_MOTHER_CODE);
			int familyCodeIndex = resultIterator
					.getColumnIndex(PERSON_FAMILY_CODE);
			int bimeCodeIndex = resultIterator.getColumnIndex(PERSON_BIME_CODE);
			int policyCodeIndex = resultIterator
					.getColumnIndex(PERSON_POLICY_CODE);
			int isAliveIndex = resultIterator.getColumnIndex(PERSON_IS_ALIVE);

			do {
				// Create Items
				String nameAndFmaily = resultIterator
						.getString(nameAndFamilyIndex);
				String nationalCode = resultIterator
						.getString(nationalCodeIndex);
				String fatherCode = resultIterator.getString(fatherCodeIndex);
				String motherCode = resultIterator.getString(motherCodeIndex);
				int familyCode = resultIterator.getInt(familyCodeIndex);
				int bimeCode = resultIterator.getInt(bimeCodeIndex);
				String policyCode = resultIterator.getString(policyCodeIndex);
				boolean isAlive = Boolean.valueOf(resultIterator
						.getString(isAliveIndex));

				PersonItem newItem = new PersonItem();
				newItem.setNameAndFamily(nameAndFmaily);
				newItem.setNationalCode(nationalCode);
				newItem.setFatherCode(fatherCode);
				newItem.setMotherCode(motherCode);
				newItem.setFamilyCode(familyCode);
				newItem.setBimeCode(bimeCode);
				newItem.setPolicyCode(policyCode);
				newItem.setIsAlive(isAlive);

				result.add(newItem);
			} while (resultIterator.moveToNext());

		}

		return result;
	}
}
