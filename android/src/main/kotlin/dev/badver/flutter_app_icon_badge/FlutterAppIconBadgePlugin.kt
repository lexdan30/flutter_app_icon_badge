package dev.badver.flutter_app_icon_badge

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterAppIconBadgePlugin */
class FlutterAppIconBadgePlugin : FlutterPlugin, MethodCallHandler {
  private lateinit var channel: MethodChannel
  private lateinit var context: Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_app_icon_badge")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
      "setBadgeCount" -> {
        val count = call.argument<Int>("count") ?: 0
        val success = setBadgeCount(count)
        if (success) {
          result.success(null)
        } else {
          result.error("UNAVAILABLE", "Badge count not set", null)
        }
      }
      "removeBadge" -> {
        val success = removeBadge()
        if (success) {
          result.success(null)
        } else {
          result.error("UNAVAILABLE", "Badge not removed", null)
        }
      }
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun setBadgeCount(count: Int): Boolean {
    // TODO: Implement badge setting logic here, e.g., using ShortcutBadger lib
    // ShortcutBadger.applyCount(context, count)
    return true
  }

  private fun removeBadge(): Boolean {
    // TODO: Implement badge removal logic here
    // ShortcutBadger.removeCount(context)
    return true
  }
}
