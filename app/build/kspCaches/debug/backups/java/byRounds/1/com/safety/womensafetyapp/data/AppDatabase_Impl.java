package com.safety.womensafetyapp.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.safety.womensafetyapp.data.dao.EmergencyContactDao;
import com.safety.womensafetyapp.data.dao.EmergencyContactDao_Impl;
import com.safety.womensafetyapp.data.dao.GuardianAckDao;
import com.safety.womensafetyapp.data.dao.GuardianAckDao_Impl;
import com.safety.womensafetyapp.data.dao.MessageLogDao;
import com.safety.womensafetyapp.data.dao.MessageLogDao_Impl;
import com.safety.womensafetyapp.data.dao.SafeZoneDao;
import com.safety.womensafetyapp.data.dao.SafeZoneDao_Impl;
import com.safety.womensafetyapp.data.dao.UnsafeZoneDao;
import com.safety.womensafetyapp.data.dao.UnsafeZoneDao_Impl;
import com.safety.womensafetyapp.data.dao.UserDao;
import com.safety.womensafetyapp.data.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile EmergencyContactDao _emergencyContactDao;

  private volatile SafeZoneDao _safeZoneDao;

  private volatile UnsafeZoneDao _unsafeZoneDao;

  private volatile MessageLogDao _messageLogDao;

  private volatile GuardianAckDao _guardianAckDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `username` TEXT NOT NULL, `email` TEXT, `phone` TEXT NOT NULL, `fullName` TEXT, `profileImage` TEXT, `emergencyMessage` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `lastActive` INTEGER NOT NULL, `homeLatitude` REAL, `homeLongitude` REAL, `workLatitude` REAL, `workLongitude` REAL, `isLocationSharingEnabled` INTEGER NOT NULL, `sosContacts` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `emergency_contacts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `name` TEXT NOT NULL, `phone` TEXT NOT NULL, `email` TEXT, `relation` TEXT, `isPrimary` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_emergency_contacts_userId` ON `emergency_contacts` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `safe_zones` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `address` TEXT NOT NULL, `phone` TEXT, `isVerified` INTEGER NOT NULL, `addedBy` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `unsafe_zones` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `address` TEXT NOT NULL, `reportedBy` TEXT NOT NULL, `reportDate` INTEGER NOT NULL, `description` TEXT, `dangerLevel` TEXT NOT NULL, `isVerified` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `message_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `messageType` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `content` TEXT NOT NULL, `recipients` TEXT NOT NULL, `status` TEXT NOT NULL, `latitude` REAL, `longitude` REAL, `locationName` TEXT, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_message_logs_userId` ON `message_logs` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `guardian_acks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `messageId` INTEGER NOT NULL, `guardianId` INTEGER NOT NULL, `ackTime` INTEGER NOT NULL, `status` TEXT NOT NULL, `message` TEXT, FOREIGN KEY(`messageId`) REFERENCES `message_logs`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`guardianId`) REFERENCES `emergency_contacts`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_guardian_acks_messageId` ON `guardian_acks` (`messageId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_guardian_acks_guardianId` ON `guardian_acks` (`guardianId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9e3842f8d220ef93e5ebae68b6c700d2')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `emergency_contacts`");
        db.execSQL("DROP TABLE IF EXISTS `safe_zones`");
        db.execSQL("DROP TABLE IF EXISTS `unsafe_zones`");
        db.execSQL("DROP TABLE IF EXISTS `message_logs`");
        db.execSQL("DROP TABLE IF EXISTS `guardian_acks`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(16);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("fullName", new TableInfo.Column("fullName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profileImage", new TableInfo.Column("profileImage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("emergencyMessage", new TableInfo.Column("emergencyMessage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("lastActive", new TableInfo.Column("lastActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("homeLatitude", new TableInfo.Column("homeLatitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("homeLongitude", new TableInfo.Column("homeLongitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("workLatitude", new TableInfo.Column("workLatitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("workLongitude", new TableInfo.Column("workLongitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isLocationSharingEnabled", new TableInfo.Column("isLocationSharingEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("sosContacts", new TableInfo.Column("sosContacts", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.safety.womensafetyapp.data.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsEmergencyContacts = new HashMap<String, TableInfo.Column>(7);
        _columnsEmergencyContacts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("relation", new TableInfo.Column("relation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("isPrimary", new TableInfo.Column("isPrimary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmergencyContacts = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysEmergencyContacts.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesEmergencyContacts = new HashSet<TableInfo.Index>(1);
        _indicesEmergencyContacts.add(new TableInfo.Index("index_emergency_contacts_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoEmergencyContacts = new TableInfo("emergency_contacts", _columnsEmergencyContacts, _foreignKeysEmergencyContacts, _indicesEmergencyContacts);
        final TableInfo _existingEmergencyContacts = TableInfo.read(db, "emergency_contacts");
        if (!_infoEmergencyContacts.equals(_existingEmergencyContacts)) {
          return new RoomOpenHelper.ValidationResult(false, "emergency_contacts(com.safety.womensafetyapp.data.model.EmergencyContact).\n"
                  + " Expected:\n" + _infoEmergencyContacts + "\n"
                  + " Found:\n" + _existingEmergencyContacts);
        }
        final HashMap<String, TableInfo.Column> _columnsSafeZones = new HashMap<String, TableInfo.Column>(9);
        _columnsSafeZones.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("isVerified", new TableInfo.Column("isVerified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSafeZones.put("addedBy", new TableInfo.Column("addedBy", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSafeZones = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSafeZones = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSafeZones = new TableInfo("safe_zones", _columnsSafeZones, _foreignKeysSafeZones, _indicesSafeZones);
        final TableInfo _existingSafeZones = TableInfo.read(db, "safe_zones");
        if (!_infoSafeZones.equals(_existingSafeZones)) {
          return new RoomOpenHelper.ValidationResult(false, "safe_zones(com.safety.womensafetyapp.data.model.SafeZone).\n"
                  + " Expected:\n" + _infoSafeZones + "\n"
                  + " Found:\n" + _existingSafeZones);
        }
        final HashMap<String, TableInfo.Column> _columnsUnsafeZones = new HashMap<String, TableInfo.Column>(9);
        _columnsUnsafeZones.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("reportedBy", new TableInfo.Column("reportedBy", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("reportDate", new TableInfo.Column("reportDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("dangerLevel", new TableInfo.Column("dangerLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnsafeZones.put("isVerified", new TableInfo.Column("isVerified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUnsafeZones = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUnsafeZones = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUnsafeZones = new TableInfo("unsafe_zones", _columnsUnsafeZones, _foreignKeysUnsafeZones, _indicesUnsafeZones);
        final TableInfo _existingUnsafeZones = TableInfo.read(db, "unsafe_zones");
        if (!_infoUnsafeZones.equals(_existingUnsafeZones)) {
          return new RoomOpenHelper.ValidationResult(false, "unsafe_zones(com.safety.womensafetyapp.data.model.UnsafeZone).\n"
                  + " Expected:\n" + _infoUnsafeZones + "\n"
                  + " Found:\n" + _existingUnsafeZones);
        }
        final HashMap<String, TableInfo.Column> _columnsMessageLogs = new HashMap<String, TableInfo.Column>(10);
        _columnsMessageLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("messageType", new TableInfo.Column("messageType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("recipients", new TableInfo.Column("recipients", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessageLogs.put("locationName", new TableInfo.Column("locationName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMessageLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMessageLogs.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMessageLogs = new HashSet<TableInfo.Index>(1);
        _indicesMessageLogs.add(new TableInfo.Index("index_message_logs_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoMessageLogs = new TableInfo("message_logs", _columnsMessageLogs, _foreignKeysMessageLogs, _indicesMessageLogs);
        final TableInfo _existingMessageLogs = TableInfo.read(db, "message_logs");
        if (!_infoMessageLogs.equals(_existingMessageLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "message_logs(com.safety.womensafetyapp.data.model.MessageLog).\n"
                  + " Expected:\n" + _infoMessageLogs + "\n"
                  + " Found:\n" + _existingMessageLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsGuardianAcks = new HashMap<String, TableInfo.Column>(6);
        _columnsGuardianAcks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGuardianAcks.put("messageId", new TableInfo.Column("messageId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGuardianAcks.put("guardianId", new TableInfo.Column("guardianId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGuardianAcks.put("ackTime", new TableInfo.Column("ackTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGuardianAcks.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGuardianAcks.put("message", new TableInfo.Column("message", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGuardianAcks = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysGuardianAcks.add(new TableInfo.ForeignKey("message_logs", "CASCADE", "NO ACTION", Arrays.asList("messageId"), Arrays.asList("id")));
        _foreignKeysGuardianAcks.add(new TableInfo.ForeignKey("emergency_contacts", "CASCADE", "NO ACTION", Arrays.asList("guardianId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesGuardianAcks = new HashSet<TableInfo.Index>(2);
        _indicesGuardianAcks.add(new TableInfo.Index("index_guardian_acks_messageId", false, Arrays.asList("messageId"), Arrays.asList("ASC")));
        _indicesGuardianAcks.add(new TableInfo.Index("index_guardian_acks_guardianId", false, Arrays.asList("guardianId"), Arrays.asList("ASC")));
        final TableInfo _infoGuardianAcks = new TableInfo("guardian_acks", _columnsGuardianAcks, _foreignKeysGuardianAcks, _indicesGuardianAcks);
        final TableInfo _existingGuardianAcks = TableInfo.read(db, "guardian_acks");
        if (!_infoGuardianAcks.equals(_existingGuardianAcks)) {
          return new RoomOpenHelper.ValidationResult(false, "guardian_acks(com.safety.womensafetyapp.data.model.GuardianAck).\n"
                  + " Expected:\n" + _infoGuardianAcks + "\n"
                  + " Found:\n" + _existingGuardianAcks);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "9e3842f8d220ef93e5ebae68b6c700d2", "995796ad07f8e5fc88eaeaf9ce489fb2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","emergency_contacts","safe_zones","unsafe_zones","message_logs","guardian_acks");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `emergency_contacts`");
      _db.execSQL("DELETE FROM `safe_zones`");
      _db.execSQL("DELETE FROM `unsafe_zones`");
      _db.execSQL("DELETE FROM `message_logs`");
      _db.execSQL("DELETE FROM `guardian_acks`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EmergencyContactDao.class, EmergencyContactDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SafeZoneDao.class, SafeZoneDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UnsafeZoneDao.class, UnsafeZoneDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MessageLogDao.class, MessageLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GuardianAckDao.class, GuardianAckDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public EmergencyContactDao emergencyContactDao() {
    if (_emergencyContactDao != null) {
      return _emergencyContactDao;
    } else {
      synchronized(this) {
        if(_emergencyContactDao == null) {
          _emergencyContactDao = new EmergencyContactDao_Impl(this);
        }
        return _emergencyContactDao;
      }
    }
  }

  @Override
  public SafeZoneDao safeZoneDao() {
    if (_safeZoneDao != null) {
      return _safeZoneDao;
    } else {
      synchronized(this) {
        if(_safeZoneDao == null) {
          _safeZoneDao = new SafeZoneDao_Impl(this);
        }
        return _safeZoneDao;
      }
    }
  }

  @Override
  public UnsafeZoneDao unsafeZoneDao() {
    if (_unsafeZoneDao != null) {
      return _unsafeZoneDao;
    } else {
      synchronized(this) {
        if(_unsafeZoneDao == null) {
          _unsafeZoneDao = new UnsafeZoneDao_Impl(this);
        }
        return _unsafeZoneDao;
      }
    }
  }

  @Override
  public MessageLogDao messageLogDao() {
    if (_messageLogDao != null) {
      return _messageLogDao;
    } else {
      synchronized(this) {
        if(_messageLogDao == null) {
          _messageLogDao = new MessageLogDao_Impl(this);
        }
        return _messageLogDao;
      }
    }
  }

  @Override
  public GuardianAckDao guardianAckDao() {
    if (_guardianAckDao != null) {
      return _guardianAckDao;
    } else {
      synchronized(this) {
        if(_guardianAckDao == null) {
          _guardianAckDao = new GuardianAckDao_Impl(this);
        }
        return _guardianAckDao;
      }
    }
  }
}
