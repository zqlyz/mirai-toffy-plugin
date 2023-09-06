package top.crazytoffy.miari

import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.ceil

object ImgMarker {
    fun mark(text: String): BufferedImage? {
        val srcImg = if(AntikusoConfig.srcImgPath == "none_path") {
            try {
                ImageIO.read(javaClass.classLoader.getResource("fuck_you.jpg"))
            } catch (e: Exception) {
                Plugin.logger.error("Antikuso读不了图片" + e.message)
                return null
            }
        } else {
            try {
                ImageIO.read(File(AntikusoConfig.srcImgPath))
            } catch (e: Exception) {
                Plugin.logger.error("Antikuso读不了图片" + e.message)
                return null
            }
        }
        val srcImgWidth = srcImg.getWidth(null)
        val srcImgHeight = srcImg.getHeight(null)

        val bufImg = BufferedImage(srcImgWidth, srcImgHeight + AntikusoConfig.textBoxHeight, BufferedImage.TYPE_INT_RGB)
        val g = bufImg.createGraphics()
        g.color = Color.WHITE
        g.fillRect(0, 0, srcImgWidth, srcImgHeight + AntikusoConfig.textBoxHeight)
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null)
        val fontSize = when(text.length) {
            in 0 until 10 -> AntikusoConfig.bigFontSize
            in 10 until 30 -> AntikusoConfig.midFontSize
            else -> AntikusoConfig.smallFontSize
        }
        val font = Font(AntikusoConfig.fontFamily, Font.BOLD, fontSize)
        g.color = Color.BLACK
        g.font = font
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        val lines = ceil(g.fontMetrics.stringWidth(text)/srcImgWidth.toDouble()).toInt()
        val subLength = text.length / lines
        val strs = mutableListOf<String>()
        for (i in 0 until lines) {
            if (i == lines - 1) {
                strs.add(text.substring(i*subLength))
            } else {
                strs.add(text.substring(i*subLength, (i+1)*subLength))
            }
        }
        for (i in 0 until strs.size) {
            val x = (srcImgWidth - g.fontMetrics.stringWidth(strs[i])) / 2
            val y = srcImgHeight + (AntikusoConfig.textBoxHeight - g.fontMetrics.height*(strs.size-1)) / 2 + g.fontMetrics.height*(i)
            g.drawString(strs[i], x, y)
        }
        g.dispose()
        return bufImg
    }
}