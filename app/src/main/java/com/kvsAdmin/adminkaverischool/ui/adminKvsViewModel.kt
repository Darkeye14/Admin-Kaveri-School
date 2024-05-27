package com.kvsAdmin.adminkaverischool.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kvsAdmin.adminkaverischool.Constants.ADMINS
import com.kvsAdmin.adminkaverischool.Constants.ANNOUNCEMET
import com.kvsAdmin.adminkaverischool.data.Announcement
import com.kvsAdmin.adminkaverischool.navigation.DestinationScreen
import com.kvsAdmin.adminkaverischool.states.errorMsg
import com.kvsAdmin.adminkaverischool.states.inProgress
import com.kvsAdmin.adminkaverischool.states.onError
import com.kvsAdmin.adminkaverischool.states.signIn
import com.kvsAdmin.util.OnErrorMessage
import com.kvsAdmin.util.navigateTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar
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
                }
                else{
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
        val item = Announcement(
            classroom = classroom,
            section = section,
            time = sortTime,
            text = text
        )

        db.collection(ANNOUNCEMET)
            .document(classroom)
            .collection(section)
            .document()
            .set(item)
    }

}

@Composable
fun CompError(modifier: Modifier = Modifier) {
    if (onError.value){
        OnErrorMessage()
    }
}