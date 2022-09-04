package zs.xmx.room.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zs.xmx.room.Utils
import zs.xmx.room.dao.CatDao
import zs.xmx.room.dao.PetAndPersonDao
import zs.xmx.room.dao.DogDao
import zs.xmx.room.dao.PersonDao
import zs.xmx.room.model.Cat
import zs.xmx.room.model.Dog
import zs.xmx.room.model.Person


@Database(
    entities = [Person::class, Dog::class, Cat::class],
    version = 2
)
abstract class RoomHelper : RoomDatabase() {

    //一对多, Person 对应 多个Pet (Dog,Cat) 的 Dao
    abstract fun petAndPersonDao(): PetAndPersonDao

    abstract fun dogDao(): DogDao

    abstract fun catDao(): CatDao

    abstract fun personDao(): PersonDao

    companion object {
        private const val DATABASE_NAME = "room_database"

        @Volatile
        private var INSTANCE: RoomHelper? = null

        fun get(): RoomHelper {
            return INSTANCE ?: synchronized(this) {
                buildDatabase().also { INSTANCE = it }
            }
        }

        private fun buildDatabase() =
            Room.databaseBuilder(Utils.getApplication(), RoomHelper::class.java, DATABASE_NAME)
                //不推荐打开这句，但是为了demo展示，我只能打开了
                .allowMainThreadQueries()
                .build()
    }

}