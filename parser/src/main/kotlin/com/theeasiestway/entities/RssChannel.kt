package com.theeasiestway.entities

class RssChannel : RssEntity() {

    var language: String? = null
        private set

    var category: String? = null
        private set

    var imageLink: String? = null
        private set

    var lastBuildDate: String? = null
        private set

    var ttl: Int = -1
        private set

    var items: ArrayList<RssItem> = ArrayList()
        private set

    fun setLanguage(language: String?) : RssChannel {
        this.language = language
        return this
    }

    fun setCategory(category: String?) : RssChannel {
        this.category = category
        return this
    }

    fun setImageLink(imageLink: String?) : RssChannel {
        this.imageLink = imageLink
        return this
    }

    fun setLastBuildDate(lastBuildDate: String?) : RssChannel {
        this.lastBuildDate = lastBuildDate
        return this
    }

    fun setTtl(ttl: Int) : RssChannel {
        this.ttl = ttl
        return this
    }

    fun addItem(item: RssItem?) : RssChannel {
        if (item != null) items.add(item)
        return this
    }

    override fun setTitle(title: String?): RssChannel {
        return super.setTitle(title) as RssChannel
    }

    override fun setLink(link: String?): RssChannel {
        return super.setLink(link) as RssChannel
    }

    override fun setDescription(description: String?): RssChannel {
        return super.setDescription(description) as RssChannel
    }

    override fun setPubDate(pubDate: String?): RssChannel {
        return super.setPubDate(pubDate) as RssChannel
    }

    override fun toString(): String {
        return "RssChannel:\n" +
                super.toString() +
                String.format(
                            "language: %s\n" +
                            "category: %s\n" +
                            "imageLink: %s\n" +
                            "lastBuildDate: %s\n" +
                            "ttl: %s\n\n",
                    language,
                    category,
                    imageLink,
                    lastBuildDate,
                    ttl
                ) +
                toStringItems() + "\n"
    }

    fun toStringItems() : String {
        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder.append("RssItems count: ").append(items.size).append("\n\n")
        if (items.size > 0) {
            for (i in items) {
                stringBuilder.append("item[").append(items.indexOf(i)).append("]:\n")
                stringBuilder.append(i.toString()).append("\n")
            }
        }
        return stringBuilder.toString()
    }
}