package com.demo.composemvvm.view

import android.content.Intent
import android.content.Intent.createChooser
import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.composemvvm.R
import com.demo.composemvvm.model.FlickrItem


@Composable
fun DetailScreen(navController: NavHostController, flickrItem: FlickrItem) {
    val context = LocalContext.current
    Column {
        Box(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context).data(flickrItem.media?.m)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken),
                placeholder = painterResource(R.drawable.ic_placeholder),
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth(1f)
            )

            Image(painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "Clickable Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        navController.popBackStack()
                    })
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                    append("Title : ")
                }
                append(flickrItem.title)
            })
            Spacer(modifier = Modifier.height(8.dp))
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                    append("Description : ")
                }
                append(Html.fromHtml(flickrItem.description))
            })
            Spacer(modifier = Modifier.height(8.dp))
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                    append("Author : ")
                }
                append(flickrItem.author)
            })
            Spacer(modifier = Modifier.height(8.dp))
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                    append("Date Of Published : ")
                }
                append(flickrItem.published)
            })

            Spacer(modifier = Modifier.height(8.dp))
            Image(painter = painterResource(id = android.R.drawable.ic_menu_share),
                contentDescription = "Share Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        try {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_TEXT, flickrItem.toString())
                                setType("*/*")
                            }
                            startActivity(context, createChooser(intent, "Share details..."), null)
                        } catch (e: Exception) {
                            println(e)
                        }
                    })
        }
    }
}