package top.crazytoffy.miari

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object KeylolConfig: AutoSavePluginConfig("KeylolConfig") {
    @ValueDescription("bot的q号")
    val botQQ: Long by value(0L)

    @ValueDescription("订阅qq群")
    val qqGroupID: Long by value(0L)

    @ValueDescription("keylol帖子地址")
    val url: String by value("https://keylol.com/forum.php?mod=viewthread&tid=572814&page=1&authorid=1307992")

    @ValueDescription("取得题目的正则")
    val regex: String by value("""<a id="thread_subject".*?>(.*?)</a>""")

    @ValueDescription("爬取间隔（小时）")
    val checkTime: Int by value(12)
}