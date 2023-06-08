package com.moresoft.domain

data class Id(
    val timestamp: Long,
    val machine: Int,
    val pid: Int,
    val increment: Int,
    val creationTime: String
)