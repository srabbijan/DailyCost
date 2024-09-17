package com.srabbijan.dashboard.domain.useCase

data class HomeUseCase(
    val fetchAll: FetchAllEntry,
    val fetchSummary : FetchSummaryEntry
)