package ciai.holidayacc.presentation.dto

data class PageableListDTO<T>(
    val list: List<T>,
    val numPages: Int
)
