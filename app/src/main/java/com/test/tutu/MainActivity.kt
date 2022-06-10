package com.test.tutu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.tutu.ui.screens.home.HomeViewModel
import com.test.tutu.ui.screens.home.UiState
import com.test.tutu.ui.theme.Purple200
import com.test.tutu.ui.theme.TestTutuTheme
import com.test.tutu.utils.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel: HomeViewModel by viewModels()

        setContent {
            val navController = rememberNavController()


            TestTutuTheme {
                Scaffold()
                {
                    NavHost(
                        navController,
                        startDestination = Screens.PlanetList.route,
                    ) {
                        composable(Screens.PlanetList.route) {
                            ListPlanets(homeViewModel, navController)
                        }
                        composable(Screens.DetailPlanet.route) {
                            CardPlanet(homeViewModel, navController)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ListPlanets(homeViewModel: HomeViewModel, navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        homeViewModel.getPlanet()

        val uiState = homeViewModel.uiListState
        val planet = homeViewModel.listPlanet

        when (uiState.value) {
            UiState.InProgress -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            UiState.PlanetListReady -> {
                homeViewModel.setPlanetDB(planet)
                LazyColumn(Modifier.fillMaxSize()) {
                    planet.forEach {
                        item {
                            RowPlanet(
                                it.name,
                                it.population,
                                it.climate,
                                it.terrain
                            ) {
                                // Bad model in Api, i don't did fixed it. Therefore did so
                                homeViewModel.id = it.url[it.url.length - 2].digitToInt()

                                navController.navigate(Screens.DetailPlanet.route)
                            }
                        }
                    }
                }
            }
            UiState.Error -> {
                Text(text = "ERROR RESPONSE")
            }
            else -> {

            }
        }

    }
}


@Composable
fun RowPlanet(
    name: String,
    population: String,
    climate: String,
    terrain: String,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(1.dp, color = Purple200)
            .clickable {
                onClick()
            }) {
        Column(
            Modifier
                .weight(0.5f)
                .padding(5.dp)
        ) {
            Text(text = "Name: $name", fontWeight = FontWeight.Bold)
            Text(text = "Population: $population")
        }
        Column(
            Modifier
                .weight(0.5f)
                .padding(5.dp)
        ) {
            Text(text = "Climate: $climate")
            Text(text = "Terrain: $terrain")
        }
    }
}

@Composable
fun CardPlanet(viewModel: HomeViewModel, navController: NavController) {
    viewModel.getDetail()
    Card(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable { navController.navigate(Screens.PlanetList.route) }
            ) {
                Icon(Icons.Default.Close, "close")
            }
            Column(Modifier.background(Purple200)) {
                Text(
                    text = "Name  Planet: ${viewModel.detailPlanet.name}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Rotation period ${viewModel.detailPlanet.rotationPeriod}, orbital_period: ${viewModel.detailPlanet.orbitalPeriod}, diametr(km): ${viewModel.detailPlanet.diameter}",
                    Modifier.padding(5.dp)
                )
                Text(
                    text = "climate: ${viewModel.detailPlanet.climate}, gravity: ${viewModel.detailPlanet.gravity}, terrain: ${viewModel.detailPlanet.terrain}, surface_water: ${viewModel.detailPlanet.surfaceWater}",
                    Modifier.padding(5.dp)
                )
                Text(
                    text = "Population: ${viewModel.detailPlanet.population}",
                    Modifier.padding(5.dp)
                )
            }
            FilmsRow(title = "Films", films = viewModel.detailPlanet.films )
        }
    }
}

@Composable
fun FilmsRow(title: String, films: List<String>) {
    var state by remember {
        mutableStateOf(false)
    }
    val icon = if (state) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    Column(Modifier.padding(top = 14.dp, start = 24.dp, end = 14.dp, bottom = 18.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { state = !state }) {
            Text(
                text = title,
                fontWeight = FontWeight(600),
                modifier = Modifier.weight(0.80f)
            )
            Image(
                imageVector = icon,
                contentDescription = "arrowDropDown",
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier.weight(0.05f)
            )

        }
        if (state) {
            films.forEach{
                Text(
                    text = it,
                    textAlign = TextAlign.Left
                )
            }
        }
    }

    Row() {
        Divider(
            color = Color.Black,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}