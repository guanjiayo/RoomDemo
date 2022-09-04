package zs.xmx.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import zs.xmx.room.model.Cat
import zs.xmx.room.model.Dog


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:
 *
 */
@Dao
interface CatDao {
    @Insert
    fun insert(cat: Cat)

    @Query("DELETE FROM cat_table")
    fun deleteAll()
}