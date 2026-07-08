package com.example.zicafe_menu_app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zicafe_menu_app.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.*
import java.util.Locale

class MenuViewModel : ViewModel() {
    private val TAG = "MenuViewModel"
    private val WEBSOCKET_URL = "wss://menu-board-api-murex.vercel.app"

    private val _menuData = MutableStateFlow<MenuData?>(null)
    val menuData: StateFlow<MenuData?> = _menuData

    private val json = Json { ignoreUnknownKeys = true }
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    init {
        connectWebSocket()
    }

    private fun connectWebSocket() {
        val request = Request.Builder()
            .url(WEBSOCKET_URL)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket Connected")
                val getMenuRequest = """{"type": "getMenu"}"""
                webSocket.send(getMenuRequest)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "Message received: $text")
                try {
                    val message = json.decodeFromString<WsMessage>(text)
                    when (message.type) {
                        "menuData", "menuUpdated" -> {
                            val categoryDtos = message.payload?.let {
                                json.decodeFromJsonElement<List<CategoryDto>>(it)
                            }
                            if (categoryDtos != null) {
                                _menuData.value = mapToDomain(categoryDtos)
                            }
                        }
                        "error" -> {
                            Log.e(TAG, "Server Error: ${message.payload}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing WebSocket message", e)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "WebSocket Failure", t)
                retryConnection()
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket Closed: $reason")
                retryConnection()
            }
        })
    }

    private fun retryConnection() {
        viewModelScope.launch {
            delay(5000)
            Log.d(TAG, "Attempting to reconnect...")
            connectWebSocket()
        }
    }

    private fun mapToDomain(dtos: List<CategoryDto>): MenuData {
        val sortedCategories = dtos
            .filter { it.isVisible }
            .sortedBy { it.orderIndex }
            .map { catDto ->
                MenuCategory(
                    name = catDto.name.uppercase(Locale.ROOT),
                    items = catDto.products
                        .filter { it.isVisible }
                        .map { prodDto ->
                            MenuItem(
                                name = prodDto.name,
                                price = formatPrice(prodDto.price),
                                detail = prodDto.detail
                            )
                        }
                )
            }
        return MenuData(sortedCategories)
    }

    private fun formatPrice(price: Double): String {
        return String.format(Locale.US, "%.3f", price)
    }

    override fun onCleared() {
        webSocket?.close(1000, "ViewModel Cleared")
    }
}
