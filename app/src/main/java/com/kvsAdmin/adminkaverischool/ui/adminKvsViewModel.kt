package com.kvsAdmin.adminkaverischool.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.kvsAdmin.adminkaverischool.Constants.ADMINS
import com.kvsAdmin.adminkaverischool.Constants.ALLPICS
import com.kvsAdmin.adminkaverischool.Constants.ANNOUNCEMET
import com.kvsAdmin.adminkaverischool.Constants.POSTS
import com.kvsAdmin.adminkaverischool.data.AllPicsUploadList
import com.kvsAdmin.adminkaverischool.data.Announcement
import com.kvsAdmin.adminkaverischool.data.ImgData
import com.kvsAdmin.adminkaverischool.data.PicUid
import com.kvsAdmin.adminkaverischool.data.addingPost
import com.kvsAdmin.adminkaverischool.data.recievingPost
import com.kvsAdmin.adminkaverischool.navigation.DestinationScreen
import com.kvsAdmin.adminkaverischool.states.allImageUriList
import com.kvsAdmin.adminkaverischool.states.announcementsDataList
import com.kvsAdmin.adminkaverischool.states.errorMsg
import com.kvsAdmin.adminkaverischool.states.imageUriList
import com.kvsAdmin.adminkaverischool.states.inProgress
import com.kvsAdmin.adminkaverischool.states.onError
import com.kvsAdmin.adminkaverischool.states.postsDataList
import com.kvsAdmin.adminkaverischool.states.signIn
import com.kvsAdmin.util.navigateTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class adminKvsViewModel @Inject constructor(
    val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    fun login(
        email: String,
        password: String,
        navController: NavController

    ) = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true
        val admin = db.collection(ADMINS)

            .where(
                Filter.and(
                    Filter.equalTo("email", email),
                    Filter.equalTo("password", password),

                    )
            )
            .get()
            .await()
        inProgress.value = false

        if (email.isEmpty() || password.isEmpty()) {
            handleException(customMessage = "Please fill all the fields")

        } else {
            inProgress.value = true
            try {
                if (!admin.isEmpty) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnFailureListener {
                            handleException(it)
                        }
                        .addOnCompleteListener {

                            if (it.isSuccessful) {
                                signIn.value = true
                                inProgress.value = false
                                auth.currentUser?.uid?.let {
                                    //                        getUserData(it)
                                }
                                afterLogin(navController)
                            } else {
                                handleException(it.exception, customMessage = "Login failed")
                            }

                        }
                } else {
                    errorMsg.value = "Invalid User"
                    onError.value = true
                }
            } catch (e: FirebaseAuthException) {
                handleException(e)
            }
            inProgress.value = false

        }
    }


    fun handleException(
        exception: Exception? = null,
        customMessage: String = ""
    ) {
        onError.value = true
        if (exception != null) {
            errorMsg.value = exception.toString()
        } else {
            errorMsg.value = customMessage
        }

        errorMsg.value = exception.toString()
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage
        val message =
            if (customMessage.isEmpty())
                errorMsg
            else
                customMessage
        onError.value = false
        inProgress.value = false
    }


    private fun afterLogin(
        navController: NavController
    ) = CoroutineScope(Dispatchers.Main).launch {
        delay(500)
        navigateTo(navController, DestinationScreen.HomeScreen.route)
    }

    fun announcement(
        classroom: String,
        section: String,
        text: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        val sortTime = Calendar.getInstance().time.time.toString()
        val time = Calendar.getInstance().time.toString()
        val uid = UUID.randomUUID().toString()
        val item = Announcement(
            classroom = classroom,
            section = section,
            sortTime = sortTime,
            text = text,
            timeStamp = time,
            uid = uid
        )

        db.collection(ANNOUNCEMET)
            .document(classroom)
            .collection(section)
            .document()
            .set(item)
    }

    fun onPost(
        selectedImage: List<Uri?>,
        shortDisc: String,
        longDisc: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        val uid = UUID.randomUUID().toString()
        val sortTime = Calendar.getInstance().time.time.toString()
        val time = Calendar.getInstance().time.toString()
        inProgress.value = true
        val uidList = mutableStateListOf<String>()

        if (selectedImage.isNotEmpty()) {
            selectedImage.forEach {
                val uidSingle = UUID.randomUUID().toString()
                uidList.add(uidSingle)
                val storageRef = storage.reference
                val imageRef = storageRef.child("PostImages/$uidSingle")
                val uploadTask = it.let { it1 ->
                    imageRef
                        .putFile(it1!!)
                }
                uploadTask.addOnSuccessListener {
                    it.metadata
                        ?.reference
                        ?.downloadUrl

                    inProgress.value = false
                }
                    .addOnFailureListener {
                        handleException(it)
                    }
                    .await()
            }

            val post = addingPost(
                title = shortDisc,
                disc = longDisc,
                imageUidList = uidList,
                uid = uid,
                sortTime = sortTime,
                timeStamp = time
            )
            db.collection(POSTS).document(uid).set(post)
                .await()

        }
        inProgress.value = false
    }

    fun uploadAllImage(
        imgList: List<Uri?>
    ) = CoroutineScope(Dispatchers.IO).launch {

        val storageRef = storage.reference
        imgList.forEach {
            val imgId = UUID.randomUUID().toString()
            val pfp = AllPicsUploadList(
                uid = imgId
            )
            db.collection(ALLPICS).document(imgId).set(pfp)
            val imageRef = storageRef.child("AllImages/$imgId")
            val uploadTask = it.let { it1 ->
                imageRef
                    .putFile(it1!!)
            }
            uploadTask.addOnSuccessListener {
                it.metadata
                    ?.reference
                    ?.downloadUrl
                inProgress.value = false
            }
                .addOnFailureListener {
                    handleException(it)
                }
        }
    }

    init {
        populatePost()
    }

    fun downloadMultipleImages(
        uid: String,
    ) = CoroutineScope(Dispatchers.IO).launch {
        val imageUri = mutableStateOf<Bitmap?>(null)

        inProgress.value = true
        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val storageRef = FirebaseStorage.getInstance().reference
            val bytes = storageRef.child("PostImages/$uid")
                .getBytes(maxDownloadSize)
                .await()
            imageUri.value = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageUriList.add(imageUri.value)


        } catch (e : FirebaseFirestoreException){
            handleException(e)
        }
        catch (e : StorageException){
            handleException(e)
        }
        catch (e: Exception) {
            handleException(e)
        }
        inProgress.value = false
    }

    fun populatePost() = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true
        db.collection(POSTS).orderBy("sortTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { value ->
                if (value != null) {
                    postsDataList.value = value.documents.mapNotNull {
                        it.toObject<recievingPost>()
                    }
                    inProgress.value = false
                }
            }
            .addOnFailureListener {
                errorMsg.value = "Invalid User"
                onError.value = true
            }
    }

    fun populateAnnouncement(
        classNo: String,
        section: String = "A"
    ) = CoroutineScope(Dispatchers.IO).launch {
        if (classNo.isNotEmpty() && section.isNotEmpty()) {
            inProgress.value = true
            db.collection(ANNOUNCEMET).document(classNo)
                .collection(section)
                .orderBy("sortTime", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { value ->
                    if (value != null) {
                        announcementsDataList.value = value.documents.mapNotNull {
                            it.toObject<Announcement>()
                        }
                        inProgress.value = false
                    }
                }
                .addOnFailureListener {
                    errorMsg.value = "Invalid User"
                    onError.value = true
                }
        } else {
            handleException(customMessage = "class number not selected")
        }
    }

    fun onDeletePost(
        postId: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        inProgress.value = true
        val post = db.collection(POSTS)
            .whereEqualTo("uid", postId)
            .get()
            .await()
        if (post.documents.isNotEmpty()) {
            for (document in post) {
                try {
                    document.reference.delete().await()
                } catch (e: FirebaseFirestoreException) {
                    handleException(e)
                } catch (e: Exception) {
                    handleException(e)
                }
            }

        }
        inProgress.value = false
    }

    fun deletePhoto(
        postId: String,
        type: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        delay(500)
        inProgress.value = true
        if (type == "allPics") {
            db.collection(ALLPICS)
                .document(postId)
                .delete()

            val storageRef = storage.reference
            val desertRef = storageRef.child("AllImages/$postId")
            desertRef.delete()
                .addOnFailureListener {
                    handleException(it)
                }
        }
        else if(type == "postPics"){
            val storageRef = storage.reference
            val desertRef = storageRef.child("AllImages/$postId")
            desertRef.delete()
                .addOnFailureListener {
                    handleException(it)
                }
        }

        inProgress.value = false
    }

    fun onDeleteAnnouncement(
        postId: String,
        classNo: String,
        section: String = "A"
    ) = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true


        val post = db.collection(ANNOUNCEMET)
            .document(classNo)
            .collection(section)
            .whereEqualTo("uid", postId)
            .get()
            .await()
        if (post.documents.isNotEmpty()) {
            for (document in post) {
                try {
                    document.reference.delete().await()
                } catch (e: FirebaseFirestoreException) {
                    handleException(e)
                } catch (e: Exception) {
                    handleException(e)
                }
            }
        }
        inProgress.value = false
    }


    fun onShowAllPics() = CoroutineScope(Dispatchers.IO).launch {
        inProgress.value = true

        val snapShot = db.collection(ALLPICS)
            .get()
            .await()

        for (doc in snapShot.documents) {
            val post = doc.toObject<PicUid>()
            downloadAllImages(post!!.uid)

        }
        inProgress.value = false
    }

    fun downloadAllImages(uid: String?) = CoroutineScope(Dispatchers.IO).launch {
        val imageUri = mutableStateOf<Bitmap?>(null)
        inProgress.value = true

        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val storageRef = FirebaseStorage.getInstance().reference

            val bytes = storageRef.child("AllImages/$uid")
                .getBytes(maxDownloadSize)
                .await()
            imageUri.value = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val data = ImgData(
                uid = uid,
                bitmap = imageUri.value
            )
            allImageUriList.add(data)


        } catch (e: Exception) {
            handleException(e)
        }
        inProgress.value = false
    }


}


