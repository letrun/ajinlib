package com.xy.baselib.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener

object PictureSelectorUtils {
    var engine: ImageEngine?=null
    fun selectImg(any: Any?,listener:OnResultCallbackListener<LocalMedia>){
        selectImg(any, null, listener)
    }

    fun selectImg(any: Any?,data:MutableList<LocalMedia>?,listener:OnResultCallbackListener<LocalMedia>){
        selectImg(any, data, 1, listener)
    }

    fun selectImg(any: Any?,data:MutableList<LocalMedia>?,more:Int,listener:OnResultCallbackListener<LocalMedia>){
        selectImg(any, data,true, more, listener)
    }

    fun selectImg(any: Any?,data:MutableList<LocalMedia>?,camera:Boolean,more:Int,listener:OnResultCallbackListener<LocalMedia>){
        selectImg(any, PictureMimeType.ofImage(),data,camera, more, listener)
    }

    fun selectImg(any: Any?,chooseMode: Int,data:MutableList<LocalMedia>?,camera:Boolean,more:Int,listener:OnResultCallbackListener<LocalMedia>){
        selectImg(any, chooseMode, data,camera, more, false, listener)
    }
    fun selectImg(any: Any?,chooseMode: Int,data:MutableList<LocalMedia>?,camera:Boolean,more:Int,enableCrop:Boolean,listener:OnResultCallbackListener<LocalMedia>){
        selectMedia(any, chooseMode, more,camera, enableCrop, data, listener)
    }


    fun selectImg(any: Any?,code:Int){
        selectImg(any, null, code)
    }

    fun selectImg(any: Any?,data:MutableList<LocalMedia>?,code:Int){
        selectImg(any, data, 1, code)
    }

    fun selectImg(any: Any?,data:MutableList<LocalMedia>?,more:Int,code:Int){
        selectImg(any,data,true,more, code)
    }

    fun selectImg(any: Any?,data:MutableList<LocalMedia>?,camera: Boolean,more:Int,code:Int){
        selectImg(any, PictureMimeType.ofImage(),data,camera, more, code)
    }

    fun selectImg(any: Any?,chooseMode: Int,data:MutableList<LocalMedia>?,camera:Boolean,more:Int,code:Int){
        selectImg(any, chooseMode, data,camera, more, false, code)
    }

    fun selectImg(any: Any?,chooseMode: Int,data:MutableList<LocalMedia>?,camera:Boolean,more:Int,enableCrop:Boolean,code:Int){
        selectMedia(any, chooseMode, more,camera, enableCrop, data, code)
    }

    /**
     * 选择头像
     */
    private fun selectMedia(any:Any?,chooseMode:Int,more: Int,camera: Boolean ,enableCrop:Boolean,data:MutableList<LocalMedia>?,code:Int){
        getPictureSelector(any, chooseMode, more,camera, enableCrop, data)?.forResult(code)
    }


    /**
     * 选择头像
     */
    private fun selectMedia(any:Any?,chooseMode:Int,more: Int,camera: Boolean ,enableCrop:Boolean,data:MutableList<LocalMedia>?,listener: OnResultCallbackListener<LocalMedia>){
        getPictureSelector(any, chooseMode, more,camera, enableCrop, data)?.forResult(listener)
    }



    private fun getPictureSelector(any:Any?,chooseMode:Int,more: Int,camera: Boolean ,enableCrop:Boolean,data:MutableList<LocalMedia>?): PictureSelectionModel?{
        var pictureSelector:PictureSelector?=null
        if (any == null || engine == null)return null
        if (any is Activity)
            pictureSelector = PictureSelector.create(any)
        else if (any is Fragment)
            pictureSelector = PictureSelector.create(any)
        return pictureSelector?.openGallery(chooseMode) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                ?.imageEngine(engine) // 外部传入图片加载引擎，必传项
                ?.selectionData(data)
                ?.isUseCustomCamera(true)
                ?.isMaxSelectEnabledMask(true) // 选择数到了最大阀值列表是否启用蒙层效果
                ?.imageSpanCount(4) // 每行显示个数
                ?.isReturnEmpty(false) // 未选择数据时点击按钮是否可以返回
                ?.closeAndroidQChangeWH(true) //如果图片有旋转角度则对换宽高,默认为true
                ?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // 设置相册Activity方向，不设置默认使用系统
                ?.selectionMode(if (more<=1) PictureConfig.SINGLE else PictureConfig.MULTIPLE) // 多选 or 单选
                ?.isPreviewImage(true) // 是否可预览图片
                ?.isCamera(camera) // 是否显示拍照按钮
                ?.maxSelectNum(if (more<=1) 9 else more)
                ?.maxVideoSelectNum(more)
                ?.isCompress(true)
                ?.isEnableCrop(enableCrop) // 是否裁剪
                ?.withAspectRatio(1, 1) // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                ?.freeStyleCropEnabled(false) // 裁剪框是否可拖拽
                ?.circleDimmedLayer(false) // 是否圆形裁剪
                ?.showCropFrame(true) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                ?.showCropGrid(false) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
    }



    /**
     * 打开相机
     */
    fun openCamera(any: Any?,listener: OnResultCallbackListener<LocalMedia>){
        getCameraSelector(any)?.forResult(listener)
    }

    fun openCamera(any: Any?,code: Int){
        getCameraSelector(any)?.forResult(code)
    }

    private fun getCameraSelector(any: Any?):PictureSelectionModel?{
        var pictureSelector:PictureSelector?=null
        if (any == null)return null
        if (any is Activity)
            pictureSelector = PictureSelector.create(any)
        else if (any is Fragment)
            pictureSelector = PictureSelector.create(any)
        return  pictureSelector?.openCamera(PictureMimeType.ofAll())
                ?.isUseCustomCamera(true)
                ?.isCompress(true)
    }

    fun getPath(resultCode:Int,data: Intent?):MutableList<LocalMedia>{
        if (resultCode != Activity.RESULT_OK || data == null)
            return ArrayList<LocalMedia>()
        return PictureSelector.obtainMultipleResult(data)?:ArrayList<LocalMedia>()
    }

    fun getOnePath(resultCode: Int,data: Intent?):LocalMedia?{
        val datas = getPath(resultCode, data)
        return if (datas.size<=0) null else datas[0]
    }

    fun getPath(data:LocalMedia?):String?{
        return if (!TextUtils.isEmpty(data?.compressPath)){
            data?.compressPath
        }
        else if (!TextUtils.isEmpty(data?.cutPath)){
            data?.cutPath
        }
        else if (!TextUtils.isEmpty(data?.originalPath)){
            data?.originalPath
        }
        else if (!TextUtils.isEmpty(data?.androidQToPath)){
            data?.androidQToPath
        }
        else if (!TextUtils.isEmpty(data?.realPath)){
            data?.realPath
        }
        else if (!TextUtils.isEmpty(data?.path)){
            data?.path
        }else{
            ""
        }
    }

    fun getOnePath(data: MutableList<LocalMedia>?):String?{
        if (data==null)return null
        return getPath(data[0])
    }
}