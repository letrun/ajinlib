package com.jianbian.baselib.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object TransformUtils {

    /*
     * 将时间戳转换为时间
     */
    fun stampToDate(date: Date?): String? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat.format(date)
    }

    /*
 * 将时间戳转换为时间
 */
    fun stampToDate(s: Long): String? {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(s)
        res = simpleDateFormat.format(date)
        return res
    }
    //时间格式转换 String pattern
    fun stampToDate(time: String?): String? {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date: Date? = null
        try {
            date = format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val format1 = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        return format1.format(date)
    }

    //给字符串某些关键字上色
    fun changeContentColor(color: Int, text: String, keyword: String): SpannableString {
        val string = text.toLowerCase()
        val key = keyword.toLowerCase()
        val pattern = Pattern.compile(key)
        val matcher = pattern.matcher(string)
        val ss = SpannableString(text)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            ss.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return ss
    }

    //让文字可以点击
    fun setContentClicked(text: String, keyword: String,l: () -> Unit):SpannableString{
        val string = text.toLowerCase()
        val key = keyword.toLowerCase()
        val pattern = Pattern.compile(key)
        val matcher = pattern.matcher(string)
        val ss = SpannableString(text)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            ss.setSpan(object : ClickableSpan() {
                override fun onClick(p0: View) {
                    l.invoke()
                }

            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return ss
    }


    /**
     * @description 方法的作用: 正则的方式隐藏中间4位手机号
     * @date: 2020/1/14 13:21
     * @author: 作者: 605536834@qq.com
     */
    fun replacePhone(phone: String): String? {
        return phone.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1****$2")
    }

    /**
     * @description 方法的作用: 正则的方式隐藏中间10位身份证号
     * @date: 2020/1/14 13:21
     * @author: 作者: 605536834@qq.com
     */
    fun replaceIdCard(idCard: String): String? {
        return idCard.replace("(\\d{4})\\d{10}(\\w{4})".toRegex(), "$1****$2")
    }

    /**
     * @description 方法的作用: 对某个数字保留两位小数
     * @date: 2020/1/17 13:18
     * @author: 作者: 605536834@qq.com
     */
    fun keepTwoDecimals(num: Float): String? {
        return String.format("%.2f", num)
    }
}