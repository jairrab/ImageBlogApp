package com.jairrab.myapp.di

import android.app.Application
import com.jairrab.domain.repository.DomainRepository
import com.jairrab.myapp.di.module.AppModule
import com.jairrab.myapp.di.modules.TestCacheModule
import com.jairrab.myapp.di.modules.TestDataModule
import com.jairrab.myapp.di.modules.TestRemoteModule
import com.jairrab.myapp.di.module.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        TestDataModule::class,
        TestCacheModule::class,
        TestRemoteModule::class,
        ViewModelFactoryModule::class
    ]
)
interface TestAppComponent {

    fun domainRepository(): DomainRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }
}