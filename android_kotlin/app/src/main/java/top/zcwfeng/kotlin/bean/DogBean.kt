package top.zcwfeng.kotlin.bean

import androidx.room.*

/**
 * 作者：zcw on 2020-01-02
 */
@Entity
data class Dog(
    @PrimaryKey val dogId:Long,
    val dogOwerId:Long,
    val name:String,
    val cuteness:Int,
    val barkVolume:Int,
    val breed:String
)

@Entity
data class Owner(@PrimaryKey val ownerId:Long,val name:String)

data class DogAndOwner(
    @Embedded
    val owner:Owner,
    @Relation(
      parentColumn = "ownerId",
        entityColumn = "dogOwnerId"
    )
    val dog:Dog
)




data class OwnerWithDogs(
    @Embedded val owner: Owner,
    @Relation(
        parentColumn = "ownerId",
        entity = Dog::class,
        entityColumn = "dogOwnerId",
        projection = ["name"],
        associateBy = Junction(DogOwnerCrossRef::class)

    )
    val dogs: List<Dog>,
    val dogNames:List<String>
)

@Entity(primaryKeys = ["dogId","ownerId"])
data class DogOwnerCrossRef(
    val dogId:Long,
    val ownerId:Long
)


data class Pup(
    val name:String,
    val cuteness:Int=11
)

data class OwnerWithPups(
    @Embedded val owner:Owner,
    @Relation(
        parentColumn = "ownerId",
        entity = Dog::class,
        entityColumn = "dogOwnerId"
    )
    val dogs:List<Pup>
)

//companion object {
//    /** 我是main入口函数 **/
//    @JvmStatic
//    fun main(args: Array<String>) {
//    }
//}