package mohamad.hoseini.catapi.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mohamad.hoseini.catapi.R
import mohamad.hoseini.catapi.utils.clickableWithoutRipple


@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String = "Back",
    onBackClicked: () -> Unit = {},
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit = {}
) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
                .height(60.dp)
                .padding(horizontal = 14.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(
                Modifier.clickableWithoutRipple { onBackClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.ic_arrow_back), "",
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(Modifier.width(8.dp))
                Text(title, fontSize = 16.sp)
            }
            content()
        }
        HorizontalDivider(color = Color(0x1A8992A9), thickness = 1.dp)
    }
}