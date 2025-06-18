package com.yousufjamil.myjquran.accessories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectionListItem(
    title: String,
    multilineTitle: Boolean = false,
    description: String,
    icon: ImageVector? = null,
    onIconClick: () -> Unit = {},
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF151c21))
            .clickable {
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (icon != null) {
            Image(
                imageVector = icon,
                contentDescription = "Icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
                    .clickable {
                        onIconClick()
                    },
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                lineHeight = 40.sp,
                maxLines = if (multilineTitle) Int.MAX_VALUE else 1,
                softWrap = multilineTitle,
                overflow = if (multilineTitle) TextOverflow.Clip else TextOverflow.Ellipsis
            )

            Text(
                text = description,
                color = Color.Gray,
                maxLines = 2,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
        }
    }
}

data class SelectionListItemData(
    val title: String,
    val description: String,
    val icon: ImageVector? = null,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun SelectionListItemPreview() {
    Column {
        SelectionListItem(
            title = "Lorem ipsum",
            description = "Sample text for preview.",
            icon = Icons.Default.ThumbUp,
            onClick = {}
        )

        SelectionListItem(
            title = "Lorem ipsum",
            description = "Sample text for preview.",
            onClick = {}
        )
    }
}