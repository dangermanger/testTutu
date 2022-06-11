package com.test.tutu.ui.screens.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.tutu.data.MainRepository
import com.test.tutu.data.model.StarWarsPlanet
import com.test.tutu.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    private val _uiState = mutableStateOf<UiState>(UiState.InProgress)
    val uiListState: State<UiState>
        get() = _uiState

    var id = 5

    var listPlanet by mutableStateOf<List<StarWarsPlanet>>(emptyList())
    var detailPlanet by mutableStateOf(StarWarsPlanet(-1))
    fun getPlanet() {
        viewModelScope.launch {
            val result = try {
                mainRepository.getPlanets()
            } catch (e: Exception) {
                NetworkResult.Error("Network request failed")
            }
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.value = UiState.InProgress
                }
                is NetworkResult.Success -> {
                    _uiState.value = UiState.PlanetListReady
                    listPlanet = result.data?.results ?: emptyList()
                }
                is NetworkResult.Error ->{
                    _uiState.value = UiState.PlanetListReady
                    getPlanetDB()

                }
            }
        }
    }

    fun setPlanetDB(listPlanet: List<StarWarsPlanet>) {
        viewModelScope.launch {
            try {
                if (listPlanet.isNotEmpty()){
                    mainRepository.setPlanetFromDB(listPlanet)
                }else{
                    getPlanet()
                }
            }catch (e: Exception){

            }
        }
    }
    private fun getPlanetDB(){
        viewModelScope.launch {
            try {
                listPlanet = mainRepository.getPlanetFromDB()
            } catch (e: Exception) {
                _uiState.value = UiState.Error
            }

        }
    }

   fun getDetailDB(){
       viewModelScope.launch {
           try {
              detailPlanet = mainRepository.getPlanetFromIdDB(id)
           } catch (e: Exception){
               _uiState.value = UiState.Error
           }
       }
    }

    fun getDetail() {
        viewModelScope.launch {
            val result = try {
                mainRepository.getDetail(id)
            } catch (e: Exception) {
                NetworkResult.Error("Network request failed")
            }
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.value = UiState.InProgress
                }
                is NetworkResult.Success -> {
                    _uiState.value = UiState.DetailPlanetReady
                    detailPlanet = result.data!!
                }
                else -> {
                    try {
                        getDetailDB()
                    } catch (e: Exception){
                        _uiState.value = UiState.Error
                    }
                }
            }
        }
    }
}


sealed class UiState() {
    object InProgress : UiState()
    object PlanetListReady : UiState()
    object DetailPlanetReady : UiState()
    object Error : UiState()
}