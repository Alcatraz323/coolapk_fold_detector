package io.alcatraz.ccd.coolapk.databeans.feed.detail

class HotReplyRows {
    var id: Long = 0
    var ftype = 0
    var fid: Long = 0
    var rid = 0
    var rrid = 0
    var uid: Long = 0
    var username: String? = null
    var ruid = 0
    var rusername: String? = null
    var pic: String? = null
    var message: String? = null
    var replynum = 0
    var likenum = 0
    var burynum = 0
    var reportnum = 0
    var rank_score = 0
    var dateline: Long = 0
    var lastupdate: Long = 0
    var is_folded = 0
    var status = 0
    var message_status = 0
    var block_status = 0
    var recent_reply_ids: String? = null
    var feedUid: Long = 0
    var fetchType: String? = null
    var entityId: Long = 0
    var avatarFetchType: String? = null
    var userAvatar: String? = null
    var entityTemplate: String? = null
    var entityType: String? = null
    var infoHtml: String? = null
    var isFeedAuthor = 0
    var userAction: UserAction? = null
    var userInfo: UserInfo? = null
    var replyRows: List<ReplyRows>? = null
    var replyRowsCount = 0
    var replyRowsMore = 0
}