package contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import database.MyDbOpenHelper;

/**
 * Created by kason_zhang on 9/5/2016.
 */
public class MyContentProvider extends ContentProvider {
    private static final int BookAll = 1;
    private static final int BookOneByOne = 2;
    private MyDbOpenHelper myDbOpenHelper;
    private static UriMatcher uriMatcher;
    private static final String AUTHORITY = "com.ui.kason_zhang.customcontentproviderdemo.provider";
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BookAll);
        uriMatcher.addURI(AUTHORITY,"book/#",BookOneByOne);
    }
    @Override
    public boolean onCreate() {
        myDbOpenHelper = new MyDbOpenHelper(getContext(),"BookStore.db",null,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection
            , String[] selectionArgs, String sortOrder) {
        SQLiteDatabase readableDatabase = myDbOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        //匹配Uri，进而查看是想获取所有数据，还是单条数据;
        switch (uriMatcher.match(uri)){
            case BookAll:
                cursor = readableDatabase.query("Book"
                        , projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BookOneByOne:
                String bookid = uri.getPathSegments().get(1);
                cursor = readableDatabase.query("Book"
                        , projection, "id = ?", new String[]{bookid}, null, null, sortOrder);
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case BookAll:
                return "vnd.android.cursor.dir/vnd.com.ui.kason_zhang." +
                        "customcontentproviderdemo.provider" +
                        ".book";
            case BookOneByOne:
                return "vnd.android.cursor.item/vnd.com.ui.kason_zhang." +
                        "customcontentproviderdemo.provider" +
                        ".book";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        Uri uri_ = null;
        switch (uriMatcher.match(uri)){
            case BookAll:

            case BookOneByOne:
                long bookid = writableDatabase.insert("Book", null, values);
                uri_ = uri.parse("content" + "//" + AUTHORITY + "/book/" + bookid);
                break;
        }
        return uri_;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        //writableDatabase.delete("Book","i")
        int bookRows = 0;
        switch (uriMatcher.match(uri)){
            case BookAll:
                bookRows = writableDatabase.delete("Book", selection, selectionArgs);
                break;
            case BookOneByOne:
                String s = uri.getPathSegments().get(0);
                bookRows = writableDatabase.delete("Book", "id = ?", new String[]{s});
                break;
        }
        return bookRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int bookRows = 0;
        SQLiteDatabase writableDatabase = myDbOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case BookAll:
                bookRows = writableDatabase.update("Book",values,selection,selectionArgs);
                break;
            case BookOneByOne:
                String s = uri.getPathSegments().get(0);
                bookRows = writableDatabase.update("Book", values, "id = ?", new String[]{s});
        }
        return bookRows;
    }
}
