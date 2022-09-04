package zs.xmx.room.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.util.UUIDUtil
import java.util.*

@Entity(tableName = "person_table", indices = [Index(value = arrayOf("personId"), unique = true)])
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val personId: String = UUID.randomUUID().toString(),
    val name: String
)


/*
    外键添加索引,约束字段
    Dog表的dogPersonId是Person表的外键，在Dog表上的dogPersonId添加index 索引
    https://blog.csdn.net/K_Hello/article/details/101282220
 */
@Entity(
    tableName = "dog_table",
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = arrayOf("personId"),
        childColumns = arrayOf("dogPersonId"),
        onUpdate = CASCADE,
        onDelete = CASCADE
    )],
    indices = [Index(
        value = arrayOf("dogId"),
        unique = true
    ), Index(value = arrayOf("dogPersonId"))]
)
data class Dog(
    @PrimaryKey(autoGenerate = true)
    var dogId: Long = 0,
    val dogPersonId: String = "",//dog表持有Person id
    val name: String,
    val age: Int,
    val gender: String
)

@Entity(tableName = "cat_table") //如果不加外键约束,PetAndPersonDao 对 Cat无影响
data class Cat(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val catPersonId: String = "",//cat表持有Person id
    val name: String,
    val gender: String
)

/**
 * 定义 Pet 与 Person 之间的关系
 */
data class PetAndPerson(
    @Embedded
    val person: Person,

    //Dog 中拥有 Person 的信息,使用 @Relation注解声明他们之间的关系
    @Relation(
        parentColumn = "personId",//不建议直接用主键,用单独定义的属性会更灵活
        entityColumn = "dogPersonId"
    )
    val dog: List<Dog>,
    //Cat 中拥有 Person 的信息,使用 @Relation注解声明他们之间的关系
    @Relation(
        parentColumn = "personId",
        entityColumn = "catPersonId"
    )
    val cats: List<Cat>
)