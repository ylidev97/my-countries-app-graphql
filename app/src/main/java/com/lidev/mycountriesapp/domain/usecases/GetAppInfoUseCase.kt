package com.lidev.mycountriesapp.domain.usecases

interface GetAppInfoUseCase {
    suspend operator fun invoke(): String
}
