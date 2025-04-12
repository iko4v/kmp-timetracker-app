package org.company.app.di

import org.company.app.presentation.screens.registration.RegistrationViewModel
import org.koin.dsl.module

private val domainModule = module {

}

private val presentationModule = module {
    single {
        RegistrationViewModel()
    }
}

private fun getAllModules() = listOf(
    domainModule, presentationModule
)

/**
 * До этой функции может достучаться из любого места.
 */
fun getSharedModules() = getAllModules()