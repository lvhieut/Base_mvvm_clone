package com.hieunv.mvvmarch.base.entity

data class PaginatedEntities<out T>(
        /** List of entities included in the current page */
        val entities: List<T>,
        /** The ID of the entity at the end of the current page. Null if there is no next */
        val afterId: Long?,

        /** The ID request the current page. (-1) with the first time */
        var requestId: Long = REQUEST_ID_FIRST_TIME
) {
    companion object {
        const val REQUEST_ID_FIRST_TIME = -1L
    }
}
