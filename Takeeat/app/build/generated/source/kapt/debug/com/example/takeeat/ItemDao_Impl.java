package com.example.takeeat;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ItemDao_Impl implements ItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ShoppingListItem> __insertionAdapterOfShoppingListItem;

  private final EntityDeletionOrUpdateAdapter<ShoppingListItem> __deletionAdapterOfShoppingListItem;

  private final EntityDeletionOrUpdateAdapter<ShoppingListItem> __updateAdapterOfShoppingListItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ItemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfShoppingListItem = new EntityInsertionAdapter<ShoppingListItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `shoppinglistitem` (`id`,`itemname`,`itemamount`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ShoppingListItem value) {
        stmt.bindLong(1, value.getId());
        if (value.getItemname() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getItemname());
        }
        stmt.bindLong(3, value.getItemamount());
      }
    };
    this.__deletionAdapterOfShoppingListItem = new EntityDeletionOrUpdateAdapter<ShoppingListItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `shoppinglistitem` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ShoppingListItem value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfShoppingListItem = new EntityDeletionOrUpdateAdapter<ShoppingListItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `shoppinglistitem` SET `id` = ?,`itemname` = ?,`itemamount` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ShoppingListItem value) {
        stmt.bindLong(1, value.getId());
        if (value.getItemname() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getItemname());
        }
        stmt.bindLong(3, value.getItemamount());
        stmt.bindLong(4, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM shoppinglistitem";
        return _query;
      }
    };
  }

  @Override
  public void insertItem(final ShoppingListItem... shoppinglistitem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfShoppingListItem.insert(shoppinglistitem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ShoppingListItem shoppingListItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfShoppingListItem.handle(shoppingListItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateItem(final ShoppingListItem... shoppinglistitem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfShoppingListItem.handleMultiple(shoppinglistitem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<ShoppingListItem> getAll() {
    final String _sql = "SELECT * FROM shoppinglistitem";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfItemname = CursorUtil.getColumnIndexOrThrow(_cursor, "itemname");
      final int _cursorIndexOfItemamount = CursorUtil.getColumnIndexOrThrow(_cursor, "itemamount");
      final List<ShoppingListItem> _result = new ArrayList<ShoppingListItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ShoppingListItem _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpItemname;
        if (_cursor.isNull(_cursorIndexOfItemname)) {
          _tmpItemname = null;
        } else {
          _tmpItemname = _cursor.getString(_cursorIndexOfItemname);
        }
        final int _tmpItemamount;
        _tmpItemamount = _cursor.getInt(_cursorIndexOfItemamount);
        _item = new ShoppingListItem(_tmpId,_tmpItemname,_tmpItemamount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
