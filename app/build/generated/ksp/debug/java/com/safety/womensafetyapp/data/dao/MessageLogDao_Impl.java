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
import com.safety.womensafetyapp.data.model.MessageLog;
import com.safety.womensafetyapp.util.Converters;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
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
public final class MessageLogDao_Impl implements MessageLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MessageLog> __insertionAdapterOfMessageLog;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<MessageLog> __deletionAdapterOfMessageLog;

  private final EntityDeletionOrUpdateAdapter<MessageLog> __updateAdapterOfMessageLog;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMessageStatus;

  public MessageLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMessageLog = new EntityInsertionAdapter<MessageLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `message_logs` (`id`,`userId`,`messageType`,`timestamp`,`content`,`recipients`,`status`,`latitude`,`longitude`,`locationName`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MessageLog entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        final String _tmp = __converters.fromMessageType(entity.getMessageType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindLong(4, entity.getTimestamp());
        statement.bindString(5, entity.getContent());
        final String _tmp_1 = __converters.fromStringList(entity.getRecipients());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
        final String _tmp_2 = __converters.fromMessageStatus(entity.getStatus());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getLongitude());
        }
        if (entity.getLocationName() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getLocationName());
        }
      }
    };
    this.__deletionAdapterOfMessageLog = new EntityDeletionOrUpdateAdapter<MessageLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `message_logs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MessageLog entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMessageLog = new EntityDeletionOrUpdateAdapter<MessageLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `message_logs` SET `id` = ?,`userId` = ?,`messageType` = ?,`timestamp` = ?,`content` = ?,`recipients` = ?,`status` = ?,`latitude` = ?,`longitude` = ?,`locationName` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MessageLog entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        final String _tmp = __converters.fromMessageType(entity.getMessageType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindLong(4, entity.getTimestamp());
        statement.bindString(5, entity.getContent());
        final String _tmp_1 = __converters.fromStringList(entity.getRecipients());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
        final String _tmp_2 = __converters.fromMessageStatus(entity.getStatus());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getLongitude());
        }
        if (entity.getLocationName() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getLocationName());
        }
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateMessageStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE message_logs \n"
                + "        SET status = ? \n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MessageLog messageLog, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMessageLog.insertAndReturnId(messageLog);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final MessageLog messageLog, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMessageLog.handle(messageLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final MessageLog messageLog, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMessageLog.handle(messageLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMessageStatus(final long messageId, final MessageLog.MessageStatus newStatus,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMessageStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __converters.fromMessageStatus(newStatus);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, messageId);
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
          __preparedStmtOfUpdateMessageStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getMessageById(final long id, final Continuation<? super MessageLog> $completion) {
    final String _sql = "SELECT * FROM message_logs WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MessageLog>() {
      @Override
      @Nullable
      public MessageLog call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfRecipients = CursorUtil.getColumnIndexOrThrow(_cursor, "recipients");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final MessageLog _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final MessageLog.MessageType _tmpMessageType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfMessageType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfMessageType);
            }
            final MessageLog.MessageType _tmp_1 = __converters.toMessageType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageType', but it was NULL.");
            } else {
              _tmpMessageType = _tmp_1;
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final List<String> _tmpRecipients;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfRecipients)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfRecipients);
            }
            final List<String> _tmp_3 = __converters.toStringList(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.String>', but it was NULL.");
            } else {
              _tmpRecipients = _tmp_3;
            }
            final MessageLog.MessageStatus _tmpStatus;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            }
            final MessageLog.MessageStatus _tmp_5 = __converters.toMessageStatus(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_5;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            _result = new MessageLog(_tmpId,_tmpUserId,_tmpMessageType,_tmpTimestamp,_tmpContent,_tmpRecipients,_tmpStatus,_tmpLatitude,_tmpLongitude,_tmpLocationName);
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
  public Flow<List<MessageLog>> getMessagesByUser(final String userId, final int limit,
      final int offset) {
    final String _sql = "\n"
            + "        SELECT * FROM message_logs \n"
            + "        WHERE userId = ? \n"
            + "        ORDER BY timestamp DESC\n"
            + "        LIMIT ? OFFSET ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    _argIndex = 3;
    _statement.bindLong(_argIndex, offset);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"message_logs"}, new Callable<List<MessageLog>>() {
      @Override
      @NonNull
      public List<MessageLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfRecipients = CursorUtil.getColumnIndexOrThrow(_cursor, "recipients");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final List<MessageLog> _result = new ArrayList<MessageLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MessageLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final MessageLog.MessageType _tmpMessageType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfMessageType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfMessageType);
            }
            final MessageLog.MessageType _tmp_1 = __converters.toMessageType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageType', but it was NULL.");
            } else {
              _tmpMessageType = _tmp_1;
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final List<String> _tmpRecipients;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfRecipients)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfRecipients);
            }
            final List<String> _tmp_3 = __converters.toStringList(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.String>', but it was NULL.");
            } else {
              _tmpRecipients = _tmp_3;
            }
            final MessageLog.MessageStatus _tmpStatus;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            }
            final MessageLog.MessageStatus _tmp_5 = __converters.toMessageStatus(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_5;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            _item = new MessageLog(_tmpId,_tmpUserId,_tmpMessageType,_tmpTimestamp,_tmpContent,_tmpRecipients,_tmpStatus,_tmpLatitude,_tmpLongitude,_tmpLocationName);
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
  public Object getMessagesByStatus(final String userId, final MessageLog.MessageStatus status,
      final Continuation<? super List<MessageLog>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM message_logs \n"
            + "        WHERE userId = ? \n"
            + "        AND status = ?\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final String _tmp = __converters.fromMessageStatus(status);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MessageLog>>() {
      @Override
      @NonNull
      public List<MessageLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfRecipients = CursorUtil.getColumnIndexOrThrow(_cursor, "recipients");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final List<MessageLog> _result = new ArrayList<MessageLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MessageLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final MessageLog.MessageType _tmpMessageType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfMessageType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfMessageType);
            }
            final MessageLog.MessageType _tmp_2 = __converters.toMessageType(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageType', but it was NULL.");
            } else {
              _tmpMessageType = _tmp_2;
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final List<String> _tmpRecipients;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecipients)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecipients);
            }
            final List<String> _tmp_4 = __converters.toStringList(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.String>', but it was NULL.");
            } else {
              _tmpRecipients = _tmp_4;
            }
            final MessageLog.MessageStatus _tmpStatus;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfStatus);
            }
            final MessageLog.MessageStatus _tmp_6 = __converters.toMessageStatus(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_6;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            _item = new MessageLog(_tmpId,_tmpUserId,_tmpMessageType,_tmpTimestamp,_tmpContent,_tmpRecipients,_tmpStatus,_tmpLatitude,_tmpLongitude,_tmpLocationName);
            _result.add(_item);
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
  public Object getMessagesByType(final String userId, final MessageLog.MessageType messageType,
      final int limit, final Continuation<? super List<MessageLog>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM message_logs \n"
            + "        WHERE userId = ? \n"
            + "        AND messageType = ?\n"
            + "        ORDER BY timestamp DESC\n"
            + "        LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final String _tmp = __converters.fromMessageType(messageType);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 3;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MessageLog>>() {
      @Override
      @NonNull
      public List<MessageLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfMessageType = CursorUtil.getColumnIndexOrThrow(_cursor, "messageType");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfRecipients = CursorUtil.getColumnIndexOrThrow(_cursor, "recipients");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final List<MessageLog> _result = new ArrayList<MessageLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MessageLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final MessageLog.MessageType _tmpMessageType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfMessageType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfMessageType);
            }
            final MessageLog.MessageType _tmp_2 = __converters.toMessageType(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageType', but it was NULL.");
            } else {
              _tmpMessageType = _tmp_2;
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final List<String> _tmpRecipients;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecipients)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecipients);
            }
            final List<String> _tmp_4 = __converters.toStringList(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.String>', but it was NULL.");
            } else {
              _tmpRecipients = _tmp_4;
            }
            final MessageLog.MessageStatus _tmpStatus;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfStatus);
            }
            final MessageLog.MessageStatus _tmp_6 = __converters.toMessageStatus(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.MessageLog.MessageStatus', but it was NULL.");
            } else {
              _tmpStatus = _tmp_6;
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            _item = new MessageLog(_tmpId,_tmpUserId,_tmpMessageType,_tmpTimestamp,_tmpContent,_tmpRecipients,_tmpStatus,_tmpLatitude,_tmpLongitude,_tmpLocationName);
            _result.add(_item);
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
