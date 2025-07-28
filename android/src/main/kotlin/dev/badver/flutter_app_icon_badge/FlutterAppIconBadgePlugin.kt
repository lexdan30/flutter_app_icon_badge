package dev.badver.flutter_app_icon_badge

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import me.leolin.shortcutbadger.ShortcutBadger

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
      "updateBadge", "setBadgeCount" -> {
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
    return try {
      ShortcutBadger.applyCount(context, count)
      true
    } catch (e: Exception) {
      e.printStackTrace()
      false
    }
  }

  private fun removeBadge(): Boolean {
    return try {
      ShortcutBadger.removeCount(context)
      true
    } catch (e: Exception) {
      e.printStackTrace()
      false
    }
  }
}
