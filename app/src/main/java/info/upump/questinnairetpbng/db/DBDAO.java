package info.upump.questinnairetpbng.db;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;

import static info.upump.questinnairetpbng.db.DataBaseHelper.PASS;


/**
 * Created by explo on 26.09.2017.
 */

public class DBDAO {
    protected SQLiteDatabase database;
    private DataBaseHelper dataBaseHelper;
    protected Context context;

    public DBDAO(Context context) {
        this.context = context;
        dataBaseHelper = DataBaseHelper.getHelper(context);
        open();
    }

    public void open() {
        if (dataBaseHelper == null)
            dataBaseHelper = DataBaseHelper.getHelper(context);
        database = dataBaseHelper.getWritableDatabase(PASS);

    }

    public void close() {
        dataBaseHelper.close();
        database = null;
    }
}
