/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.watchlist.network

import android.content.Context
import com.raywenderlich.android.watchlist.interceptors.AnalyticsInterceptor
import com.raywenderlich.android.watchlist.interceptors.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

object OkHttpProvider {

  // Timeout for the network requests
  private const val REQUEST_TIMEOUT = 3L

  private var okHttpClient: OkHttpClient? = null

  fun getOkHttpClient(context: Context): OkHttpClient {
    return if (okHttpClient == null) {
      val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
      loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      loggingInterceptor.redactHeader("x-amz-cf-id")

      val okHttpClient = OkHttpClient.Builder()
          .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
          .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
          .addInterceptor(ApiKeyInterceptor())
          .addInterceptor(AnalyticsInterceptor(context))
          .addInterceptor(loggingInterceptor)
          .build()
      OkHttpProvider.okHttpClient = okHttpClient
      okHttpClient
    } else {
      okHttpClient!!
    }
  }
}