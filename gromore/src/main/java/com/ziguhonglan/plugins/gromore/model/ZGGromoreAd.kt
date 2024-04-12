package com.ziguhonglan.plugins.gromore.model

sealed class ZGGromoreAd(
    val slot: String?,
    val adn: String?,
    val price: String?
) {
    /**
     * 开屏广告
     * @constructor
     */
    class Splash(
        slot: String?,
        adn: String?,
        price: String?
    ): ZGGromoreAd(
        slot = slot, adn = adn, price = price
    )

    /**
     * 激励视频广告
     * @constructor
     */
    class Reward(
        slot: String?,
        adn: String?,
        price: String?
    ): ZGGromoreAd(
        slot = slot, adn = adn, price = price
    )
}
