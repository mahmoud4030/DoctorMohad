package ir.unicodes.doctor.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ir.unicodes.doctor.classes.Variables;
import ir.unicodes.doctor.objects.Object_Data;

public class database extends SQLiteOpenHelper {

	public final String path="data/data/ir.unicodes.doctor/databases/";
	public final String DATABASE_NAME="dbdoctor.db";
	public SQLiteDatabase db;
	private String TAG = Variables.Tag;

	private SharedPreferences prefs;
	private int DATABASE_VERSION = 1;

	private final Context mycontext;

	private static final String TABLE_NEWS 		= "tblnews";
	private static final String TABLE_EXTRA 	= "tblextra";
	private static final String TABLE_SERVICES 	= "tblservice";

	static final String Sid			= "Sid";        // 1
	static final String Faction		= "Faction";	// 2
	static final String Title 		= "Title";      // 3
	static final String Content 	= "Content";    // 4
	static final String ImageUrl 	= "ImageUrl";   // 5
	static final String Favorite 	= "Favorite";   // 6
	static final String Category 	= "Category";   // 7
	static final String CategoryT 	= "CategoryT";  // 8

	public database(Context context) {
		super(context, "dbdoctor", null, 1);
		mycontext=context;
		prefs = mycontext.getSharedPreferences("pf", 0);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {}

	@Override
	public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {}

	public void useable(){
		boolean checkdb=checkdb();
		if(checkdb){
			Log.i(TAG, "db exists");
			if(prefs.getInt("db", 1) < DATABASE_VERSION){
				Log.i(TAG, "new database");
				try{
					mycontext.deleteDatabase(DATABASE_NAME);
					copydatabase();
				}catch(IOException e){e.printStackTrace();}
			}
		}else{
			Log.i(TAG, "db notexists");
			this.getReadableDatabase();
			try{
				copydatabase();
			}catch(IOException e){e.printStackTrace();}
		}
	}

	public void open(){
		db = SQLiteDatabase.openDatabase(path+DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
	}

	public void close(){
		db.close();
	}

	public boolean checkdb(){
		SQLiteDatabase db = null;
		try{
			db=SQLiteDatabase.openDatabase(path+DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLException e) {e.printStackTrace();}
		return db != null ? true:false ;
	}

	public void copydatabase() throws IOException{
		OutputStream myOutput = new FileOutputStream(path+DATABASE_NAME);
		byte[] buffer = new byte[1024];
		int length;
		InputStream myInput = mycontext.getAssets().open(DATABASE_NAME);
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myInput.close();
		myOutput.flush();
		myOutput.close();

		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("db", DATABASE_VERSION);
		editor.commit();
		prefs = null;
	}

	public int CountAll(String Column,String Index){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+Column+" = '"+ Index +"' ;", null);
		int s = cu.getCount();
		Log.i(TAG, "Count is: " + s);
		return s;
	}

	public int CountAllService(String Column,String Index,String Column1,String Index1){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+
				Column+" = '"+ Index +"' and "+
				Column1+" = '"+ Index1 +"' ;", null);
		int s = cu.getCount();
		Log.i(TAG, "Count is: " + s);
		return s;
	}

	public String DisplayAll(int row,int field,String Column,String Index){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+Column+" = '"+ Index +"' ;", null);
		cu.moveToPosition(row);
		String result = cu.getString(field);
		return result;
	}// end Display()

