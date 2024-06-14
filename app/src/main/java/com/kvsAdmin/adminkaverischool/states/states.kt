package com.kvsAdmin.adminkaverischool.states

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.kvsAdmin.adminkaverischool.data.Announcement
import com.kvsAdmin.adminkaverischool.data.ImgData
import com.kvsAdmin.adminkaverischool.data.PicUid
import com.kvsAdmin.adminkaverischool.data.recievingPost

var inProgress = mutableStateOf( false)
var onError = mutableStateOf( false)
var errorMsg = mutableStateOf( "")
var signIn = mutableStateOf(false)
var imageUriList = mutableStateListOf<ImgData?>(null)
var toastState = mutableStateOf(false)
var postsDataList = mutableStateOf<List<recievingPost>>(listOf())
var announcementsDataList = mutableStateOf<List<Announcement>>(listOf())
var allImageUriList = mutableStateListOf<ImgData?>(null)
var profilesPics = mutableStateOf<List<PicUid>>(listOf())

