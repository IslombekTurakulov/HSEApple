package com.iuturakulov.hseapple.view.views

data class TextMessageView(
    override val id: String,
    override val text: String,
    override val from: String,
    override val sended: String,
    override val fileUrl: String = ""
) : MessageView {
    override fun getTypeView(): Int {
        return MessageView.MESSAGE_TEXT
    }

    override fun equals(other: Any?): Boolean {
        return (other as MessageView).id == id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + sended.hashCode()
        result = 31 * result + fileUrl.hashCode()
        return result
    }
}