package com.taskforce141.comelapp.models

data class PostData(
    var postId: String = "",
    var userId: String = "",
    var username: String = "",
    var name: String = "",
    var love: Boolean = false,
    var postText: String = "",
)
