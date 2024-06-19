import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmpfirebase.composeapp.generated.resources.Res
import kmpfirebase.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth()) {
            var list by remember { mutableStateOf(listOf<User>()) }
            LaunchedEffect(Unit) {
                list = getUsers()
            }
            LazyColumn {
                items(list) {
                    UserItem(it)
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Column {
        Text(
            text = user.name
        )
        Text(
            text = user.age.toString()
        )
    }
}

suspend fun getUsers(): List<User> {
    val firebaseFirestore = Firebase.firestore
    try {
        val userResponse =
            firebaseFirestore.collection("USERS").get()
        return userResponse.documents.map {
            it.data()
        }
    } catch (e: Exception) {
        throw e
    }
}