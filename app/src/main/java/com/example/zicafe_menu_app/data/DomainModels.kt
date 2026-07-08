package com.example.zicafe_menu_app.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Domain models for the UI
 */
data class MenuItem(
    val name: String,
    val price: String,
    val detail: String? = null
)

data class MenuCategory(
    val name: String,
    val items: List<MenuItem>
)

data class MenuData(
    val categories: List<MenuCategory>
)

/**
 * DTOs for WebSocket API Communication
 */
@Serializable
data class WsMessage(
    val type: String,
    val payload: JsonElement? = null
)

@Serializable
data class CategoryDto(
    val id: String? = null,
    val name: String,
    val orderIndex: Int = 0,
    val isVisible: Boolean = true,
    val products: List<ProductDto> = emptyList()
)

@Serializable
data class ProductDto(
    val id: String? = null,
    val name: String,
    val price: Double,
    val isVisible: Boolean = true,
    val detail: String? = null
)
