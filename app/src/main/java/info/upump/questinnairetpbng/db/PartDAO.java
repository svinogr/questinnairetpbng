package info.upump.questinnairetpbng.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.questinnairetpbng.entity.Part;


public class PartDAO extends DBDAO {
    public PartDAO(Context context) {
        super(context);
    }

    public long save(Part part) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_SHORT_NAME, part.getShortName());

        cv.put(DataBaseHelper.TABLE_KEY_NAME, part.getName());

        return database.insert(DataBaseHelper.TABLE_PART, null, cv);
    }

    public List<Part> getParts(){
        Cursor cursor = null;
        List<Part> parts = new ArrayList<>();
        try {
            cursor = database.query(DataBaseHelper.TABLE_PART,
                    new String[]{
                            DataBaseHelper.TABLE_KEY_ID,
                            DataBaseHelper.TABLE_KEY_SHORT_NAME,
                            DataBaseHelper.TABLE_KEY_NAME},
                    null, null, null, null, null
            );
            if (cursor.moveToFirst()) {
                do {
                    Part part = new Part();
                    part.setId(cursor.getInt(0));
                    part.setShortName(cursor.getString(1));
                    part.setName(cursor.getString(2));
                    parts.add(part);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return parts;
    }



    public Part getById(int id){
        return null;
    }
}
