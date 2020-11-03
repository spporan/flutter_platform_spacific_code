package com.poran.flutter_platform_spacific_code

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        FirstWidgetPlugin.registerWith( flutterEngine)

    }
}