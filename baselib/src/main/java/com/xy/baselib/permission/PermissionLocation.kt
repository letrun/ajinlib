package com.xy.baselib.permission

import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.PermissionListener

class PermissionLocation : PermissionBase {
    constructor(view: Any?,listener: PermissionListener)
            : super(view,listener,Permission.LOCATION,1000)
}