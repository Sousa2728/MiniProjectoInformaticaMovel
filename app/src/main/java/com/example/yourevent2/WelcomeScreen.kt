package com.example.yourevent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.sp
import com.example.yourevent2.R

@Composable
fun WelcomeScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text=stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 55.sp,
            modifier = Modifier.padding(bottom = 200.dp)
        )

        Button(
            onClick = onContinueClicked,
            colors= ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(
                text=stringResource(R.string.continue_button_text),
                fontSize = 20.sp
            )


        }
    }

}

