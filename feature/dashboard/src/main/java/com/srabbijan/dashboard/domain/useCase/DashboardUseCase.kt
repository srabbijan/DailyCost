package com.srabbijan.dashboard.domain.useCase

data class DashboardUseCase(
    val fetchAll: FetchAllEntry,
    val fetchSummary : FetchSummaryEntry
)