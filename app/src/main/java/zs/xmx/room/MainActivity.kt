package zs.xmx.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import zs.xmx.room.databinding.ActivityMainBinding
import zs.xmx.room.db.RoomHelper
import zs.xmx.room.model.Cat
import zs.xmx.room.model.Dog
import zs.xmx.room.model.Person

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData()
        initEvent(mBinding)
    }

    private fun initData() {
        RoomHelper.get().petAndPersonDao().deleteAll()
        RoomHelper.get().catDao().deleteAll()
        //张三有三只狗
        val person = Person(name = "张三")
        RoomHelper.get().personDao().insert(person)
        for (i in 1..3) {
            RoomHelper.get().dogDao().insert(
                Dog(
                    dogPersonId = person.personId,
                    name = "狗$i",
                    age = i * 2,
                    gender = "母"
                )
            )
        }

        val person2 = Person(name = "李四")
        //李四有两狗两猫
        RoomHelper.get().personDao().insert(person2)
        for (i in 1..2) {
            RoomHelper.get().dogDao().insert(
                Dog(
                    dogPersonId = person2.personId,
                    name = "狗$i",
                    age = i * 2,
                    gender = "母"
                )
            )

            RoomHelper.get().catDao().insert(
                Cat(
                    catPersonId = person2.personId,
                    name = "猫$i",
                    gender = "公"
                )
            )
        }
    }

    private fun initEvent(mBinding: ActivityMainBinding) {
        mBinding.actionDeleteAll.setOnClickListener {
            RoomHelper.get().petAndPersonDao().deleteAll()
            RoomHelper.get().catDao().deleteAll()
        }
        mBinding.actionQueryMaxValue.setOnClickListener {
            //第一个Person 的 personId
            val personId = RoomHelper.get().personDao().queryAll()[0].personId
            lifecycleScope.launch {
                val max = RoomHelper.get().dogDao().queryMaxAge(personId)
                val min = RoomHelper.get().dogDao().queryMinAge(personId)
                val avg = RoomHelper.get().dogDao().queryAvgAge(personId)
                Log.i("TTTT", "Max: $max \n Min: $min \n Avg: $avg")
            }
        }
        mBinding.actionQueryAll.setOnClickListener {
            val dogsAndOwners = RoomHelper.get().petAndPersonDao().getPetAndOwners()
            dogsAndOwners.forEach {
                Log.i("TTTT", "Person: ${it.person} \n DogList: ${it.dog} \n CatList: ${it.cats}")
            }
        }
        mBinding.actionInsert.setOnClickListener {
            //王五有五只狗
            val person = Person(name = "王五")
            RoomHelper.get().personDao().insert(person)
            for (i in 1..5) {
                RoomHelper.get().dogDao().insert(
                    Dog(
                        dogPersonId = person.personId,
                        name = "狗DX$i",
                        age = i * 2,
                        gender = "雌性"
                    )
                )
            }
        }
    }
}