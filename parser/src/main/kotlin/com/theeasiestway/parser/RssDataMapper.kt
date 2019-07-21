package com.theeasiestway.parser

import com.theeasiestway.entities.RssChannel
import com.theeasiestway.entities.RssItem
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.BufferedInputStream
import java.lang.NumberFormatException
import javax.xml.parsers.DocumentBuilderFactory

object RssDataMapper {

    private val CHANNEL = "channel"
    private val ITEM = "item"

    private val TITLE = "title"
    private val LINK = "link"
    private val DESCRIPTION = "description"
    private val PUB_DATE = "pubDate"

    private val LANGUAGE = "language"
    private val CATEGORY = "category"
    private val IMAGE = "image"
    private val LAST_BUILD = "lastBuildDate"
    private val TTL = "ttl"

    private val ENCLOSURE = "enclosure"
    private val URL = "url"
    private val LENGTH = "length"
    private val TYPE = "type"

    private var replacer: Replacer? = null

    fun map(inputStream: BufferedInputStream) : RssChannel? {
        return map(inputStream, null, {})
    }

    fun map(inputStream: BufferedInputStream, replacer: Replacer) : RssChannel? {
        return map(inputStream, replacer, {})
    }

    fun map(inputStream: BufferedInputStream, onFinish: () -> Unit) : RssChannel? {
        return map(inputStream, null, onFinish)
    }

    fun map(inputStream: BufferedInputStream, replacer: Replacer?, onFinish: () -> Unit) : RssChannel? {
        this.replacer = replacer
        try {
            var builderFactory = DocumentBuilderFactory.newInstance()
            var documentBuilder = builderFactory.newDocumentBuilder()
            var document = documentBuilder.parse(inputStream)
            var element = document.documentElement              // getting "rss" tag
            element.normalize()

            var chNodeList = element.getElementsByTagName(CHANNEL)  // getting nodes of "channel" tag
            if (chNodeList.length == 0) return null

            var channel = chNodeList.item(0) as Element
            var chTitle = getTitle(channel)
            var chLink = getLink(channel)
            var chDescription = getDescription(channel)
            var chPubDate = getPubDate(channel)
            var chLanguage = getChannelLanguage(channel)
            var chCategory = getChannelCategory(channel)
            var chImageLink = getChannelImageLink(channel)
            var chLastBuild = getChannelLastBuildDate(channel)
            var chTTL = getChannelTTL(channel)

            var rssChannel = RssChannel()
                .setTitle(chTitle)
                .setLink(chLink)
                .setDescription(chDescription)
                .setPubDate(chPubDate)
                .setLanguage(chLanguage)
                .setCategory(chCategory)
                .setImageLink(chImageLink)
                .setLastBuildDate(chLastBuild)
                .setTtl(chTTL)

            var itemsNodeList = element.getElementsByTagName(ITEM)
            var itemsCount = itemsNodeList.length
            if (itemsCount == 0) return rssChannel

            for (i in 0 until itemsCount) {
                var item: Element = itemsNodeList.item(i) as Element
                var itemTitle = getTitle(item)
                var itemLink = getLink(item)
                var itemDescription = getDescription(item)
                var itemPubDate = getPubDate(item)
                var itemEnclosure = getItemEnclosure(item)

                var rssItem = RssItem()
                    .setTitle(itemTitle)
                    .setLink(itemLink)
                    .setDescription(itemDescription)
                    .setPubDate(itemPubDate)
                    .setEnclosure(itemEnclosure)

                rssChannel.addItem(rssItem)
            }
            return rssChannel

        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        } finally {
            try { inputStream.close() } catch (e: Exception) { e.printStackTrace() }
            this.replacer = null
            onFinish.invoke()
        }
    }

    class Replacer(_regexp: String, _replacement: String) {
        val regexp: String = _regexp
        val replacement: String = _replacement
    }

    private fun getTitle(channel: Element) : String? {
        return getNodeValue(getElement(channel, TITLE))
    }

    private fun getLink(channel: Element) : String? {
        return getNodeValue(getElement(channel, LINK))
    }

    private fun getDescription(channel: Element) : String? {
        return getNodeValue(getElement(channel, DESCRIPTION))
    }

    private fun getPubDate(channel: Element) : String? {
        return getNodeValue(getElement(channel, PUB_DATE))
    }

    private fun getChannelLanguage(channel: Element) : String? {
        return getNodeValue(getElement(channel, LANGUAGE))
    }

    private fun getChannelCategory(channel: Element) : String? {
        return getNodeValue(getElement(channel, CATEGORY))
    }

    private fun getChannelImageLink(channel: Element) : String? {
        return getNodeValue(getElement(channel, IMAGE))
    }

    private fun getChannelLastBuildDate(channel: Element) : String? {
        return getNodeValue(getElement(channel, LAST_BUILD))
    }

    private fun getChannelTTL(channel: Element) : Int {
        var ttlString: String? = getNodeValue(getElement(channel, TTL))
        var ttl = -1
        if (ttlString != null) {
            try {
                ttl = Integer.valueOf(ttlString)
            } catch (e: NumberFormatException) { e.printStackTrace(); }
        }
        return ttl
    }

    private fun getItemEnclosure(item: Element) : RssItem.Enclosure? {
        var enclosure = getElement(item, ENCLOSURE) ?: return null
        var nodeMap = enclosure.attributes
        var enclosureClass: RssItem.Enclosure? = null
        if (nodeMap.length > 2) {
            var url = nodeMap.getNamedItem(URL).nodeValue

            var length: Long = -1
            try {
                length = nodeMap.getNamedItem(LENGTH).nodeValue.toLong()
            } catch (e: NumberFormatException) { e.printStackTrace(); }

            var mimeType = nodeMap.getNamedItem(TYPE).nodeValue
            enclosureClass = RssItem.Enclosure(url, length, mimeType)
        }
        return enclosureClass
    }

    private fun getNodeValue(element: Element?) : String? {
        if (element == null) return null
        var node: Node? = element.firstChild ?: return null
        var nodeValue = node!!.nodeValue
        if (nodeValue != null && nodeValue.trim().isEmpty()) nodeValue = null
        if (replacer != null && nodeValue != null && nodeValue.trim().isNotEmpty()) {
            nodeValue = nodeValue.trim().replace(replacer!!.regexp, replacer!!.replacement)
        }
        return nodeValue
    }

    private fun getElement(element: Element, name: String) : Element? {
        var nodeList = element.getElementsByTagName(name)
        if (nodeList.length == 0) return null
        return nodeList.item(0) as Element?
    }
}