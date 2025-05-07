package mohamad.hoseini.catapi.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlin.math.ln
import kotlin.math.pow

@Composable
infix fun Modifier.clickableWithoutRipple(onClicked: () -> Unit): Modifier {
    return this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClicked
    )
}