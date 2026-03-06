package com.lidev.mycountriesapp.ui.usecase

import android.content.Context
import com.lidev.mycountriesapp.domain.usecases.GetAppInfoUseCase

class GetAppInfoUseCaseImpl(
    private val context: Context,
) : GetAppInfoUseCase {
    override suspend fun invoke(): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName ?: "1.0"
    }
}
