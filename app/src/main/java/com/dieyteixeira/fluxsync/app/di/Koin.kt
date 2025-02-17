package com.dieyteixeira.fluxsync.app.di

import com.dieyteixeira.fluxsync.app.repository.AuthRepository
import com.dieyteixeira.fluxsync.ui.login.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthRepository(FirebaseAuth.getInstance()) }

    viewModel { LoginViewModel(get()) } // firebase auth
}