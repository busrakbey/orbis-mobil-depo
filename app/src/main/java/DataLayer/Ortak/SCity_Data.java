package DataLayer.Ortak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import EntityLayer.Ortak.SCity;
import ToolLayer.OrbisDefaultException;

/**
 * Created by busrakbey on 22.02.2021
 */
public class SCity_Data extends DataController<SCity> {

    public SCity_Data(Context ctx) {
        super(ctx, new SCity());
    }

    public List<SCity> loadFromQuery(String queryStr) throws OrbisDefaultException {
        List<SCity> ilceList = new ArrayList<SCity>();
        try {
            db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryStr, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ilceList.add((SCity) CursorToObject(cursor));
                }
                cursor.close();
            }

        } catch (Exception e) {
            throw new OrbisDefaultException(e.toString());
        } finally {
            db.close();
            return ilceList;
        }
    }

    public SCity CursorToObject(Cursor cursor) throws OrbisDefaultException {
        SCity o = new SCity();
        o.setId(cursor.getLong(cursor.getColumnIndex("id")));
        o.setAdi(cursor.getString(cursor.getColumnIndex("adi")));
        return o;
    }

    public Boolean insertFromContent(List<SCity> itms) throws OrbisDefaultException {
        Boolean status = false;
        if (itms != null && itms.size() > 0) {
            Long m_id = 0L;
            db = helper.getWritableDatabase();
            db.beginTransaction();
            for (SCity kayit : itms) {
                long id = 0;
                try {
                    ContentValues line = new ContentValues();
                    line = ObjectToContentValues(kayit);
                    m_id = db.insertOrThrow("S_IL", null, line);
                    if (m_id > 0) {
                        status = true;
                        kayit.setMid(m_id);
                    } else {
                        status = false;
                        throw new OrbisDefaultException("oragacdata-insert:Kayit Eklenemedi, database tablosu hatalı !" + kayit.toString());
                    }

                } catch (SQLiteConstraintException e) {
                    status = false;
                    Log.d("DataController", e.getMessage());
                    throw new OrbisDefaultException("DataController(insert)Hata:" + e.getMessage());
                } catch (SQLiteDatatypeMismatchException e) {
                    Log.d("DataController", e.getMessage());
                    status = false;
                    throw new OrbisDefaultException("DataController(insert)Hata:" + e.getMessage());
                } catch (SQLiteException e) {
                    Log.d("DataController", e.getMessage());
                    status = false;
                    throw new OrbisDefaultException("DataController(insert)Hata:" + e.getMessage());
                } catch (Throwable e) {
                    Log.d("DataController:insert\n", e.getMessage());
                    status = false;
                    throw new OrbisDefaultException("DataController:insert\n:" + e.toString());
                }

            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        return status;
    }

    public ContentValues ObjectToContentValues(SCity o) throws OrbisDefaultException {
        ContentValues satir = new ContentValues();
        try {
            satir.put("id", o.getId());
            satir.put("adi", o.getAdi());

        } catch (Exception e) {
            throw new OrbisDefaultException(e.toString());
        } finally {
            return satir;
        }
    }


    public Boolean deleteAllBirim() throws OrbisDefaultException {
        Boolean status = true;
        try {
            db = helper.getWritableDatabase();
            db.beginTransaction();
            db.execSQL("DELETE FROM S_IL");
            Log.v("DD "," tablosunda  kayıtlar silindi. ");
        } catch (SQLiteException e) {
            e.printStackTrace();
            status = false;
            throw new OrbisDefaultException("DataController:clearDataTable->" + e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            status = false;
            throw new OrbisDefaultException("DataController:clearDataTable->" + e.getMessage());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return status;
    }

}