package mohamad.hoseini.catapi.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mohamad.hoseini.catapi.ui.theme.ButtonText


@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    text: String = "Button"
) {
    Button(
        onClick = { if (!loading) onClick() },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) backgroundColor else Color.Gray,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = contentColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = ButtonText
            )
        }
    }
}
