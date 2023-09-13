package top.crazytoffy.miari

import kotlinx.coroutines.*
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.info
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO

object Plugin : KotlinPlugin(
    JvmPluginDescription(
        id = "top.crazytoffy.plugin",
        name = "toffy's-plugin",
        version = "0.1",
    ) {
        author("toffy")
        info("""toffy自用的一些插件\n1.antikuso\n2.keylol-cheker\n3.gamekags签到""")
    }
) {
    private var keylol: Job? = null
    fun checkConfig(): Boolean {
        var flag = true
/*
        if (AntikusoConfig.qqGroup == 0L){
            logger.error("AntikusoConfig请设置qq群号")
            flag = false
        }
*/
        if (KeylolConfig.botQQ == 0L){
            logger.error("KeylolConfig请设置botQQ号")
            flag = false
        }
        if (KeylolConfig.qqGroupID == 0L){
            logger.error("KeylolConfig请设置botQQ群号")
            flag = false
        }
/*
        if (GamekagsConfig.botQQ == 0L){
            logger.error("GamekagsConfig请设置botQQ")
            flag = false
        }
        if (GamekagsConfig.masterQQ == 0L){
            logger.error("GamekagsConfig请设置接收qq")
            flag = false
        }
 */
        return flag
    }

    override fun onEnable() {
//        AntikusoConfig.reload()
        KeylolConfig.reload()
//        GamekagsConfig.reload()

        if (!checkConfig()) return

        keylol = launch {
            KeylolChecker.check()
        }
        logger.info { "Plugin loaded" }
    }

    override fun onDisable() {
        super.onDisable()
        keylol?.cancel()
        logger.info { "Plugin unloaded" }
    }
}