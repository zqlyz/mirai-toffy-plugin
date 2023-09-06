package top.crazytoffy.miari

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object AntikusoConfig: AutoSavePluginConfig("AntikusoConfig") {
    @ValueDescription("注册到的群号")
    val qqGroup: Long by value(0L)

    @ValueDescription("正则表达式")
    val reg: String by value(""".*呀屎啦.*""")

    @ValueDescription("文字前缀")
    val prefix: String by value("呀屎啦！")

    @ValueDescription("图片路径地址")
    val srcImgPath: String by value("none_path")

    @ValueDescription("文字框高度")
    val textBoxHeight: Int by value(100)

    @ValueDescription("字体名字")
    val fontFamily: String by value("黑体")

    @ValueDescription("大字号")
    val bigFontSize: Int by value(36)

    @ValueDescription("中字号")
    val midFontSize: Int by value(28)

    @ValueDescription("小字号")
    val smallFontSize: Int by value(20)
}