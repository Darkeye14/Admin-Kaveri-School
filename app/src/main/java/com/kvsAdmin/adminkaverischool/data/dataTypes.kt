package com.kvsAdmin.adminkaverischool.data

import android.net.Uri

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
data class Post(
    val title :String? = "",
    val disc :String ? = "",
    val imageList : List<Uri?>,
    val uid :String?= ""
)