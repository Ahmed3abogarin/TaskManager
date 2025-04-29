package com.ahmed.taskmanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM Task")
    fun getTasks(): Flow<List<Task>>

    @Query(
        """
        SELECT * FROM Task ORDER BY
    CASE
        WHEN priority LIKE 'L%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'H%' THEN 3
    END
    """
    )
    fun sortByLowPriority(): Flow<List<Task>>


    @Query(
        """
        SELECT * FROM Task ORDER BY
    CASE
        WHEN priority LIKE 'H%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'L%' THEN 3
    END
    """
    )
    fun sortByHighPriority(): Flow<List<Task>>


    @Query("""
        SELECT * FROM Task 
        WHERE (:filter = 0 OR 
              (:filter = 1 AND done = 1) OR 
              (:filter = 2 AND done = 0))
        ORDER BY
           CASE 
            WHEN :sortOrder = 0 THEN
                CASE priority
                    WHEN 'NONE' THEN 4
                    WHEN 'LOW' THEN 3
                    WHEN 'MEDIUM' THEN 2
                    WHEN 'HIGH' THEN 1
                END
        END ASC,
        CASE 
            WHEN :sortOrder = 1 THEN
                CASE priority
                    WHEN 'HIGH' THEN 1
                    WHEN 'MEDIUM' THEN 2
                    WHEN 'LOW' THEN 3
                    WHEN 'NONE' THEN 4
                END
        END DESC,
        id ASC
    """)
    fun getTasksList(filter: Int, sortOrder: Int): Flow<List<Task>>

}