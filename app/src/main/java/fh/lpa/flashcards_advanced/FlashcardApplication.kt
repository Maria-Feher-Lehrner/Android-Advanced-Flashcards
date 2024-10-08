package fh.lpa.flashcards_advanced

import android.app.Application
import android.util.Log
import fh.lpa.flashcards_advanced.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FlashcardApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@FlashcardApplication)
            modules(appModule)
        }

        Log.i("TOP LEVEL","FlashcardApplication was created")
    }
}