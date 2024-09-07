package com.srabbijan.dashboard.domain.useCase


import com.srabbijan.common.utils.DataResource
import com.srabbijan.common.utils.ErrorType
import com.srabbijan.dashboard.domain.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchAllEntry(
    private val repository: DashboardRepository
) {
    operator fun invoke(startDate:String,endDate:String) = flow<DataResource<*>> {
        emit(DataResource.loading(null))
        val response = repository.fetchAll(startDate, endDate )
        if (response.isSuccess) {
            emit(DataResource.success(data = response.getOrThrow()))
        } else {
            emit(
                DataResource.error(
                    errorType = ErrorType.IO,
                    code = 400,
                    message = response.exceptionOrNull()?.localizedMessage,
                    data = null
                )
            )
        }
    }.catch {
        emit(
            DataResource.error(
                errorType = ErrorType.IO,
                code = 400,
                message = it.message,
                data = null
            )
        )
    }.flowOn(Dispatchers.IO)

}