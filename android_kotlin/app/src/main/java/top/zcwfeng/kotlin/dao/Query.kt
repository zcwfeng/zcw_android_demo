package top.zcwfeng.kotlin.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import top.zcwfeng.kotlin.bean.Dog
import top.zcwfeng.kotlin.bean.DogAndOwner
import top.zcwfeng.kotlin.bean.DogOwnerCrossRef
import top.zcwfeng.kotlin.bean.OwnerWithDogs

/**
 * 作者：zcw on 2020-01-02
 */

@Dao
interface QueryDao{

    @Transaction
//    @Query("SELECT * FROM Owner")
    @Query
        ("""
            SELECT
                Dog.dogId AS dogId,
        Dog.dogOwerId AS dogOwnerId,
        Dog.name AS name,
        _junction.ownerId
                FROM
                DogOwnerCrossRef AS _junction
                INNER JOIN Dog ON (_junction.dogId = Dog.dogId)
                WHERE _junction.ownerId IN (1, 2)
                """
    )
    fun getDogsAndOwners(): List<OwnerWithDogs>
}


