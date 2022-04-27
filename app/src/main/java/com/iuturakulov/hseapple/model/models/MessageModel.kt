package com.iuturakulov.hseapple.model.models

data class MessageModel(
    val id: String = "",
    var type: String = "",
    var text: String = "",
    var from: String = "",
    var sended: Any = "",
    var fileUrl: String = "empty"
) {
    override fun equals(other: Any?): Boolean {
        return (other as MessageModel).id == id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + sended.hashCode()
        result = 31 * result + fileUrl.hashCode()
        return result
    }
}