package fh.lpa.flashcards_advanced.di

import androidx.room.Room
import fh.lpa.flashcards_advanced.entity.WordpairsAppDatabase
import fh.lpa.flashcards_advanced.repository.VocabularyRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    //TODO: implement viewModelOf

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