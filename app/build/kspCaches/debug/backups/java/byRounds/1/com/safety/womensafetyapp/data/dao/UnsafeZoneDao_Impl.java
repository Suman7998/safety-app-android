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
import com.safety.womensafetyapp.data.model.UnsafeZone;
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
public final class UnsafeZoneDao_Impl implements UnsafeZoneDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UnsafeZone> __insertionAdapterOfUnsafeZone;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<UnsafeZone> __deletionAdapterOfUnsafeZone;

  private final EntityDeletionOrUpdateAdapter<UnsafeZone> __updateAdapterOfUnsafeZone;

  public UnsafeZoneDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUnsafeZone = new EntityInsertionAdapter<UnsafeZone>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `unsafe_zones` (`id`,`latitude`,`longitude`,`address`,`reportedBy`,`reportDate`,`description`,`dangerLevel`,`isVerified`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UnsafeZone entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getLatitude());
        statement.bindDouble(3, entity.getLongitude());
        statement.bindString(4, entity.getAddress());
        statement.bindString(5, entity.getReportedBy());
        statement.bindLong(6, entity.getReportDate());
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        final String _tmp = __converters.fromDangerLevel(entity.getDangerLevel());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final int _tmp_1 = entity.isVerified() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
      }
    };
    this.__deletionAdapterOfUnsafeZone = new EntityDeletionOrUpdateAdapter<UnsafeZone>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `unsafe_zones` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UnsafeZone entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfUnsafeZone = new EntityDeletionOrUpdateAdapter<UnsafeZone>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `unsafe_zones` SET `id` = ?,`latitude` = ?,`longitude` = ?,`address` = ?,`reportedBy` = ?,`reportDate` = ?,`description` = ?,`dangerLevel` = ?,`isVerified` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UnsafeZone entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getLatitude());
        statement.bindDouble(3, entity.getLongitude());
        statement.bindString(4, entity.getAddress());
        statement.bindString(5, entity.getReportedBy());
        statement.bindLong(6, entity.getReportDate());
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        final String _tmp = __converters.fromDangerLevel(entity.getDangerLevel());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final int _tmp_1 = entity.isVerified() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final UnsafeZone unsafeZone, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfUnsafeZone.insertAndReturnId(unsafeZone);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final UnsafeZone unsafeZone, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUnsafeZone.handle(unsafeZone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final UnsafeZone unsafeZone, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUnsafeZone.handle(unsafeZone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsafeZoneById(final long id,
      final Continuation<? super UnsafeZone> $completion) {
    final String _sql = "SELECT * FROM unsafe_zones WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UnsafeZone>() {
      @Override
      @Nullable
      public UnsafeZone call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfReportedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedBy");
          final int _cursorIndexOfReportDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reportDate");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDangerLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "dangerLevel");
          final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "isVerified");
          final UnsafeZone _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpReportedBy;
            _tmpReportedBy = _cursor.getString(_cursorIndexOfReportedBy);
            final long _tmpReportDate;
            _tmpReportDate = _cursor.getLong(_cursorIndexOfReportDate);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final UnsafeZone.DangerLevel _tmpDangerLevel;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDangerLevel)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDangerLevel);
            }
            final UnsafeZone.DangerLevel _tmp_1 = __converters.toDangerLevel(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.UnsafeZone.DangerLevel', but it was NULL.");
            } else {
              _tmpDangerLevel = _tmp_1;
            }
            final boolean _tmpIsVerified;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsVerified);
            _tmpIsVerified = _tmp_2 != 0;
            _result = new UnsafeZone(_tmpId,_tmpLatitude,_tmpLongitude,_tmpAddress,_tmpReportedBy,_tmpReportDate,_tmpDescription,_tmpDangerLevel,_tmpIsVerified);
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
  public LiveData<List<UnsafeZone>> getUnsafeZonesInArea(final double southWestLat,
      final double southWestLng, final double northEastLat, final double northEastLng,
      final long fromTimestamp) {
    final String _sql = "\n"
            + "        SELECT * FROM unsafe_zones \n"
            + "        WHERE latitude BETWEEN ? AND ? \n"
            + "        AND longitude BETWEEN ? AND ?\n"
            + "        AND reportDate >= ?\n"
            + "        ORDER BY dangerLevel DESC, reportDate DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, southWestLat);
    _argIndex = 2;
    _statement.bindDouble(_argIndex, northEastLat);
    _argIndex = 3;
    _statement.bindDouble(_argIndex, southWestLng);
    _argIndex = 4;
    _statement.bindDouble(_argIndex, northEastLng);
    _argIndex = 5;
    _statement.bindLong(_argIndex, fromTimestamp);
    return __db.getInvalidationTracker().createLiveData(new String[] {"unsafe_zones"}, false, new Callable<List<UnsafeZone>>() {
      @Override
      @Nullable
      public List<UnsafeZone> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfReportedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "reportedBy");
          final int _cursorIndexOfReportDate = CursorUtil.getColumnIndexOrThrow(_cursor, "reportDate");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDangerLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "dangerLevel");
          final int _cursorIndexOfIsVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "isVerified");
          final List<UnsafeZone> _result = new ArrayList<UnsafeZone>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnsafeZone _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpReportedBy;
            _tmpReportedBy = _cursor.getString(_cursorIndexOfReportedBy);
            final long _tmpReportDate;
            _tmpReportDate = _cursor.getLong(_cursorIndexOfReportDate);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final UnsafeZone.DangerLevel _tmpDangerLevel;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDangerLevel)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDangerLevel);
            }
            final UnsafeZone.DangerLevel _tmp_1 = __converters.toDangerLevel(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.UnsafeZone.DangerLevel', but it was NULL.");
            } else {
              _tmpDangerLevel = _tmp_1;
            }
            final boolean _tmpIsVerified;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsVerified);
            _tmpIsVerified = _tmp_2 != 0;
            _item = new UnsafeZone(_tmpId,_tmpLatitude,_tmpLongitude,_tmpAddress,_tmpReportedBy,_tmpReportDate,_tmpDescription,_tmpDangerLevel,_tmpIsVerified);
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
  public Object getNearestUnsafeZones(final double lat, final double lng, final double radiusKm,
      final long fromTimestamp, final int limit,
      final Continuation<? super List<UnsafeZone>> $completion) {
    final String _sql = "SELECT `id`, `latitude`, `longitude`, `address`, `reportedBy`, `reportDate`, `description`, `dangerLevel`, `isVerified` FROM (\n"
            + "        SELECT * FROM (\n"
            + "            SELECT *, \n"
            + "            (6371 * acos(cos(radians(?)) * cos(radians(latitude)) * \n"
            + "            cos(radians(longitude) - radians(?)) + sin(radians(?)) * \n"
            + "            sin(radians(latitude)))) AS distance \n"
            + "            FROM unsafe_zones \n"
            + "            WHERE reportDate >= ?\n"
            + "        )\n"
            + "        WHERE distance <= ?\n"
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
    _statement.bindLong(_argIndex, fromTimestamp);
    _argIndex = 5;
    _statement.bindDouble(_argIndex, radiusKm);
    _argIndex = 6;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UnsafeZone>>() {
      @Override
      @NonNull
      public List<UnsafeZone> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfLatitude = 1;
          final int _cursorIndexOfLongitude = 2;
          final int _cursorIndexOfAddress = 3;
          final int _cursorIndexOfReportedBy = 4;
          final int _cursorIndexOfReportDate = 5;
          final int _cursorIndexOfDescription = 6;
          final int _cursorIndexOfDangerLevel = 7;
          final int _cursorIndexOfIsVerified = 8;
          final List<UnsafeZone> _result = new ArrayList<UnsafeZone>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnsafeZone _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpReportedBy;
            _tmpReportedBy = _cursor.getString(_cursorIndexOfReportedBy);
            final long _tmpReportDate;
            _tmpReportDate = _cursor.getLong(_cursorIndexOfReportDate);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final UnsafeZone.DangerLevel _tmpDangerLevel;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDangerLevel)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDangerLevel);
            }
            final UnsafeZone.DangerLevel _tmp_1 = __converters.toDangerLevel(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.safety.womensafetyapp.data.model.UnsafeZone.DangerLevel', but it was NULL.");
            } else {
              _tmpDangerLevel = _tmp_1;
            }
            final boolean _tmpIsVerified;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsVerified);
            _tmpIsVerified = _tmp_2 != 0;
            _item = new UnsafeZone(_tmpId,_tmpLatitude,_tmpLongitude,_tmpAddress,_tmpReportedBy,_tmpReportDate,_tmpDescription,_tmpDangerLevel,_tmpIsVerified);
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
