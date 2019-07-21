package com.theeasiestway.parser

import com.theeasiestway.entities.RssChannel
import com.theeasiestway.net.NetworkService

class RssParser {

    private var networkService: NetworkService = NetworkService()

    fun parse(url: String) : RssChannel? {
        return RssDataMapper.map(networkService.request(url), {networkService.disconnect()})
    }

    fun parse(url: String, regexp: String, replacement: String) : RssChannel? {
        return RssDataMapper.map(networkService.request(url), RssDataMapper.Replacer(regexp, replacement), {networkService.disconnect()})
    }

    fun setConnTimeout(timeout: Int) : RssParser {
        networkService.setConnTimeout(timeout)
        return this
    }

    fun setReadTimeout(timeout: Int) : RssParser {
        networkService.setReadTimeout(timeout)
        return this
    }
}