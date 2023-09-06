package top.crazytoffy.miari

import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets

object KeylolChecker {
    private var titleText: String? = null

   private suspend fun sendQQ(text: String){
       Bot.getInstance(KeylolConfig.botQQ)
           .getGroup(KeylolConfig.qqGroupID)
           ?.sendMessage(text)
   }

    suspend fun check() {
        delay(10 * 1000L)
        while (true) {
            try {
                val conn = URL(KeylolConfig.url).openConnection()
                val inputStream = conn.getInputStream()
                val result = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } != -1) {
                    result.write(buffer, 0, length)
                }
                val regex = KeylolConfig.regex.toRegex()
                val title: String? = regex.find(result.toString(StandardCharsets.UTF_8))?.groupValues?.get(1)
                if (title == null) {
                    Plugin.logger.error("读取不了keylol帖子题目，或许正则表达式不适用，或许帖子地址有错误")
                    sendQQ("读取不了keylol帖子题目，或许正则表达式不适用，或许帖子地址有错误")
                }
                Plugin.logger.info("KeylolChecker取得题目:$title")
                if (title != titleText) {
                    titleText = title
                    sendQQ("$titleText${KeylolConfig.url}")
                }
            } catch (e: MalformedURLException) {
                Plugin.logger.error("keylol配置的url有问题" + e.message)
                sendQQ("keylol配置的url有问题")
            } catch (e: IOException) {
                Plugin.logger.error("发生I/O错误" + e.message)
                sendQQ("keylol发生I/O错误")
            }
            delay((KeylolConfig.checkTime * 1000 * 60 * 60).toLong())
        }
    }
}