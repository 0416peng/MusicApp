package com.example.data.model.artist

data class ArtistDetail(
    val code: Int,
    val `data`: Data,
    val message: String
)

data class Data(
    val artist: ArtistX,
    val blacklist: Boolean,
    val eventCount: Int,
    val identify: Identify,
    val preferShow: Int,
    val secondaryExpertIdentiy: List<SecondaryExpertIdentiy>,
    val showPriMsg: Boolean,
    val user: User,
    val videoCount: Int,
    val vipRights: VipRights
)

data class ArtistX(
    val albumSize: Int,
    val alias: List<String>,
    val avatar: String,
    val briefDesc: String,
    val cover: String,
    val id: Int,
    val identifyTag: List<String>,
    val identities: List<String>,
    val musicSize: Int,
    val mvSize: Int,
    val name: String,
    val rank: Rank,
    val transNames: List<String>?
)

data class Identify(
    val actionUrl: String,
    val imageDesc: String,
    val imageUrl: String
)

data class SecondaryExpertIdentiy(
    val expertIdentiyCount: Int,
    val expertIdentiyId: Int,
    val expertIdentiyName: String
)

data class User(
    val accountStatus: Int,
    val accountType: Int,
    val anchor: Boolean,
    val authStatus: Int,
    val authenticated: Boolean,
    val authenticationTypes: Int,
    val authority: Int,
    val avatarDetail: AvatarDetail,
    val avatarImgId: Long,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundUrl: String,
    val birthday: Long,
    val city: Int,
    val createTime: Long,
    val defaultAvatar: Boolean,
    val description: String,
    val detailDescription: String,
    val djStatus: Int,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val gender: Int,
    val lastLoginIP: String,
    val lastLoginTime: Long,
    val locationStatus: Int,
    val mutual: Boolean,
    val nickname: String,
    val province: Int,
    val remarkName: Any,
    val shortUserName: String,
    val signature: String,
    val userId: Int,
    val userName: String,
    val userType: Int,
    val vipType: Int
)

data class VipRights(
    val now: Long,
    val oldProtocol: Boolean,
    val redVipAnnualCount: Int,
    val redVipLevel: Int,
    val rightsInfoDetailDtoList: List<RightsInfoDetailDto>
)

data class Rank(
    val rank: Int,
    val type: Int
)

data class AvatarDetail(
    val identityIconUrl: String,
    val identityLevel: Int,
    val userType: Int
)

data class RightsInfoDetailDto(
    val dynamicIconUrl: Any,
    val expireTime: Long,
    val iconUrl: Any,
    val sign: Boolean,
    val signDeduct: Boolean,
    val signIap: Boolean,
    val signIapDeduct: Boolean,
    val vipCode: Int,
    val vipLevel: Int
)