package fh.lpa.flashcards_advanced.di

import androidx.room.Room

import org.koin.dsl.module
import fh.lpa.flashcards_advanced.vocabDetail.DetailViewModel
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import fh.lpa.flashcards_advanced.vocabList.ListViewModel
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import org.koin.android.ext.koin.androidContext

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf


val appModule = module {

    viewModelOf(::DetailViewModel)
    viewModelOf(::ListViewModel)

    singleOf(::VocabularyRepository)

    //TODO: evtl. implementieren HttpClient & ApiAccess

    single<WordpairsAppDatabase> {
        val database = Room.databaseBuilder(
            androidContext(),
            WordpairsAppDatabase::class.java,
            "FlashcardDB"
        ).fallbackToDestructiveMigration()
            .build()
        database
    }
}