package com.kvsAdmin.adminkaverischool.data

data class ToggleableInfo(
    val isChecked: Boolean,
    val text : String
)

data class Announcement(
    val classroom : String ?="",
    val section : String?="",
    val text : String?="",
    val time : String?="",
)