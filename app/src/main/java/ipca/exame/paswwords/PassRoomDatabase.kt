package ipca.exame.paswwords

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Pass::class], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun passDao(): PassDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )

                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.passDao())
                    }
                }
            }
        }


        fun populateDatabase(passDao: PassDao) {

            passDao.deleteAll()

            var pass = Pass("Password", "facebook.com", "boa passe", "31/12/2019")
            passDao.insert(pass)
            pass = Pass("Portugal", "instagram.com", "horrivel", "20/10/2002")
            passDao.insert(pass)
        }
    }

}