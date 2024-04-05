package com.bogdan801.heartbeat_test_task.data.local_db

import androidx.room.Dao

@Dao
interface Dao {
    //insert
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activityInformationEntity: ActivityInformationEntity)*/

    //update
    /*@Update
    suspend fun updateStudentActivity(studentActivityEntity: StudentActivityEntity)*/

    //delete
    /*@Query("DELETE FROM activityinformationentity WHERE activityID == :activityID")
    suspend fun deleteActivity(activityID: Int)*/


    //delete all
    /*@Query("DELETE FROM activityinformationentity")
    suspend fun deleteAllActivities()*/


    //select
    /*@Query("SELECT * FROM activityinformationentity")
    fun getActivities() : Flow<List<ActivityInformationEntity>>*/
}