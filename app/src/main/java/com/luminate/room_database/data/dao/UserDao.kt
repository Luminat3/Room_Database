package com.luminate.room_database.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luminate.room_database.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY title ASC")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds) ORDER BY title ASC")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE title LIKE '%' || :search || '%' ORDER BY title ASC")
    fun searchByTitle(search: String): List<User>


    @Query("SELECT * FROM user WHERE title LIKE :first AND " +
            "detail LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query ("SELECT * FROM user WHERE uid = :uid ORDER BY title ASC")
    fun get(uid: Int) : User

    @Update
    fun update (user: User)
}