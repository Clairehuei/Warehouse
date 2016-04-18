package com.fabric.warehouse.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fabric.warehouse.Helper.MyDBHelper;
import com.fabric.warehouse.Model.Product;
import com.fabric.warehouse.Model.instance.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 6193 on 2016/4/15.
 */
public class DatabaseDAO {

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public DatabaseDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    public static final String TABLE_NAME = "item";// 表格名稱
    public static final String KEY_ID = "_id";// 編號表格欄位名稱，固定不變
    // 其它表格欄位名稱
    public static final String DATETIME_COLUMN = "datetime";
    public static final String TITLE_COLUMN = "title";
    public static final String CONTENT_COLUMN = "content";
    public static final String FILENAME_COLUMN = "filename";
    public static final String LATITUDE_COLUMN = "latitude";
    public static final String LONGITUDE_COLUMN = "longitude";
    public static final String LASTMODIFY_COLUMN = "lastmodify";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + DATETIME_COLUMN + " INTEGER NOT NULL, "
                            + TITLE_COLUMN + " TEXT NOT NULL, "
                            + CONTENT_COLUMN + " TEXT NOT NULL, "
                            + FILENAME_COLUMN + " TEXT, "
                            + LATITUDE_COLUMN + " REAL, "
                            + LONGITUDE_COLUMN + " REAL, "
                            + LASTMODIFY_COLUMN + " INTEGER)";


    /**
     * 關閉資料庫，一般的應用都不需要修改
     */
    public void close() {
        db.close();
    }


    /**
     * 新增參數指定的物件
     * @param item
     * @return
     */
    public Item insert(Item item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(DATETIME_COLUMN, item.getDatetime());
        cv.put(TITLE_COLUMN, item.getTitle());
        cv.put(CONTENT_COLUMN, item.getContent());
        cv.put(FILENAME_COLUMN, item.getFileName());
        cv.put(LATITUDE_COLUMN, item.getLatitude());
        cv.put(LONGITUDE_COLUMN, item.getLongitude());
        cv.put(LASTMODIFY_COLUMN, item.getLastModify());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        item.setId(id);
        // 回傳結果
        return item;
    }


    /**
     * 修改參數指定的物件
     * @param item
     * @return
     */
    public boolean update(Item item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(DATETIME_COLUMN, item.getDatetime());
        cv.put(TITLE_COLUMN, item.getTitle());
        cv.put(CONTENT_COLUMN, item.getContent());
        cv.put(FILENAME_COLUMN, item.getFileName());
        cv.put(LATITUDE_COLUMN, item.getLatitude());
        cv.put(LONGITUDE_COLUMN, item.getLongitude());
        cv.put(LASTMODIFY_COLUMN, item.getLastModify());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + item.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }


