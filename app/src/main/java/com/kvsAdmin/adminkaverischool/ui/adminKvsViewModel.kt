package com.kvsAdmin.adminkaverischool.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.kvsAdmin.adminkaverischool.Constants.ADMINS
import com.kvsAdmin.adminkaverischool.Constants.ALLPICS
import com.kvsAdmin.adminkaverischool.Constants.ANNOUNCEMET
import com.kvsAdmin.adminkaverischool.Constants.POSTS
import com.kvsAdmin.adminkaverischool.data.AllPicsUploadList
import com.kvsAdmin.adminkaverischool.data.Announcement
import com.kvsAdmin.adminkaverischool.data.addingPost
import com.kvsAdmin.adminkaverischool.data.recievingPost
import com.kvsAdmin.adminkaverischool.navigation.DestinationScreen
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

        val item = Announcement(
            classroom = classroom,
            section = section,
            sortTime = sortTime,
            text = text,
            timeStamp = time
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

        val post = addingPost(
            title = shortDisc,
            disc = longDisc,
            imageList = selectedImage,
            uid = uid,
            sortTime = sortTime,
            timeStamp = time
        )
        db.collection(POSTS).document(uid).set(post)
            .await()

        if (selectedImage.isNotEmpty()) {
            selectedImage.forEach {

                val storageRef = storage.reference
                val imageRef = storageRef.child("images/$uid/$it")
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
        uri: String?,
        uid : String,
    ) = CoroutineScope(Dispatchers.IO).launch {
        val imageUri = mutableStateOf<Bitmap?>(null)

        inProgress.value = true
        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val storageRef = FirebaseStorage.getInstance().reference
            val bytes = storageRef.child("images/$uid/$uri")
                .getBytes(maxDownloadSize)
                .await()
            imageUri.value = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            imageUriList.add(imageUri.value)


        } catch (e: Exception) {
            handleException(e)
        }
        inProgress.value = false
    }

    fun populatePost()= CoroutineScope(Dispatchers.IO).launch  {
        inProgress.value = true
        db.collection(POSTS).orderBy("sortTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { value->
                if (value != null) {
                    postsDataList.value = value.documents.mapNotNull {
                        it.toObject<recievingPost>()
                    }
                    inProgress.value = false
                }
            }
            .addOnFailureListener{
                errorMsg.value = "Invalid User"
                onError.value = true
            }
    }
}

