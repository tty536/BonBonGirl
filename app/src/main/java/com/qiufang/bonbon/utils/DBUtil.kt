package com.qiufang.bonbon.utils

import android.util.Log
import java.sql.*
import java.util.*
import kotlin.collections.HashMap

class DBUtil {
  companion object {
    private const val Tag = "DBLink"

    private const val driver = "com.mysql.jdbc.Driver"
    private const val user = "root"
    private const val password = "qiufang@123"
    private const val ip = "47.99.111.71"
    private const val port = "3306"

    private var stmt: Statement? = null
    private var connection: Connection? = null





    fun init(dbName: String) {
      try {
        Class.forName(driver)
        val url = "jdbc:mysql://$ip:$port/$dbName?" + "useUnicode=true&" +
                "characterEncoding=UTF-8&"+"serverTimezone=UTC"
        connection = DriverManager.getConnection(url, user, password)
        stmt = connection?.createStatement()
        Log.e(Tag,"Init connection and stmt Success")
      } catch (e: Exception) {
        Log.e(Tag,"Init connection and stmt Wrong$e")
        e.printStackTrace()
      }
    }

    fun login( id : Int,password: String) : Boolean{
      if (connection == null){
        return false;
      }else {
        val sql = "select * from users where id = ? and password = ? "
        return try {
          val pres:PreparedStatement = connection!!.prepareStatement(sql)
          pres.setInt(1,id)
          pres.setString(2,password)
          val res : ResultSet = pres.executeQuery()
          val t :Boolean= res.next()
          Log.e(Tag,"login $id Success")
          t
        } catch  (e:java.lang.Exception) {
          Log.e(Tag,"login $id Wrong$e")
          false
        }
      }
    }

    fun checkAccount(id:Int):Boolean{
      if (connection == null){
        return false;
      }else {
        val sql = "select * from users where id = ?  "
        return try {
          val pres:PreparedStatement = connection!!.prepareStatement(sql)
          pres.setInt(1,id)
          val res : ResultSet = pres.executeQuery()
          val t :Boolean= res.next()
          Log.e(Tag,"login $id Success")
          t
        } catch  (e:java.lang.Exception) {
          Log.e(Tag,"login $id Wrong$e")
          false
        }
      }
    }


    /*fun  register(name:String,password:String,telephone:String):Boolean{
      return if (connection==null){
        false;
      }else {
          //进行数据库操作
          val sql = "insert into users(name,password,telephone) values(?,?,?)";
        try {
          val pre = connection!!.prepareStatement(sql)
          pre.setString(1,name)
          pre.setString(2,password)
          pre.setString(3,telephone)
          Log.e(Tag,"insert $name Success")
          pre.execute()
          } catch ( e:java.lang.Exception) {
          Log.e(Tag,"insert $name Wrong$e")
          false
          }
      }
    }*/

    fun  closeDB() {
      try {
        stmt?.close();
        connection?.close()
        Log.e(Tag,"Close DataBase Success")
      } catch (e:java.lang.Exception) {
        Log.e(Tag,"Close DataBase Wrong$e")
        e.printStackTrace()
      }
    }


    fun queryMap(sql: String, vararg values: Any?): List<Map<*, *>> { //用于select
      val ret: MutableList<Map<*, *>> = LinkedList()

      if(null != connection){
        try {
          val pstmt = connection!!.prepareStatement(sql)
          for (i in values.indices) {
            pstmt.setObject(i + 1, values[i]) //setObject从1开始计数
          }
          val rs = pstmt.executeQuery() //返回resultSet类型
          val rsmt = pstmt.metaData //获得结果集元数据
          val colnum = rsmt.columnCount //获得列数
          while (rs.next()) {
            val row = mutableMapOf<String,String>()
            for (i in 1..colnum) {
              row[rsmt.getColumnLabel(i)] = rs.getObject(i).toString()  //存放 名称+数据入当前行数
            }
            ret.add(row) //将一行数据存放入总表
          }
        } catch (e: java.lang.Exception) {
          e.printStackTrace()
        }
      }
      return ret
    }

  }
}