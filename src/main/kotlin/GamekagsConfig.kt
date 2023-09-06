package top.crazytoffy.miari

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object GamekagsConfig : AutoSavePluginConfig("GamekagsConfig"){
    @ValueDescription("botQQ号")
    val botQQ: Long by value(0L)

    @ValueDescription("管理q号")
    val masterQQ: Long by value(0L)

    @ValueDescription("url")
    val url: String by value("https://gamekegs.com/wp-content/themes/modown/action/user.php")
}