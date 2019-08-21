package com.ifansdev.umorili.api

object SearchRepositoryProvider {

    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(BashApiService.create())
    }
}