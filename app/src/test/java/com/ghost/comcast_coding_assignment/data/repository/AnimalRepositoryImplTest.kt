package com.ghost.comcast_coding_assignment.data.repository


import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.data.remote.ApiService
import com.ghost.comcast_coding_assignment.utils.AnimalType
import com.ghost.comcast_coding_assignment.utils.UiStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

class AnimalRepositoryImplTest {

 private lateinit var apiService: ApiService
 private lateinit var repository: AnimalRepositoryImpl

 @Before
 fun setUp() {
  apiService = mock(ApiService::class.java)
  repository = AnimalRepositoryImpl(apiService)
 }

 @Test
 fun `getAllAnimals emits Error when all fetches fail`() = runTest {
  val errorResponse = Response.error<ArrayList<AnimalListItemModel>>(
   500,
   "error".toResponseBody("application/json".toMediaTypeOrNull())
  )
  `when`(apiService.getAnimals(anyString())).thenReturn(errorResponse)

  val result = repository.getAllAnimals().first { it is UiStatus.Error }
  assertTrue(result is UiStatus.Error)
 }

 @Test
 fun `getAllAnimals emits Error when one fetch throws exception`() = runTest {
  //Arrange
  `when`(apiService.getAnimals(AnimalType.DOG.type)).thenThrow(RuntimeException("Network error"))
  `when`(apiService.getAnimals(AnimalType.BIRD.type)).thenReturn(
   Response.success(arrayListOf())
  )
  `when`(apiService.getAnimals(AnimalType.BUG.type)).thenReturn(
   Response.success(arrayListOf())
  )

  //Act
  val result = repository.getAllAnimals().first { it is UiStatus.Error }

  //Assert
  assertTrue(result is UiStatus.Error)
 }

 @Test
 fun `getAllAnimals emits Error if response is html`() = runTest {
  //Arrange
  val response = Response.success(arrayListOf<AnimalListItemModel>())
  val spyResponse = spy(response)
  `when`(spyResponse.headers()).thenReturn(mapOf("Content-Type" to "text/html").toHeaders())
  `when`(apiService.getAnimals(anyString())).thenReturn(spyResponse)

  val result = repository.getAllAnimals().first { it is UiStatus.Error }
  assertTrue(result is UiStatus.Error)
 }
}

// Helper extension for headers mocking
fun Map<String, String>.toHeaders(): Headers {
 val builder = Headers.Builder()
 forEach { (k, v) -> builder.add(k, v) }
 return builder.build()
}