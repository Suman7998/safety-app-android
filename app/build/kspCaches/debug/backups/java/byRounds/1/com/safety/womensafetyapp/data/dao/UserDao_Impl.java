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
import com.safety.womensafetyapp.data.model.User;
import com.safety.womensafetyapp.util.Converters;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastActive;

  private final SharedSQLiteStatement __preparedStmtOfUpdateUserActiveStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateHomeLocation;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWorkLocation;

  private final SharedSQLiteStatement __preparedStmtOfSetLocationSharingEnabled;

  private final SharedSQLiteStatement __preparedStmtOfUpdateEmergencyMessage;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSosContacts;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`username`,`email`,`phone`,`fullName`,`profileImage`,`emergencyMessage`,`isActive`,`createdAt`,`lastActive`,`homeLatitude`,`homeLongitude`,`workLatitude`,`workLongitude`,`isLocationSharingEnabled`,`sosContacts`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUsername());
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        statement.bindString(4, entity.getPhone());
        if (entity.getFullName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getFullName());
        }
        if (entity.getProfileImage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getProfileImage());
        }
        statement.bindString(7, entity.getEmergencyMessage());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getLastActive());
        if (entity.getHomeLatitude() == null) {
          statement.bindNull(11);
        } else {
          statement.bindDouble(11, entity.getHomeLatitude());
        }
        if (entity.getHomeLongitude() == null) {
          statement.bindNull(12);
        } else {
          statement.bindDouble(12, entity.getHomeLongitude());
        }
        if (entity.getWorkLatitude() == null) {
          statement.bindNull(13);
        } else {
          statement.bindDouble(13, entity.getWorkLatitude());
        }
        if (entity.getWorkLongitude() == null) {
          statement.bindNull(14);
        } else {
          statement.bindDouble(14, entity.getWorkLongitude());
        }
        final int _tmp_1 = entity.isLocationSharingEnabled() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        final String _tmp_2 = __converters.fromLongList(entity.getSosContacts());
        if (_tmp_2 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `users` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`username` = ?,`email` = ?,`phone` = ?,`fullName` = ?,`profileImage` = ?,`emergencyMessage` = ?,`isActive` = ?,`createdAt` = ?,`lastActive` = ?,`homeLatitude` = ?,`homeLongitude` = ?,`workLatitude` = ?,`workLongitude` = ?,`isLocationSharingEnabled` = ?,`sosContacts` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUsername());
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        statement.bindString(4, entity.getPhone());
        if (entity.getFullName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getFullName());
        }
        if (entity.getProfileImage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getProfileImage());
        }
        statement.bindString(7, entity.getEmergencyMessage());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getLastActive());
        if (entity.getHomeLatitude() == null) {
          statement.bindNull(11);
        } else {
          statement.bindDouble(11, entity.getHomeLatitude());
        }
        if (entity.getHomeLongitude() == null) {
          statement.bindNull(12);
        } else {
          statement.bindDouble(12, entity.getHomeLongitude());
        }
        if (entity.getWorkLatitude() == null) {
          statement.bindNull(13);
        } else {
          statement.bindDouble(13, entity.getWorkLatitude());
        }
        if (entity.getWorkLongitude() == null) {
          statement.bindNull(14);
        } else {
          statement.bindDouble(14, entity.getWorkLongitude());
        }
        final int _tmp_1 = entity.isLocationSharingEnabled() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        final String _tmp_2 = __converters.fromLongList(entity.getSosContacts());
        if (_tmp_2 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_2);
        }
        statement.bindString(17, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateLastActive = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET lastActive = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateUserActiveStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isActive = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateHomeLocation = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET homeLatitude = ?, homeLongitude = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateWorkLocation = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET workLatitude = ?, workLongitude = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetLocationSharingEnabled = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isLocationSharingEnabled = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateEmergencyMessage = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET emergencyMessage = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSosContacts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET sosContacts = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastActive(final String userId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastActive.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
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
          __preparedStmtOfUpdateLastActive.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUserActiveStatus(final String userId, final boolean isActive,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateUserActiveStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isActive ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
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
          __preparedStmtOfUpdateUserActiveStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHomeLocation(final String userId, final double lat, final double lng,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateHomeLocation.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, lat);
        _argIndex = 2;
        _stmt.bindDouble(_argIndex, lng);
        _argIndex = 3;
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
          __preparedStmtOfUpdateHomeLocation.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWorkLocation(final String userId, final double lat, final double lng,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWorkLocation.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, lat);
        _argIndex = 2;
        _stmt.bindDouble(_argIndex, lng);
        _argIndex = 3;
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
          __preparedStmtOfUpdateWorkLocation.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setLocationSharingEnabled(final String userId, final boolean isEnabled,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetLocationSharingEnabled.acquire();
        int _argIndex = 1;
        final int _tmp = isEnabled ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
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
          __preparedStmtOfSetLocationSharingEnabled.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEmergencyMessage(final String userId, final String message,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateEmergencyMessage.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, message);
        _argIndex = 2;
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
          __preparedStmtOfUpdateEmergencyMessage.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSosContacts(final String userId, final List<Long> contactIds,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSosContacts.acquire();
        int _argIndex = 1;
        final String _tmp = __converters.fromLongList(contactIds);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
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
          __preparedStmtOfUpdateSosContacts.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserById(final String id, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImage");
          final int _cursorIndexOfEmergencyMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyMessage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastActive = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActive");
          final int _cursorIndexOfHomeLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLatitude");
          final int _cursorIndexOfHomeLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLongitude");
          final int _cursorIndexOfWorkLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLatitude");
          final int _cursorIndexOfWorkLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLongitude");
          final int _cursorIndexOfIsLocationSharingEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocationSharingEnabled");
          final int _cursorIndexOfSosContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "sosContacts");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpProfileImage;
            if (_cursor.isNull(_cursorIndexOfProfileImage)) {
              _tmpProfileImage = null;
            } else {
              _tmpProfileImage = _cursor.getString(_cursorIndexOfProfileImage);
            }
            final String _tmpEmergencyMessage;
            _tmpEmergencyMessage = _cursor.getString(_cursorIndexOfEmergencyMessage);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastActive;
            _tmpLastActive = _cursor.getLong(_cursorIndexOfLastActive);
            final Double _tmpHomeLatitude;
            if (_cursor.isNull(_cursorIndexOfHomeLatitude)) {
              _tmpHomeLatitude = null;
            } else {
              _tmpHomeLatitude = _cursor.getDouble(_cursorIndexOfHomeLatitude);
            }
            final Double _tmpHomeLongitude;
            if (_cursor.isNull(_cursorIndexOfHomeLongitude)) {
              _tmpHomeLongitude = null;
            } else {
              _tmpHomeLongitude = _cursor.getDouble(_cursorIndexOfHomeLongitude);
            }
            final Double _tmpWorkLatitude;
            if (_cursor.isNull(_cursorIndexOfWorkLatitude)) {
              _tmpWorkLatitude = null;
            } else {
              _tmpWorkLatitude = _cursor.getDouble(_cursorIndexOfWorkLatitude);
            }
            final Double _tmpWorkLongitude;
            if (_cursor.isNull(_cursorIndexOfWorkLongitude)) {
              _tmpWorkLongitude = null;
            } else {
              _tmpWorkLongitude = _cursor.getDouble(_cursorIndexOfWorkLongitude);
            }
            final boolean _tmpIsLocationSharingEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLocationSharingEnabled);
            _tmpIsLocationSharingEnabled = _tmp_1 != 0;
            final List<Long> _tmpSosContacts;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSosContacts)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSosContacts);
            }
            final List<Long> _tmp_3 = __converters.toLongList(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.Long>', but it was NULL.");
            } else {
              _tmpSosContacts = _tmp_3;
            }
            _result = new User(_tmpId,_tmpUsername,_tmpEmail,_tmpPhone,_tmpFullName,_tmpProfileImage,_tmpEmergencyMessage,_tmpIsActive,_tmpCreatedAt,_tmpLastActive,_tmpHomeLatitude,_tmpHomeLongitude,_tmpWorkLatitude,_tmpWorkLongitude,_tmpIsLocationSharingEnabled,_tmpSosContacts);
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
  public LiveData<User> observeUserById(final String id) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"users"}, false, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImage");
          final int _cursorIndexOfEmergencyMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyMessage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastActive = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActive");
          final int _cursorIndexOfHomeLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLatitude");
          final int _cursorIndexOfHomeLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLongitude");
          final int _cursorIndexOfWorkLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLatitude");
          final int _cursorIndexOfWorkLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLongitude");
          final int _cursorIndexOfIsLocationSharingEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocationSharingEnabled");
          final int _cursorIndexOfSosContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "sosContacts");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpProfileImage;
            if (_cursor.isNull(_cursorIndexOfProfileImage)) {
              _tmpProfileImage = null;
            } else {
              _tmpProfileImage = _cursor.getString(_cursorIndexOfProfileImage);
            }
            final String _tmpEmergencyMessage;
            _tmpEmergencyMessage = _cursor.getString(_cursorIndexOfEmergencyMessage);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastActive;
            _tmpLastActive = _cursor.getLong(_cursorIndexOfLastActive);
            final Double _tmpHomeLatitude;
            if (_cursor.isNull(_cursorIndexOfHomeLatitude)) {
              _tmpHomeLatitude = null;
            } else {
              _tmpHomeLatitude = _cursor.getDouble(_cursorIndexOfHomeLatitude);
            }
            final Double _tmpHomeLongitude;
            if (_cursor.isNull(_cursorIndexOfHomeLongitude)) {
              _tmpHomeLongitude = null;
            } else {
              _tmpHomeLongitude = _cursor.getDouble(_cursorIndexOfHomeLongitude);
            }
            final Double _tmpWorkLatitude;
            if (_cursor.isNull(_cursorIndexOfWorkLatitude)) {
              _tmpWorkLatitude = null;
            } else {
              _tmpWorkLatitude = _cursor.getDouble(_cursorIndexOfWorkLatitude);
            }
            final Double _tmpWorkLongitude;
            if (_cursor.isNull(_cursorIndexOfWorkLongitude)) {
              _tmpWorkLongitude = null;
            } else {
              _tmpWorkLongitude = _cursor.getDouble(_cursorIndexOfWorkLongitude);
            }
            final boolean _tmpIsLocationSharingEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLocationSharingEnabled);
            _tmpIsLocationSharingEnabled = _tmp_1 != 0;
            final List<Long> _tmpSosContacts;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSosContacts)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSosContacts);
            }
            final List<Long> _tmp_3 = __converters.toLongList(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.Long>', but it was NULL.");
            } else {
              _tmpSosContacts = _tmp_3;
            }
            _result = new User(_tmpId,_tmpUsername,_tmpEmail,_tmpPhone,_tmpFullName,_tmpProfileImage,_tmpEmergencyMessage,_tmpIsActive,_tmpCreatedAt,_tmpLastActive,_tmpHomeLatitude,_tmpHomeLongitude,_tmpWorkLatitude,_tmpWorkLongitude,_tmpIsLocationSharingEnabled,_tmpSosContacts);
          } else {
            _result = null;
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
  public Object getUserByPhone(final String phone, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE phone = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, phone);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImage");
          final int _cursorIndexOfEmergencyMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyMessage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastActive = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActive");
          final int _cursorIndexOfHomeLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLatitude");
          final int _cursorIndexOfHomeLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLongitude");
          final int _cursorIndexOfWorkLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLatitude");
          final int _cursorIndexOfWorkLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLongitude");
          final int _cursorIndexOfIsLocationSharingEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocationSharingEnabled");
          final int _cursorIndexOfSosContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "sosContacts");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpProfileImage;
            if (_cursor.isNull(_cursorIndexOfProfileImage)) {
              _tmpProfileImage = null;
            } else {
              _tmpProfileImage = _cursor.getString(_cursorIndexOfProfileImage);
            }
            final String _tmpEmergencyMessage;
            _tmpEmergencyMessage = _cursor.getString(_cursorIndexOfEmergencyMessage);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastActive;
            _tmpLastActive = _cursor.getLong(_cursorIndexOfLastActive);
            final Double _tmpHomeLatitude;
            if (_cursor.isNull(_cursorIndexOfHomeLatitude)) {
              _tmpHomeLatitude = null;
            } else {
              _tmpHomeLatitude = _cursor.getDouble(_cursorIndexOfHomeLatitude);
            }
            final Double _tmpHomeLongitude;
            if (_cursor.isNull(_cursorIndexOfHomeLongitude)) {
              _tmpHomeLongitude = null;
            } else {
              _tmpHomeLongitude = _cursor.getDouble(_cursorIndexOfHomeLongitude);
            }
            final Double _tmpWorkLatitude;
            if (_cursor.isNull(_cursorIndexOfWorkLatitude)) {
              _tmpWorkLatitude = null;
            } else {
              _tmpWorkLatitude = _cursor.getDouble(_cursorIndexOfWorkLatitude);
            }
            final Double _tmpWorkLongitude;
            if (_cursor.isNull(_cursorIndexOfWorkLongitude)) {
              _tmpWorkLongitude = null;
            } else {
              _tmpWorkLongitude = _cursor.getDouble(_cursorIndexOfWorkLongitude);
            }
            final boolean _tmpIsLocationSharingEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLocationSharingEnabled);
            _tmpIsLocationSharingEnabled = _tmp_1 != 0;
            final List<Long> _tmpSosContacts;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSosContacts)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSosContacts);
            }
            final List<Long> _tmp_3 = __converters.toLongList(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.Long>', but it was NULL.");
            } else {
              _tmpSosContacts = _tmp_3;
            }
            _result = new User(_tmpId,_tmpUsername,_tmpEmail,_tmpPhone,_tmpFullName,_tmpProfileImage,_tmpEmergencyMessage,_tmpIsActive,_tmpCreatedAt,_tmpLastActive,_tmpHomeLatitude,_tmpHomeLongitude,_tmpWorkLatitude,_tmpWorkLongitude,_tmpIsLocationSharingEnabled,_tmpSosContacts);
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
  public Object getUserByUsername(final String username,
      final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE username = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, username);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImage");
          final int _cursorIndexOfEmergencyMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyMessage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastActive = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActive");
          final int _cursorIndexOfHomeLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLatitude");
          final int _cursorIndexOfHomeLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLongitude");
          final int _cursorIndexOfWorkLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLatitude");
          final int _cursorIndexOfWorkLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLongitude");
          final int _cursorIndexOfIsLocationSharingEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocationSharingEnabled");
          final int _cursorIndexOfSosContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "sosContacts");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpProfileImage;
            if (_cursor.isNull(_cursorIndexOfProfileImage)) {
              _tmpProfileImage = null;
            } else {
              _tmpProfileImage = _cursor.getString(_cursorIndexOfProfileImage);
            }
            final String _tmpEmergencyMessage;
            _tmpEmergencyMessage = _cursor.getString(_cursorIndexOfEmergencyMessage);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastActive;
            _tmpLastActive = _cursor.getLong(_cursorIndexOfLastActive);
            final Double _tmpHomeLatitude;
            if (_cursor.isNull(_cursorIndexOfHomeLatitude)) {
              _tmpHomeLatitude = null;
            } else {
              _tmpHomeLatitude = _cursor.getDouble(_cursorIndexOfHomeLatitude);
            }
            final Double _tmpHomeLongitude;
            if (_cursor.isNull(_cursorIndexOfHomeLongitude)) {
              _tmpHomeLongitude = null;
            } else {
              _tmpHomeLongitude = _cursor.getDouble(_cursorIndexOfHomeLongitude);
            }
            final Double _tmpWorkLatitude;
            if (_cursor.isNull(_cursorIndexOfWorkLatitude)) {
              _tmpWorkLatitude = null;
            } else {
              _tmpWorkLatitude = _cursor.getDouble(_cursorIndexOfWorkLatitude);
            }
            final Double _tmpWorkLongitude;
            if (_cursor.isNull(_cursorIndexOfWorkLongitude)) {
              _tmpWorkLongitude = null;
            } else {
              _tmpWorkLongitude = _cursor.getDouble(_cursorIndexOfWorkLongitude);
            }
            final boolean _tmpIsLocationSharingEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLocationSharingEnabled);
            _tmpIsLocationSharingEnabled = _tmp_1 != 0;
            final List<Long> _tmpSosContacts;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSosContacts)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSosContacts);
            }
            final List<Long> _tmp_3 = __converters.toLongList(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.Long>', but it was NULL.");
            } else {
              _tmpSosContacts = _tmp_3;
            }
            _result = new User(_tmpId,_tmpUsername,_tmpEmail,_tmpPhone,_tmpFullName,_tmpProfileImage,_tmpEmergencyMessage,_tmpIsActive,_tmpCreatedAt,_tmpLastActive,_tmpHomeLatitude,_tmpHomeLongitude,_tmpWorkLatitude,_tmpWorkLongitude,_tmpIsLocationSharingEnabled,_tmpSosContacts);
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
  public Object getUserByEmail(final String email, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, email);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImage");
          final int _cursorIndexOfEmergencyMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyMessage");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastActive = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActive");
          final int _cursorIndexOfHomeLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLatitude");
          final int _cursorIndexOfHomeLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "homeLongitude");
          final int _cursorIndexOfWorkLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLatitude");
          final int _cursorIndexOfWorkLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "workLongitude");
          final int _cursorIndexOfIsLocationSharingEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocationSharingEnabled");
          final int _cursorIndexOfSosContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "sosContacts");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            final String _tmpProfileImage;
            if (_cursor.isNull(_cursorIndexOfProfileImage)) {
              _tmpProfileImage = null;
            } else {
              _tmpProfileImage = _cursor.getString(_cursorIndexOfProfileImage);
            }
            final String _tmpEmergencyMessage;
            _tmpEmergencyMessage = _cursor.getString(_cursorIndexOfEmergencyMessage);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastActive;
            _tmpLastActive = _cursor.getLong(_cursorIndexOfLastActive);
            final Double _tmpHomeLatitude;
            if (_cursor.isNull(_cursorIndexOfHomeLatitude)) {
              _tmpHomeLatitude = null;
            } else {
              _tmpHomeLatitude = _cursor.getDouble(_cursorIndexOfHomeLatitude);
            }
            final Double _tmpHomeLongitude;
            if (_cursor.isNull(_cursorIndexOfHomeLongitude)) {
              _tmpHomeLongitude = null;
            } else {
              _tmpHomeLongitude = _cursor.getDouble(_cursorIndexOfHomeLongitude);
            }
            final Double _tmpWorkLatitude;
            if (_cursor.isNull(_cursorIndexOfWorkLatitude)) {
              _tmpWorkLatitude = null;
            } else {
              _tmpWorkLatitude = _cursor.getDouble(_cursorIndexOfWorkLatitude);
            }
            final Double _tmpWorkLongitude;
            if (_cursor.isNull(_cursorIndexOfWorkLongitude)) {
              _tmpWorkLongitude = null;
            } else {
              _tmpWorkLongitude = _cursor.getDouble(_cursorIndexOfWorkLongitude);
            }
            final boolean _tmpIsLocationSharingEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLocationSharingEnabled);
            _tmpIsLocationSharingEnabled = _tmp_1 != 0;
            final List<Long> _tmpSosContacts;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSosContacts)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSosContacts);
            }
            final List<Long> _tmp_3 = __converters.toLongList(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.Long>', but it was NULL.");
            } else {
              _tmpSosContacts = _tmp_3;
            }
            _result = new User(_tmpId,_tmpUsername,_tmpEmail,_tmpPhone,_tmpFullName,_tmpProfileImage,_tmpEmergencyMessage,_tmpIsActive,_tmpCreatedAt,_tmpLastActive,_tmpHomeLatitude,_tmpHomeLongitude,_tmpWorkLatitude,_tmpWorkLongitude,_tmpIsLocationSharingEnabled,_tmpSosContacts);
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
  public Object isPhoneRegistered(final String phone,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM users WHERE phone = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, phone);
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

  @Override
  public Object isEmailRegistered(final String email,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM users WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, email);
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
