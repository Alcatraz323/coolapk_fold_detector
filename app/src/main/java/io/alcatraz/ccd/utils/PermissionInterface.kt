package io.alcatraz.ccd.utils

interface PermissionInterface {
    fun onResult(requestCode: Int, permissions: Array<String>, granted: IntArray)
}
