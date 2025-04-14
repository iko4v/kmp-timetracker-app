package org.company.app.di

import org.company.app.presentation.screens.login.LoginViewModel
import org.company.app.presentation.screens.registration.RegistrationViewModel
import org.koin.dsl.module

private val domainModule = module {
    // TODO: Здесь будут зависимости, связанные с бизнес-логикой.
}

private val presentationModule = module {
    single {
        RegistrationViewModel()
    }
    single {
        LoginViewModel()
    }
}

private fun getAllModules() = listOf(
    domainModule, presentationModule
)

/**
 * До этой функции может достучаться из любого места.
 */
fun getSharedModules() = getAllModules()