package com.example.vkmusicapp

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKAudioRequest : VKRequest<List<Audio>>("audio.get") {
    init {
        // Параметры запроса можно добавить здесь, если необходимо
    }

    override fun parse(r: JSONObject): List<Audio> {
        val audios = r.getJSONObject("response").getJSONArray("items")
        val result = mutableListOf<Audio>()
        for (i in 0 until audios.length()) {
            result.add(Audio.parse(audios.getJSONObject(i)))
        }
        return result
    }
}

data class Audio(
    val id: Long,
    val ownerId: Long,
    val artist: String,
    val title: String,
    val url: String
) {
    companion object {
        fun parse(json: JSONObject): Audio {
            return Audio(
                id = json.getLong("id"),
                ownerId = json.getLong("owner_id"),
                artist = json.getString("artist"),
                title = json.getString("title"),
                url = json.getString("url")
            )
        }
    }
}

