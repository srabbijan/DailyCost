package com.srabbijan.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.srabbijan.common.utils.getCurrentDateTime

@Entity(tableName = "category_table")
data class CategoryTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "icon") val icon: Int? = null,
    @ColumnInfo(name = "color") val color: String? = null,
)
