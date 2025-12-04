package com.safety.womensafetyapp.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.safety.womensafetyapp.data.model.EmergencyContact;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EmergencyContactDao_Impl implements EmergencyContactDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EmergencyContact> __insertionAdapterOfEmergencyContact;

  private final EntityDeletionOrUpdateAdapter<EmergencyContact> __deletionAdapterOfEmergencyContact;

  private final EntityDeletionOrUpdateAdapter<EmergencyContact> __updateAdapterOfEmergencyContact;

  private final SharedSQLiteStatement __preparedStmtOfClearPrimaryFlags;

  public EmergencyContactDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmergencyContact = new EntityInsertionAdapter<EmergencyContact>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `emergency_contacts` (`id`,`userId`,`name`,`phone`,`email`,`relation`,`isPrimary`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmergencyContact entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getPhone());
        if (entity.getEmail() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEmail());
        }
        if (entity.getRelation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRelation());
        }
        final int _tmp = entity.isPrimary() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__deletionAdapterOfEmergencyContact = new EntityDeletionOrUpdateAdapter<EmergencyContact>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `emergency_contacts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmergencyContact entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfEmergencyContact = new EntityDeletionOrUpdateAdapter<EmergencyContact>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `emergency_contacts` SET `id` = ?,`userId` = ?,`name` = ?,`phone` = ?,`email` = ?,`relation` = ?,`isPrimary` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmergencyContact entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getPhone());
        if (entity.getEmail() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEmail());
        }
        if (entity.getRelation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRelation());
        }
        final int _tmp = entity.isPrimary() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfClearPrimaryFlags = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE emergency_contacts SET isPrimary = 0 WHERE userId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final EmergencyContact contact,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEmergencyContact.insertAndReturnId(contact);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final EmergencyContact contact,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEmergencyContact.handle(contact);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final EmergencyContact contact,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEmergencyContact.handle(contact);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearPrimaryFlags(final String userId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearPrimaryFlags.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearPrimaryFlags.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getContactById(final long id,
      final Continuation<? super EmergencyContact> $completion) {
    final String _sql = "SELECT * FROM emergency_contacts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EmergencyContact>() {
      @Override
      @Nullable
      public EmergencyContact call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfRelation = CursorUtil.getColumnIndexOrThrow(_cursor, "relation");
          final int _cursorIndexOfIsPrimary = CursorUtil.getColumnIndexOrThrow(_cursor, "isPrimary");
          final EmergencyContact _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpRelation;
            if (_cursor.isNull(_cursorIndexOfRelation)) {
              _tmpRelation = null;
            } else {
              _tmpRelation = _cursor.getString(_cursorIndexOfRelation);
            }
            final boolean _tmpIsPrimary;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
            _tmpIsPrimary = _tmp != 0;
            _result = new EmergencyContact(_tmpId,_tmpUserId,_tmpName,_tmpPhone,_tmpEmail,_tmpRelation,_tmpIsPrimary);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EmergencyContact>> getContactsByUser(final String userId) {
    final String _sql = "SELECT * FROM emergency_contacts WHERE userId = ? ORDER BY isPrimary DESC, name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emergency_contacts"}, new Callable<List<EmergencyContact>>() {
      @Override
      @NonNull
      public List<EmergencyContact> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfRelation = CursorUtil.getColumnIndexOrThrow(_cursor, "relation");
          final int _cursorIndexOfIsPrimary = CursorUtil.getColumnIndexOrThrow(_cursor, "isPrimary");
          final List<EmergencyContact> _result = new ArrayList<EmergencyContact>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyContact _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpRelation;
            if (_cursor.isNull(_cursorIndexOfRelation)) {
              _tmpRelation = null;
            } else {
              _tmpRelation = _cursor.getString(_cursorIndexOfRelation);
            }
            final boolean _tmpIsPrimary;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
            _tmpIsPrimary = _tmp != 0;
            _item = new EmergencyContact(_tmpId,_tmpUserId,_tmpName,_tmpPhone,_tmpEmail,_tmpRelation,_tmpIsPrimary);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPrimaryContact(final String userId,
      final Continuation<? super EmergencyContact> $completion) {
    final String _sql = "SELECT * FROM emergency_contacts WHERE userId = ? AND isPrimary = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EmergencyContact>() {
      @Override
      @Nullable
      public EmergencyContact call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfRelation = CursorUtil.getColumnIndexOrThrow(_cursor, "relation");
          final int _cursorIndexOfIsPrimary = CursorUtil.getColumnIndexOrThrow(_cursor, "isPrimary");
          final EmergencyContact _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpRelation;
            if (_cursor.isNull(_cursorIndexOfRelation)) {
              _tmpRelation = null;
            } else {
              _tmpRelation = _cursor.getString(_cursorIndexOfRelation);
            }
            final boolean _tmpIsPrimary;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
            _tmpIsPrimary = _tmp != 0;
            _result = new EmergencyContact(_tmpId,_tmpUserId,_tmpName,_tmpPhone,_tmpEmail,_tmpRelation,_tmpIsPrimary);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getContactCount(final String userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM emergency_contacts WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
