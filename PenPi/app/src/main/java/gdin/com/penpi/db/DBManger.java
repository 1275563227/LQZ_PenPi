package gdin.com.penpi.db;

import android.content.Context;

/**
 * 项目名称：PenPi
 * 作者：Administrator
 * 时间：2016.12.03
 */
public class DBManger {
    private static MyDatabaseHelper mHelper;

    public static MyDatabaseHelper getInstance(Context context) {
        if (mHelper == null) {
            synchronized (MyDatabaseHelper.class) {
                if (mHelper == null) {
                    mHelper = new MyDatabaseHelper(context);
                }
            }
        }
        return mHelper;
    }
}
