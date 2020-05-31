package top.zcwfeng.kotlin.study

import java.util.ArrayList

class Repository
// keeping the constructor private to enforce the usage of getInstance
private constructor() {

    private var users: MutableList<User>? = null




    val formattedUserNames: List<String>
        get() {
            val userNames = ArrayList<String>(users!!.size)
            for ((firstName, lastName) in users!!) {
                val name: String?

                if (lastName != null) {
                    if (firstName != null) {
                        name = "$firstName $lastName"
                    } else {
                        name = lastName
                    }
                } else if (firstName != null) {
                    name = firstName
                } else {
                    name = "Unknown"
                }
                userNames.add(name)
            }
            return userNames
        }

    init {

        val user1 = User("Jane", "")
        val user2 = User("John", null)
        val user3 = User("Anne", "Doe")

        users = ArrayList()
        users!!.add(user1)
        users!!.add(user2)
        users!!.add(user3)
    }

    fun getUsers(): List<User>? {
        return users
    }

    companion object {

        private var INSTANCE: Repository? = null

        val instance: Repository?
            get() {
                if (INSTANCE == null) {
                    synchronized(Repository::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = Repository()
                        }
                    }
                }
                return INSTANCE
            }
    }





//    object Repository{
//        private val _users = mutableListOf(User("Jane",""),User("John",""),User("Anne","Doe"))
//        val users: List<User>
//            get() = _users
//        val formattedUserNames:List<String>
//            get() = _users.map { user -> user.formattedName }
//    }
}


val User.formattedName:String get() {
    return if(lastName != null){
        if(firstName != null) {
            "$firstName $lastName"
        } else {
            lastName ?: "Unknown"
        }
    } else {
        firstName ?: "Unknown"
    }
}