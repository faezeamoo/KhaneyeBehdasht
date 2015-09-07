package com.simplegroup.khanebehdasht.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class MainDatabaseOpenHelper {
	/*
	 * #########################################################################
	 * ############################ Attributes #################################
	 * #########################################################################
	 */
	// Constant
	private static final String DATABASE_NAME = "KhaneBehdashtDB";
	private static final String DATA_PATH = Environment.getDataDirectory()
			+ "/data/";

	// Variable
	private static SQLiteDatabase db;
	private static Context context;

	/*
	 * #########################################################################
	 * ############################### basic Methods ###########################
	 * #########################################################################
	 */
	// Constructor
	public static void Start(Context context) {
		MainDatabaseOpenHelper.context = context;
		open();
		// call onCreate function.
		onCreate(db);
	}

	public static void open() {
		// MAKH :
		// this statement open database and if couldn't find database file
		// create a new database file.
		// and if there is other problem throw an exception in log cat with a
		// tag equal database name.
		try {
			Log.d(DATABASE_NAME, DATA_PATH + context.getPackageName() + "/"
					+ DATABASE_NAME);
			db = SQLiteDatabase.openDatabase(
					DATA_PATH + context.getPackageName() + "/" + DATABASE_NAME,
					null, SQLiteDatabase.CREATE_IF_NECESSARY);
		} catch (Exception e) {
			Log.e(DATABASE_NAME, e.getMessage());
		}
	}

	public static void close() {
		// MAKH :
		// this statement open database and if couldn't find database file
		// create a new database file.
		// and if there is other problem throw an exception in log cat with a
		// tag equal database name.
		try {
			db.close();
		} catch (Exception e) {
			Log.e(DATABASE_NAME, e.getMessage());
		}
	}

	// this function was public MAKH change it to private for security reason.
	private static void onCreate(SQLiteDatabase db) {
		// All Table Creations Calls Here
		createBimeTable(db);
		createDrugTable(db);
		createExpertTable(db);
		createKhaneBehdashtTable(db);
		createMaladyTable(db);
		createPersonTable(db);
		createFamilyTable(db);
		createMorajeeTable(db);
	}

	public static void upgrade() {
		// drops
		dropKhaneBehdashtTable(db);
		dropBimeTable(db);
		dropDrugTable(db);
		dropExpertTable(db);
		dropMaladyTable(db);
		dropPersonTable(db);
		dropFamilyTable(db);
		dropMorajeeTable(db);

		// creates
		createBimeTable(db);
		createKhaneBehdashtTable(db);
		createDrugTable(db);
		createExpertTable(db);
		createMaladyTable(db);
		createPersonTable(db);
		createFamilyTable(db);
		createMorajeeTable(db);

		// throw log
		Log.d(DATABASE_NAME, "Database is upgraded.");
	}

	// MAKH : this function return a SQLitedatabase for other model classes.
	public static SQLiteDatabase getDatabase() {
		return db;
	}

	/*
	 * #########################################################################
	 * ########### there are tables create and drop functions in below. ########
	 * #########################################################################
	 */
	// bime
	private static void createBimeTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS Bime ("
				+ "Code INTEGER PRIMARY KEY," + "Name TEXT,"
				+ "IsAvailable TEXT" + ")";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropBimeTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS Bime";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	// khane behdasht
	private static void createKhaneBehdashtTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS KhaneBehdasht ("
				+ "Code INTEGER PRIMARY KEY AUTOINCREMENT," + "Name TEXT,"
				+ "IsAvailable TEXT" + ")";
		try {
			MainDatabaseOpenHelper.getDatabase().execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropKhaneBehdashtTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS KhaneBehdasht";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	// drug
	private static void createDrugTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS Drug ("
				+ "Code INTEGER PRIMARY KEY," + "Name TEXT,"
				+ "IsAvailable TEXT" + ")";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropDrugTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS Drug";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	// expert
	private static void createExpertTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS Expert ("
				+ "Code INTEGER PRIMARY KEY," + "Name TEXT,"
				+ "IsAvailable TEXT" + ")";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropExpertTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS Expert";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	// malady
	private static void createMaladyTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS Malady ("
				+ "Code INTEGER PRIMARY KEY ," + "Name TEXT,"
				+ "IsAvailable TEXT" + ")";

		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropMaladyTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS Malady";

		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	// person
	private static void createPersonTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS Person ("
				+ "NameAndFamily TEXT," + "NationalCode TEXT PRIMARY KEY,"
				+ "FatherCode TEXT," + "MotherCode TEXT,"
				+ "FamilyCode INTEGER," + "BimeCode INTEGER,"
				+ "PolicyCode TEXT," + "IsAlive TEXT" + ")";
		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropPersonTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS Person";

		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	// Family
	private static void createFamilyTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS Family ("
				+ "Code INTEGER PRIMARY KEY ," + "FatherCode TEXT,"
				+ "KhaneBehdashtCode INTEGER," + "IsAvailable TEXT" + ")";

		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropFamilyTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS Family";

		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	// morajee
	private static void createMorajeeTable(SQLiteDatabase db) {
		String sqlExperssion = "CREATE TABLE IF NOT EXISTS Morajee ("
				+ " PersonCode TEXT , " + " MDate TEXT , "
				+ " MaladyCode INTEGER , " + " Seeing TEXT , "
				+ " Tajviz TEXT " + ", PRIMARY KEY (PersonCode, MDate))";

		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	private static void dropMorajeeTable(SQLiteDatabase db) {
		String sqlExperssion = "DROP TABLE IF EXISTS Morajee";

		try {
			db.execSQL(sqlExperssion);
		} catch (Exception e) {
			Log.w(DATABASE_NAME, e.getMessage());
		}
	}

	/*
	 * #########################################################################
	 * ########### end of the entity create and drop functions. ################
	 * #########################################################################
	 */

}
