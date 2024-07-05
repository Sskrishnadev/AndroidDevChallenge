import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.composemvvm.R
import com.demo.composemvvm.navigation.Screens
import com.demo.composemvvm.viewmodel.FlickrViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlickrScreen(navController: NavHostController, viewModel: FlickrViewModel) {
    val items by viewModel.photoItems.observeAsState(null)
    var text by rememberSaveable { mutableStateOf("Flowers") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.fetchCreditCards("Flowers")
    }

    Column {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = text,
                label = { Text(text = "Please Enter Tag Here") },
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.fetchCreditCards(text)
                    keyboardController?.hide()
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                Button(onClick = {
                    viewModel.fetchCreditCards(text)
                    keyboardController?.hide()
                }) {
                    Text(text = "Find")
                }
            }
        }

        if (items == null) {
            // Show loading indicator or placeholder
            Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                GridCells.Fixed(2), modifier = Modifier.padding(10.dp)
            ) {
                items(items?.results?.size ?: 0) {
                    Card(
                        onClick = {
                            val selectedItem = Gson().toJson(items?.results?.get(it))
                            navController.navigate(Screens.Detail.route.plus("?flickerSelectedItem=${selectedItem}"))
                        }, modifier = Modifier.padding(8.dp), elevation = 6.dp
                    ) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            AsyncImage(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(items?.results?.get(it)?.media?.m).crossfade(true)
                                    .build(),
                                error = painterResource(R.drawable.ic_broken),
                                placeholder = painterResource(R.drawable.ic_placeholder),
                                contentDescription = stringResource(R.string.app_name),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth(1f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = items?.results?.get(it)?.title.toString(),
                                modifier = Modifier.padding(4.dp),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
            }
        }
    }
}
