package fh.lpa.flashcards_advanced.di

import androidx.room.Room
import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import fh.lpa.flashcards_advanced.vocabDetail.DetailViewModel
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import fh.lpa.flashcards_advanced.vocabList.ListViewModel
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import fh.lpa.flashcards_advanced.start.StartViewModel

val appModule = module {

    viewModelOf(::DetailViewModel)
    viewModelOf(::ListViewModel)
    viewModel { StartViewModel(get()) }


    singleOf(::VocabularyRepository)

    //TODO: evtl. implementieren HttpClient & ApiAccess

    single<WordpairsAppDatabase> {
        val database = Room.databaseBuilder(
            get(),
            WordpairsAppDatabase::class.java,
            "FlashcardDB"
        ).fallbackToDestructiveMigration()
            .build()
        database
    }
}