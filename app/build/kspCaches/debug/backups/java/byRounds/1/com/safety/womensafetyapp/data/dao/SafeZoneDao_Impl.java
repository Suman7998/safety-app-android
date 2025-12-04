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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.safety.womensafetyapp.data.model.SafeZone;
import com.safety.womensafetyapp.util.Converters;
import java.lang.Class;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SafeZoneDao_Impl implements SafeZoneDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SafeZone> __insertionAdapterOfSafeZone;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<SafeZone> __deletionAdapterOfSafeZone;

  private final EntityDeletionOrUpdateAdapter<SafeZone> __updateAdapterOfSafeZone;

  public SafeZoneDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSafeZone = new EntityInsertionAdapter<SafeZone>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `safe_zones` (`id`,`name`,`type`,`latitude`,`longitude`,`address`,`phone`,`isVerified`,`addedBy`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SafeZone entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.fromZoneType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindDouble(4, entity.getLatitude());
        statement.bindDouble(5, entity.getLongitude());
        statement.bindString(6, entity.getAddress());
        if (entity.getPhone() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPhone());
        }
        final int _tmp_1 = entity.isVerified() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindString(9, entity.getAddedBy());
      }
    };
    this.__deletionAdapterOfSafeZone = new EntityDeletionOrUpdateAdapter<SafeZone>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `safe_zones` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SafeZone entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSafeZone = new EntityDeletionOrUpdateAdapter<SafeZone>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `safe_zones` SET `id` = ?,`name` = ?,`type` = ?,`latitude` = ?,`longitude` = ?,`address` = ?,`phone` = ?,`isVerified` = ?,`addedBy` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SafeZone entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.fromZoneType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        statement.bindDouble(4, entity.getLatitude());
        statement.bindDouble(5, entity.getLongitude());
        statement.bindString(6, entity.getAddress());
        if (entity.getPhone() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPhone());
        }
        final int _tmp_1 = entity.isVerified() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindString(9, entity.getAddedBy());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final SafeZone safeZone, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSafeZone.insertAndReturnId(safeZone);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final SafeZone safeZone, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSafeZone.handle(safeZone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final SafeZone safeZone, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSafeZone.handle(safeZone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSafeZoneById(final long id, final Continuation<? super SafeZone> $completion) {
    final String _sql = "SELECT * FROM safe_zones WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SafeZone>() {
      @Override
      @Nullable
      public SafeZone call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "isVerified");
          final int _cursorIndexOfAddedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "addedBy");
          final SafeZone _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final SafeZone.ZoneType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            final SafeZone.ZoneType _tmp_1 = __converters.toZoneType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.SafeZone.ZoneType', but it was NULL.");
            } else {
              _tmpType = _tmp_1;
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final boolean _tmpIsVerified;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsVerified);
            _tmpIsVerified = _tmp_2 != 0;
            final String _tmpAddedBy;
            _tmpAddedBy = _cursor.getString(_cursorIndexOfAddedBy);
            _result = new SafeZone(_tmpId,_tmpName,_tmpType,_tmpLatitude,_tmpLongitude,_tmpAddress,_tmpPhone,_tmpIsVerified,_tmpAddedBy);
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
  public LiveData<List<SafeZone>> getSafeZonesByType(final SafeZone.ZoneType type) {
    final String _sql = "SELECT * FROM safe_zones WHERE type = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromZoneType(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"safe_zones"}, false, new Callable<List<SafeZone>>() {
      @Override
      @Nullable
      public List<SafeZone> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "isVerified");
          final int _cursorIndexOfAddedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "addedBy");
          final List<SafeZone> _result = new ArrayList<SafeZone>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SafeZone _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final SafeZone.ZoneType _tmpType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfType);
            }
            final SafeZone.ZoneType _tmp_2 = __converters.toZoneType(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.SafeZone.ZoneType', but it was NULL.");
            } else {
              _tmpType = _tmp_2;
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final boolean _tmpIsVerified;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsVerified);
            _tmpIsVerified = _tmp_3 != 0;
            final String _tmpAddedBy;
            _tmpAddedBy = _cursor.getString(_cursorIndexOfAddedBy);
            _item = new SafeZone(_tmpId,_tmpName,_tmpType,_tmpLatitude,_tmpLongitude,_tmpAddress,_tmpPhone,_tmpIsVerified,_tmpAddedBy);
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
  public LiveData<List<SafeZone>> getSafeZonesInArea(final double southWestLat,
      final double southWestLng, final double northEastLat, final double northEastLng) {
    final String _sql = "\n"
            + "        SELECT * FROM safe_zones \n"
            + "        WHERE latitude BETWEEN ? AND ? \n"
            + "        AND longitude BETWEEN ? AND ?\n"
            + "        ORDER BY \n"
            + "            CASE \n"
            + "                WHEN type = 'POLICE_STATION' THEN 1\n"
            + "                WHEN type = 'HOSPITAL' THEN 2\n"
            + "                WHEN type = 'SHELTER' THEN 3\n"
            + "                ELSE 4\n"
            + "            END\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, southWestLat);
    _argIndex = 2;
    _statement.bindDouble(_argIndex, northEastLat);
    _argIndex = 3;
    _statement.bindDouble(_argIndex, southWestLng);
    _argIndex = 4;
    _statement.bindDouble(_argIndex, northEastLng);
    return __db.getInvalidationTracker().createLiveData(new String[] {"safe_zones"}, false, new Callable<List<SafeZone>>() {
      @Override
      @Nullable
      public List<SafeZone> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "isVerified");
          final int _cursorIndexOfAddedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "addedBy");
          final List<SafeZone> _result = new ArrayList<SafeZone>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SafeZone _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final SafeZone.ZoneType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            final SafeZone.ZoneType _tmp_1 = __converters.toZoneType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.SafeZone.ZoneType', but it was NULL.");
            } else {
              _tmpType = _tmp_1;
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final boolean _tmpIsVerified;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsVerified);
            _tmpIsVerified = _tmp_2 != 0;
            final String _tmpAddedBy;
            _tmpAddedBy = _cursor.getString(_cursorIndexOfAddedBy);
            _item = new SafeZone(_tmpId,_tmpName,_tmpType,_tmpLatitude,_tmpLongitude,_tmpAddress,_tmpPhone,_tmpIsVerified,_tmpAddedBy);
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
  public Object getNearestSafeZones(final double lat, final double lng,
      final SafeZone.ZoneType type, final int limit,
      final Continuation<? super List<SafeZone>> $completion) {
    final String _sql = "SELECT `id`, `name`, `type`, `latitude`, `longitude`, `address`, `phone`, `isVerified`, `addedBy` FROM (\n"
            + "        SELECT *, \n"
            + "        (6371 * acos(cos(radians(?)) * cos(radians(latitude)) * \n"
            + "        cos(radians(longitude) - radians(?)) + sin(radians(?)) * \n"
            + "        sin(radians(latitude)))) AS distance \n"
            + "        FROM safe_zones \n"
            + "        WHERE (? IS NULL OR type = ?)\n"
            + "        ORDER BY distance ASC\n"
            + "        LIMIT ?\n"
            + "    )";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 6);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, lat);
    _argIndex = 2;
    _statement.bindDouble(_argIndex, lng);
    _argIndex = 3;
    _statement.bindDouble(_argIndex, lat);
    _argIndex = 4;
    final String _tmp = __converters.fromZoneType(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 5;
    final String _tmp_1 = __converters.fromZoneType(type);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    _argIndex = 6;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SafeZone>>() {
      @Override
      @NonNull
      public List<SafeZone> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfType = 2;
          final int _cursorIndexOfLatitude = 3;
          final int _cursorIndexOfLongitude = 4;
          final int _cursorIndexOfAddress = 5;
          final int _cursorIndexOfPhone = 6;
          final int _cursorIndexOfIsVerified = 7;
          final int _cursorIndexOfAddedBy = 8;
          final List<SafeZone> _result = new ArrayList<SafeZone>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SafeZone _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final SafeZone.ZoneType _tmpType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfType);
            }
            final SafeZone.ZoneType _tmp_3 = __converters.toZoneType(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.SafeZone.ZoneType', but it was NULL.");
            } else {
              _tmpType = _tmp_3;
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final boolean _tmpIsVerified;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsVerified);
            _tmpIsVerified = _tmp_4 != 0;
            final String _tmpAddedBy;
            _tmpAddedBy = _cursor.getString(_cursorIndexOfAddedBy);
            _item = new SafeZone(_tmpId,_tmpName,_tmpType,_tmpLatitude,_tmpLongitude,_tmpAddress,_tmpPhone,_tmpIsVerified,_tmpAddedBy);
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
