package gdin.com.penpi.dbUtils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名称：PenPi
 * 作者：Administrator
 * 时间：2016.12.03
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;


    private static final String DB_NANE = "MyOrderRecord.db";   //数据库名称
    private static final int DB_VERSION = 1;                    //数据库版本

    public static final String TABLE_IN_NAME = "MyGrabOrder";
    public static final String TABLE_OUT_NAME = "MyPublishOrder";

    public static final String TABLE_ORDER_ID = "id";
    public static final String TABLE_STAART_PLACE = "start_place";
    public static final String TABLE_END_PLACE = "end_place";
    public static final String TABLE_PEOPLE_NAME = "name";
    public static final String TABLE_PHONE = "phone_number";
    public static final String TABLE_CHARGES = "charges";
    public static final String TABLE_REMARK = "remark";
    public static final String TABLE_STATE = "state";
    public static final String TABLE_DATE    = "date";




//    创建抢单表
    public static final String CREATE_IN_ORDER = "create table "+ TABLE_IN_NAME + "("
            + TABLE_ORDER_ID + " text primary key, "
            + TABLE_STAART_PLACE + " text, "
            + TABLE_END_PLACE + " text, "
            + TABLE_PEOPLE_NAME + " text, "
            + TABLE_PHONE + " text, "
            + TABLE_CHARGES + " text, "
            + TABLE_REMARK + " text, "
            + TABLE_STATE + " text, "
            + TABLE_DATE + " text)";
//    创建发单表
    public static final String CREATE_OUT_ORDER = "create table "+ TABLE_OUT_NAME + "("
            + TABLE_ORDER_ID + " text primary key, "
            + TABLE_STAART_PLACE + " text, "
            + TABLE_END_PLACE + " text, "
            + TABLE_PEOPLE_NAME + " text, "
            + TABLE_PHONE + " text, "
            + TABLE_CHARGES + " text, "
            + TABLE_REMARK + " text, "
            + TABLE_STATE + " text, "
            + TABLE_DATE + " text)";

    public MyDatabaseHelper(Context context) {
        super(context.getApplicationContext(), DB_NANE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_IN_ORDER);
            db.execSQL(CREATE_OUT_ORDER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
