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

import EntityLayer.Ortak.SKoyBelde;
import ToolLayer.OrbisDefaultException;

/**
 * Created by busrakbey on 22.02.2021
 */
public class SKoyBelde_Data extends DataController<SKoyBelde> {

    public SKoyBelde_Data(Context ctx) {
        super(ctx, new SKoyBelde());
    }

    public List<SKoyBelde> loadFromQuery(String queryStr) throws OrbisDefaultException {
        List<SKoyBelde> ilceList = new ArrayList<SKoyBelde>();
        try {
            db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryStr, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ilceList.add((SKoyBelde) CursorToObject(cursor));
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

    public SKoyBelde CursorToObject(Cursor cursor) throws OrbisDefaultException {
        SKoyBelde o = new SKoyBelde();
        o.setId(cursor.getLong(cursor.getColumnIndex("id")));
        o.setKoyAdi(cursor.getString(cursor.getColumnIndex("koyAdi")));
        o.setIlAdi(cursor.getString(cursor.getColumnIndex("ilAdi")));
        o.setMulkiYerAdi(cursor.getString(cursor.getColumnIndex("mulkiYerAdi")));
        o.setBirimAdi(cursor.getString(cursor.getColumnIndex("birimAdi")));
        return o;
    }

    public Boolean insertFromContent(List<SKoyBelde> itms) throws OrbisDefaultException {
        Boolean status = false;
        if (itms != null && itms.size() > 0) {
            Long m_id = 0L;
            db = helper.getWritableDatabase();
            db.beginTransaction();
            for (SKoyBelde kayit : itms) {
                long id = 0;
                try {
                    ContentValues line = new ContentValues();
                    line = ObjectToContentValues(kayit);
                    m_id = db.insertOrThrow("S_KOY_BELDE", null, line);
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

    public ContentValues ObjectToContentValues(SKoyBelde o) throws OrbisDefaultException {
        ContentValues satir = new ContentValues();
        try {
            satir.put("id", o.getId());
            satir.put("koyAdi", o.getKoyAdi());
            satir.put("ilAdi", o.getIlAdi());
            satir.put("mulkiYerAdi", o.getMulkiYerAdi());
            satir.put("birimAdi", o.getBirimAdi());

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
            db.execSQL("DELETE FROM S_KOY_BELDE");
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