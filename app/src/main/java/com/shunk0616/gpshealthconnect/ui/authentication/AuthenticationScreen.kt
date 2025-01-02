package com.shunk0616.gpshealthconnect.ui.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun AuthenticationRoute(onFormCompleted: () -> Unit) {
    AuthenticationScreen(
        onFormCompleted = onFormCompleted
    )
}

@Composable
fun AuthenticationScreen(onFormCompleted: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "メアド認証Screen")

        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("メアド入力欄") },
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = onFormCompleted,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "入力完了")
        }
    }
}
