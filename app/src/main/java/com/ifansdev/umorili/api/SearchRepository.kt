package com.ifansdev.umorili.api

import com.ifansdev.umorili.api.dataclass.Quote
import com.ifansdev.umorili.api.dataclass.SourceOfQuotes

class SearchRepository(private val apiService: BashApiService) {

    fun searchQuotes(site: String, name: String): io.reactivex.Observable<List<Quote>> {
        return apiService.searchQuotes(
                site,
                name,
                50
        )
    }

    fun searchSourcesOfQuotes(): io.reactivex.Observable<List<List<SourceOfQuotes>>> {
        return apiService.searchSources()
    }
}