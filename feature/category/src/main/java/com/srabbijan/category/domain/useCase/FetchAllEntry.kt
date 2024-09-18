package com.srabbijan.category.domain.useCase


import com.srabbijan.category.domain.CategoryRepository
import com.srabbijan.category.domain.model.CategoryRequest
import com.srabbijan.common.utils.DataResource
import com.srabbijan.common.utils.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchAllEntry(
    private val repository: CategoryRepository
) {
    operator fun invoke() = flow<DataResource<*>> {
        emit(DataResource.loading(null))
        val response = repository.fetchAll()
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