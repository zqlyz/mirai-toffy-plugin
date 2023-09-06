package top.crazytoffy.miari

import kotlinx.coroutines.delay
import net.mamoe.mirai.Bot
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets

object GamekagsChecker {
    private suspend fun sendQQ(text: String){
        Bot.getInstance(GamekagsConfig.botQQ)
            .getFriend(GamekagsConfig.masterQQ)
            ?.sendMessage(text)
    }
    private suspend fun checkIn(name: String, cookie: String) {
        try {
            val conn = URL(GamekagsConfig.url).openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01")
//                conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br")
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
            conn.setRequestProperty("Connection", "keep-alive")
            conn.setRequestProperty("Cookie", cookie)
            conn.setRequestProperty("Host", "gamekegs.com")
            conn.setRequestProperty("Origin", "https://gamekegs.com")
            conn.setRequestProperty("Referer", "https://gamekegs.com/user")
            conn.setRequestProperty("Sec-Fetch-Dest", "empty")
            conn.setRequestProperty("Sec-Fetch-Mode", "cors")
            conn.setRequestProperty("Sec-Fetch-Site", "same-origin")
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:95.0) Gecko/20100101 Firefox/95.0")
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest")
            // Post
            conn.doOutput = true
            conn.outputStream.write("action=user.checkin".toByteArray())

            val inputStream = conn.inputStream
            val result = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } != -1) {
                result.write(buffer, 0, length)
            }
            if (conn.responseCode == 200) {
                Plugin.logger.info(result.toString(StandardCharsets.UTF_8))
                val regex = """"error":(.*?),"msg":"(.*?)"""".toRegex()
                val errorCode: String? = regex.find(result.toString(StandardCharsets.UTF_8))?.groupValues?.get(1)
                val msg: String? = regex.find(result.toString(StandardCharsets.UTF_8))?.groupValues?.get(2)
                when(errorCode) {
                    "0" -> {}
                    "1" -> {
                        sendQQ("Gamekags $name 签到失败:今天已经签到完毕")
                    }
                    else -> {
                        sendQQ("Gamekags $name 签到失败，未知原因 Code:$errorCode msg:$msg")
                    }
                }
            } else {
                Plugin.logger.info("Gamekags $name 没有成功进入网站，返回非200")
                sendQQ("Gamekags $name 没有成功进入网站，返回非200")
            }
        } catch (e: MalformedURLException) {
            Plugin.logger.error("Gamekags $name 配置的url有问题" + e.message)
            sendQQ("Gamekags $name 配置的url有问题")
        } catch (e: IOException) {
            Plugin.logger.error("Gamekags $name 发生I/O错误" + e.message)
            sendQQ("Gamekags $name 发生I/O错误")
        }
    }
    suspend fun check(){
        delay(8 * 1000L)
        while (true) {
            checkIn("crazytoffy", "wordpress_logged_in_540b8bd556d799a8246d1d25fb7f2504=crazytoffy%7C1641807510%7CMgsCtzercaGMYIg1xCvr5KNv2Xvarqpa8Ur6oosuX2G%7C28096656105c36849c07361acfcb014ea474fc1e40a410aa088a837e8006ffa9")
            checkIn("zqlyz1", "wordpress_logged_in_540b8bd556d799a8246d1d25fb7f2504=zqlyz1%7C1641824512%7CjnLRTwVsWmXd2APAJtZOWTJLyd5uDgoM1AZgBDwRB0g%7C7ffa3b7872f598f21f7a5faa1a3c9480f417422050d6478f30c4949e0407d7d2")
            checkIn("zqlyz", "wordpress_logged_in_540b8bd556d799a8246d1d25fb7f2504=zqyingzhao%7C1641825282%7CbYHSTLlfyfkVJv67fwudWZpYfYz5C7BcnZlWlqtCoex%7C63a1766ad6372b7cb8cf1e630dd941404c59424bcb1e420625b1b8925ef367c7")
            // 24 hour
            delay((24 * 1000 * 60 * 60).toLong())
        }
    }
}