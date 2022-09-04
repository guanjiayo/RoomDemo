package zs.xmx.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import zs.xmx.room.model.Dog


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:
 *
 */
@Dao
interface DogDao {
    @Insert
    fun insert(dog: Dog)

    @Query("select max(age) from dog_table where dogPersonId = :personId")
    suspend fun queryMaxAge(personId: String): Double?

    @Query("select min(age) from dog_table where dogPersonId = :personId")
    suspend fun queryMinAge(personId: String): Double?

    @Query("select avg(age) from dog_table where dogPersonId = :personId")
    suspend fun queryAvgAge(personId: String): Double?
}