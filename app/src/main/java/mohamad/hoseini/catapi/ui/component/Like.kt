package mohamad.hoseini.catapi.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mohamad.hoseini.catapi.R

@Composable
fun Like(isLiked: Boolean, onLikeToggle: (isLiked: Boolean) -> Unit) {
    Box(
        Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onLikeToggle(!isLiked) },
        contentAlignment = Alignment.Center
    ) {
        if (isLiked) {
            Image(painter = painterResource(R.drawable.ic_liked), contentDescription = null)
        } else {
            Image(
                painter = painterResource(R.drawable.ic_unliked),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }

    }
}