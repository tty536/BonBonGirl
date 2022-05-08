package com.qiufang.bonbon.data.model

class user {
    private lateinit var name : String
    private lateinit var image : String
    companion object{
        @Volatile
        private var instance : user? = null

        fun getInstance():user{
            if (instance == null){
                synchronized(this){
                    instance = user()
                }
            }
            return instance!!
        }
    }
}