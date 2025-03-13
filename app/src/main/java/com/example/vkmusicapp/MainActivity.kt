package com.example.vkmusicapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private var accessToken: String? = null

    // Регистрация обработчика результата авторизации
    private val loginLauncher = registerForActivityResult(
        VK.getVKAuthActivityResultContract()
    ) { result: VKAuthenticationResult ->
        when (result) {
            is VKAuthenticationResult.Success -> {
                // Сохраняем токен
                accessToken = result.token.accessToken
                fetchPhotos()
            }
            is VKAuthenticationResult.Failed -> {
                Toast.makeText(this, "Ошибка авторизации: ${result.exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val vkApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VKApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Обработка кнопки Logout
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            logout()
        }

        if (VK.isLoggedIn()) {
            fetchPhotos()
        } else {
            startAuth()
        }
    }

    private fun startAuth() {
        loginLauncher.launch(arrayListOf(VKScope.PHOTOS))
    }

    private fun fetchPhotos() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                accessToken?.let { token ->
                    val response = vkApiService.getPhotos("232716745", "profile", "5.131", token) //43706933
                    val photoList = response.response?.items ?: emptyList()

                    runOnUiThread {
                        photoAdapter = PhotoAdapter(photoList)
                        recyclerView.adapter = photoAdapter
                    }
                } ?: run {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Токен не найден", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Ошибка получения фотографий: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun logout() {
        VK.logout() // Вызов метода выхода
        accessToken = null // Очистка токена
        Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
        // Дополнительно можно обновить интерфейс, например, вызвать старт авторизации
        startAuth()
    }
}