    /**
     * 刪除參數指定編號的資料
     * @param id
     * @return
     */
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    /**
     * 讀取所有記事資料
     * @return
     */
    public List<Item> getAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }


    /**
     * 測試初始化查詢資料
     * @return
     */
    public List<Product> initData() {
        System.out.println("==initData==");
        List<Product> result = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " limit 0,15 ", null);

        while (cursor.moveToNext()) {
            result.add(getProductRecord(cursor));
        }

        cursor.close();
        return result;
    }


    public List<Product> getMoreData() {
        System.out.println("==getMoreData==");
        List<Product> result = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " limit 16,19 ", null);

        while (cursor.moveToNext()) {
            result.add(getProductRecord(cursor));
        }

        cursor.close();
        return result;
    }


    /**
     * 取得指定編號的資料物件
     * @param id
     * @return
     */
    public Item get(long id) {
        // 準備回傳結果用的物件
        Item item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }


    /**
     * 把Cursor目前的資料包裝為物件
     * @param cursor
     * @return
     */
    public Item getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Item result = new Item();

        result.setId(cursor.getLong(0));
        result.setDatetime(cursor.getLong(1));
        result.setTitle(cursor.getString(3));
        result.setContent(cursor.getString(4));
        result.setFileName(cursor.getString(5));
        result.setLatitude(cursor.getDouble(6));
        result.setLongitude(cursor.getDouble(7));
        result.setLastModify(cursor.getLong(8));

        // 回傳結果
        return result;
    }


    public Product getProductRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Product result = new Product();
        result.setName(cursor.getString(2));
        result.setQuantity(cursor.getString(3));

        // 回傳結果
        return result;
    }


    /**
     * 取得資料數量
     * @return
     */
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        cursor.close();

        return result;
    }


    /**
     * 建立範例資料
     */
    public void sample() {
//        Item item = new Item(0, new Date().getTime(), "關於Android Tutorial的事情.", "Hello content", "","", 0,0,0);
//        Item item2 = new Item(1, new Date().getTime(), "一隻非常可愛的小狗狗!", "她的名字叫「大熱狗」，又叫\n作「奶嘴」，是一隻非常可愛\n的小狗。", "","", 25.04719, 121.516981, 0);
//        Item item3 = new Item(2, new Date().getTime(), "一首非常好聽的音樂！", "Hello content", "","", 0, 0, 0);
//        Item item4 = new Item(3, new Date().getTime(), "儲存在資料庫的資料", "Hello content", "",",", 0, 0, 0);

        Item item01 = new Item(4, new Date().getTime(), "商品01", "101", "",",", 0, 0, 0);
        Item item02 = new Item(5, new Date().getTime(), "商品02", "102", "",",", 0, 0, 0);
        Item item03 = new Item(6, new Date().getTime(), "商品03", "103", "",",", 0, 0, 0);
        Item item04 = new Item(7, new Date().getTime(), "商品04", "104", "",",", 0, 0, 0);
        Item item05 = new Item(8, new Date().getTime(), "商品05", "105", "",",", 0, 0, 0);
        Item item06 = new Item(9, new Date().getTime(), "商品06", "106", "",",", 0, 0, 0);
        Item item07 = new Item(10, new Date().getTime(), "商品07", "107", "",",", 0, 0, 0);
        Item item08 = new Item(11, new Date().getTime(), "商品08", "108", "",",", 0, 0, 0);
        Item item09 = new Item(12, new Date().getTime(), "商品09", "109", "",",", 0, 0, 0);
        Item item10 = new Item(13, new Date().getTime(), "商品10", "110", "",",", 0, 0, 0);
        Item item11 = new Item(14, new Date().getTime(), "商品11", "111", "",",", 0, 0, 0);
        Item item12 = new Item(15, new Date().getTime(), "商品12", "112", "",",", 0, 0, 0);
        Item item13 = new Item(16, new Date().getTime(), "商品13", "113", "",",", 0, 0, 0);
        Item item14 = new Item(17, new Date().getTime(), "商品14", "114", "",",", 0, 0, 0);
        Item item15 = new Item(18, new Date().getTime(), "商品15", "115", "",",", 0, 0, 0);
        Item item16 = new Item(19, new Date().getTime(), "商品16", "116", "",",", 0, 0, 0);
        Item item17 = new Item(20, new Date().getTime(), "商品17", "117", "",",", 0, 0, 0);
        Item item18 = new Item(21, new Date().getTime(), "商品18", "118", "",",", 0, 0, 0);
        Item item19 = new Item(22, new Date().getTime(), "商品19", "119", "",",", 0, 0, 0);
        Item item20 = new Item(23, new Date().getTime(), "商品20", "120", "",",", 0, 0, 0);

//        insert(item);
//        insert(item2);
//        insert(item3);
//        insert(item4);
        insert(item01);
        insert(item02);
        insert(item03);
        insert(item04);
        insert(item05);
        insert(item06);
        insert(item07);
        insert(item08);
        insert(item09);
        insert(item10);
        insert(item11);
        insert(item12);
        insert(item13);
        insert(item14);
        insert(item15);
        insert(item16);
        insert(item17);
        insert(item18);
        insert(item19);
        insert(item20);
    }

}
