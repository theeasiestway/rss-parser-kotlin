package com.theeasiestway.entities

class RssItem : RssEntity() {

    var enclosure: Enclosure? = null
        private set

    fun setEnclosure(enclosure: Enclosure?) : RssItem {
        this.enclosure = enclosure
        return this
    }

    override fun setTitle(title: String?): RssItem {
        return super.setTitle(title) as RssItem
    }

    override fun setLink(link: String?): RssItem {
        return super.setLink(link) as RssItem
    }

    override fun setDescription(description: String?): RssItem {
        return super.setDescription(description) as RssItem
    }

    override fun setPubDate(pubDate: String?): RssItem {
        return super.setPubDate(pubDate) as RssItem
    }

    class Enclosure() {

        var url: String? = null
            private set
        var length: Long = -1
            private set
        var mimeType: String? = null
            private set

        constructor(url: String?, length: Long, mimeType: String?) : this() {
            this.url = url
            this.length = length
            this.mimeType = mimeType
        }

        fun setUrl(url: String?) : Enclosure {
            this.url = url
            return this
        }

        fun setLength(length: Long) : Enclosure {
            this.length = length
            return this
        }

        fun setMimeType(mimeType: String?) : Enclosure {
            this.mimeType = mimeType
            return this
        }

        override fun toString(): String {
            return String.format(
                        "url: %s\n" +
                        "length: %s\n" +
                        "mimeType: %s",
                url,
                length,
                mimeType
            )
        }
    }

    override fun toString(): String {
        return "RssItem:\n" +
                super.toString() +
                String.format("enclosure: %s\n", (if (enclosure == null) null else enclosure.toString()))
    }
}