package com.srabbijan.database.dao

import androidx.room.*
import com.srabbijan.common.utils.TransactionType
import com.srabbijan.database.dto.ExpenseModel
import com.srabbijan.database.entity.ExpenseTable

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<ExpenseTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ExpenseTable)
    @Update
    suspend fun update(data: ExpenseTable)

    @Transaction
    @Query("DELETE FROM expense_table ")
    suspend fun deleteAll()

    @Transaction
    @Query("DELETE FROM expense_table  where id =:id")
    suspend fun deleteById( id: Long)

    @Transaction
    @Query(
        "SELECT et.id as id, 1 as transactions, et.type as type, et.amount as amount,  et.date as date, et.description as description, et.category_id as categoryId, " +
                " CASE WHEN et.category_id is not null THEN ct.name ELSE 'Others' END AS categoryName,"+
                " CASE WHEN et.category_id is not null THEN ct.icon ELSE null END AS categoryIcon"+

                " FROM expense_table as et" +
                " LEFT JOIN  category_table ct ON et.category_id = ct.id" +

                " WHERE (date(et.date) BETWEEN date(:start) AND date(:end))" +
                " ORDER BY et.date DESC"
    )
    suspend fun fetchBetweenDate(start: String, end: String): List<ExpenseModel>

    @Transaction
    @Query(
        "SELECT et.id as id, count(*) as transactions, et.type as type, sum(et.amount) as amount,  et.date as date, et.description as description, et.category_id as categoryId, " +
                " CASE WHEN et.category_id is not null THEN ct.name ELSE 'Others' END AS categoryName,"+
                " CASE WHEN et.category_id is not null THEN ct.icon ELSE null END AS categoryIcon"+

                " FROM expense_table as et" +
                " LEFT JOIN  category_table ct ON et.category_id = ct.id" +

                " WHERE (date(et.date) BETWEEN date(:start) AND date(:end))" +
                " GROUP BY et.category_id"+
                " ORDER BY amount DESC"
    )
    suspend fun fetchReportBetweenDate(start: String, end: String): List<ExpenseModel>

    @Transaction
    @Query(
        "select SUM (amount)  from expense_table where type=:type and (date(date) BETWEEN date(:start) AND date(:end))"
    )
    suspend fun sumAmountBetweenDate(start: String, end: String,type: String = TransactionType.CASH_IN.value): Double?
}
