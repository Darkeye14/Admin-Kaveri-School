package com.kvsAdmin.adminkaverischool.data

import android.graphics.Bitmap
import android.net.Uri

data class ToggleableInfo(
    val isChecked: Boolean,
    val text : String
)
data class PicUid(
    val uid : String ?= ""
)

data class ImgData(
    val uid :String ?="",
    val bitmap : Bitmap?
)

data class Announcement(
    val classroom : String ?="",
    val section : String?="",
    val text : String?="",
    val sortTime : String?="",
    val timeStamp :String?="",
    val uid : String ?= ""
)
data class AllPicsUploadList(
    val uid: String?= ""
)
data class addingPost(
    val title :String? = "",
    val disc :String ? = "",
    val imageUidList : List<String?> ?= listOf(),
    val uid :String?= "",
    val sortTime : String?="",
    val timeStamp :String?="",
)
data class recievingPost(
    val title :String? = "",
    val disc :String ? = "",
    val imageUidList : List<String?> ?= listOf(),
    val uid :String?= "",
    val sortTime : String?="",
    val timeStamp :String?="",
)
