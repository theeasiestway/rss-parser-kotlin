package com.theeasiestway.entities

open class RssEntity {

    var title: String? = null
        private set

    var link: String? = null
        private set

    var description: String? = null
        private set

    var pubDate: String? = null
        private set

    open fun setTitle(title: String?) : RssEntity {
        this.title = title
        return this
    }

    open fun setLink(link: String?) : RssEntity {
        this.link = link
        return this
    }

    open fun setDescription(description: String?) : RssEntity {
        this.description = description
        return this
    }

    open fun setPubDate(pubDate: String?) : RssEntity {
        this.pubDate = pubDate
        return this
    }

    override fun toString(): String {
        return String.format(
                   "title: %s\n" +
                    "link: %s\n" +
                    "description: %s\n" +
                    "pubDate: %s\n",
            title,
            link,
            description,
            pubDate)
    }
}