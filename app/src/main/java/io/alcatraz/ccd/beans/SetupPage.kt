package io.alcatraz.ccd.beans

import android.view.View

class SetupPage(var title: String) {
    var rootView: View? = null
    var rootViewResId: Int? = null
    constructor(title: String, rootView: View) : this(title) {
        this.rootView = rootView
    }
    constructor(title: String, rootViewResId: Int) : this(title) {
        this.rootViewResId = rootViewResId
    }
}