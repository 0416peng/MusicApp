package com.example.data.model.search

data class SearchSuggestData(
    val code: Int,
    val result: SuggestResult
)

data class SuggestResult(
    val allMatch: List<AllMatch>
)

data class AllMatch(
    val alg: String,
    val feature: String,
    val keyword: String,
    val lastKeyword: String,
    val type: Int
)