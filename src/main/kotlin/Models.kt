package ru.androidsprint.englishTrainer

import kotlinx.serialization.Serializable

@Serializable
data class GetMeResponse(
    val ok: Boolean,
    val result: BotInfo
)

@Serializable
data class BotInfo(
    val id: Long,
    val is_bot: Boolean,
    val first_name: String,
    val username: String
)

@Serializable
data class Update(
    val update_id: Long,
    val message: Message?
)

@Serializable
data class Message(
    val message_id: Long,
    val from: User,
    val chat: Chat,
    val text: String
)

@Serializable
data class User(
    val id: Long,
    val is_bot: Boolean,
    val first_name: String,
    val username: String
)

@Serializable
data class Chat(
    val id: Long,
    val first_name: String,
    val username: String
)

@Serializable
data class GetUpdatesResponse(
    val ok: Boolean,
    val result: List<Update>
)