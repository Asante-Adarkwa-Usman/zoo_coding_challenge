package com.ghost.comcast_coding_assignment.presentation.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ghost.comcast_coding_assignment.data.model.AnimalListItemModel
import com.ghost.comcast_coding_assignment.data.model.CharacteristicsModel
import com.ghost.comcast_coding_assignment.data.model.TaxonomyModel
import com.ghost.comcast_coding_assignment.domain.repository.AnimalUseCase
import com.ghost.comcast_coding_assignment.presentation.vm.AnimalsViewModel
import com.ghost.comcast_coding_assignment.utils.AnimalType
import com.ghost.comcast_coding_assignment.utils.UiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AnimalListScreenTest {

 @get:Rule
 val composeTestRule = createAndroidComposeRule<ComponentActivity>()

 private lateinit var viewModel: AnimalsViewModel


 @Before
 fun setUp() {
  val fakeUseCase =  FakeAnimalUseCase()
  viewModel = AnimalsViewModel(fakeUseCase, Dispatchers.Unconfined)

  composeTestRule.setContent {
   AnimalListScreen(viewModel = viewModel, modifier = Modifier)
  }
 }

 @Test
 fun testSearchFieldIsDisplayed() {
  composeTestRule
   .onNodeWithText("Search by name or common name")
   .assertIsDisplayed()
 }

 @Test
 fun testSearchUpdatesList() {
  composeTestRule
   .onNodeWithText("Search by name or common name")
   .performTextInput("African Wild Dog")

  composeTestRule.waitForIdle()

  composeTestRule
   .onNodeWithText("African Wild Dog")
   .assertIsDisplayed()
 }
}

//mocking use case class
class FakeAnimalUseCase : AnimalUseCase {
 override suspend fun execute(): Flow<UiStatus<List<AnimalListItemModel>>> {
  return flowOf(
   UiStatus.Success(testAnimals)
  )
 }

 //list of animals
 val testAnimals = listOf(
  AnimalListItemModel(
   name = "African Wild Dog",
   type = AnimalType.DOG,
   taxonomy = TaxonomyModel(
    kingdom = "Animalia",
    phylum = "Chordata",
    order = "Carnivora",
    family = "Canidae",
    genus = "Lycaon",
    scientificName = "Lycaon pictus"
   ),
   characteristics = CharacteristicsModel(
    prey = "Antelope, Warthog, Rodents",
    nameOfYoung = "Pup",
    groupBehavior = "Pack",
    estimatedPopulationSize = "Less than 5,000",
    biggestThreat = "Habitat loss",
    mostDistinctiveFeature = "Four toes on each foot rather than five",
    otherNames = "Hunting Dog, Painted Dog, Painted Wolf",
    gestationPeriod = "70 days",
    habitat = "Open plains and savanna",
    predators = "Lions, Hyenas, Humans",
    diet = "Carnivore",
    averageLitterSize = "8",
    lifestyle = "Crepuscular",
    commonName = "African Wild Dog",
    numberOfSpecies = "1",
    location = "sub-Saharan Africa",
    slogan = "Also known as the painted dog!",
    group = "Mammal",
    color = "BrownGreyRedBlackWhiteGoldTan",
    skinType = "Fur",
    topSpeed = "45 mph",
    lifespan = "10 - 13 years",
    weight = "17kg - 36kg (39lbs - 79lbs)",
    length = "75cm - 110cm (29in - 43in)",
    ageOfSexualMaturity = "12 - 18 months",
    ageOfWeaning = "3 months"
   ),
   locations = listOf("Africa")
  ),
  AnimalListItemModel(
   name = "American Bulldog",
   type = AnimalType.DOG,
   taxonomy = TaxonomyModel(
    kingdom = "Animalia",
    phylum = "Chordata",
    order = "Carnivora",
    family = "Canidae",
    genus = "Canis",
    scientificName = "Canis Lupus"
   ),
   characteristics = CharacteristicsModel(
    distinctiveFeature = "Small pendant shaped ears and strong body",
    temperament = "Assertive but loyal and friendly",
    training = "Hard",
    diet = "Omnivore",
    averageLitterSize = "8",
    type = "Mastiff",
    commonName = "American Bulldog",
    slogan = "Can jump up to 6ft high!",
    group = "Dog",
    color = "BrownRedBlackWhiteTanBrindle",
    skinType = "Hair"
   ),
   locations = listOf("North-America")
  ),
  AnimalListItemModel(
   name = "American Dog Tick",
   type = AnimalType.BUG,
   taxonomy = TaxonomyModel(
    kingdom = "Animalia",
    phylum = "Arthropoda",
    order = "Ixodida",
    family = "Ixodidae",
    genus = "Dermacentor",
    scientificName = "Dermacentor variabilis"
   ),
   characteristics = CharacteristicsModel(
    prey = "Blood",
    nameOfYoung = "Larvae",
    groupBehavior = "Solitary",
    biggestThreat = "Ingestion by mites or nematodes",
    mostDistinctiveFeature = "Brown bodies with variable tan markings",
    otherNames = "Wood tick, dog tick",
    gestationPeriod = "1-30 days",
    litterSize = "3,000-5,000 eggs",
    diet = "Omnivore",
    type = "Arachnid",
    commonName = "Dog tick",
    numberOfSpecies = "1",
    location = "North America",
    group = "Ticks",
    color = "Brown",
    skinType = "Exoskeleton",
    lifespan = "1-5 years",
    length = "3-15 mm",
    ageOfSexualMaturity = "50-600 days"
   ),
   locations = listOf("North-America")
  )
 )
}