	public String DisplayAll(int row,int field,String Column,String Index,String Column1,String Index1){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+
				Column+" = '"+ Index +"' and "+
				Column1+" = '"+ Index1 +"' ;", null);
		cu.moveToPosition(row);
		String result = cu.getString(field);
		return result;
	}// end Display()

	public String DisplayOne(int field,String MColumn,String MIndex,String Column,String Index){
		Cursor cu = db.rawQuery("select * from "+ TABLE_NEWS +" where "+ MColumn +" = '"+ MIndex +"' and "+ Column +" = '"+ Index +"' ;", null);
		cu.moveToFirst();
		String result = cu.getString(field);
		return result;
	}// end Display()

	public void	update(String Column1, String newFav,String MColumn,String MIndex, String Column, String Index){
		db.execSQL("update "+ TABLE_NEWS +" set "+ Column1 +" = '" + newFav + "' where " + MColumn + " = '" + MIndex + "' and "+ Column +" = '"+ Index +"' ;");
		Log.i(TAG, "update");
	}

	public void Insert(Object_Data ob){
		ContentValues cv = new ContentValues();
		cv.put(Sid, ob.getSid());
		cv.put(Faction,ob.getFaction());
		cv.put(Title,ob.getTitle());
		cv.put(Content,ob.getContent());
		cv.put(ImageUrl,ob.getImage_url());
		cv.put(Favorite,ob.getFaction());
		cv.put(Category,ob.getCategory());
		cv.put(CategoryT,ob.getCategoryT());
		db.insert(TABLE_NEWS, Sid, cv);
		Log.i(TAG, "insert");
	}

	public void UpdateByGroup(Object_Data ob){
		ContentValues cv = new ContentValues();
		cv.put(Sid, ob.getSid());
		cv.put(Faction,ob.getFaction());
		cv.put(Title,ob.getTitle());
		cv.put(Content,ob.getContent());
		cv.put(ImageUrl,ob.getImage_url());
		cv.put(Category,ob.getCategory());
		cv.put(CategoryT,ob.getCategoryT());
		db.update(
				TABLE_NEWS,
				cv,
				//" Faction='"+ob.getFaction()+"' and "+
				//" Category='"+ob.getCategory()+"' and "+
				//" CategoryT='"+ob.getCategoryT()+"' and "+
				" Sid='"+ob.getSid()+"' ",
				null);
		Log.i(TAG, "update");
	}

	public void Update(Object_Data ob){
		ContentValues cv = new ContentValues();
		cv.put(Sid, ob.getSid());
		cv.put(Faction,ob.getFaction());
		cv.put(Title,ob.getTitle());
		cv.put(Content,ob.getContent());
		cv.put(ImageUrl,ob.getImage_url());
		cv.put(Category,ob.getCategory());
		cv.put(CategoryT,ob.getCategoryT());
		db.update(TABLE_NEWS, cv, " Sid='"+ob.getSid()+"' and "+" Faction='"+ob.getFaction()+"'", null);
		Log.i(TAG, "update");
	}

	public boolean CheckExistanceNews(String MColumn,String MIndex,String Column, String Index){
		Cursor cursor = db.rawQuery("select * from "+ TABLE_NEWS +" where "+ MColumn +" = '"+ MIndex +"'  and "+ Column +" = '"+ Index +"' ;", null);
		cursor.moveToFirst();
		int result = cursor.getCount();
		if(result == 0){
			Log.i(TAG,"false");
			return false;
		}
		else{
			Log.i(TAG,"true");
			return true;
		}
	}

	public int CountByGroub(String Column, String Index,String GroubBy){
		Cursor cu = db.rawQuery("SELECT * FROM "+ TABLE_NEWS +" WHERE "+
				Column+" = '"+ Index +"' GROUP BY "+ GroubBy +";", null);
		int s = cu.getCount();
		Log.i(TAG, "Count is: " + s);
		return s;
	}

	public String SelectAllByGroup(int row,int field,String Column, String Index,String GroubBy){
		Cursor cu = db.rawQuery("SELECT * FROM "+ TABLE_NEWS +" WHERE "+
				Column+" = '"+ Index +"' GROUP BY "+ GroubBy +";", null);
		cu.moveToPosition(row);
		String result = cu.getString(field);
		return result;
	}

	public boolean DeleteTabele1(String Column,String Index){
		Log.i(TAG,"delte");
		return db.delete(TABLE_NEWS, Column + "=" + Index, null) > 0;
	}

	@SuppressLint("DefaultLocale")
	public void DeleteTabele2(String Column, String Index) {
		db.execSQL(String.format(
				"DELETE FROM %s WHERE %s = %d",
				TABLE_NEWS,
				Column,Index
		));
	}




	// ************* TABLE_EXTRA ******************

	public boolean CheckExistanceExtra(String MColumn,String MIndex){
		Cursor cursor = db.rawQuery("select * from "+ TABLE_EXTRA +" where "+ MColumn +" = '"+ MIndex +"' ;", null);
		cursor.moveToFirst();
		int result = cursor.getCount();
		if(result == 0){
			Log.i(TAG,"false");
			return false;
		}
		else{
			Log.i(TAG,"true");
			return true;
		}
	}

	public void InsertExtra(Object_Data ob){
		ContentValues cv = new ContentValues();
		cv.put(Sid,ob.getSid());
		cv.put(Title, ob.getTitle());
		cv.put(Content,ob.getContent());
		db.insert(TABLE_EXTRA, Sid, cv);
		Log.i(TAG, "insert");
	}

	public void UpdateExtra(Object_Data ob){
		ContentValues cv = new ContentValues();
		cv.put(Sid,ob.getSid());
		cv.put(Title, ob.getTitle());
		cv.put(Content,ob.getContent());
		db.update(TABLE_EXTRA,cv," Sid='"+ob.getSid()+"'",null);
		Log.i(TAG, "update");
	}

	public String DisplayExtra(int field,String Column,String Index){
		Log.i(Variables.Tag,"Column in db: "+Column);
		Cursor cu = db.rawQuery("select * from "+ TABLE_EXTRA +" where "+ Column +" = '"+ Index +"' ;", null);
		cu.moveToFirst();
		String result = cu.getString(field);
		return result;
	}// end Display()


}// end class