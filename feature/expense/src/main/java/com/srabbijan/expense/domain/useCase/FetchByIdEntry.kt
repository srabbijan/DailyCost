package com.srabbijan.expense.domain.useCase


import com.srabbijan.common.utils.DataResource
import com.srabbijan.common.utils.ErrorType
import com.srabbijan.expense.domain.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchByIdEntry(
    private val repository: ExpenseRepository
) {
    operator fun invoke(request: Long) = flow<DataResource<*>> {
        emit(DataResource.loading(null))
        val response = repository.fetchById(request)
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