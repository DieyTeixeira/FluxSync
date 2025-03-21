package com.dieyteixeira.fluxsync.app.di.koin

import com.dieyteixeira.fluxsync.app.configs.UserPreferences
import com.dieyteixeira.fluxsync.app.di.repository.AuthRepository
import com.dieyteixeira.fluxsync.app.di.repository.FirestoreRepository
import com.dieyteixeira.fluxsync.ui.home.viewmodel.HomeViewModel
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthRepository(FirebaseAuth.getInstance()) }
    single { FirestoreRepository() }
    single { UserPreferences(get()) }

    viewModel { LoginViewModel(get()) } // firebase auth
    viewModel { HomeViewModel(get(), get()) } // firebase firestore
}