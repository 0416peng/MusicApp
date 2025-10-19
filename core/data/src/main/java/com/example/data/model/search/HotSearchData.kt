package com.example.data.model.search

data class HotSearchData(
    val code: Int,
    val result: Result
)

data class Result(
    val hots: List<Hot>
)

data class Hot(
    val first: String,
    val iconType: Int,
    val second: Int,
    val third: Any
)