package com.safety.womensafetyapp.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.safety.womensafetyapp.data.model.GuardianAck;
import com.safety.womensafetyapp.util.Converters;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GuardianAckDao_Impl implements GuardianAckDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GuardianAck> __insertionAdapterOfGuardianAck;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<GuardianAck> __deletionAdapterOfGuardianAck;

  private final EntityDeletionOrUpdateAdapter<GuardianAck> __updateAdapterOfGuardianAck;

  private final SharedSQLiteStatement __preparedStmtOfUpdateAckStatus;

  public GuardianAckDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGuardianAck = new EntityInsertionAdapter<GuardianAck>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `guardian_acks` (`id`,`messageId`,`guardianId`,`ackTime`,`status`,`message`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GuardianAck entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMessageId());
        statement.bindLong(3, entity.getGuardianId());
        statement.bindLong(4, entity.getAckTime());
        final String _tmp = __converters.fromAckStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        if (entity.getMessage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getMessage());
        }
      }
    };
    this.__deletionAdapterOfGuardianAck = new EntityDeletionOrUpdateAdapter<GuardianAck>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `guardian_acks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GuardianAck entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfGuardianAck = new EntityDeletionOrUpdateAdapter<GuardianAck>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `guardian_acks` SET `id` = ?,`messageId` = ?,`guardianId` = ?,`ackTime` = ?,`status` = ?,`message` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GuardianAck entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMessageId());
        statement.bindLong(3, entity.getGuardianId());
        statement.bindLong(4, entity.getAckTime());
        final String _tmp = __converters.fromAckStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        if (entity.getMessage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getMessage());
        }
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateAckStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE guardian_acks \n"
                + "        SET status = ?,\n"
                + "            ackTime = ?,\n"
                + "            message = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final GuardianAck ack, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfGuardianAck.insertAndReturnId(ack);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final GuardianAck ack, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfGuardianAck.handle(ack);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final GuardianAck ack, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfGuardianAck.handle(ack);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAckStatus(final long ackId, final GuardianAck.AckStatus newStatus,
      final long ackTime, final String message, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateAckStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __converters.fromAckStatus(newStatus);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, ackTime);
        _argIndex = 3;
        if (message == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, message);
        }
        _argIndex = 4;
        _stmt.bindLong(_argIndex, ackId);
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
          __preparedStmtOfUpdateAckStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAckById(final long id, final Continuation<? super GuardianAck> $completion) {
    final String _sql = "SELECT * FROM guardian_acks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GuardianAck>() {
      @Override
      @Nullable
      public GuardianAck call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfAckTime = CursorUtil.getColumnIndexOrThrow(_cursor, "ackTime");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final GuardianAck _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMessageId;
            _tmpMessageId = _cursor.getLong(_cursorIndexOfMessageId);
            final long _tmpGuardianId;
            _tmpGuardianId = _cursor.getLong(_cursorIndexOfGuardianId);
            final long _tmpAckTime;
            _tmpAckTime = _cursor.getLong(_cursorIndexOfAckTime);
            final GuardianAck.AckStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            final GuardianAck.AckStatus _tmp_1 = __converters.toAckStatus(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.GuardianAck.AckStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_1;
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            _result = new GuardianAck(_tmpId,_tmpMessageId,_tmpGuardianId,_tmpAckTime,_tmpStatus,_tmpMessage);
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
  public LiveData<List<GuardianAck>> getAcksForMessage(final long messageId) {
    final String _sql = "\n"
            + "        SELECT * FROM guardian_acks \n"
            + "        WHERE messageId = ?\n"
            + "        ORDER BY ackTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, messageId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"guardian_acks"}, false, new Callable<List<GuardianAck>>() {
      @Override
      @Nullable
      public List<GuardianAck> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfAckTime = CursorUtil.getColumnIndexOrThrow(_cursor, "ackTime");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final List<GuardianAck> _result = new ArrayList<GuardianAck>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GuardianAck _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMessageId;
            _tmpMessageId = _cursor.getLong(_cursorIndexOfMessageId);
            final long _tmpGuardianId;
            _tmpGuardianId = _cursor.getLong(_cursorIndexOfGuardianId);
            final long _tmpAckTime;
            _tmpAckTime = _cursor.getLong(_cursorIndexOfAckTime);
            final GuardianAck.AckStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            final GuardianAck.AckStatus _tmp_1 = __converters.toAckStatus(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.GuardianAck.AckStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_1;
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            _item = new GuardianAck(_tmpId,_tmpMessageId,_tmpGuardianId,_tmpAckTime,_tmpStatus,_tmpMessage);
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
  public LiveData<List<GuardianAck>> getAcksForGuardian(final long guardianId, final int limit,
      final int offset) {
    final String _sql = "\n"
            + "        SELECT * FROM guardian_acks \n"
            + "        WHERE guardianId = ?\n"
            + "        ORDER BY ackTime DESC\n"
            + "        LIMIT ? OFFSET ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, guardianId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    _argIndex = 3;
    _statement.bindLong(_argIndex, offset);
    return __db.getInvalidationTracker().createLiveData(new String[] {"guardian_acks"}, false, new Callable<List<GuardianAck>>() {
      @Override
      @Nullable
      public List<GuardianAck> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfAckTime = CursorUtil.getColumnIndexOrThrow(_cursor, "ackTime");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final List<GuardianAck> _result = new ArrayList<GuardianAck>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GuardianAck _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMessageId;
            _tmpMessageId = _cursor.getLong(_cursorIndexOfMessageId);
            final long _tmpGuardianId;
            _tmpGuardianId = _cursor.getLong(_cursorIndexOfGuardianId);
            final long _tmpAckTime;
            _tmpAckTime = _cursor.getLong(_cursorIndexOfAckTime);
            final GuardianAck.AckStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            final GuardianAck.AckStatus _tmp_1 = __converters.toAckStatus(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.GuardianAck.AckStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_1;
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            _item = new GuardianAck(_tmpId,_tmpMessageId,_tmpGuardianId,_tmpAckTime,_tmpStatus,_tmpMessage);
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
  public Object getAck(final long messageId, final long guardianId,
      final Continuation<? super GuardianAck> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM guardian_acks \n"
            + "        WHERE messageId = ? \n"
            + "        AND guardianId = ?\n"
            + "        LIMIT 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, messageId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, guardianId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GuardianAck>() {
      @Override
      @Nullable
      public GuardianAck call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfGuardianId = CursorUtil.getColumnIndexOrThrow(_cursor, "guardianId");
          final int _cursorIndexOfAckTime = CursorUtil.getColumnIndexOrThrow(_cursor, "ackTime");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final GuardianAck _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpMessageId;
            _tmpMessageId = _cursor.getLong(_cursorIndexOfMessageId);
            final long _tmpGuardianId;
            _tmpGuardianId = _cursor.getLong(_cursorIndexOfGuardianId);
            final long _tmpAckTime;
            _tmpAckTime = _cursor.getLong(_cursorIndexOfAckTime);
            final GuardianAck.AckStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            final GuardianAck.AckStatus _tmp_1 = __converters.toAckStatus(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.GuardianAck.AckStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_1;
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            _result = new GuardianAck(_tmpId,_tmpMessageId,_tmpGuardianId,_tmpAckTime,_tmpStatus,_tmpMessage);
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
  public Object countAcksByStatus(final long messageId, final GuardianAck.AckStatus status,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM guardian_acks \n"
            + "        WHERE messageId = ? \n"
            + "        AND status = ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, messageId);
    _argIndex = 2;
    final String _tmp = __converters.fromAckStatus(status);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(0);
            _result = _tmp_1;
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
