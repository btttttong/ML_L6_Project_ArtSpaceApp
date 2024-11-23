package com.supakavadeer.m5_l6_project_artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.supakavadeer.m5_l6_project_artspaceapp.datasource.Artwork
import com.supakavadeer.m5_l6_project_artspaceapp.ui.theme.M5_L6_Project_ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            M5_L6_Project_ArtSpaceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val currentIndex = remember { mutableStateOf(0) }
    val imageIds: List<Int> = R.drawable::class.java.fields
        .filter { it.name.contains("cat") }
        .map { field -> field.getInt(null) }

    val artworks = imageIds.mapNotNull { imageId ->
        val resourceName = context.resources.getResourceEntryName(imageId)
        val titleResId =
            context.resources.getIdentifier(resourceName, "string", context.packageName)
        if (titleResId != 0) {
            Artwork(
                imageId = imageId,
                title = context.getString(titleResId),
                artist = "BT",
                year = "2024"
            )
        } else {
            null
        }
    }

    if (artworks.isNotEmpty()) {
        val currentArtwork = artworks[currentIndex.value]
        ArtSpaceLayout(
            modifier,
            artWork = currentArtwork,
            onPreviousClick =
            {
                if (currentIndex.value > 0) {
                    currentIndex.value -= 1
                }
            },
            onNextClick =
            {
                if (currentIndex.value < artworks.size - 1) {
                    currentIndex.value += 1
                }
            }
        )
    } else {
        Text(
            text = "No images found!"
        )
    }
}

@Composable
fun ArtSpaceLayout(
    modifier: Modifier = Modifier,
    artWork: Artwork,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onPreviousClick,
                    modifier = Modifier.size(width = 120.dp, height = 40.dp)
                ) {
                    Text(text = "Previous")
                }
                Button(
                    onClick = onNextClick,
                    modifier = Modifier.size(width = 120.dp, height = 40.dp)
                ) {
                    Text(text = "Next")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(400.dp)
                    .border(20.dp, Color.White, shape = RectangleShape)
                    .shadow(
                        elevation = 2.dp
                    )
            ) {
                Image(
                    painter = painterResource(id = artWork.imageId),
                    contentDescription = artWork.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .size(250.dp, 100.dp)
                    .background(Color(0xFFecebf4)),
                verticalArrangement = Arrangement.Center

                ) {
                Text(
                    text = artWork.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
                Text(
                    text = "${artWork.artist} (${artWork.year})",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpacePreview() {
    M5_L6_Project_ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}