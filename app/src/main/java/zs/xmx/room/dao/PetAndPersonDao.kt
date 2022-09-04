package zs.xmx.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import zs.xmx.room.model.PetAndPerson


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:   一对多
 *
 */
@Dao
interface PetAndPersonDao {

    @Transaction
    @Query("SELECT * FROM person_table")
    fun getPetAndOwners(): List<PetAndPerson>

    @Transaction
    @Query("DELETE FROM person_table")
    fun deleteAll()

}