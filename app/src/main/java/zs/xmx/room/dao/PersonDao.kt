package zs.xmx.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import zs.xmx.room.model.Person
import zs.xmx.room.model.PetAndPerson


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:
 *
 */
@Dao
interface PersonDao {
    @Insert
    fun insert(person: Person)

    @Query("SELECT * FROM person_table")
    fun queryAll(): List<Person>
}