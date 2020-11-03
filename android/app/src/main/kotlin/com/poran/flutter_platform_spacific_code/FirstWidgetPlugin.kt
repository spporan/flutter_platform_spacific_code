package com.poran.flutter_platform_spacific_code

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.PluginRegistry

object FirstWidgetPlugin {
    fun registerWith(flutterEngine: FlutterEngine){
        flutterEngine
                .platformViewsController
                .registry
                .registerViewFactory("platform_view/first_widgets",FirstWidgetFactory(flutterEngine.dartExecutor))

    }
}