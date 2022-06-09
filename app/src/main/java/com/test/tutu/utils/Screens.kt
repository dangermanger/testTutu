package com.test.tutu.utils

sealed class Screens(val route: String) {
    object PlanetList: Screens("planetList")
    object DetailPlanet: Screens("detailPlanet")
}